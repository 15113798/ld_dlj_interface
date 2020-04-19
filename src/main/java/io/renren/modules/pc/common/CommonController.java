package io.renren.modules.pc.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.ExcelUtil.ExcelReader;
import io.renren.common.utils.R;
import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import io.renren.modules.generator.service.DljIndustryService;
import io.renren.modules.pc.entity.LastYearEleConEntity;
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
@RequestMapping("common")
public class CommonController {


    @Autowired
    private DljIndustryDataService service;

    @RequestMapping("/savaDate")
    public R getData(@RequestParam Map<String, Object> params){
        String url = String.valueOf(params.get("url"));
        String time = String.valueOf(params.get("time"));
        List<DljIndustryDataEntity> list = ExcelReader.readExcel(url,time);
        service.saveBatch(list);
        return R.ok();
    }




}
