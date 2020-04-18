package io.renren.modules.pc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.service.DljIndustryService;
import io.renren.modules.pc.entity.InvSugEntity;
import io.renren.modules.pc.util.MenuTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("BigUserIndExpanCon")
public class BigUserIndExpanController {

    @Autowired
    private DljIndustryService industryService;
    @Autowired
    private MenuTreeUtil menuTreeUtil;


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
        InvSugEntity entity = new InvSugEntity();
        entity.setGrade("9");
        entity.setIndCapUtil("60%");
        entity.setIndCapUtilScore("60");
        entity.setGrowthEleCon("10%");
        entity.setGrowthEleConScore("100");
        entity.setComScore("80");
        return R.ok().put("data", entity);
    }












}
