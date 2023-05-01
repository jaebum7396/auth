package auth.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import auth.model.Response;

@RestControllerAdvice
public class ErrorResponseAdvice {
	private Logger logger = LoggerFactory.getLogger(ErrorResponseAdvice.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleException(Exception e) {
		Response responseResult;
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		e.printStackTrace();
        responseResult = Response.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("서버쪽 오류가 발생했습니다. 관리자에게 문의하십시오")
                .result(resultMap).build();
        return ResponseEntity.internalServerError().body(responseResult);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity handleBadCredentialsException(BadCredentialsException e) {
		Response responseResult;
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		e.printStackTrace();
        responseResult = Response.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED)
                .message("잘못된 접근입니다.")
                .result(resultMap).build();
        return ResponseEntity.internalServerError().body(responseResult);
	}
}
