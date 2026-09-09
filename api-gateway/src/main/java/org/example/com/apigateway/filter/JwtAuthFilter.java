package org.example.com.apigateway.filter;

import org.example.com.securityplatform.jwt.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//继承了GlobalFilter表示所有经过Gateway的请求，都要先经过这个filter
@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //这是Gateway每收到一个请求时会调用的方法，exchange表示当前这一次HTTP请求+响应
    //chain为后面的过滤器/后续处理流程
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {

        String path = exchange.getRequest().getURI().getPath();

        // 注册、登录接口直接放行
        if ("/user".equals(path)
                || "/user/login".equals(path)) {
            //放行
            return chain.filter(exchange);
        }

        String authorization =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst("Authorization");

        if (authorization == null
                || !authorization.startsWith("Bearer ")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            //不准继续了
            return exchange.getResponse().setComplete();
        }

        String token = authorization.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        Long userId = jwtTokenProvider.getUserId(token);
        String username = jwtTokenProvider.getUsername(token);

        //把解析出的内容塞进请求头
        ServerWebExchange newExchange = exchange.mutate()
                .request(
                        exchange.getRequest()
                                .mutate()
                                .header("X-User-Id", String.valueOf(userId))
                                .header("X-Username", username)
                                .build()
                )
                .build();

        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}