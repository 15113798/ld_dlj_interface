package io.renren.modules.pc.entity;


import lombok.Data;

@Data
public class InvSugEntity {

    //投资建议等级
    private String grade;
    //行业产能利用率
    private String indCapUtil;
    //行业产能利用率得分
    private String indCapUtilScore;
    //用电增长率
    private String growthEleCon;
    //用电增长率得分
    private String growthEleConScore;
    //综合得分
    private String comScore;




}
