package pers.jd.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductionMainVO {


    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("生产的商品id")
    private String commodityId;

    @ApiModelProperty("生产的商品数量")
    private Integer quantity;

    @ApiModelProperty("仓储id")
    private String warehouseId;

    @ApiModelProperty("生产状态：0未开始，1进行中，2已完成，3中止")
    private Integer status;


    @ApiModelProperty("创建时间")
    private LocalDateTime creationTime;

    @ApiModelProperty("完成时间")
    private LocalDateTime finishTime;

    @ApiModelProperty("花费时间（天）")
    private Integer spentDays;

    @ApiModelProperty("跟进人")
    private String principalId;


}
