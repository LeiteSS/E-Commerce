package lab.aulaDIO.checkout_api.service;

import lab.aulaDIO.checkout_api.entity.CheckoutEntity;
import lab.aulaDIO.checkout_api.event.CheckoutCreatedEvent;
import lab.aulaDIO.checkout_api.repository.CheckoutRepository;
import lab.aulaDIO.checkout_api.resource.checkout.CheckoutRequest;
import lab.aulaDIO.checkout_api.streaming.CheckoutCreatedSource;
import lab.aulaDIO.checkout_api.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;
    private final UUIDUtil uuidUtil;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
//        log.info("M=create, checkoutRequest={}", checkoutRequest);
//        final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
//                .code(uuidUtil.createUUID().toString())
//                .status(CheckoutEntity.Status.CREATED)
//                .saveAddress(checkoutRequest.getSaveAddress())
//                .saveInformation(checkoutRequest.getSaveInfo())
//                .shipping(ShippingEntity.builder()
//                        .address(checkoutRequest.getAddress())
//                        .complement(checkoutRequest.getComplement())
//                        .country(checkoutRequest.getCountry())
//                        .state(checkoutRequest.getState())
//                        .cep(checkoutRequest.getCep())
//                        .build())
//                .build();
//        checkoutEntity.setItems(checkoutRequest.getProducts()
//                .stream()
//                .map(product -> CheckoutItemEntity.builder()
//                        .checkout(checkoutEntity)
//                        .product(product)
//                        .build())
//                .collect(Collectors.toList()));
//        final CheckoutEntity entity = checkoutRepository.save(checkoutEntity);
//        final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
//                .setCheckoutCode(entity.getCode())
//                .setStatus(entity.getStatus().name())
//                .build();
//        checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());
//        return Optional.of(entity);
        final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
                .code(uuidUtil.createUUID().toString())
                .status(CheckoutEntity.Status.CREATED)
                .build();
        final CheckoutEntity entity = checkoutRepository.save(checkoutEntity);
        final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
                .setCheckoutCode(entity.getCode())
                .setStatus(entity.getStatus().name())
                .build();
        checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());

        return Optional.of(entity);

    }

    @Override
    public Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status) {
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(checkoutCode).orElse(CheckoutEntity.builder().build());
        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);
        return Optional.of(checkoutRepository.save(checkoutEntity));
    }
}
