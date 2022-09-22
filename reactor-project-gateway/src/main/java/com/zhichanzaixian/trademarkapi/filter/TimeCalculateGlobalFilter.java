package com.zhichanzaixian.trademarkapi.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * @author syl
 */
@Component
@Slf4j
public class TimeCalculateGlobalFilter implements GlobalFilter, Ordered  {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {



        long startTime =System.nanoTime();

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {

            Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
            String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
            Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
            URI routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);


            ServerHttpRequest request = exchange.getRequest();
            XForwardedRemoteAddressResolver resolver = XForwardedRemoteAddressResolver.maxTrustedIndex(1);
            InetSocketAddress inetSocketAddress = resolver.resolve(exchange);
            long endTime = System.nanoTime();
            double time = (endTime - startTime) / 1000000000.0;
            log.info("\n[OriginalUri ->][{}],\n[IP->]{},\n[x-auth-token->]{},\n[User-Agent->]{}\n[Routed-TO-Id->]{},[uri->]{},\n[Exec-Second->]{}"
                    , originalUri
                    , request.getHeaders().getFirst("X-Real-IP") == null ? inetSocketAddress.getHostString() : request.getHeaders().getFirst("X-Real-IP")
                    , request.getHeaders().getFirst("x-auth-token")
                    , request.getHeaders().getFirst("User-Agent")
                    , Optional.ofNullable(route).map(Route::getId).orElseGet(() -> null)
                    , routeUri,
                    time
            );


        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    public static void main(String[] args) {
        log.info("\n[OriginalUri ->][{}],\n[IP->]{},\n[x-auth-token->]{},\n[User-Agent->]{}\n[Routed-TO-Id->]{},[uri->]{},\n[Exec-Time->]{}",2,3,4,5,6,7,8);
    }


}
