package com.wuzhangze.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuzhangze.commonutil.JwtUtils;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.eduservice.client.MemberClient;
import com.wuzhangze.eduservice.entity.EduComment;
import com.wuzhangze.eduservice.service.EduCommentService;
import com.wuzhangze.eduservice.utils.OkHttpUtils;
import com.wuzhangze.servicebase.api.ApiManager;
import com.wuzhangze.servicebase.exception.LzException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-09-09
 */
@RestController
@RequestMapping(ApiManager.V + "/eduservice/comment/front")
public class CommentFrontController {
    @Autowired
    private EduCommentService commentService;
    @Autowired
    private MemberClient memberClient;

    @ApiOperation(value = "添加评论")
    @PostMapping("/add")
    public R addComment(HttpServletRequest request,@Valid @RequestBody EduComment comment) {
        R result = memberClient.getUserInfO(request.getHeader("token"));
        // 判断用户是否存在
        if(!result.getSuccess()) {
            throw new LzException(result.getCode(),result.getMessage());
        }
        boolean save = commentService.save(comment);
        return save ? R.ok() : R.err().message("评论失败");
    }

    @ApiOperation(value = "根据课程id 获取评论列表")
    @GetMapping("/get/{id}")
    public R getCommentList(@PathVariable String id) {
        List<EduComment> comments = commentService.list(new QueryWrapper<EduComment>().eq("course_id", id));

        // 取出子评论放到父评论中(setChilds)
        for (int i = 0; i < comments.size(); i++) {
            EduComment parent = comments.get(i);
            if(parent.getParentId() != null) continue;
            List<EduComment> childs = new ArrayList<>();

            for (int j = 0; j < comments.size(); j++) {
                EduComment child = comments.get(j);
                if(child.getParentId() != null && child.getParentId().equals(parent.getId())){
                    childs.add(child);
                    comments.remove(j--);
                }
            }
            parent.setChilds(childs);
        }
        return R.ok().list(comments);
    }

    @ApiOperation(value = "根据评论id 删除评论")
    @DeleteMapping("/delete/{id}")
    public R deleteCommentById(@PathVariable String id) {
        commentService.removeCommentById(id);
        return R.ok();
    }

}

