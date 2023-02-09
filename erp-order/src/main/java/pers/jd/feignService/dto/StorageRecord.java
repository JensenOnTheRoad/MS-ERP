package pers.jd.feignService.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 仓储记录表
 * </p>
 *
 * @author jd
 * @since 2022-03-13
 */
@TableName("storage_record")
@Data
@ApiModel(value = "StorageRecord对象", description = "仓储记录表")
public class StorageRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("仓储编号")
    private Integer storageId;

    @ApiModelProperty("仓储类型")
    private Integer storageType;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("出入库类型：0入库，1出库")
    private Integer type;

    @ApiModelProperty("出库时间")
    private LocalDateTime recordTime;



}
