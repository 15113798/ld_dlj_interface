package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.DljIndustryDataEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 行业数据表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 15:18:41
 */
@Mapper
public interface DljIndustryDataDao extends BaseMapper<DljIndustryDataEntity> {

    List<DljIndustryDataEntity>queryListOrderByLyl(String time);
	
}
