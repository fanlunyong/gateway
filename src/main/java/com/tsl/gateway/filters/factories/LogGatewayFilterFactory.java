package com.tsl.gateway.filters.factories;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author fanlunyong
 * 局部过滤器
 */
@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    /**构造函数**/
    public  LogGatewayFilterFactory(){
        super(LogGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // 如果我们需要拦截数据，不再往下跑，并写内容到response那么可以参照全局过滤器的写法
                if (config.isCacheLog()){
                    System.out.println("cacheLog已经开启了......");
                }
                if (config.isConsoleLog()){
                    System.out.println("consoleLog已经开启了......");
                }
                return chain.filter(exchange);
            }
        };
    }

    /**读取配置文件中的参数，赋值到配置类中**/
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("consoleLog","cacheLog");
    }

    @Data
    @NoArgsConstructor
    public static class Config{
        private boolean consoleLog;
        private boolean cacheLog;
    }
}
