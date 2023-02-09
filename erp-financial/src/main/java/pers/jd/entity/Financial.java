package pers.jd.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 财务流水单
 * </p>
 *
 * @author jd
 * @since 2022-03-16
 */
@Data
@ApiModel(value = "Financial对象", description = "财务流水单")
public class Financial implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("流水类型")
    private Integer typeId;

    @ApiModelProperty("创建时间")
    private LocalDateTime creationTime;

    @ApiModelProperty("审核状态")
    private Integer auditStatus;

    @ApiModelProperty("审核人")
    private Integer auditorId;

    @ApiModelProperty("审核时间")
    private LocalDateTime auditTime;

    @ApiModelProperty("备注")
    private String remark;

}
