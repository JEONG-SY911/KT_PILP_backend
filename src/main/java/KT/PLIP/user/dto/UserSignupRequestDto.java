package KT.PLIP.user.dto;

import lombok.Data;

@Data
public class UserSignupRequestDto {
    private String username;
    private String email;
    private String password;
    private String nickname;
}
