package com.wuzhangze.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhangze.commonutil.JwtUtils;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.client.OrderClient;
import com.wuzhangze.eduservice.entity.EduChapter;
import com.wuzhangze.eduservice.entity.EduCourse;
import com.wuzhangze.eduservice.entity.EduVideo;
import com.wuzhangze.eduservice.entity.vo.CourseCoverVo;
import com.wuzhangze.eduservice.entity.vo.CourseFrontQuery;
import com.wuzhangze.eduservice.entity.vo.CourseFrontVo;
import com.wuzhangze.eduservice.service.EduChapterService;
import com.wuzhangze.eduservice.service.EduCourseService;
import com.wuzhangze.eduservice.service.EduVideoService;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author OldWu
 * @date 2021/9/7 22:38
 */
@RequestMapping(ApiManager.V + "/eduservice/course/front")
@RestController
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "根据条件获取发布了的课程（分页）")
    @PostMapping("/page/{current}/{size}")
    public R getCoursePage(@PathVariable Integer current,
                           @PathVariable Integer size,
                           @RequestBody CourseFrontQuery courseQuery) {
        Page<EduCourse> page = new Page<>(current,size);
        Map<String, Object> map = courseService.getCoursePageByCondition(page, courseQuery);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据课程id 获取课程详情 和该课程的章节信息")
    @GetMapping("/get/{id}")
    public R getCourseInfo(@PathVariable String id, HttpServletRequest request) {
        // 获取章节和小节信息
        List<EduChapter> chapterVideo = chapterService.getChapterVideo(
                chapterService.list(new QueryWrapper<EduChapter>().eq("course_id", id).orderByAsc("sort")),
                videoService.list(new QueryWrapper<EduVideo>().eq("course_id", id).orderByAsc("sort")));

        CourseFrontVo courseFrontVoInto = courseService.getCourseFrontVoInto(id);
        boolean isBuy = false;
        if(courseFrontVoInto.getPrice().compareTo(new BigDecimal(0)) == 1) {
            try {
                isBuy = orderClient.getOrderStatus(JwtUtils.getMemberIdByJwtToken(request), id);
            }catch (Exception e) {}
        }

        return R.ok().obj(courseFrontVoInto).data("chapter",chapterVideo).data("isBuy",isBuy);
    }
}
