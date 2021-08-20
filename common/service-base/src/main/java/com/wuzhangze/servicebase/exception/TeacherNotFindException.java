package com.wuzhangze.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author OldWu
 * @date 2021/8/19 16:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TeacherNotFindException extends RuntimeException{
    private Integer code;
    private String msg;
}
