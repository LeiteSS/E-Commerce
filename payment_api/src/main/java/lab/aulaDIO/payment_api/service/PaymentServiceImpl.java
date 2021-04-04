package lab.aulaDIO.payment_api.service;

import lab.aulaDIO.payment_api.entity.PaymentEntity;
import lab.aulaDIO.payment_api.event.CheckoutCreatedEvent;
import lab.aulaDIO.payment_api.service.PaymentService;
import lab.aulaDIO.payment_api.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent) {
        final PaymentEntity paymentEntity = PaymentEntity.builder()
                .checkoutCode(checkoutCreatedEvent.getCheckoutCode().toString())
                .code(UUID.randomUUID().toString())
                .build();
        paymentRepository.save(paymentEntity);
        return Optional.of(paymentEntity);
    }
}
