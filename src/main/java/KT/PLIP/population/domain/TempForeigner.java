package KT.PLIP.population.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "temp_foreigner")
@Getter
@Setter
public class TempForeigner {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tot_lvpop_co")
    private Integer totLvpopCo;
    
    @Column(name = "china_staypop_co")
    private Double chinaStaypopCo;
    
    @Column(name = "other_staypop_co")
    private Double otherStaypopCo;
    
    @Column(name = "oa_cd")
    private String oaCd;
    
    @Column(name = "stdr_de_id")
    private String stdrDeId;
    
    @Column(name = "tmzon_pd_se")
    private String tmzonPdSe;
    
    @Column(name = "adstrd_code_se")
    private String adstrdCodeSe;
}
