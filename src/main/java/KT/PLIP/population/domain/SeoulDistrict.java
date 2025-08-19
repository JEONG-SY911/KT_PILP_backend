package KT.PLIP.population.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seoul_districts")
@Getter @Setter
public class SeoulDistrict {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "district_code", unique = true, nullable = false)
    private String districtCode;        // 구 코드 (예: 11680)
    
    @Column(name = "district_name", nullable = false)
    private String districtName;        // 구 이름 (예: 강남구)
    
    @Column(name = "district_name_eng")
    private String districtNameEng;     // 영문 이름 (예: Gangnam-gu)
    
    @Column(name = "description")
    private String description;         // 구 설명
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;    // 활성화 여부
    
    @Column(name = "sort_order")
    private Integer sortOrder;          // 정렬 순서
}
