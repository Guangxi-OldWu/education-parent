package com.wuzhangze.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author OldWu
 * @date 2021/9/7 22:45
 */
@Data
public class CourseFrontQuery {
    @ApiModelProperty(value = "一级分类id")
    private String oneSubjectId;

    @ApiModelProperty(value = "二级分类id")
    private String twoSubjectId;

    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "价格排序")
    private String priceSort;
}
