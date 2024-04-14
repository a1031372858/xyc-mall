package org.xyc.app.login.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.xyc.app.login.constant.LoginRedisKey;
import org.xyc.app.login.model.AuthCodeTO;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xuyachang
 * @date 2024/3/31
 */
@RequiredArgsConstructor
@Service
public class LoginService {

    private final RedisTemplate<String,String> redisTemplate;

    /**
     * 生成用户登录验证码，有效期 5分钟
     * @param phone
     * @return
     */
    public String makeUserAuthCode(String phone){
        String json = redisTemplate.opsForValue().get(LoginRedisKey.LOGIN_USER_CODE + phone);
        if(Objects.nonNull(json)){
            AuthCodeTO authCodeTO = JSON.parseObject(json, AuthCodeTO.class);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(authCodeTO.getCreateAt());
            calendar.add(Calendar.MINUTE,1);
            Date expirationTime = calendar.getTime();
            //如果验证码存在，且未超过1分钟，不生成新验证码，直接返回老验证码
            if(DateUtil.compare(expirationTime, new Date()) <= 0){
                return authCodeTO.getCode();
            }
        }
        //验证码不存在，或验证码超过1分钟，生成新验证码并更新缓存
        String code = RandomUtil.randomNumbers(4);
        AuthCodeTO newAuthCode = new AuthCodeTO();
        newAuthCode.setCode(code);
        newAuthCode.setCreateAt(new Date());
        String newAuthCodeJson = JSON.toJSONString(newAuthCode);
        redisTemplate.opsForValue().set(LoginRedisKey.LOGIN_USER_CODE + phone,newAuthCodeJson,300, TimeUnit.SECONDS);
        return code;
    }

    /**
     * 获取验证码信息
     * @param phone
     * @return
     */
    public AuthCodeTO getUserAuthCodeTO(String phone){
        String json = redisTemplate.opsForValue().get(LoginRedisKey.LOGIN_USER_CODE + phone);
        if(Objects.isNull(json)){
            return null;
        }
        return JSON.parseObject(json, AuthCodeTO.class);
    }

    /**
     * 删除验证码
     * @param phone
     */
    public void removeUserAuthCodeTO(String phone){
        redisTemplate.delete(LoginRedisKey.LOGIN_USER_CODE + phone);
    }
}
