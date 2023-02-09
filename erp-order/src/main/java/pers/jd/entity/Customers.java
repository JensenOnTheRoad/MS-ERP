package pers.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author jd
 * @since 2022-03-20
 */
@Data
@ApiModel(value = "Customers对象", description = "客户表")
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("租户号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("客户名称")
    private String name;

    @ApiModelProperty("客户所在公司的编号")
    private String companyId;

    @ApiModelProperty("客户电话")
    private String tel;

    @ApiModelProperty("客户备注")
    private String remark;


}
