package com.wuzhangze.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.entity.EduSubject;
import com.wuzhangze.eduservice.service.EduCourseService;
import com.wuzhangze.eduservice.service.EduSubjectService;
import com.wuzhangze.servicebase.api.ApiManager;
import com.wuzhangze.servicebase.exception.FileFormatException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author OldWu
 * @date 2021/9/7 21:51
 */
@RequestMapping(ApiManager.V + "/eduservice/subject/front")
@RestController
public class SubjectFrontController {
    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation(value = "获取所有的课程分类和当前课程分类的子课程")
    @GetMapping("/")
    public R findAll(){
        List<EduSubject> parents = subjectService.list(new QueryWrapper<EduSubject>().eq("parent_id", 0));
        List<EduSubject> list = subjectService.list(new QueryWrapper<EduSubject>().notIn("parent_id",0));
        List<EduSubject> subjectTreeSort = subjectService.subjectTreeSort(list, parents);
        return R.ok().list(subjectTreeSort);
    }

    @ApiOperation(value = "获取所有一级分类")
    @GetMapping("/get/one")
    public R findAllOneSubject(){
        List<EduSubject> subjects = subjectService.list(new QueryWrapper<EduSubject>().eq("parent_id","0"));
        return R.ok().list(subjects);
    }

    @ApiOperation(value = "获取所有子课程")
    @GetMapping("/get/other_subject")
    public R getOtherSubject(){
        List<EduSubject> subjects = subjectService.list(new QueryWrapper<EduSubject>().notIn("parent_id","0"));
        return R.ok().list(subjects);
    }
}
