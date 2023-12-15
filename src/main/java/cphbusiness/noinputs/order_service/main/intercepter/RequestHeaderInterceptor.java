package cphbusiness.noinputs.order_service.main.intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpCookie;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Configuration
public class RequestHeaderInterceptor implements WebGraphQlInterceptor {

    // This method is called before the request is processed, we are getting the jwt-token from the request and adding it to the execution context
    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        HttpCookie jwtToken = request.getCookies().getFirst("jwt-token");

        if(jwtToken != null) {
            request.configureExecutionInput((executionInput, builder) ->
                    builder.graphQLContext(Collections.singletonMap("jwt-token", jwtToken)).build());
        } else {
            HttpCookie badCookie = new HttpCookie("jwt-token", "");
            request.configureExecutionInput((executionInput, builder) ->
                    builder.graphQLContext(Collections.singletonMap("jwt-token", badCookie)).build());
        }

        return chain.next(request);
    }
}
