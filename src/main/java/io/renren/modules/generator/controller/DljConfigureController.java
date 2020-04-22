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

import io.renren.modules.generator.entity.DljConfigureEntity;
import io.renren.modules.generator.service.DljConfigureService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-22 16:53:55
 */
@RestController
@RequestMapping("generator/dljconfigure")
public class DljConfigureController {
    @Autowired
    private DljConfigureService dljConfigureService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:dljconfigure:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dljConfigureService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:dljconfigure:info")
    public R info(@PathVariable("id") Integer id){
		DljConfigureEntity dljConfigure = dljConfigureService.getById(id);

        return R.ok().put("dljConfigure", dljConfigure);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:dljconfigure:save")
    public R save(@RequestBody DljConfigureEntity dljConfigure){
		dljConfigureService.save(dljConfigure);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:dljconfigure:update")
    public R update(@RequestBody DljConfigureEntity dljConfigure){
		dljConfigureService.updateById(dljConfigure);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:dljconfigure:delete")
    public R delete(@RequestBody Integer[] ids){
		dljConfigureService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
