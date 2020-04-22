package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.DljConfigureDao;
import io.renren.modules.generator.entity.DljConfigureEntity;
import io.renren.modules.generator.service.DljConfigureService;


@Service("dljConfigureService")
public class DljConfigureServiceImpl extends ServiceImpl<DljConfigureDao, DljConfigureEntity> implements DljConfigureService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DljConfigureEntity> page = this.page(
                new Query<DljConfigureEntity>().getPage(params),
                new QueryWrapper<DljConfigureEntity>()
        );

        return new PageUtils(page);
    }

}