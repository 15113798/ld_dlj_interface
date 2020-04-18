package io.renren.modules.pc.entity;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class LastYearEleConEntity {

    //一产
    private BigDecimal oneProduction;
    //二产
    private BigDecimal twoProduction;
    //三产
    private BigDecimal threeProduction;
    //居民
    private BigDecimal ResidentProduction;
}
