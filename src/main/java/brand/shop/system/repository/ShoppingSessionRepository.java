package brand.shop.system.repository;

import brand.shop.system.model.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession, Integer> {

    Optional<ShoppingSession> findByUserId(Integer integer);

    void deleteByUserId(Integer integer);

}
