package com.wuzhangze.eduservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author OldWu
 * @date 2021/8/19 22:18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    private String name;
    private String sex;
    private Integer age;
}
