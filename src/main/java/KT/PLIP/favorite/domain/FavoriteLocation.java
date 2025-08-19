package KT.PLIP.favorite.domain;

import KT.PLIP.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_locations")
@Getter
@Setter
public class FavoriteLocation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "adstrd_code_se", nullable = false)
    private String adstrdCodeSe;
    
    @Column(name = "dong_name", nullable = false)
    private String dongName;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
