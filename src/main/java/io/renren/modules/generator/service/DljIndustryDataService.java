package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.DljIndustryDataEntity;

import java.util.Map;

/**
 * 行业数据表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 15:18:41
 */
public interface DljIndustryDataService extends IService<DljIndustryDataEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

