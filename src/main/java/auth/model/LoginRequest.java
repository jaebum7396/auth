package auth.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @ApiModelProperty(value="userId", example="gildongh0366", required=true)
    private String userId;
    @ApiModelProperty(value="userPw", example="pwd0366", required=true)
    private String userPw;
}