package com.wuzhangze.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.wuzhangze.order.entity.Order;
import com.wuzhangze.order.entity.PayLog;
import com.wuzhangze.order.mapper.PayLogMapper;
import com.wuzhangze.order.properties.WxPayProperties;
import com.wuzhangze.order.service.OrderService;
import com.wuzhangze.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuzhangze.order.utils.OkHttpUtils;
import com.wuzhangze.servicebase.exception.LzException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-10
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private WxPayProperties wxPayProperties;

    @Override
    public Map getQrcode(String orderNo) {
        try {
            // 根据订单id获取订单信息
            Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));

            Map m = new HashMap();
            m.put("appid",wxPayProperties.getAppId());
            m.put("mch_id", wxPayProperties.getPartner());
            // 生成随机的字符串，使二维码每次都不同
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            // 二维码中要显示的数据：课程名
            m.put("body", order.getCourseTitle());
            // 二维码唯一标识：订单号
            m.put("out_trade_no", orderNo);
            // 以分为单位，0.01*100 = 1分，转为long类型再变字符串
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            // 进行支付的ip地址，如向www.baidu.co给钱
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", wxPayProperties.getNotifyUrl() + "\n");
            // 根据价格生成二维码做支付
            m.put("trade_type", "NATIVE");

            // 发送post请求，传递参数xml格式，微信支付提供的固定的地址。
            // 返回的res.body.string也是xml格式
            String xml = OkHttpUtils.postXml(
                    "https://api.mch.weixin.qq.com/pay/unifiedorder",
                    WXPayUtil.generateSignedXml(m, wxPayProperties.getPartnerKey()));

            //把xml格式转换map集合，把map集合返回
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装
            Map map = new HashMap();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            // 返回二维码操作状态码
            map.put("result_code", resultMap.get("result_code"));
            // 二维码地址
            map.put("code_url", resultMap.get("code_url"));
            return map;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new LzException(20001,"生成二维码失败");
        }
    }

    @Override
    public Map<String, String> getPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", wxPayProperties.getAppId());
            m.put("mch_id", wxPayProperties.getPartner());
            m.put("out_trade_no", orderNo);
            // 随机字符串，让二维码不重复
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            String xml = OkHttpUtils.postXml(
                    "https://api.mch.weixin.qq.com/pay/orderquery",
                    WXPayUtil.generateSignedXml(m, wxPayProperties.getPartnerKey()));

            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map再返回
            return resultMap;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //从map获取订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        //更新订单表订单状态
        if(order.getStatus().intValue() == 1) { return; }
        order.setStatus(1);//1代表已经支付
        orderService.updateById(order);

        //向支付表添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);  //订单号
        payLog.setPayTime(new Date()); //订单完成时间
        payLog.setPayType(1);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额(分)

        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id")); //流水号
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);
    }
}
