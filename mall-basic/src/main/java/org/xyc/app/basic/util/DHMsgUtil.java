package org.xyc.app.basic.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author xuyachang
 * @date 2024/3/2
 */
@Slf4j
public class DHMsgUtil {

    /**
     * 根据手机号和内容发短信
     * @param mobiles 多个手机号用 ","隔开。 例："13809098787,13700000000"
     * @param content 短信内容
     * @return
     */
    public static boolean sendMsg(String mobiles,String content) {
        try {
            Map<String,String> param = new HashMap<>();
            param.put("account","guoyao001");
            param.put("sign","【国大药房】");
            param.put("businesstype","002");
            param.put("phones",mobiles);
            param.put("content",content);
            param.put("msgid",randomId());
            String passwordMd5 = DigestUtils.md5DigestAsHex("82ZXy7r1".getBytes());
            param.put("password",passwordMd5);
            String json = JSON.toJSONString(param);
            String url = "http://10.44.1.47:8181" + "/json/sms/submit";
            log.info("发短信入参={}", json);
            String result = OKHttpUtil.postJson(url, json);
            log.info("发短信入参={},发短信出参={}", json, result);
            if(Objects.isNull(result)){
                return false;
            }else{
                JSONObject jsonObject = JSON.parseObject(result);
                return "0".equals(jsonObject.getString("result"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String randomId() {
        String randomStr = UUID.randomUUID().toString();
        randomStr = randomStr.replace("-", "");
        return randomStr.substring(0, 32);
    }

    public static void main(String[] args) {
        DHMsgUtil.sendMsg("15797704512","测试内容");
    }
}
