package brand.shop.system.repository;

import brand.shop.system.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findAllByShoppingSessionId(Integer sessionId);
    void deleteByShoppingSessionId(Integer sessionId);

}
