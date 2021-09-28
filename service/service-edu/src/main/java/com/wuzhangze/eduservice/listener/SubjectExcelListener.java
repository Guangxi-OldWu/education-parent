package com.wuzhangze.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.eduservice.entity.EduSubject;
import com.wuzhangze.eduservice.excel.SubjectData;
import com.wuzhangze.eduservice.service.EduSubjectService;
import com.wuzhangze.servicebase.exception.NotFindException;

/**
 * @author OldWu
 * @date 2021/8/23 15:52
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService subjectService;

    /**
     * 因为 listener 不能交给Spring 管理，
     * 在这里要进行数据库操作的话不能@Autowrite获取数据库对象，需要把对象过来
     * @param eduSubjectService
     */
    public SubjectExcelListener(EduSubjectService eduSubjectService){
        this.subjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new NotFindException(40000,"文件数据为空");
        }
        EduSubject oneSubject = existOneSubject(subjectData.getOneSubject());
        if(oneSubject == null) {
            oneSubject = new EduSubject(null, subjectData.getOneSubject(), "0", 0, null, null, null);
            subjectService.save(oneSubject);
        }

        if(subjectData.getTwoSubject() == null) return;
        EduSubject twoSubject = existTwoSubjcet(subjectData.getTwoSubject(),oneSubject.getId());
        if(twoSubject == null){
            twoSubject = new EduSubject(null,subjectData.getTwoSubject(),oneSubject.getId(),0,null,null,null);
            subjectService.save(twoSubject);
        }

        if(subjectData.getThreeSubject() == null) return;
        EduSubject threeSubject = existThreeSubject(subjectData.getThreeSubject(),twoSubject.getId());
        if(threeSubject == null){
            threeSubject = new EduSubject(null,subjectData.getThreeSubject(),twoSubject.getId(),0,null,null,null);
            subjectService.save(threeSubject);
        }
    }

    private EduSubject existOneSubject(String name) {
        EduSubject subject = subjectService.getOne(new QueryWrapper<EduSubject>().eq("title", name).eq("parent_id", 0));
        return subject;
    }

    private EduSubject existTwoSubjcet(String name,String parentId) {
        EduSubject two = subjectService.getOne(new QueryWrapper<EduSubject>().eq("title", name).eq("parent_id", parentId));
        return two;
    }

    private EduSubject existThreeSubject(String name,String parentId) {
        EduSubject three = subjectService.getOne(new QueryWrapper<EduSubject>().eq("title", name).eq("parent_id", parentId));
        return three;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
