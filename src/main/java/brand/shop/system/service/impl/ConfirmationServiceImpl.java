package brand.shop.system.service.impl;

import brand.shop.system.model.ConfirmationToken;
import brand.shop.system.model.User;
import brand.shop.system.repository.ConfirmationTokenRepository;
import brand.shop.system.service.main.ConfirmationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public String saveConfirmationToken(User user) {
        try {
            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = ConfirmationToken.builder()
                    .token(token)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(15))
                    .user(user)
                    .build();
            confirmationTokenRepository.save(confirmationToken);
            return token;
        }catch (Exception e){
            log.error("Error while saving confirmation token into database !!!");
        }
        return null;
    }

    @Override
    public ConfirmationToken getConfirmationByToken(String token) {
        try {
            return confirmationTokenRepository.findByToken(token);
        }catch (Exception e){
            log.error("Error while getting ConfirmationToken by token !!!");
            return null;
        }
    }

    @Override
    public void setConfirmationTime(LocalDateTime date, String token) {
        try {
            confirmationTokenRepository.setConfirmedAt(date, token);
        }catch (Exception e){
            log.error("Error while setting confirmation time !!!");
        }
    }

    @Override
    public void deleteConfirmationByUserId(Integer userId) {
        try {
            confirmationTokenRepository.deleteByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error while deleting confirmation token by user id !!!");
        }
    }


}
