package brand.shop.system.repository;

import brand.shop.system.model.Discount;
import brand.shop.system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByProductCategoryId(Integer id);

    List<Product> findAllByIdIn(List<Integer> list);

    List<Product> findAllByDiscount(Discount discount);

}
