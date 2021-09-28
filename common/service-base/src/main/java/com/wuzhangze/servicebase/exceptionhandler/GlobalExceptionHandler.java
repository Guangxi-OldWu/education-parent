package com.wuzhangze.servicebase.exceptionhandler;

import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.exception.FileFormatException;
import com.wuzhangze.servicebase.exception.LzException;
import com.wuzhangze.servicebase.exception.NotFindException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author OldWu
 * @date 2021/8/19 2:15
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.err().message("执行了全局异常处理");
    }

    @ExceptionHandler({ArithmeticException.class})
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.err().message("执行了特定异常处理");
    }

    @ExceptionHandler({NotFindException.class})
    @ResponseBody
    public R error(NotFindException e){
        e.printStackTrace();
        log.error(e.getMsg(),e);
        return R.err().code(e.getCode()).message(e.getMsg());
    }

    @ExceptionHandler({FileFormatException.class})
    @ResponseBody
    public R error(FileFormatException e){
        e.printStackTrace();
        log.error(e.getMsg(),e);
        return R.err().code(e.getCode()).message(e.getMsg());
    }

    @ExceptionHandler({LzException.class})
    @ResponseBody
    public R error(LzException e){
        e.printStackTrace();
        log.error(e.getMsg(),e);
        return R.err().code(e.getCode()).message(e.getMsg());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public R error(MethodArgumentNotValidException e) {
        e.printStackTrace();
        log.error(e.getMessage(),e);
        return R.err().code(20003).message(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler({ExpiredJwtException.class})
    @ResponseBody
    public R error(ExpiredJwtException e) {
        return R.err().code(50000).message(e.getMessage());
    }
}
