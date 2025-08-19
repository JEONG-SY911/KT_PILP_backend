package KT.PLIP.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {
    private String message;
    private Long id;
    private String username;
    private String email;
    private String nickname;
}
