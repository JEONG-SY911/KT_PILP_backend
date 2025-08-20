package KT.PLIP.population.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "local_people")
@Getter
@Setter
public class LocalPeople {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tot_lvpop_co")
    private Double totLvpopCo;
    
    @Column(name = "male_f0t9_lvpop_co")
    private Double maleF0t9LvpopCo;
    
    @Column(name = "male_f10t14_lvpop_co")
    private Double maleF10t14LvpopCo;
    
    @Column(name = "male_f15t19_lvpop_co")
    private Double maleF15t19LvpopCo;
    
    @Column(name = "male_f20t24_lvpop_co")
    private Double maleF20t24LvpopCo;
    
    @Column(name = "male_f25t29_lvpop_co")
    private Double maleF25t29LvpopCo;
    
    @Column(name = "male_f30t34_lvpop_co")
    private Double maleF30t34LvpopCo;
    
    @Column(name = "male_f35t39_lvpop_co")
    private Double maleF35t39LvpopCo;
    
    @Column(name = "male_f40t44_lvpop_co")
    private Double maleF40t44LvpopCo;
    
    @Column(name = "male_f45t49_lvpop_co")
    private Double maleF45t49LvpopCo;
    
    @Column(name = "male_f50t54_lvpop_co")
    private Double maleF50t54LvpopCo;
    
    @Column(name = "male_f55t59_lvpop_co")
    private Double maleF55t59LvpopCo;
    
    @Column(name = "male_f60t64_lvpop_co")
    private Double maleF60t64LvpopCo;
    
    @Column(name = "male_f65t69_lvpop_co")
    private Double maleF65t69LvpopCo;
    
    @Column(name = "male_f70t74_lvpop_co")
    private Double maleF70t74LvpopCo;
    
    @Column(name = "female_f0t9_lvpop_co")
    private Double femaleF0t9LvpopCo;
    
    @Column(name = "female_f10t14_lvpop_co")
    private Double femaleF10t14LvpopCo;
    
    @Column(name = "female_f15t19_lvpop_co")
    private Double femaleF15t19LvpopCo;
    
    @Column(name = "female_f20t24_lvpop_co")
    private Double femaleF20t24LvpopCo;
    
    @Column(name = "female_f25t29_lvpop_co")
    private Double femaleF25t29LvpopCo;
    
    @Column(name = "female_f30t34_lvpop_co")
    private Double femaleF30t34LvpopCo;
    
    @Column(name = "female_f35t39_lvpop_co")
    private Double femaleF35t39LvpopCo;
    
    @Column(name = "female_f40t44_lvpop_co")
    private Double femaleF40t44LvpopCo;
    
    @Column(name = "female_f45t49_lvpop_co")
    private Double femaleF45t49LvpopCo;
    
    @Column(name = "female_f50t54_lvpop_co")
    private Double femaleF50t54LvpopCo;
    
    @Column(name = "female_f55t59_lvpop_co")
    private Double femaleF55t59LvpopCo;
    
    @Column(name = "female_f60t64_lvpop_co")
    private Double femaleF60t64LvpopCo;
    
    @Column(name = "female_f65t69_lvpop_co")
    private Double femaleF65t69LvpopCo;
    
    @Column(name = "female_f70t74_lvpop_co")
    private Double femaleF70t74LvpopCo;
    
    @Column(name = "stdr_de_id")
    private String stdrDeId;
    
    @Column(name = "tmzon_pd_se")
    private String tmzonPdSe;
    
    @Column(name = "adstrd_code_se")
    private String adstrdCodeSe;
}
