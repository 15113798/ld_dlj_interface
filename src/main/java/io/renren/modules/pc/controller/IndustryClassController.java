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
import io.renren.modules.pc.entity.EchartData;
import io.renren.modules.pc.entity.LastYearEleConEntity;
import io.renren.modules.pc.entity.Series;
import io.renren.modules.pc.service.IndClassService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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




    @RequestMapping("/getData")
    public R getData(@RequestParam Map<String, Object> params){
        String  industryId = String.valueOf(params.get("industryId"));

        //数据统一是以查上月为准
        String DATE_PATTERN = "yyyy-MM-dd";
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
        DljIndustryDataEntity dataEntity =  dataList.get(0);
        return R.ok().put("data", dataEntity);
    }



    @RequestMapping("/getDisData")
    public R getDisData(@RequestParam Map<String, Object> params){
        String  industryId = String.valueOf(params.get("industryId"));
        String  type = String.valueOf(params.get("type"));
        //通过行业id获取行业名称
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",industryId);
        List<DljIndustryEntity> list= industryService.list(wrapper);
        DljIndustryEntity indEntity = list.get(0);

        //通过行业去获取所有的list，然后获取到list拼接折线图的数据
        QueryWrapper dataWrapper = new QueryWrapper();
        dataWrapper.eq("industry_name",indEntity.getOverName());
        List<DljIndustryDataEntity> dataList = service.list(dataWrapper);
        if(dataList != null){
            String typeName = "";

            if(type.equals("1")){
                typeName = "用户量";
            }else if(type.equals("2")){
                typeName = "装机容量";
            }else if(type.equals("3")){
                typeName = "电量";
            }else{
                typeName = "产能利用率";
            }

            //分组
            List<String> legend = new ArrayList<String>();
            legend.add(typeName);

            List<String> category= new ArrayList<>();
            List<String> dataSerList = new ArrayList<>();
            for (DljIndustryDataEntity entity : dataList) {
                category.add(entity.getRecordTime());
                String dataStr = "";
                if(type.equals("1")){
                    dataStr = entity.getUserNum();
                }else if(type.equals("2")){
                    dataStr = entity.getInstalledCapacity();
                }else if(type.equals("3")){
                    dataStr = entity.getEleConMonth();
                }else{
                    dataStr = entity.getIndustryCapUtil();
                }
                dataSerList.add(dataStr);
            }

            List<Series> series = new ArrayList<>();//纵坐标
            series.add(new Series(typeName, "line",dataSerList));

            EchartData data=new EchartData(legend, category, series);

            return R.ok().put("data",data);
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
            BigDecimal totalResidentPro = indClassService.getTotalData(zdlList4);


            //测试总和是否和总量一致 测试通过
            //BigDecimal testTotal = totalOnePro.add(totalTwoPro).add(totalThreePro).add(totalResidentPro);

            //得到占比
            BigDecimal oneProduction = totalOnePro.divide(totalPro,4, BigDecimal.ROUND_HALF_UP);
            BigDecimal twoProduction = totalTwoPro.divide(totalPro,4, BigDecimal.ROUND_HALF_UP);
            BigDecimal threeProduction = totalThreePro.divide(totalPro,4, BigDecimal.ROUND_HALF_UP);
            BigDecimal residentProduction = new BigDecimal(1).subtract(oneProduction).subtract(twoProduction).subtract(threeProduction);


            entity.setOneProduction(oneProduction);
            entity.setTwoProduction(twoProduction);
            entity.setThreeProduction(threeProduction);
            entity.setResidentProduction(residentProduction);

            return R.ok().put("data", entity);
        }

        return R.ok().put("data", null);
    }


}
