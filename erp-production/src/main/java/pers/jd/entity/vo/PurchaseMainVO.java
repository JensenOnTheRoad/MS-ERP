package pers.jd.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 采购主单
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@Data

public class PurchaseMainVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("仓储id")
    private String warehouseId;

    @ApiModelProperty("采购状态：0未开始，1进行中，2已完成，3中止")
    private Integer status;

    @ApiModelProperty("跟进人")
    private String principalId;

    @ApiModelProperty("采购总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("采购时间")
    private LocalDateTime purchaseTime;

    @ApiModelProperty("采购入库时间")
    private LocalDateTime warehousingTime;



}
