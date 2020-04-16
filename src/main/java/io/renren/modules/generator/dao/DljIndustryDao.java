package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.DljIndustryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 行业关系表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 00:43:54
 */
@Mapper
public interface DljIndustryDao extends BaseMapper<DljIndustryEntity> {
	
}
