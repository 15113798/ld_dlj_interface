package io.renren.modules.pc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.entity.DljIndustryMenuEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import io.renren.modules.generator.service.DljIndustryMenuService;
import io.renren.modules.generator.service.DljIndustryService;
import io.renren.modules.pc.common.CommonService;
import io.renren.modules.pc.entity.EchartData;
import io.renren.modules.pc.entity.LastYearEleConEntity;
import io.renren.modules.pc.entity.Series;
import io.renren.modules.pc.service.IndClassService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("industryClass")
public class IndustryClassController {
    @Autowired
    private DljIndustryDataService service;
    @Autowired
    private DljIndustryService industryService;
    @Autowired
    private IndClassService indClassService;
    @Autowired
    private CommonService commonService;




    @RequestMapping("/getData")
    public R getData(@RequestParam Map<String, Object> params) throws ParseException {
        String  industryId = String.valueOf(params.get("industryId"));
        String  timeType = String.valueOf(params.get("timeType"));

        DljIndustryDataEntity dataEntity = new DljIndustryDataEntity();
        if(timeType.equals("1")){
            //按年的话就是查询该行业上一年的数据
            //通过行业id获取行业名称
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("id",industryId);
            List<DljIndustryEntity> list= industryService.list(wrapper);
            DljIndustryEntity indEntity = list.get(0);

            //通过行业和时间去获取该年数据的集合
            String time = DateUtils.getLastYear();

            QueryWrapper dataWrapper = new QueryWrapper();
            dataWrapper.like("record_time",time);
            dataWrapper.eq("industry_name",indEntity.getOverName());
            List<DljIndustryDataEntity> dataList = service.list(dataWrapper);
            if(dataList == null || dataList.size() ==0){
                return R.ok().put("data", null);
            }

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

            dataEntity.setUserNum(totalUserNum.toString());
            dataEntity.setInstalledCapacity(totalInstalledCapacity.toString());
            dataEntity.setEleConMonth(totalEleConMonth.toString());
            dataEntity.setIndustryCapUtil(commonService.calIndCapUtil(totalEleConMonth.toString(),totalInstalledCapacity.toString(),1,time));
        }else{
            //按月的话就是查询指定月的数据
            //即上个月的数据
            String lastMonth = DateUtils.getLastMonth();

            //通过行业id获取行业名称
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("id",industryId);
            List<DljIndustryEntity> list= industryService.list(wrapper);
            DljIndustryEntity indEntity = list.get(0);

            //通过行业和时间去获取准确的一条消息
            QueryWrapper dataWrapper = new QueryWrapper();
            dataWrapper.eq("record_time",lastMonth);
            dataWrapper.eq("industry_name",indEntity.getOverName());
            List<DljIndustryDataEntity> dataList = service.list(dataWrapper);
            if(dataList != null && dataList.size() != 0){
                dataEntity =  dataList.get(0);
            }else{
                dataEntity.setIndustryCapUtil("0.00%");
                dataEntity.setUserChainRatio("0.00%");
                dataEntity.setUserNum("0");
                dataEntity.setEleConMonth("0");
                dataEntity.setInstalledCapacity("0");
                dataEntity.setUserYearToYear("0.00");
            }
        }


        return R.ok().put("data", dataEntity);
    }



    @RequestMapping("/getDisData")
    public R getDisData(@RequestParam Map<String, Object> params){
        String  timeType = String.valueOf(params.get("timeType"));
        if("1".equals(timeType)){
            return R.ok().put("data",indClassService.getDisDataByYear(params));
        }else if("2".equals(timeType)){
            return R.ok().put("data",indClassService.getDisDataByMonth(params));
        }

        return R.ok().put("data", null);
    }





    @RequestMapping("/getLastYearEleCon")
    public R getLastYearEleCon(@RequestParam Map<String, Object> params){
        //获取开始时间和结束时间
        String startTime = String.valueOf(params.get("startTime"));
        String endTime = String.valueOf(params.get("endTime"));

        //全社会总用电量，第一产业，第二产业，第三产业，城乡居民生活用电合计（居民用电合计）
        List<DljIndustryDataEntity>zdlList = indClassService.getDataListByIndName("全社会用电总计",startTime);

        List<DljIndustryDataEntity>zdlList1 = indClassService.getDataListByIndName("第一产业",startTime);

        List<DljIndustryDataEntity>zdlList2 = indClassService.getDataListByIndName("第二产业",startTime);

        List<DljIndustryDataEntity>zdlList3 = indClassService.getDataListByIndName("第三产业",startTime);

        List<DljIndustryDataEntity>zdlList4 = indClassService.getDataListByIndName("B、城乡居民生活用电合计",startTime);

        LastYearEleConEntity entity = new LastYearEleConEntity();
        if(zdlList != null){
            //获取全年一产用电量，二产用电量，三产用电量和居民用电。然后把所有数据加起来算总电量，再算占比
            BigDecimal totalPro = indClassService.getTotalData(zdlList);
            BigDecimal totalOnePro = indClassService.getTotalData(zdlList1);
            BigDecimal totalTwoPro = indClassService.getTotalData(zdlList2);
            BigDecimal totalThreePro = indClassService.getTotalData(zdlList3);

            //测试总和是否和总量一致 测试通过
            //BigDecimal testTotal = totalOnePro.add(totalTwoPro).add(totalThreePro).add(totalResidentPro);

            //得到占比
            BigDecimal oneProduction = totalOnePro.divide(totalPro,3, BigDecimal.ROUND_HALF_UP);
            if(oneProduction.compareTo(new BigDecimal(0)) == 1 && oneProduction.compareTo(new BigDecimal(0.001)) == -1){
                oneProduction = new BigDecimal(0.001);
            }
            BigDecimal twoProduction = totalTwoPro.divide(totalPro,3, BigDecimal.ROUND_HALF_UP);
           if(twoProduction.compareTo(new BigDecimal(0)) == 1 && twoProduction.compareTo(new BigDecimal(0.001)) == -1){
                twoProduction = new BigDecimal(0.001);
            }
            BigDecimal threeProduction = totalThreePro.divide(totalPro,3, BigDecimal.ROUND_HALF_UP);
            if(threeProduction.compareTo(new BigDecimal(0)) == 1 && threeProduction.compareTo(new BigDecimal(0.001)) == -1){
                threeProduction = new BigDecimal(0.001);
            }
            BigDecimal residentProduction = new BigDecimal(1).subtract(oneProduction).subtract(twoProduction).subtract(threeProduction);

            entity.setOneProduction(oneProduction);
            entity.setTwoProduction(twoProduction);
            entity.setThreeProduction(threeProduction);
            entity.setResidentProduction(residentProduction);

            return R.ok().put("data", entity);
        }
        return R.ok().put("data", null);
    }



    @RequestMapping("/getMonthCapUtil")
    public R getMonthCapUtil(@RequestParam Map<String, Object> params){
        //数据统一是以查上月为准
        String month = DateUtils.getLastMonth();
        /*//通过行业id获取行业名称
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("record_time",month);
        wrapper.orderByDesc("industry_cap_util");*/

        List<DljIndustryDataEntity> list= service.queryListOrderByLyl(month);
        List<DljIndustryDataEntity> dataList = new ArrayList<>();

        int count = 0 ;
        for (int i = 0; i < list.size(); i++) {
            if(count == 5){
                break;
            }
            DljIndustryDataEntity entity = list.get(i);
            QueryWrapper wp = new QueryWrapper();
            wp.eq("over_name",entity.getIndustryName());
            wp.eq("is_ind",1);

            List<DljIndustryEntity>indList=industryService.list(wp);
            if(indList != null && indList.size() !=0 ){
                DljIndustryEntity industryEntity = indList.get(0);
                String indName = industryEntity.getName();
                entity.setIndustryName(indName);
                dataList.add(entity);
                count++;
            }
        }
        return R.ok().put("data", dataList);
    }





}
