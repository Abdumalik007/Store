package brand.shop.system.repository;

import brand.shop.system.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    OrderDetails findByUserIdAndCreatedAtAfter(Integer userId, Date after);

}
