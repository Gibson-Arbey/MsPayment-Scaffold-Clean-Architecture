package co.clean_architecture.usecase.payment;

import co.clean_architecture.model.payment.CurrencyEnum;
import co.clean_architecture.model.payment.Payment;
import co.clean_architecture.model.payment.StatusPaymentEnum;
import co.clean_architecture.model.payment.criteria.PaymentCriteria;
import co.clean_architecture.model.payment.exception.PaymentNotCreatedException;
import co.clean_architecture.model.payment.gateways.PaymentRepository;
import co.clean_architecture.model.useraccount.exception.UserAccountNotFoundException;
import co.clean_architecture.model.useraccount.gateways.UserAccountRepository;
import co.clean_architecture.usecase.payment.command.CreatePaymentCommand;
import co.clean_architecture.usecase.payment.exception.StatusPaymentNotValidException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final UserAccountRepository userAccountRepository;


    public Mono<Payment> createPayment(CreatePaymentCommand command) {

        Payment payment = Payment.create(command.userAccountId(), command.amount(), CurrencyEnum.valueOf(command.currency()));

        return userAccountRepository.getUserAccountById(command.userAccountId()).switchIfEmpty(Mono.error(new UserAccountNotFoundException("User account not found for ID: " + command.userAccountId()))).doOnNext(userAccount -> userAccount.validatePayment(payment.getAmount())).flatMap(userAccount -> paymentRepository.save(payment).switchIfEmpty(Mono.error(new PaymentNotCreatedException("Failed to create payment for User Account ID: " + command.userAccountId()))));
    }

    public Mono<Payment> getPaymentById(Long paymentId) {
        return paymentRepository.getById(paymentId).switchIfEmpty(Mono.error(new PaymentNotCreatedException("Payment not found for ID: " + paymentId)));
    }

    public Mono<Void> updatePaymentStatus(Long paymentId, String status) {
        validateStatusIsValid(status);

        return paymentRepository.getById(paymentId).switchIfEmpty(Mono.error(new PaymentNotCreatedException("Payment not found for ID: " + paymentId))).flatMap(payment -> {
            if (payment.getStatus().equals(StatusPaymentEnum.COMPLETED.name())) {
                return Mono.error(new StatusPaymentNotValidException("Cannot update status of a COMPLETED payment with ID: " + paymentId));
            }

            return paymentRepository.updateStatus(paymentId, status);
        });
    }

    public Flux<Payment> getAllPayments(PaymentCriteria criteria) {
        return paymentRepository.getAllByFilters(criteria);
    }


    private void validateStatusIsValid(String status) {
        try {
            StatusPaymentEnum.valueOf(status);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new StatusPaymentNotValidException("Invalid status: " + status);
        }
    }

}
