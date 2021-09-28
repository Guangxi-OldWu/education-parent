package com.wuzhangze.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.entity.EduSubject;
import com.wuzhangze.eduservice.service.EduSubjectService;
import com.wuzhangze.servicebase.api.ApiManager;
import com.wuzhangze.servicebase.exception.FileFormatException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author OldWu
 * @date 2021/8/22 22:21
 */
@RestController
@RequestMapping(ApiManager.SUBJECT)
public class EduSubjectController {
    @Resource
    EduSubjectService subjectService;

    @GetMapping("/findAll")
    public R findAll(){
        List<EduSubject> parents = subjectService.list(new QueryWrapper<EduSubject>().eq("parent_id", 0));
        List<EduSubject> list = subjectService.list(new QueryWrapper<EduSubject>().notIn("parent_id",0));
        List<EduSubject> subjectTreeSort = subjectService.subjectTreeSort(list, parents);
        return R.ok().list(subjectTreeSort);
    }

    @ApiOperation(value = "读取存储课程信息的Excel文件，存储到数据库")
    @PostMapping("/addSubject")
    public R addSubject(@ApiParam(name = "file",value = "Excel文件") @RequestPart("file") MultipartFile file) {
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if(!fileSuffix.equals(".xls") && !fileSuffix.equals(".xlsx")){
            throw new FileFormatException(20002,"只能上传.xls或.xlsx格式的文件");
        }
        subjectService.saveSubjectByExcel(file,subjectService);
        return R.ok();
    }

}
