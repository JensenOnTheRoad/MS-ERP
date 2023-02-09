package pers.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 生产主单
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@Data
@TableName("production_main")
@ApiModel(value = "ProductionMain对象", description = "生产主单")
public class ProductionMain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("生产的商品id")
    private Integer commodityId;

    @ApiModelProperty("生产的商品数量")
    private Integer quantity;

    @ApiModelProperty("仓储id")
    private Integer warehouseId;

    @ApiModelProperty("生产状态：0未开始，1进行中，2已完成，3中止")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime creationTime;

    @ApiModelProperty("完成时间")
    private LocalDateTime finishTime;

    @ApiModelProperty("花费时间（天）")
    private Integer spentDays;

    @ApiModelProperty("跟进人")
    private Integer principalId;

}
