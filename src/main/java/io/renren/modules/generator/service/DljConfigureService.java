package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.DljConfigureEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-22 16:53:55
 */
public interface DljConfigureService extends IService<DljConfigureEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

