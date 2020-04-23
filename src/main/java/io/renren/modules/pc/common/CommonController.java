package io.renren.modules.pc.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.exception.RRException;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.ExcelUtil.ExcelReader;
import io.renren.common.utils.R;
import io.renren.modules.generator.entity.DljConfigureEntity;
import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.service.DljConfigureService;
import io.renren.modules.generator.service.DljIndustryDataService;
import io.renren.modules.generator.service.DljIndustryService;
import io.renren.modules.pc.entity.LastYearEleConEntity;
import io.renren.modules.pc.service.IndClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("common")
public class CommonController {


    @Autowired
    private DljIndustryDataService service;
    @Autowired
    private DljConfigureService configService;



    //获取动态ip和端口
    @RequestMapping("/getIpAndPort")
    public R getData(@RequestParam Map<String, Object> params){
        String url = String.valueOf(params.get("ipAndPort"));
        QueryWrapper wrapper = new QueryWrapper();
        List<DljConfigureEntity>list = configService.list(wrapper);
        DljConfigureEntity entity = list.get(0);
        String value = entity.getValue();
        return R.ok().put("data",value);
    }





    //上传文件
    @RequestMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file)throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        String type = file.getContentType();
        //originalFilename = UUID.randomUUID().toString()+originalFilename;
        System.out.println("目标文件名称："+originalFilename+",目标文件类型："+type);


        //获取到流和时间。调用工具类方法
        InputStream is = file.getInputStream();
        Map<String,Object>map = ExcelReader.readExcel((FileInputStream) is,originalFilename);
        if(null == map.get("list")){
            String errorMsg = (String) map.get("errorMsg");
            return R.error(errorMsg);
        }else{
            List<DljIndustryDataEntity>list = (List<DljIndustryDataEntity>) map.get("list");
            //拿到list，然后遍历list
            //判断数据否在库里已经存在了
            String recordTime = list.get(0).getRecordTime();
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("record_time",recordTime);
            DljIndustryDataEntity queryEntity  = service.getOne(wrapper);
            if(queryEntity != null){
                return R.error("该excel已存在，请确认数据时间");
            }
            for (DljIndustryDataEntity entity:list) {
                //给用户量环比和同比赋值
                String userCount = entity.getUserNum();
                String indName = entity.getIndustryName();
                String userChainRaotio = getUserChainRatio(indName,recordTime,userCount);
                String userYearToYear = getUserYearToYear(indName,recordTime,userCount);
                entity.setUserChainRatio(userChainRaotio);
                entity.setUserYearToYear(userYearToYear);
            }
            service.saveBatch(list);
            return R.ok();
        }
    }


    public String getUserChainRatio(String indName,String time,String userCount){
        //通过时间去获取当前数据的上一个月的数据
        String lastTime = DateUtils.getLastMonthByTime(time);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("record_time",lastTime);
        queryWrapper.eq("industry_name",indName);
        DljIndustryDataEntity entity= service.getOne(queryWrapper);
        if(null == entity){
            return "100%";
        }
        String lastTimeUserCount = entity.getUserNum();
       if("0".equals(lastTimeUserCount)){
           return "100%";
       }
        String userChainRatio = (new BigDecimal(userCount).subtract(new BigDecimal(lastTimeUserCount))).divide(new BigDecimal(lastTimeUserCount),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"%";
        return userChainRatio;
    }


    public String getUserYearToYear(String indName,String time,String userCount){

        //通过时间去获取当前数据的上一个月的数据
        String lastTime = DateUtils.getLastYearByTime(time)+"-"+time.split("-")[1];
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("record_time",lastTime);
        queryWrapper.eq("industry_name",indName);
        DljIndustryDataEntity entity= service.getOne(queryWrapper);
        if(null == entity){
            return "100%";
        }
        String lastTimeUserCount = entity.getUserNum();
        if("0".equals(lastTimeUserCount)){
            return "100%";
        }
        String userYearToYear = (new BigDecimal(userCount).subtract(new BigDecimal(lastTimeUserCount))).divide(new BigDecimal(lastTimeUserCount),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"%";
        return userYearToYear;
    }







}
