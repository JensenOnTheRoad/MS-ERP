package pers.jd.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderSubVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("主订单编号")
    private Integer orderMainId;

    @ApiModelProperty("商品编号")
    private String commodityId;

    @ApiModelProperty("数量")
    private Integer quantify;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("总价")
    private BigDecimal totalAmount;

}
