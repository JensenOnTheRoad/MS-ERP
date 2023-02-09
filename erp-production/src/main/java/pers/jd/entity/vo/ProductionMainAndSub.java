package pers.jd.entity.vo;

import lombok.Data;
import pers.jd.entity.ProductionMain;
import pers.jd.entity.ProductionSub;

import java.util.List;

@Data
public class ProductionMainAndSub {
    public ProductionMain productionMain;
    public List<ProductionSub> productionSubs;
}