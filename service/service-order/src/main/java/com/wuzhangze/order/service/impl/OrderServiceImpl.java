package com.wuzhangze.order.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.order.client.CourseClient;
import com.wuzhangze.order.client.MemberClient;
import com.wuzhangze.order.entity.Order;
import com.wuzhangze.order.mapper.OrderMapper;
import com.wuzhangze.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuzhangze.servicebase.dto.CourseOrderInfo;
import com.wuzhangze.servicebase.dto.MemberOrderInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-10
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private MemberClient memberClient;
    @Autowired
    private CourseClient courseClient;


    @Override
    public String createOrder(String courseId, String memberId) {
        MemberOrderInfo member = memberClient.getMemberOrderInfo(memberId);
        CourseOrderInfo course = courseClient.getCourse(courseId);
        Order order = new Order();
        order.setCourseId(courseId);
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        order.setTeacherName(course.getTeacherName());
        order.setMemberId(memberId);
        order.setNickname(member.getNickname());
        order.setMobile(member.getMobile());
        order.setTotalFee(course.getPrice());
        order.setPayType(1);
        order.setStatus(0);
        order.setOrderNo(Long.toString(IdUtil.getSnowflake().nextId()));
        baseMapper.insert(order);
        return order.getOrderNo();
    }


}
