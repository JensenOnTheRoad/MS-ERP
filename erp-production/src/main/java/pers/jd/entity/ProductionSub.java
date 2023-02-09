package pers.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 生产子单
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@TableName("production_sub")
@ApiModel(value = "ProductionSub对象", description = "生产子单")
public class ProductionSub implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("所需物资id")
    private Integer materialId;

    @ApiModelProperty("使用物资的数量")
    private Integer quantify;

    @ApiModelProperty("生产主单id")
    private Integer productionMainId;

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
    public Integer getProductionMainId() {
        return productionMainId;
    }

    public void setProductionMainId(Integer productionMainId) {
        this.productionMainId = productionMainId;
    }

    @Override
    public String toString() {
        return "ProductionSub{" +
            "id=" + id +
            ", materialId=" + materialId +
            ", quantify=" + quantify +
            ", productionMainId=" + productionMainId +
        "}";
    }
}
