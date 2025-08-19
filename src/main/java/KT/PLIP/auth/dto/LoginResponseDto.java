package KT.PLIP.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private Long id;
    private String username;
    private String email;
    private String nickname;
}
