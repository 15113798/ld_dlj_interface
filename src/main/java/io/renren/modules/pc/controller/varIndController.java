package io.renren.modules.pc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.service.DljIndustryService;
import io.renren.modules.pc.util.MenuTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("varIndCon")
public class varIndController {

    @Autowired
    private DljIndustryService industryService;
    @Autowired
    private MenuTreeUtil menuTreeUtil;


    @RequestMapping("/getIndList")
    public R getIndList(@RequestParam Map<String, Object> params){

        //行业树的pid为1000
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("pid",1000);
        List<DljIndustryEntity> list = industryService.list(wrapper);
        DljIndustryEntity entity = list.get(0);

        QueryWrapper dw = new QueryWrapper();
        dw.eq("is_ind",1);
        List<DljIndustryEntity> dList = industryService.list(dw);

        List<Object> menuList = menuTreeUtil.menuList(dList);


        return R.ok().put("data", menuList);
    }



    @RequestMapping("/getData")
    public R getData(@RequestParam Map<String, Object> params){

        return R.ok().put("data", null);
    }


    @RequestMapping("/getDisData")
    public R getDisData(@RequestParam Map<String, Object> params){

        return R.ok().put("data", null);
    }








}
