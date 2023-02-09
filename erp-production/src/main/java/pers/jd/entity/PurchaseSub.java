package pers.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 采购子单
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@TableName("purchase_sub")
@ApiModel(value = "PurchaseSub对象", description = "采购子单")
public class PurchaseSub implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("物资id")
    private Integer materialId;

    @ApiModelProperty("采购物资的数量")
    private Integer quantify;

    @ApiModelProperty("采购金额")
    private BigDecimal costAmount;

    @ApiModelProperty("采购主单id")
    private Integer purchaseMainId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getQuantify() {
        return quantify;
    }

    public void setQuantify(Integer quantify) {
        this.quantify = quantify;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public Integer getPurchaseMainId() {
        return purchaseMainId;
    }

    public void setPurchaseMainId(Integer purchaseMainId) {
        this.purchaseMainId = purchaseMainId;
    }


    @Override
    public String toString() {
        return "PurchaseSub{" +
                "id=" + id +
                ", materialId=" + materialId +
                ", quantify=" + quantify +
                ", costAmount=" + costAmount +
                ", purchaseMainId=" + purchaseMainId +
                "}";
    }
}
