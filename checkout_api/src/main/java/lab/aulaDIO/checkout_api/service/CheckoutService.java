package lab.aulaDIO.checkout_api.service;

import lab.aulaDIO.checkout_api.entity.CheckoutEntity;
import lab.aulaDIO.checkout_api.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);

    Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status);
}
