package com.wuzhangze.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author OldWu
 * @date 2021/8/19 0:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherQuery {
    @ApiModelProperty(value = "教师名称")
    private String name;
    @ApiModelProperty(value = "教师级别")
    private Integer level;
    @ApiModelProperty(value = "查询开始时间",example = "2021-08-19 10:10:10")
    private String begin;
    @ApiModelProperty(value = "查询结束时间",example = "2021-08-19 10:10:10")
    private String end;
}
