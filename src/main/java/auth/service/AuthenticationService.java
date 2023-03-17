package auth.service;

import auth.jwt.JwtProvider;
import auth.model.CustomUserDetails;
import auth.model.LoginRequest;
import auth.model.ResponseResult;
import auth.model.UserEntity;
import auth.repository.AuthenticationRepository;
import auth.utils.AES128Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;
    private final AES128Util aes128Util = new AES128Util();
    public ResponseEntity login(LoginRequest loginRequest) throws Exception {
        ResponseResult responseResult;
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try{
            UserEntity userEntity = authenticationRepository.findByUserId(loginRequest.getUserId()).orElseThrow(() ->
                new BadCredentialsException(loginRequest.getUserId()+": 아이디가 존재하지 않습니다."));
            if (!passwordEncoder.matches(loginRequest.getUserPw(), userEntity.getUserPw())) {
                throw new BadCredentialsException("잘못된 비밀번호입니다.");
            }
            resultMap.put("userId", userEntity.getUserId());
            resultMap.put("name", userEntity.getUserNm());
            resultMap.put("roles", userEntity.getRoles());
            resultMap.put("token", jwtProvider.createToken(userEntity.getUserId(), userEntity.getRoles()));

            responseResult = ResponseResult.builder()
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("로그인 성공")
                    .result(resultMap).build();
            return ResponseEntity.ok().body(responseResult);
        }catch(BadCredentialsException be){
            System.out.println(be.getMessage());
            responseResult = ResponseResult.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .message(be.getMessage())
                    .result(resultMap).build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseResult);
        }catch(Exception e){
            e.printStackTrace();
            responseResult = ResponseResult.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("서버쪽 오류가 발생했습니다. 관리자에게 문의하십시오")
                    .result(resultMap).build();
            return ResponseEntity.internalServerError().body(responseResult);
        }
    }
    public boolean loginPasswordValidate(LoginRequest loginRequest, UserEntity userEntity) {
        boolean check = passwordEncoder.matches(loginRequest.getUserPw(), userEntity.getUserPw());
        return check;
    }
    @Override
    public UserDetails loadUserByUsername(String userNm) throws UsernameNotFoundException {
        UserEntity userEntity = authenticationRepository.findByUserNm(userNm).orElseThrow(
            () -> new UsernameNotFoundException("Invalid authentication!")
        );
        return new CustomUserDetails(userEntity);
    }
}