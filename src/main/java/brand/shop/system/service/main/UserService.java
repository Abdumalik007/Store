package brand.shop.system.service.main;

import brand.shop.system.dto.JwtResponseDto;
import brand.shop.system.dto.LoginDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.dto.UserDto;

public interface UserService {

    ResponseDto<String> registerUserCustomer(UserDto userDto);


    ResponseDto<String> registerUserAdmin(UserDto userDto);

    ResponseDto<UserDto> updateUser(UserDto userDto);

    ResponseDto<UserDto> getUser(Integer id);

    ResponseDto<Boolean> deleteUser(Integer id);

    ResponseDto<JwtResponseDto> login(LoginDto loginDto);

    ResponseDto<String> confirmToken(String token);
}
