package com.wuzhangze.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author OldWu
 * @date 2021/8/23 21:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileFormatException extends RuntimeException{
    private Integer code;
    private String msg;
}
