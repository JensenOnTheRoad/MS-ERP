package pers.jd.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RequestModelPurchaseMain implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer id;
    public Integer number;
    public Integer principalId;
    public Integer warehouseId;
}
