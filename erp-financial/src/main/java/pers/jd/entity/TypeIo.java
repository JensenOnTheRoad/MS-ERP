package pers.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 收入支出类型
 * </p>
 *
 * @author jd
 * @since 2022-03-16
 */
@TableName("type_io")
@Data
@ApiModel(value = "TypeIo对象", description = "收入支出类型")
public class TypeIo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("支出类型名称")
    private String name;

}
