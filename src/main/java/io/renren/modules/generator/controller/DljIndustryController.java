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

import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.service.DljIndustryService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 行业关系表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 15:05:46
 */
@RestController
@RequestMapping("generator/dljindustry")
public class DljIndustryController {
    @Autowired
    private DljIndustryService dljIndustryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:dljindustry:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dljIndustryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:dljindustry:info")
    public R info(@PathVariable("id") Integer id){
		DljIndustryEntity dljIndustry = dljIndustryService.getById(id);

        return R.ok().put("dljIndustry", dljIndustry);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:dljindustry:save")
    public R save(@RequestBody DljIndustryEntity dljIndustry){
		dljIndustryService.save(dljIndustry);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:dljindustry:update")
    public R update(@RequestBody DljIndustryEntity dljIndustry){
		dljIndustryService.updateById(dljIndustry);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:dljindustry:delete")
    public R delete(@RequestBody Integer[] ids){
		dljIndustryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
