package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.DljIndustryEntity;

import java.util.Map;

/**
 * 行业关系表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 00:43:54
 */
public interface DljIndustryService extends IService<DljIndustryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

