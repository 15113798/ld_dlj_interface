package io.renren.modules.pc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("indClassService")
public class IndClassService {

    @Autowired
    private DljIndustryDataService service;


    public BigDecimal getTotalData(List<DljIndustryDataEntity> list){
        BigDecimal totalPro = new BigDecimal(0);

        for (DljIndustryDataEntity entity:list) {
            String data = entity.getEleConMonth();
            BigDecimal bigData = new BigDecimal(data);
            totalPro = totalPro.add(bigData);
        }

        return totalPro;
    }



    public List<DljIndustryDataEntity> getDataListByIndName(String name,String startTime){
        QueryWrapper w = new QueryWrapper();
        w.eq("industry_name",name);
        w.like("record_time",startTime);
        if( null !=service.list(w)){
            return service.list(w);
        }
        return null;
    }
}
