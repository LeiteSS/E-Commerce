package lab.aulaDIO.checkout_api;

import io.cucumber.spring.CucumberContextConfiguration;
import lab.aulaDIO.checkout_api.setup.PostgreSQLSetup;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@CucumberContextConfiguration
@SpringBootTest(classes = {
        CheckoutApiApplication.class,
        PostgreSQLSetup.class
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8888)
public class SpringIntegrationTest {
}