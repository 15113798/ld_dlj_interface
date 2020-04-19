package io.renren.modules.pc.util;

import io.renren.modules.generator.entity.DljIndustryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuTreeUtil {
    //已经被buildTree的list集合
    private List<DljIndustryEntity> menuCommon;


    public List<Object> menuList(List<DljIndustryEntity> menu){

        //返回给前端的NewTree List集合
        List<Object> list = new ArrayList<Object>();
        this.menuCommon = menu;

        // 通过遍历menu，找到父节点为0的节点，它是顶级父节点
        // 然后调用menuChild，递归遍历所有子节点
        for (DljIndustryEntity x : menu) {
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
            if(1000 == x.getPid()){
                mapArr.put("id", x.getId());
                mapArr.put("name", x.getName());
                mapArr.put("pid", x.getPid());

                //遍历开始
                mapArr.put("childList", menuChild(x.getId()));
                list.add(mapArr);
            }
        }
        return list;
    }

    private List<?> menuChild(Integer id){
        List<Object> lists = new ArrayList<Object>();
        //继续遍历menu
        for(DljIndustryEntity a:menuCommon){
            Map<String,Object> childArray = new LinkedHashMap<String, Object>();
            //找到父ID等于父节点ID的子节点
            if(a.getPid() == id){
                childArray.put("id", a.getId());
                childArray.put("name", a.getName());
                childArray.put("pid", a.getPid());
                //向下递归
                childArray.put("childList", menuChild(a.getId()));
                lists.add(childArray);
            }
        }
        return lists;
    }
}