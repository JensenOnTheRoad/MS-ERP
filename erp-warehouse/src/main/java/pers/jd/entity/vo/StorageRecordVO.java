package pers.jd.entity.vo;


import java.io.Serializable;
import java.time.LocalDateTime;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data

public class StorageRecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty("仓储编号")
    private String storageId;

    @ApiModelProperty("仓储类型")
    private String storageType;

    @ApiModelProperty("出入库类型：0入库，1出库")
    private String type;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("出库时间")
    private LocalDateTime recordTime;


}
