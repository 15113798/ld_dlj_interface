package io.renren.modules.pc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import io.renren.modules.generator.service.DljIndustryService;
import io.renren.modules.pc.common.CommonService;
import io.renren.modules.pc.entity.InvSugEntity;
import io.renren.modules.pc.util.MenuTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("BigUserIndExpanCon")
public class BigUserIndExpanController {

    @Autowired
    private DljIndustryService industryService;
    @Autowired
    private DljIndustryDataService service;
    @Autowired
    private MenuTreeUtil menuTreeUtil;
    @Autowired
    private CommonService commonService;


    //获取行业数
    @RequestMapping("/getIndList")
    public R getIndList(@RequestParam Map<String, Object> params){
        return R.ok().put("data", null);
    }

    @RequestMapping("/getData")
    public R getData(@RequestParam Map<String, Object> params){
        return R.ok().put("data", null);
    }

    @RequestMapping("/getDisData")
    public R getDisData(@RequestParam Map<String, Object> params){

        return R.ok().put("data", null);
    }


    @RequestMapping("/getInvSug")
    public R getInvSug(@RequestParam Map<String, Object> params){
        String industryId = String.valueOf(params.get("industryId"));
        String timeType = String.valueOf(params.get("timeType"));
        InvSugEntity sugEntity = new InvSugEntity();

        //1年 2月
        //通过行业去查询利用率
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",industryId);
        List<DljIndustryEntity> list= industryService.list(wrapper);
        DljIndustryEntity dljEntity = list.get(0);

        String voerName = dljEntity.getOverName();
        QueryWrapper qw = new QueryWrapper();
        qw.eq("industry_name",voerName);

        //按年查的获取上一年一年的建议。如果是按月的话，获取上个月的建议
        if(timeType.equals("1")){
            String time = DateUtils.getLastYear();
            QueryWrapper dataWrapper = new QueryWrapper();
            dataWrapper.like("record_time",time);
            dataWrapper.eq("industry_name",voerName);
            List<DljIndustryDataEntity> dataList = service.list(dataWrapper);
            //遍历一下需要的基础数据
            BigDecimal totalUserNum = new BigDecimal(0);
            BigDecimal totalInstalledCapacity = new BigDecimal(0);
            BigDecimal totalEleConMonth = new BigDecimal(0);
            BigDecimal totalIndustryCapUtil = new BigDecimal(0);

            for (DljIndustryDataEntity entity :dataList) {
                String userNum = entity.getUserNum();
                String installedCap = entity.getInstalledCapacity();
                String eleConMonth = entity.getEleConMonth();
                String induCapUtil = entity.getIndustryCapUtil();
                totalUserNum = totalUserNum.add(new BigDecimal(userNum));
                totalInstalledCapacity = totalInstalledCapacity.add(new BigDecimal(installedCap));
                totalEleConMonth = totalEleConMonth.add(new BigDecimal(eleConMonth));
                totalIndustryCapUtil = totalIndustryCapUtil.add(new BigDecimal(induCapUtil.split("%")[0]));
            }

            String indCapUtil = commonService.calIndCapUtil(totalEleConMonth.toString(),totalInstalledCapacity.toString(),1);
            String lylDf = getLylDf(indCapUtil.split("%")[0]);
            //用电增长率

            String twoTime = DateUtils.getLastTwoYear();
            QueryWrapper dataWrapper1 = new QueryWrapper();
            dataWrapper1.like("record_time",twoTime);
            dataWrapper1.eq("industry_name",voerName);
            List<DljIndustryDataEntity> dataList1 = service.list(dataWrapper1);
            //遍历一下需要的基础数据
            BigDecimal totalUserNum1 = new BigDecimal(0);
            BigDecimal totalInstalledCapacity1 = new BigDecimal(0);
            BigDecimal totalEleConMonth1 = new BigDecimal(0);
            BigDecimal totalIndustryCapUtil1 = new BigDecimal(0);

            for (DljIndustryDataEntity entity1 :dataList1) {
                String userNum = entity1.getUserNum();
                String installedCap = entity1.getInstalledCapacity();
                String eleConMonth = entity1.getEleConMonth();
                String induCapUtil = entity1.getIndustryCapUtil();
                totalUserNum1 = totalUserNum1.add(new BigDecimal(userNum));
                totalInstalledCapacity1 = totalInstalledCapacity1.add(new BigDecimal(installedCap));
                totalEleConMonth1 = totalEleConMonth1.add(new BigDecimal(eleConMonth));
                totalIndustryCapUtil1 = totalIndustryCapUtil1.add(new BigDecimal(induCapUtil.split("%")[0]));
            }

            //增长率A
            String Azzl = getAzzl(totalEleConMonth1.toString(),totalEleConMonth.toString());
            String Adf = getAdf(totalEleConMonth1.toString(),totalEleConMonth.toString());
            String zhdf = getZhpf(lylDf,Adf);

            sugEntity.setGrade("9");
            sugEntity.setIndCapUtil(indCapUtil);
            sugEntity.setIndCapUtilScore(lylDf);
            sugEntity.setGrowthEleCon(Azzl);
            sugEntity.setGrowthEleConScore(Adf);
            sugEntity.setComScore(zhdf);
        }else{
            String monthTime = DateUtils.getLastMonth();
            String monthTwoTime = DateUtils.getLastTwoMonth();

            QueryWrapper wpMonth = new QueryWrapper();
            wpMonth.eq("industry_name",voerName);
            wpMonth.eq("record_time",monthTime);
            List<DljIndustryDataEntity> dljList = service.list(wpMonth);
            DljIndustryDataEntity dEntity = dljList.get(0);

            QueryWrapper wpMonth1 = new QueryWrapper();
            wpMonth1.eq("industry_name",voerName);
            wpMonth1.eq("record_time",monthTwoTime);
            List<DljIndustryDataEntity> dljList1 = service.list(wpMonth1);
            DljIndustryDataEntity dEntity1 = dljList1.get(0);

            String installedCap = dEntity.getInstalledCapacity();
            String eleConMonth = dEntity.getEleConMonth();
            String indCapUtil = commonService.calIndCapUtil(eleConMonth.toString(),installedCap.toString(),2);
            String lylDf = getLylDf(indCapUtil.split("%")[0]);



            //增长率A
            String Azzl = getAzzl(dEntity1.getEleConMonth().toString(),eleConMonth.toString());
            String Adf = getAdf(dEntity1.getEleConMonth().toString(),eleConMonth.toString());
            String zhdf = getZhpf(lylDf,Adf);

            sugEntity.setGrade("9");
            sugEntity.setIndCapUtil(indCapUtil);
            sugEntity.setIndCapUtilScore(lylDf);
            sugEntity.setGrowthEleCon(Azzl);
            sugEntity.setGrowthEleConScore(Adf);
            sugEntity.setComScore(zhdf);





        }

        return R.ok().put("data", sugEntity);
    }






    public String getLylDf(String lyl){
        String df = "";
        double a = Double.valueOf(lyl);
        if(a<=0.1){
            df = "20";
        }else if(a <= 0.2){
            df = "40";
        }else if(a<= 0.3){
            df = "60";
        }else if(a<= 0.4){
            df = "80";
        }else{
            df = "100";
        }
        return df;
    }


    public String getAzzl(String twoyearData,String yearData){
        BigDecimal bigData = new BigDecimal(yearData).subtract(new BigDecimal(twoyearData));
        return bigData.divide(new BigDecimal(twoyearData),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).stripTrailingZeros().toString()+"%";
    }

    public String getAdf(String twoyearData,String yearData){
        String df = "";
        BigDecimal bigData = new BigDecimal(yearData).subtract(new BigDecimal(twoyearData));
        if(bigData.compareTo(new BigDecimal(0)) == -1){
            System.out.println("今年比去年小，0分");
            df = "0";
        }else{
            df ="100";
        }
        return df;
    }


    public String getZhpf(String lylDf,String Adf){
        String df = "";
        BigDecimal zhpf = new BigDecimal(lylDf).multiply(new BigDecimal(0.5)).add(new BigDecimal(Adf).multiply(new BigDecimal(0.5)));
        return zhpf.stripTrailingZeros().toPlainString();
    }





}
