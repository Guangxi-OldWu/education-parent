package com.wuzhangze.servicebase.exceptionhandler;

import com.wuzhangze.commonutil.R;
import com.wuzhangze.servicebase.exception.TeacherNotFindException;
import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler({TeacherNotFindException.class})
    @ResponseBody
    public R error(TeacherNotFindException e){
        e.printStackTrace();
        log.error(e.getMsg(),e);
        return R.err().message(e.getMsg());
    }
}
