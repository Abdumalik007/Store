package brand.shop.system.repository;

import brand.shop.system.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
    void deleteByOrderDetailsId(Integer orderDetailsId);

    List<OrderItems> findByOrderByAmountAsc();

}
