package org.xyc.app.open.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.xyc.app.open.base.BaseTransferHandler;
import org.xyc.app.open.context.TransferContextLoad;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author xuyachang
 * @date 2024/9/1
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/open/receive/order")
public class ReceiveOrderMsgApi {

    private final TransferContextLoad transferContextLoad;

    @GetMapping("test")
    public String test(){
        return "test";
    }


    @PostMapping("/{channelCode}")
    public String receiveOrder(HttpServletRequest request, @PathVariable("channelCode") String channelCode){

        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))){
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
        }catch (Exception e){

        }
        String json = sb.toString();
        BaseTransferHandler transferHandler = transferContextLoad.getTransferHandler(channelCode);

        return transferHandler.handle(json);
    }
}
