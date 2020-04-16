package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.DljIndustryMenuEntity;

import java.util.Map;

/**
 * 菜单表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 00:43:54
 */
public interface DljIndustryMenuService extends IService<DljIndustryMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

