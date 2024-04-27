package org.xyc.app.trade.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @author xuyachang
 * @date 2024/4/24
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HelloJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("欢迎来到XYC-MALL");
    }
}
