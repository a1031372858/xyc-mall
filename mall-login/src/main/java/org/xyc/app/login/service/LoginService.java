package org.xyc.app.login.service;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xyc.app.basic.constant.UrlConstant;
import org.xyc.app.basic.util.OKHttpUtil;
import org.xyc.domain.user.model.to.UserTO;

/**
 * @author xuyachang
 * @date 2024/2/28
 */

@RequiredArgsConstructor
@Service
public class LoginService {

    private final OKHttpUtil okHttpUtil;

    public String loginByPhone(String phone, String code) {
        String url = "http://" +UrlConstant.USER_HOST+":"+UrlConstant.USER_PORT+UrlConstant.FIND_USER_BY_PHONE;
        UserTO userTO = new UserTO();
        userTO.setPhone(phone);
        String result = okHttpUtil.postJson(url, JSON.toJSONString(userTO));
        return result;
    }
}
