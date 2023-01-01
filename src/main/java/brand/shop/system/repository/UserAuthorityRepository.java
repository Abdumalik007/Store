package brand.shop.system.repository;

import brand.shop.system.model.Authority;
import brand.shop.system.model.User;
import brand.shop.system.model.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Integer> {

    @Query(value = "select u.authority from UserAuthority u where u.user = :user")
    Set<Authority> getAuthorities(User user);

}
