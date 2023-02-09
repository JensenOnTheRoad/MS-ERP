package pers.jd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户明细表
 * </p>
 *
 * @author jd
 * @since 2022-03-17
 */
@TableName("user_detail")
@ApiModel(value = "UserDetail对象", description = "用户明细表")
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("职位名称")
    private String positionTitle;

    @ApiModelProperty("基本工资")
    private BigDecimal salaryBasic;

    @ApiModelProperty("提成工资")
    private BigDecimal salaryCommission;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("联系方式")
    private String tel;

    @ApiModelProperty("用户id")
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }
    public BigDecimal getSalaryBasic() {
        return salaryBasic;
    }

    public void setSalaryBasic(BigDecimal salaryBasic) {
        this.salaryBasic = salaryBasic;
    }
    public BigDecimal getSalaryCommission() {
        return salaryCommission;
    }

    public void setSalaryCommission(BigDecimal salaryCommission) {
        this.salaryCommission = salaryCommission;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
            "id=" + id +
            ", positionTitle=" + positionTitle +
            ", salaryBasic=" + salaryBasic +
            ", salaryCommission=" + salaryCommission +
            ", address=" + address +
            ", tel=" + tel +
            ", userId=" + userId +
        "}";
    }
}
