package io.renren.modules.pc.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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







}
