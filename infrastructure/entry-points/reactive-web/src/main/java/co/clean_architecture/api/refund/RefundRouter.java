package co.clean_architecture.api.refund;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RefundRouter {

    @Bean
    public RouterFunction<ServerResponse> refundRoutes(RefundHandler handler) {
        return RouterFunctions.route()
                .POST("/api/v1/refunds", handler::createRefund)
                .GET("/api/v1/refunds/payment/{paymentId}", handler::getRefundsByPaymentId)
                .build();
    }
}
