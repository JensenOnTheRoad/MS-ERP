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
 * 子订单
 * </p>
 *
 * @author jd
 * @since 2022-03-20
 */
@TableName("order_sub")
@ApiModel(value = "OrderSub对象", description = "子订单")
public class OrderSub implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("主订单编号")
    private Integer orderMainId;

    @ApiModelProperty("商品编号")
    private Integer commodityId;

    @ApiModelProperty("数量")
    private Integer quantify;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("总价")
    private BigDecimal totalAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOrderMainId() {
        return orderMainId;
    }

    public void setOrderMainId(Integer orderMainId) {
        this.orderMainId = orderMainId;
    }
    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }
    public Integer getQuantify() {
        return quantify;
    }

    public void setQuantify(Integer quantify) {
        this.quantify = quantify;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderSub{" +
            "id=" + id +
            ", orderMainId=" + orderMainId +
            ", commodityId=" + commodityId +
            ", quantify=" + quantify +
            ", price=" + price +
            ", totalAmount=" + totalAmount +
        "}";
    }
}
