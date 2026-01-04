package co.clean_architecture.api.useraccount;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserAccounRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(UserAccountHandler handler) {
        return RouterFunctions.route()
                .POST("/api/v1/user-accounts", handler::create)
                .build();
    }
}
