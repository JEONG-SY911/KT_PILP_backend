package KT.PLIP.population.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "gangnam_population")
@Getter
@Setter
public class GangnamPopulation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "dong_name")
    private String dongName;
    
    @Column(name = "adstrd_code")
    private String adstrdCodeSe;
}
