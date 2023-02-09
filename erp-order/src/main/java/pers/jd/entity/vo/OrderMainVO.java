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

@Data
public class OrderMainVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单状态：0未进行，1进行中，2已完成，3订单作废，4退单")
    private Integer orderStatus;

    @ApiModelProperty("订单跟进负责人")
    private String principalId;

    @ApiModelProperty("用户编号")
    private String orderCustomerId;

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

}
