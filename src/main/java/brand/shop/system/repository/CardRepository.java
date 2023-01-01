package brand.shop.system.repository;

import brand.shop.system.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    Card findByUserId(Integer userId);

}
