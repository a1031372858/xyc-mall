package org.xyc.app.login.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.xyc.app.basic.constant.UrlConstant;
import org.xyc.app.basic.model.SecurityUser;
import org.xyc.app.basic.util.OKHttpUtil;
import org.xyc.domain.base.model.Response;
import org.xyc.domain.user.model.to.UserTO;

import java.util.Objects;

/**
 * @author xuyachang
 * @date 2024/3/20
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserTO userTO = getUserInfo(username);
        String password = StringUtils.EMPTY;
        if(Objects.nonNull(userTO)){
            password = userTO.getPassword();
        }
        SecurityUser securityUser = new SecurityUser(username, password, AuthorityUtils.createAuthorityList());
        securityUser.setUserTO(userTO);
        return securityUser;
    }

    private UserTO getUserInfo(String username){
        String url = UrlConstant.USER_URL + UrlConstant.FIND_USER_BY_PHONE;
        UserTO userTO = new UserTO();
        userTO.setPhone(username);
        String responseResult = OKHttpUtil.postJson(url, JSON.toJSONString(userTO));
        Response response = JSONObject.parseObject(responseResult, Response.class);
        if(response.getData() instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) response.getData();
            UserTO result = jsonObject.toJavaObject(UserTO.class);
            return result;
        }else{
            return null;
        }
    }
}
