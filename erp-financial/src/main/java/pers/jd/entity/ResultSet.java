package pers.jd.entity;

import lombok.Data;

//返回结果集
@Data
public class ResultSet<T> {

    // 返回码：成功1，失败0
    private Integer returnCode;
    // 返回信息
    private String returnMessage;
    // 返回数据
    private T data;

}
