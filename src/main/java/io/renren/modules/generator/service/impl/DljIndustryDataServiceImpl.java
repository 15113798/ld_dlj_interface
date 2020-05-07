package io.renren.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private DljIndustryDataDao dao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DljIndustryDataEntity> page = this.page(
                new Query<DljIndustryDataEntity>().getPage(params),
                new QueryWrapper<DljIndustryDataEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<DljIndustryDataEntity> queryListOrderByLyl(String time) {
        return dao.queryListOrderByLyl(time);
    }

}