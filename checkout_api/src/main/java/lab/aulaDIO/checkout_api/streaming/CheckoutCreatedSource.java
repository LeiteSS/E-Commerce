package lab.aulaDIO.checkout_api.streaming;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CheckoutCreatedSource {

    String OUTPUT = "checkout-created-output";

    @Output(OUTPUT)
    MessageChannel output();
}
