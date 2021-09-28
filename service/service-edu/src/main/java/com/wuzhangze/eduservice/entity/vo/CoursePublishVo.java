package com.wuzhangze.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author OldWu
 * @date 2021/8/27 16:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePublishVo implements Serializable {
    @ApiModelProperty(value = "课程id")
    private String id;
    @ApiModelProperty(value = "课程名")
    private String title;
    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;
    @ApiModelProperty(value = "课程课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程封面图")
    private String cover;
    @ApiModelProperty(value = "课程简介")
    private String description;
    @ApiModelProperty(value = "讲师姓名")
    private String name;
    @ApiModelProperty(value = "课程一级分类")
    private String oneSubject;
    @ApiModelProperty(value = "课程二级分类")
    private String twoSubject;
}
