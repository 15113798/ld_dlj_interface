package io.renren.modules.pc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import io.renren.modules.generator.service.DljIndustryService;
import io.renren.modules.pc.entity.EchartData;
import io.renren.modules.pc.entity.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("indClassService")
public class IndClassService {

    @Autowired
    private DljIndustryDataService service;
    @Autowired
    private DljIndustryService industryService;


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



    public EchartData getDisDataByMonth(Map<String, Object> params){
        String  industryId = String.valueOf(params.get("industryId"));
        String  type = String.valueOf(params.get("type"));


        //通过行业id获取行业名称
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",industryId);
        List<DljIndustryEntity> list= industryService.list(wrapper);
        DljIndustryEntity indEntity = list.get(0);

        //通过行业去获取所有的list，然后获取到list拼接折线图的数据
        QueryWrapper dataWrapper = new QueryWrapper();
        dataWrapper.eq("industry_name",indEntity.getOverName());
        List<DljIndustryDataEntity> dataList = service.list(dataWrapper);
        if(dataList != null){
            String typeName = "";

            if(type.equals("1")){
                typeName = "用户量";
            }else if(type.equals("2")){
                typeName = "装机容量";
            }else if(type.equals("3")){
                typeName = "电量";
            }else{
                typeName = "产能利用率";
            }

            //分组
            List<String> legend = new ArrayList<String>();
            legend.add(typeName);

            List<String> category= new ArrayList<>();
            List<String> dataSerList = new ArrayList<>();
            for (DljIndustryDataEntity entity : dataList) {
                category.add(entity.getRecordTime());
                String dataStr = "";
                if(type.equals("1")){
                    dataStr = entity.getUserNum();
                }else if(type.equals("2")){
                    dataStr = entity.getInstalledCapacity();
                }else if(type.equals("3")){
                    dataStr = entity.getEleConMonth();
                }else{
                    dataStr = entity.getIndustryCapUtil();
                }
                dataSerList.add(dataStr);
            }

            List<Series> series = new ArrayList<>();//纵坐标
            series.add(new Series(typeName, "line",dataSerList));

            EchartData data=new EchartData(legend, category, series);
            return data;
        }

        return null;
    }



    public EchartData getDisDataByYear(Map<String, Object> params){
        String  industryId = String.valueOf(params.get("industryId"));
        String  type = String.valueOf(params.get("type"));

        //通过行业id获取行业名称
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",industryId);
        List<DljIndustryEntity> list= industryService.list(wrapper);
        DljIndustryEntity indEntity = list.get(0);

        //通过行业去获取所有的list，然后获取到list拼接折线图的数据
        QueryWrapper dataWrapper = new QueryWrapper();
        dataWrapper.eq("industry_name",indEntity.getOverName());
        List<DljIndustryDataEntity> dataList = service.list(dataWrapper);
        if(dataList != null){
            String typeName = "";

            if(type.equals("1")){
                typeName = "用户量";
            }else if(type.equals("2")){
                typeName = "装机容量";
            }else if(type.equals("3")){
                typeName = "电量";
            }else{
                typeName = "产能利用率";
            }

            //分组
            List<String> legend = new ArrayList<String>();
            legend.add(typeName);

            List<String> category= new ArrayList<>();
            List<String> dataSerList = new ArrayList<>();
            for (DljIndustryDataEntity entity : dataList) {
                category.add(entity.getRecordTime());
                String dataStr = "";
                if(type.equals("1")){
                    dataStr = entity.getUserNum();
                }else if(type.equals("2")){
                    dataStr = entity.getInstalledCapacity();
                }else if(type.equals("3")){
                    dataStr = entity.getEleConMonth();
                }else{
                    dataStr = entity.getIndustryCapUtil();
                }
                dataSerList.add(dataStr);
            }

            List<Series> series = new ArrayList<>();//纵坐标
            series.add(new Series(typeName, "line",dataSerList));

            EchartData data=new EchartData(legend, category, series);
            return data;
        }

        return null;
    }
}
