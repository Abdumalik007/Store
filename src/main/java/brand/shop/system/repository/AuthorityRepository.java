package brand.shop.system.repository;

import brand.shop.system.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Set<Authority> findAllByNameIn(Set<String> names);

}
