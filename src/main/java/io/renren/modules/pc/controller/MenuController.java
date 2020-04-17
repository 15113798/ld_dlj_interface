package io.renren.modules.pc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.generator.entity.DljIndustryMenuEntity;
import io.renren.modules.generator.service.DljIndustryMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private DljIndustryMenuService dljIndustryMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("pid",params.get("pid"));
        List<DljIndustryMenuEntity> list = dljIndustryMenuService.list(wrapper);

        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:dljindustrymenu:info")
    public R info(@PathVariable("id") Integer id){
		DljIndustryMenuEntity dljIndustryMenu = dljIndustryMenuService.getById(id);

        return R.ok().put("dljIndustryMenu", dljIndustryMenu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:dljindustrymenu:save")
    public R save(@RequestBody DljIndustryMenuEntity dljIndustryMenu){
		dljIndustryMenuService.save(dljIndustryMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:dljindustrymenu:update")
    public R update(@RequestBody DljIndustryMenuEntity dljIndustryMenu){
		dljIndustryMenuService.updateById(dljIndustryMenu);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:dljindustrymenu:delete")
    public R delete(@RequestBody Integer[] ids){
		dljIndustryMenuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
