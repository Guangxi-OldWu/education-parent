package com.wuzhangze.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuzhangze.eduservice.entity.EduSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author OldWu
 * @date 2021/8/22 22:36
 */
public interface EduSubjectService extends IService<EduSubject> {
    /**
     * 返回树形的科目列表
     * @param list  parent_id 不为0的科目列表
     * @param parents parent_id 为0的科目列表
     * @return
     */
    List<EduSubject> subjectTreeSort(List<EduSubject> list,List<EduSubject> parents);

    /**
     * 根据Excel文件存储科目数据
     * @param file Excel文件
     */
    void saveSubjectByExcel(MultipartFile file,EduSubjectService subjectService);
}
