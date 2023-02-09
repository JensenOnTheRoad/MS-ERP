package pers.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 采购主单
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@TableName("purchase_main")
@ApiModel(value = "PurchaseMain对象", description = "采购主单")
public class PurchaseMain implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("仓储id")
    private Integer warehouseId;

    @ApiModelProperty("采购状态：0未开始，1进行中，2已完成，3中止")
    private Integer status;

    @ApiModelProperty("跟进人")
    private Integer principalId;

    @ApiModelProperty("采购总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("采购时间")
    private LocalDateTime purchaseTime;

    @ApiModelProperty("采购入库时间")
    private LocalDateTime warehousingTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(Integer principalId) {
        this.principalId = principalId;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
    public LocalDateTime getWarehousingTime() {
        return warehousingTime;
    }

    public void setWarehousingTime(LocalDateTime warehousingTime) {
        this.warehousingTime = warehousingTime;
    }

    @Override
    public String toString() {
        return "PurchaseMain{" +
            "id=" + id +
            ", warehouseId=" + warehouseId +
            ", status=" + status +
            ", principalId=" + principalId +
            ", totalAmount=" + totalAmount +
            ", purchaseTime=" + purchaseTime +
            ", warehousingTime=" + warehousingTime +
        "}";
    }
}
