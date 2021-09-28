package com.wuzhangze.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author OldWu
 * @date 2021/9/7 21:19
 */
@Data
public class CourseCoverVo {
    @ApiModelProperty(value = "课程id")
    private String id;
    @ApiModelProperty(value = "课程封面")
    private String cover;
    @ApiModelProperty(value = "课程名")
    private String title;
}
