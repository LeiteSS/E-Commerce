package lab.aulaDIO.payment_api.repository;

import lab.aulaDIO.payment_api.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
