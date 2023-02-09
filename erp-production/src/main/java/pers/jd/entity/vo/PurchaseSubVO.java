package pers.jd.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 采购子单
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@Data
public class PurchaseSubVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("物资id")
    private String materialId;

    @ApiModelProperty("采购物资的数量")
    private Integer quantify;

    @ApiModelProperty("采购金额")
    private BigDecimal costAmount;

    @ApiModelProperty("采购主单id")
    private Integer purchaseMainId;


}
