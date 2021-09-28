package com.wuzhangze.test.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author OldWu
 * @date 2021/8/23 13:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoData {
    @ExcelProperty("学生编号")
//    @ExcelProperty(index = 0)
    private Integer sno;
    @ExcelProperty("学生姓名")
    private String sname;
}
