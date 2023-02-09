
package pers.jd.entity;

import lombok.Data;

import java.util.List;

@Data
//region 返回结构体
public class ResultGetAllPaging<T> {
    long countTotal;
    long countCurrent;
    List<T> dataList;
}
//endregion