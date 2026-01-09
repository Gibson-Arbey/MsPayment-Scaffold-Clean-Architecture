package co.clean_architecture.api.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PaymentRouter {

    @Bean
    public RouterFunction<ServerResponse> paymentRoutes(PaymentHandler handler) {
        return RouterFunctions.route()
            .POST("/api/v1/payment", handler::createPayment)
            .GET("/api/v1/payment/{id}", handler::getPaymentById)
            .PATCH("/api/v1/payment/{id}/status", handler::updatePaymentStatus)
            .build();
    }
}
