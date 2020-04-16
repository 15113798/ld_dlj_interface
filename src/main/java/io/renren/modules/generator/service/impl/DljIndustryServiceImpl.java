package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.DljIndustryDao;
import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.service.DljIndustryService;


@Service("dljIndustryService")
public class DljIndustryServiceImpl extends ServiceImpl<DljIndustryDao, DljIndustryEntity> implements DljIndustryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DljIndustryEntity> page = this.page(
                new Query<DljIndustryEntity>().getPage(params),
                new QueryWrapper<DljIndustryEntity>()
        );

        return new PageUtils(page);
    }

}