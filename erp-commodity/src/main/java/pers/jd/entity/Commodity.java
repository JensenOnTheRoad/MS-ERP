package pers.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author jd
 * @since 2022-03-16
 */
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Commodity{" +
            "id=" + id +
            ", name=" + name +
            ", specification=" + specification +
            ", price=" + price +
        "}";
    }
}
