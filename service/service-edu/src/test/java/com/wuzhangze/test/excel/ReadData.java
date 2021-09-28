package com.wuzhangze.test.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author OldWu
 * @date 2021/8/23 14:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadData {
    @ExcelProperty(index = 0)
    private Integer sid;
    @ExcelProperty(index = 1)
    private String sname;

}
