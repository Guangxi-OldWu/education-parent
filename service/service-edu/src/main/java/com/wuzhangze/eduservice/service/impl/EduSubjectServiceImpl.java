package com.wuzhangze.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuzhangze.eduservice.entity.EduSubject;
import com.wuzhangze.eduservice.excel.SubjectData;
import com.wuzhangze.eduservice.listener.SubjectExcelListener;
import com.wuzhangze.eduservice.mapper.EduSubjectMapper;
import com.wuzhangze.eduservice.service.EduSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OldWu
 * @date 2021/8/22 22:36
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public List<EduSubject> subjectTreeSort(List<EduSubject> list, List<EduSubject> parents) {
        for (int i = 0;i<parents.size();i++) {
            EduSubject parent = parents.get(i);
            // 记录子科目
            List<EduSubject> childs = new ArrayList<>();
            for (int j = 0;j < list.size(); j++){
                EduSubject subject = list.get(j);
                if(!parent.getId().equals(subject.getId()) && parent.getId().equals(subject.getParentId())) {
                    childs.add(subject);
                    list.remove(j--);
                }
            }
            if(childs.size() > 0){
                parent.setChilds(childs);
                subjectTreeSort(list,parent.getChilds());
            }
        }
        return parents;
    }

    @Override
    public void saveSubjectByExcel(MultipartFile file,EduSubjectService subjectService) {
        InputStream in = null;
        try {
            in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
