package brand.shop.system.service.impl;

import brand.shop.system.dto.JwtResponseDto;
import brand.shop.system.dto.LoginDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.dto.UserDto;
import brand.shop.system.helper.AppCode;
import brand.shop.system.helper.AppMessage;
import brand.shop.system.helper.DateUtil;
import brand.shop.system.mapper.UserCustomMapper;
import brand.shop.system.model.Authority;
import brand.shop.system.model.ConfirmationToken;
import brand.shop.system.model.User;
import brand.shop.system.redis.UserSession;
import brand.shop.system.redis.UserSessionRepository;
import brand.shop.system.repository.AuthorityRepository;
import brand.shop.system.repository.UserAuthorityRepository;
import brand.shop.system.repository.UserRepository;
import brand.shop.system.security.JwtUtil;
import brand.shop.system.security.roles.Role;
import brand.shop.system.service.main.ConfirmationService;
import brand.shop.system.service.main.EmailSender;
import brand.shop.system.service.main.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserSessionRepository userSessionRepository;
    private final JwtUtil jwtUtil;
    private final UserAuthorityRepository userAuthorityRepository;

    private final EmailSender emailSender;

    private final PasswordEncoder encoder;

    private final ConfirmationService confirmationService;


    @Override
    public ResponseDto<String> registerUserCustomer(UserDto userDto) {
        try {

            userDto.setCreatedAt(DateUtil.getCurrentDate());

            User user = setAdditionalFields(userDto);
            user.setPassword(encoder.encode(user.getPassword()));

            userRepository.save(user);

            userDto.setId(user.getId());
            userDto.setCreatedAt(user.getCreatedAt());

            String token = confirmationService.saveConfirmationToken(user);
            String link = "localhost:8000/api/user/confirm?token=".concat(token);

            emailSender.send(userDto.getEmail(), buildEmail(user.getFirstname(), link));

            return ResponseDto.buildResponse(token, AppCode.OK, AppMessage.OK, true);

        }catch (Exception e){
            log.error("Error while saving user customer into database !!!");
            return ResponseDto.buildResponse(null, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }

    @Override
    public ResponseDto<String> registerUserAdmin(UserDto userDto) {
        try {
            userDto.setCreatedAt(DateUtil.getCurrentDate());
            User user = setAdditionalFields(userDto);

            userRepository.save(user);


            userDto.setId(user.getId());
            userDto.setCreatedAt(user.getCreatedAt());

            String token = confirmationService.saveConfirmationToken(user);

            String link = "http://localhost:8000/api/user/confirm?token=".concat(token);

            emailSender.send(userDto.getEmail(), buildEmail(user.getFirstname(), link));

            return ResponseDto.buildResponse(token, AppCode.OK, AppMessage.OK, true);

        }catch (Exception e){
            e.printStackTrace();
            log.error("Error while saving user admin into database !!!");
            return ResponseDto.buildResponse(null, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }

    @Override
    public ResponseDto<UserDto> updateUser(UserDto userDto) {
        if(!userRepository.existsById(userDto.getId()))
            return ResponseDto.buildResponse(null, AppCode.NOT_FOUND, AppMessage.NOT_FOUND, false);
        try {
            User user = UserCustomMapper.toEntity(userDto);

            userDto.setModifiedAt(DateUtil.getCurrentDate());
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseDto.buildResponse(userDto, AppCode.OK, AppMessage.OK, true);
        }catch (Exception e){
            log.error("Error while updating user !!!");
            return ResponseDto.buildResponse(null, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }


    @Override
    public ResponseDto<UserDto> getUser(Integer id) {
        try {
            Optional<User> optional = userRepository.findById(id);
            if(optional.isPresent()){
                UserDto userDto = optional.map(UserCustomMapper::toDto).get();
                return ResponseDto.buildResponse(userDto, AppCode.OK, AppMessage.OK, true);
            }else {
                return ResponseDto.buildResponse(null, AppCode.NOT_FOUND, AppMessage.NOT_FOUND, false);
            }
        }catch (Exception e){
            log.error("Error while getting user by id !!!");
            return ResponseDto.buildResponse(null, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }

    @Override
    public ResponseDto<Boolean> deleteUser(Integer id) {
        try {
            if(!userRepository.existsById(id))
                return ResponseDto.buildResponse(false, AppCode.NOT_FOUND, AppMessage.NOT_FOUND, false);

            userRepository.deleteById(id);
            return ResponseDto.buildResponse(true, AppCode.OK, AppMessage.OK, true);
        }catch (Exception e){
            log.error("Error while deleting user by id !!!");
            return ResponseDto.buildResponse(false, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }

    @Override
    public ResponseDto<JwtResponseDto> login(LoginDto loginDto) {
        Optional<User> optional = userRepository.findByEmail(loginDto.getEmail());

        if(optional.isEmpty() || encoder.matches(loginDto.getPassword(), optional.get().getPassword()))
            return ResponseDto.buildResponse(null, AppCode.NOT_FOUND,
                    "Email or password is incorrect !!!", false);

        if(!optional.get().isEnabled())
            return ResponseDto.buildResponse(null, AppCode.ERROR,
                    "User has not confirm his email!", false);

        Set<SimpleGrantedAuthority> authorities =
                userAuthorityRepository.getAuthorities(optional.get())
                        .stream().map(auth -> new SimpleGrantedAuthority(auth.getName()))
                        .collect(Collectors.toSet());
        try {

            UserDto userDto = optional.map(UserCustomMapper::toDto).get();
            userDto.setAuthorities(authorities);

            UserSession userSession = new UserSession(userDto.getId(), userDto);
            userSessionRepository.save(userSession);

            String token = jwtUtil.generateToken(String.valueOf(userDto.getId()));

            JwtResponseDto jwt = new JwtResponseDto(token, DateUtil.getCurrentDate(), DateUtil.getDateAfterFiveHour());
            return ResponseDto.buildResponse(jwt, AppCode.OK, AppMessage.OK, true);
        }catch (Exception e){
            log.error("Error while user login: ".concat(e.getMessage()));
            return ResponseDto.buildResponse(null, AppCode.ERROR, AppMessage.ERROR, false);
        }

    }

    @Override
    public ResponseDto<String> confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationService.getConfirmationByToken(token);

        if(confirmationToken == null)
            return ResponseDto.buildResponse("Token not found",
                    AppCode.NOT_FOUND, AppMessage.NOT_FOUND, false);

        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            return ResponseDto.buildResponse("Token has expired", AppCode.ERROR, AppMessage.ERROR, false);

        confirmationService.setConfirmationTime(LocalDateTime.now(), confirmationToken.getToken());
        User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        return ResponseDto.buildResponse("Confirmed successfully", AppCode.OK, AppMessage.OK, true);
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


    private User setAdditionalFields(UserDto userDto){
        User user = UserCustomMapper.toEntity(userDto);
        Set<Authority> authorities = authorityRepository
                .findAllByNameIn(Role.ADMIN.getGrantedAuthoritiesNames());

        user.setAuthorities(authorities);
        user.setCreatedAt(DateUtil.getCurrentDate());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(false);
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);

        return user;
    }


}