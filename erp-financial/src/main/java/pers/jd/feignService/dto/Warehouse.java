package pers.jd.feignService.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 仓库表
 * </p>
 *
 * @author jd
 * @since 2022-03-13
 */
@ApiModel(value = "Warehouse对象", description = "仓库表")
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("仓储编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("物品类型：0物资，1商品")
    private Integer type;

    @ApiModelProperty("物资名称")
    private String name;

    @ApiModelProperty("累计数量")
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
            "id=" + id +
            ", type=" + type +
            ", name=" + name +
            ", count=" + count +
        "}";
    }
}
