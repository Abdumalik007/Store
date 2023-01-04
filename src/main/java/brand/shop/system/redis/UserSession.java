package brand.shop.system.redis;

import brand.shop.system.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(timeToLive = 60 * 60 * 24 * 100)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    @Id
    private Integer id;
    private UserDto userDto;
}
