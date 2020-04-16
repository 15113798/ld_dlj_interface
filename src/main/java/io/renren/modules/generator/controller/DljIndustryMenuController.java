package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.DljIndustryMenuEntity;
import io.renren.modules.generator.service.DljIndustryMenuService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 菜单表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 00:43:54
 */
@RestController
@RequestMapping("generator/dljindustrymenu")
public class DljIndustryMenuController {
    @Autowired
    private DljIndustryMenuService dljIndustryMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:dljindustrymenu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dljIndustryMenuService.queryPage(params);

        return R.ok().put("page", page);
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
