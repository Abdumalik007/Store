package brand.shop.system.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSessionRepository extends CrudRepository<OrderSession, Integer> {
}
