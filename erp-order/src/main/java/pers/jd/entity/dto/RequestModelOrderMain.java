package pers.jd.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RequestModelOrderMain implements Serializable {


    private static final long serialVersionUID = 1L;
    public Integer principalId;
    public Integer customerId;
    public Integer commodityId;
    public Integer number;
    public Integer deliveryType;
    public String remark;

}