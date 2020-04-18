package io.renren.modules.pc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import io.renren.modules.generator.service.DljIndustryService;
import io.renren.modules.pc.entity.EchartData;
import io.renren.modules.pc.entity.LastYearEleConEntity;
import io.renren.modules.pc.entity.Series;
import io.renren.modules.pc.service.IndClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("highCon")
public class HighConController {
    @Autowired
    private DljIndustryDataService service;
    @Autowired
    private DljIndustryService industryService;
    @Autowired
    private IndClassService indClassService;




    @RequestMapping("/getData")
    public R getData(@RequestParam Map<String, Object> params){

        return R.ok().put("data", null);
    }



    @RequestMapping("/getDisData")
    public R getDisData(@RequestParam Map<String, Object> params){

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



    @RequestMapping("/getMonthCapUtil")
    public R getMonthCapUtil(@RequestParam Map<String, Object> params){
        //数据统一是以查上月为准
        String month = DateUtils.getLastMonth();
        //通过行业id获取行业名称
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("record_time",month);
        wrapper.orderByDesc("industry_cap_util");

        List<DljIndustryDataEntity> list= service.list(wrapper);
        List<DljIndustryDataEntity> dataList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DljIndustryDataEntity entity = list.get(i);
            dataList.add(entity);
        }
        return R.ok().put("data", dataList);
    }





}
