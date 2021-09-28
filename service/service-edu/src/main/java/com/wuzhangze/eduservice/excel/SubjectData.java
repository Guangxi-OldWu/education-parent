package com.wuzhangze.eduservice.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author OldWu
 * @date 2021/8/23 15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubject;
    @ExcelProperty(index = 1)
    private String twoSubject;
    @ExcelProperty(index = 2)
    private String threeSubject;
}
