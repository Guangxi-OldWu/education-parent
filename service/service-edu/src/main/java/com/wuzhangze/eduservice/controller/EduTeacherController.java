package com.wuzhangze.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.commonutil.ResultCode;
import com.wuzhangze.eduservice.entity.EduTeacher;
import com.wuzhangze.eduservice.entity.vo.TeacherQuery;
import com.wuzhangze.eduservice.service.EduTeacherService;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-08-18
 */
@Api("讲师管理")
@RestController
@RequestMapping(ApiManager.TEACHER)
public class EduTeacherController {

    @Resource
    EduTeacherService teacherService;

    @ApiOperation(value = "获取讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher(){
        return R.ok().list(teacherService.list());
    }

    @CacheEvict(value = {"hotTeacher"})
    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("/remove/{id}")
    public R removeTeacher(
            @ApiParam(name = "id",value = "讲师id",required = true)
            @PathVariable("id") String id){
        boolean tag = teacherService.removeById(id);
        return tag ? R.ok() : R.err();
    }

    @ApiOperation("根据分页获取讲师列表")
    @GetMapping("/pageTeacher/{current}/{size}")
    public R pageListTeacher(@PathVariable("current") @ApiParam(name = "current",value = "当前页") Long current,
                             @PathVariable("size") @ApiParam(name = "size",value = "每一页数据量") Long size){
        Page<EduTeacher> teacherPage = teacherService.page(new Page<>(current,size));
        Map map = new HashMap();
        map.put(ResultCode.ITEMS.resultName,teacherPage.getRecords());
        map.put(ResultCode.TOTAL.resultName,teacherPage.getTotal());
        return R.ok().data(map);
    }

    @ApiOperation(value = "多条件分页查询")
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@PathVariable("current") @ApiParam(name = "current",value = "当前页") Long current,
                                  @PathVariable("size") @ApiParam(name = "size",value = "每一页数据量") Long size,
                                  @RequestBody @ApiParam(name = "teacherQuery",value = "查询的条件") TeacherQuery teacherQuery){
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        if(teacherQuery != null){
            if(!StringUtils.isEmpty(teacherQuery.getName())){
                queryWrapper.like("name",teacherQuery.getName());
            }
            if(!StringUtils.isEmpty(teacherQuery.getLevel())){
                queryWrapper.eq("level",teacherQuery.getLevel());
            }
            if(!StringUtils.isEmpty(teacherQuery.getBegin())){
                queryWrapper.ge("gmt_create",teacherQuery.getBegin());
            }
            if(!StringUtils.isEmpty(teacherQuery.getEnd())){
                queryWrapper.le("gmt_create",teacherQuery.getEnd());
            }
        }
        Page<EduTeacher> teacherPage = teacherService.page(new Page<>(current, size), queryWrapper);
        return R.ok().list(teacherPage.getRecords()).data(ResultCode.TOTAL,teacherPage.getTotal());
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody @ApiParam(name = "teacher",value = "讲师实体类")EduTeacher teacher){
        boolean save = teacherService.save(teacher);
        return save ? R.ok() : R.err();
    }

    @ApiOperation(value = "根据id查询讲师信息")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id){
        return R.ok().obj(teacherService.getById(id));
    }

    @CacheEvict(value = {"hotTeacher"})
    @ApiOperation("修改讲师信息")
    @PutMapping("/updateTeacher/{id}")
    public R updateTeacher(
            @ApiParam(name = "id",value = "要修改的讲师id")
            @PathVariable("id") String id,
            @ApiParam(name = "teacher",value = "要修改的讲师实体")
            @RequestBody EduTeacher teacher){
        boolean update = teacherService.update(teacher, new UpdateWrapper<EduTeacher>().eq("id", id));
        return update ? R.ok() : R.err();
    }

}

