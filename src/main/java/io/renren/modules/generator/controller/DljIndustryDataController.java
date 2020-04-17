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

import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 行业数据表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 15:18:41
 */
@RestController
@RequestMapping("generator/dljindustrydata")
public class DljIndustryDataController {
    @Autowired
    private DljIndustryDataService dljIndustryDataService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:dljindustrydata:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dljIndustryDataService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:dljindustrydata:info")
    public R info(@PathVariable("id") Integer id){
		DljIndustryDataEntity dljIndustryData = dljIndustryDataService.getById(id);

        return R.ok().put("dljIndustryData", dljIndustryData);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:dljindustrydata:save")
    public R save(@RequestBody DljIndustryDataEntity dljIndustryData){
		dljIndustryDataService.save(dljIndustryData);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:dljindustrydata:update")
    public R update(@RequestBody DljIndustryDataEntity dljIndustryData){
		dljIndustryDataService.updateById(dljIndustryData);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:dljindustrydata:delete")
    public R delete(@RequestBody Integer[] ids){
		dljIndustryDataService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
