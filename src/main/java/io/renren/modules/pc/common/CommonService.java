package io.renren.modules.pc.common;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static io.renren.common.utils.ExcelUtil.ExcelReader.getDaysOfMonth;
import static io.renren.common.utils.ExcelUtil.ExcelReader.getDaysOfYear;

@Service
public class CommonService {




    /*
        计算本月行业产能利用率
        入参：用电量，装机量。
     */
    public static String calIndCapUtil(String yd, String zj, int yearOrMonth,String time) throws ParseException {
        int timeCount = 0;
        if(yearOrMonth == 1){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            int yearDay = getDaysOfYear(sdf.parse(time));
            timeCount = 24 * yearDay;
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            int monthDay = getDaysOfMonth(sdf.parse(time));
            timeCount = 24 * monthDay;
        }

        if(!zj.equals("0")){
            BigDecimal ydBig = new BigDecimal(yd);
            BigDecimal zjBig = new BigDecimal(zj);

            BigDecimal indBig = ydBig.multiply(new BigDecimal(10000)).divide(zjBig, 4, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(timeCount), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            DecimalFormat df1 = new DecimalFormat("0.00");
            String str = df1.format(indBig);
            return str + "%";
        }

        return "0.00%";
    }



}
