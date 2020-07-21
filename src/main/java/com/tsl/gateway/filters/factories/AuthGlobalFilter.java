package com.tsl.gateway.filters.factories;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

/**
 * @author fanlunyong
 * 自定义全局的过滤器，需要实现
 */
@Component
public class AuthGlobalFilter implements Ordered,GlobalFilter {

    /**
     逻辑判断**/
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (StringUtils.isEmpty(token)){
            System.out.println("鉴权失败");
            ServerHttpResponse response = exchange.getResponse();
            //设置http响应状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //设置响应头信息Content-Type类型
            response.getHeaders().add("Content-Type","application/json");
            //设置返回json数据
            return response.writeAndFlushWith(Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(getWrapData()))));
            //直接返回（没有返回数据）
//        return response.setComplete().then();
            //设置返回的数据（非json格式）
//        return response.writeWith(Flux.just(response.bufferFactory().wrap("".getBytes())));
            
        }
        // 继续向下执行
        return chain.filter(exchange);
    }

    /**
     顺序号,越小越靠前
     **/
    @Override
    public int getOrder() {
        return 0;
    }

    private byte[] getWrapData(){
        return "{msg:'么得权限'}".getBytes();
    }
}
