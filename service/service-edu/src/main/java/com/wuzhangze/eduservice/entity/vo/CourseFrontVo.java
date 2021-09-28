package com.wuzhangze.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 课程前台的详情vo类，有教师信息，一级二级课程信息，课程及课程详情信息
 * @author OldWu
 * @date 2021/9/8 19:30
 */
@Data
public class CourseFrontVo {
    private String id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面路径")
    private String cover;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "讲师资历")
    private String intro;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;

    @ApiModelProperty(value = "课程类别id")
    private String subjectLevelOneId;

    @ApiModelProperty(value = "课程类别名称")
    private String subjectLevelOne;

    @ApiModelProperty(value = "课程类别id")
    private String subjectLevelTwoId;

    @ApiModelProperty(value = "课程类别名称")
    private String subjectLevelTwo;

}
