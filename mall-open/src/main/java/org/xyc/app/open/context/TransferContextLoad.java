package org.xyc.app.open.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.xyc.app.open.annotation.ChannelApi;
import org.xyc.app.open.base.BaseTransferHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuyachang
 * @date 2024/9/1
 */
@Component
@Slf4j
public class TransferContextLoad implements ApplicationListener<ContextRefreshedEvent> {

    private static final Map<String, BaseTransferHandler> transferHandlerMap = new HashMap<>();
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, Object> channelApiClassMap = applicationContext.getBeansWithAnnotation(ChannelApi.class);
        channelApiClassMap.keySet().forEach(o->{
            Object obj = channelApiClassMap.get(o);
            String channelCode = obj.getClass().getAnnotation(ChannelApi.class).channelCode();
            if(obj instanceof BaseTransferHandler){
                transferHandlerMap.putIfAbsent(channelCode,(BaseTransferHandler)obj);
            }
        });
    }

    public BaseTransferHandler getTransferHandler(String channelCode){
        return transferHandlerMap.get(channelCode);
    }
}
