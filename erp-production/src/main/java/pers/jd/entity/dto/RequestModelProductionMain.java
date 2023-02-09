package pers.jd.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RequestModelProductionMain implements Serializable {

    private static final long serialVersionUID = 1L;
    public Integer principalId;
    public Integer commodityId;
    public Integer warehouseId;
    public Integer requiredMaterialId;
    public Integer number;
    public Integer productionQuantity;


}
