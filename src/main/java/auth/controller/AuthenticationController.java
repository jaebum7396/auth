package auth.controller;

import auth.model.LoginRequest;
import auth.model.ResponseResult;
import auth.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "AuthenticationController")
@Tag(name = "AuthenticationController", description = "회원가입, 로그인, 유저정보")
@RestController
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping(value = "/auth/login")
    @Operation(summary="로그인", description="가입한 회원을 로그인 하는 API")
    @ApiResponses({
        @ApiResponse(code = 200, message="ok",response = ResponseResult.class),
        @ApiResponse(code = 400, message="잘못된 요청",response = ResponseResult.class),
        @ApiResponse(code = 500, message="서버 에러",response = ResponseResult.class)
    })
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) throws Exception {
        return authenticationService.login(loginRequest);
    }
}