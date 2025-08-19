package KT.PLIP.favorite.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FavoriteLocationResponseDto {
    private Long id;
    private String adstrdCodeSe;
    private String dongName;
    private LocalDateTime createdAt;
}
