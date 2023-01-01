package brand.shop.system.repository;

import brand.shop.system.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetails, Integer> {
    void deleteByOrderDetailsId(Integer orderDetailsId);
}
