package com.wuzhangze.ucenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wuzhangze.commonutil.JwtUtils;
import com.wuzhangze.servicebase.exception.LzException;
import com.wuzhangze.ucenter.entity.Member;
import com.wuzhangze.ucenter.properties.WeixinProperties;
import com.wuzhangze.ucenter.service.MemberService;
import com.wuzhangze.ucenter.utils.OkHttpUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author OldWu
 * @date 2021/9/5 21:51
 */
@RequestMapping("/api/ucenter/wx")
@Controller
public class WxApiController {

    @Autowired
    private WeixinProperties weixinProperties;
    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "生成微信登录的二维码")
    @GetMapping("/code")
    public String getWxCode() {
        String baseCodeUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = null;
        try {
            // 文档要求了重定向url 需要进行编码
            redirectUrl = URLEncoder.encode(weixinProperties.getRedirectUri(),"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String codeUrl = String.format(baseCodeUrl,
                    weixinProperties.getAppId(),
                    redirectUrl,
                    "wuzhangze");

        return "redirect:" + codeUrl;
    }

    /**
     * 登陆后跳转到这个方法里（在微信开发中设置跳转的地址），并会返回两个参数
     * @param code  授权的票据，随机且唯一。得到这个code可以请求一个微信的接口，返回两个值：
     *              1.access_token：访问凭证
     *              2.openid：每个微信的唯一标识
     *              有了这两个值再去请求另一个微信接口，才可以获取扫码人的信息（昵称、头像等）
     * @param state 生成微信二维码时传入的state
     * @return 返回前端主页
     */
    @ApiOperation(value = "微信扫码登录跳转到这个地址")
    @GetMapping("/callback")
    public String callback(String code,String state) {
        // 获取 access_token 和 openid 的地址，微信提供的api
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        // 根据access_token 和 openid 获取用户信息
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                weixinProperties.getAppId(),
                weixinProperties.getAppSecret(),
                code);

        // {"access_token":"48__uvm0wjEfH8EtS8g98RMFPIO_UjC6ZRR3Y_4Fj89prZyOTY1ji-SxQV7tN9Ycb9vdbAecPuC_TTXZRupLEgi6yilqyCJ1hd0exs04xNnyOY",
        // "expires_in":7200,
        // "refresh_token":"48_bF7FsG5lu9cLavKobz7g6ZDecteZofK-_iOBZs_zy_uEAaF2bcgbF00StEbDkzq4T85fc5mmsacWlFdCCi0W6aVOn9RkiA62Os5cpEU7GwE",
        // "openid":"o3_SC53gkhL_pVMxx2oVYCJtqtZI",
        // "scope":"snsapi_login",
        // "unionid":"oWgGz1KJ9G1xyzduo_uoul1abvU0"}

        // 获取 accessToken 和 aopenid 就可以访问微信登陆人的信息了
        Gson gson = new Gson();
        String accessTokenStr = OkHttpUtils.get(accessTokenUrl);
        HashMap<String,String> hashMap = gson.fromJson(accessTokenStr, HashMap.class);
        String accessToken = hashMap.get("access_token");
        String openid = hashMap.get("openid");

        // 拼接地址
        String userInfoUrl = String.format(baseUserInfoUrl, accessToken,openid);
        // {"openid":"o3_SC53gkhL_pVMxx2oVYCJtqtZI",
        // "nickname":".",
        // "sex":1,
        // "language":"zh_CN",
        // "city":"",
        // "province":"",
        // "country":"CN",
        // "headimgurl":"https:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/PiajxSqBRaEJqJRSiciap2oMfyfAKTv1nUJTz9nicrCpEicbQJytcA3mv6gSVjJHRprrBBw7F5J8qVISgd1UqM0oQJg\/132",
        // "privilege":[],
        // "unionid":"oWgGz1KJ9G1xyzduo_uoul1abvU0"}
        String userInfoStr = OkHttpUtils.get(userInfoUrl);
        HashMap userInfoMap = gson.fromJson(userInfoStr,HashMap.class);
        String nickname = (String) userInfoMap.get("nickname");
        String avatar = (String) userInfoMap.get("headimgurl");
        Double doubleSex = (Double) userInfoMap.get("sex");
        Integer sex = Integer.valueOf(doubleSex.intValue());

        // 查询该用户是否用微信登录过
        Member member = memberService.getOne(new QueryWrapper<Member>().eq("openid", openid));
        if(member == null) {
            member = new Member();
            member.setNickname(nickname);
            member.setAvatar(avatar);
            member.setSex(sex);
            memberService.save(member);
        }else {
            if(member.getIsDisabled()) {
                throw new LzException(20001,"你已被系统封禁");
            }
        }

        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return "redirect:http://localhost:3000?token=" + token;
    }
}
