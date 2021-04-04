package lab.aulaDIO.payment_api.config;

import lab.aulaDIO.payment_api.streaming.CheckoutProcessor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(CheckoutProcessor.class)
public class StreamingConfig {
}
