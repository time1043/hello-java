package com.time1043.yupaobackend.once;
// https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read#%E5%AF%B9%E8%B1%A1

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Excel 映射实体类
 */
@Data
@EqualsAndHashCode
public class ZSXQTableUserInfo {

    @ExcelProperty("成员编号")  // index = 0
    private String planetCode;

    @ExcelProperty("成员昵称")
    private String username;

}