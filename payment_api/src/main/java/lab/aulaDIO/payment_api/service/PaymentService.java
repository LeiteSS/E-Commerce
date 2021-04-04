package lab.aulaDIO.payment_api.service;

import lab.aulaDIO.payment_api.entity.PaymentEntity;
import lab.aulaDIO.payment_api.event.CheckoutCreatedEvent;

import java.util.Optional;

public interface PaymentService {
    Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent);
}
