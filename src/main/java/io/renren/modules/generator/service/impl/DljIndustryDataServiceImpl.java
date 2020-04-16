package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.DljIndustryDataDao;
import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.service.DljIndustryDataService;


@Service("dljIndustryDataService")
public class DljIndustryDataServiceImpl extends ServiceImpl<DljIndustryDataDao, DljIndustryDataEntity> implements DljIndustryDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DljIndustryDataEntity> page = this.page(
                new Query<DljIndustryDataEntity>().getPage(params),
                new QueryWrapper<DljIndustryDataEntity>()
        );

        return new PageUtils(page);
    }

}