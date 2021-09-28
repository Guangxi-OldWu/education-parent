package com.wuzhangze.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author OldWu
 * @date 2021/8/22 22:28
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("edu_subject")
@ApiModel(value="课程分类对象", description="课程分类")
public class EduSubject {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "课程类别ID")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "子科目")
    @TableField(exist = false)
    private List<EduSubject> childs;
}
