package com.wuzhangze.servicebase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author OldWu
 * @date 2021/9/11 21:28
 */
@Data
public class CourseOrderInfo implements Serializable {
    @ApiModelProperty(value = "课程id")
    private String id;
//    @ApiModelProperty(value = "讲师id")
//    private String teacherId;
    @ApiModelProperty(value = "课程封面")
    private String cover;
    @ApiModelProperty(value = "课程名")
    private String title;
    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;
    @ApiModelProperty(value = "课程金额")
    private BigDecimal price;


}
