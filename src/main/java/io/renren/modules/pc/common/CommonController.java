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
            service.saveBatch(list);
            return R.ok();
        }
    }








}
