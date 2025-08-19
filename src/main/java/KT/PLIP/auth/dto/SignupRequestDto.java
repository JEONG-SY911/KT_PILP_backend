package KT.PLIP.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String email;
    private String password;
    private String nickname;
}
