package pers.jd.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductionSubVO  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("所需物资id")
    private String materialId;

    @ApiModelProperty("使用物资的数量")
    private Integer quantify;

    @ApiModelProperty("生产主单id")
    private Integer productionMainId;



}
