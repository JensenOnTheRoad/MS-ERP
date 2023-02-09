package pers.jd.feignService.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author jd
 * @since 2022-03-16
 */
@Data
@ApiModel(value = "Commodity对象", description = "商品表")
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品规格")
    private String specification;

    @ApiModelProperty("商品单价")
    private BigDecimal price;

}
