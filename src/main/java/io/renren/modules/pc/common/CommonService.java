package io.renren.modules.pc.common;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CommonService {




    /*
        计算本月行业产能利用率
        入参：用电量，装机量。
     */
    public String calIndCapUtil(String yd,String zj,int yearOrMonth){
        int timeCount = 0;
        if(yearOrMonth == 1){
            timeCount = 24*30;
        }else{
            timeCount = 24*30*365;
        }

        BigDecimal ydBig = new BigDecimal(yd);
        BigDecimal zjBig = new BigDecimal(zj);

        BigDecimal indBig = ydBig.multiply(new BigDecimal(10000)).divide(zjBig).divide(new BigDecimal(timeCount));
        return indBig.toString();
    }




}
