package com.tsl.gateway.predicate.factories;

import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author fanlunyong
 * @author fanlunyong
 * 自定义一个断言工厂
 */
@Component()
/** 范型 用于接受一个配置类，配置类用于接收配置文件文件中的配置 **/
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.AgeConfig> {

    public AgeRoutePredicateFactory() {
        super(AgeConfig.class);
    }

    /**断言**/
    @Override
    public Predicate<ServerWebExchange> apply(AgeConfig config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                //从ServerWebExchange获取传入的参数
                String ageStr=serverWebExchange.getRequest().getQueryParams().getFirst("age");
                if (ageStr!=null && ageStr.length()>0) {
                    int age=Integer.parseInt(ageStr);
                    return age>config.minAge && age<config.maxAge;
                }
                return true;
            }
        };
    }

    /**用于从配置文件中获取参数值到配置类中的属性**/
    @Override
    public List<String> shortcutFieldOrder() {
        // 注意这里的顺序要跟配置文件中的顺序一致
        return Arrays.asList("minAge","maxAge");
    }

    @Data
    public static class AgeConfig{
        private int minAge;
        private int maxAge;
//        public AgeConfig(){}
//        public AgeConfig(int minAge,int maxAge){
//            this.minAge=minAge;
//            this.maxAge=maxAge;
//        }
//
//        public int getMinAge() {
//            return minAge;
//        }
//
//        public void setMinAge(int minAge) {
//            this.minAge = minAge;
//        }
//
//        public int getMaxAge() {
//            return maxAge;
//        }
//
//        public void setMaxAge(int maxAge) {
//            this.maxAge = maxAge;
//        }
//
//        @Override
//        public String toString() {
//            return "AgeConfig{" +
//                    "minAge=" + minAge +
//                    ", maxAge=" + maxAge +
//                    '}';
//        }
    }
}
