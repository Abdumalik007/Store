package brand.shop.system.service.main;


import brand.shop.system.model.ConfirmationToken;
import brand.shop.system.model.User;

import java.time.LocalDateTime;

public interface ConfirmationService {
    String saveConfirmationToken(User user);

    ConfirmationToken getConfirmationByToken(String token);

    void setConfirmationTime(LocalDateTime date, String token);

    void deleteConfirmationByUserId(Integer userId);
}
