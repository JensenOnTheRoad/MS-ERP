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
 * 主订单
 * </p>
 *
 * @author jd
 * @since 2022-03-20
 */
@TableName("order_main")
@ApiModel(value = "OrderMain对象", description = "主订单")
public class OrderMain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单状态：0未进行，1进行中，2已完成，3订单作废，4退单")
    private Integer orderStatus;

    @ApiModelProperty("订单跟进负责人")
    private Integer principalId;

    @ApiModelProperty("用户编号")
    private Integer orderCustomerId;

    @ApiModelProperty("最终金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty("支付状态")
    private Integer payStatus;

    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty("配送方式")
    private Integer deliveryType;

    @ApiModelProperty("订单完成时间")
    private LocalDateTime finishTime;

    @ApiModelProperty("订单创建日期")
    private LocalDateTime createTime;

    @ApiModelProperty("订单备注")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    public Integer getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(Integer principalId) {
        this.principalId = principalId;
    }
    public Integer getOrderCustomerId() {
        return orderCustomerId;
    }

    public void setOrderCustomerId(Integer orderCustomerId) {
        this.orderCustomerId = orderCustomerId;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }
    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OrderMain{" +
            "id=" + id +
            ", orderStatus=" + orderStatus +
            ", principalId=" + principalId +
            ", orderCustomerId=" + orderCustomerId +
            ", totalAmount=" + totalAmount +
            ", payAmount=" + payAmount +
            ", payStatus=" + payStatus +
            ", payTime=" + payTime +
            ", deliveryType=" + deliveryType +
            ", finishTime=" + finishTime +
            ", createTime=" + createTime +
            ", remark=" + remark +
        "}";
    }
}
