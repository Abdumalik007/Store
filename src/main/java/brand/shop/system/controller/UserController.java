package brand.shop.system.controller;


import brand.shop.system.dto.JwtResponseDto;
import brand.shop.system.dto.LoginDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.dto.UserDto;
import brand.shop.system.service.main.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/registration-customer")
    public ResponseDto<String> addUserCustomer(@RequestBody @Valid UserDto userDto){
        return userService.registerUserCustomer(userDto);
    }

    @PostMapping("/registration-admin")
    public ResponseDto<String> addUser(@RequestBody @Valid UserDto userDto){
        return userService.registerUserAdmin(userDto);
    }

    @PutMapping("/login")
    public ResponseDto<JwtResponseDto> login(@RequestBody @Valid LoginDto loginDto){
        return userService.login(loginDto);
    }

    @PutMapping("/update")
    public ResponseDto<UserDto> updateUser(@RequestBody @Valid UserDto userDto){
        return userService.updateUser(userDto);
    }

    @GetMapping("/confirm")
    public ResponseDto<String> confirmToken(@RequestParam String token){
        return userService.confirmToken(token);
    }

    @GetMapping("/get")
    public ResponseDto<UserDto> getUser(@RequestParam Integer id){
        return userService.getUser(id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<Boolean> deleteUser(@RequestParam Integer id){
        return userService.deleteUser(id);
    }

}
