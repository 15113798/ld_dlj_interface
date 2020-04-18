package io.renren.modules.pc.common;

import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class CommonService {

    @Autowired
    private DljIndustryDataService service;



    //入库到行业数据表中
    public void  insertData(DljIndustryDataEntity entity){
        service.save(entity);
    }


    /*
        计算本月行业产能利用率
        入参：用电量，装机量。
     */
    public String calIndCapUtil(String yd,String zj){
        BigDecimal ydBig = new BigDecimal(yd);
        BigDecimal zjBig = new BigDecimal(zj);

        BigDecimal indBig = ydBig.multiply(new BigDecimal(10000)).divide(zjBig).divide(new BigDecimal(24*30));
        return indBig.toString();
    }




}
