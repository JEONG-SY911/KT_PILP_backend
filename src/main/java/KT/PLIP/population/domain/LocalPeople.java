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
    private Integer totLvpopCo;
    
    @Column(name = "male_f0t9_lvpop_co")
    private Integer maleF0t9LvpopCo;
    
    @Column(name = "male_f10t14_lvpop_co")
    private Integer maleF10t14LvpopCo;
    
    @Column(name = "male_f15t19_lvpop_co")
    private Integer maleF15t19LvpopCo;
    
    @Column(name = "male_f20t24_lvpop_co")
    private Integer maleF20t24LvpopCo;
    
    @Column(name = "male_f25t29_lvpop_co")
    private Integer maleF25t29LvpopCo;
    
    @Column(name = "male_f30t34_lvpop_co")
    private Integer maleF30t34LvpopCo;
    
    @Column(name = "male_f35t39_lvpop_co")
    private Integer maleF35t39LvpopCo;
    
    @Column(name = "male_f40t44_lvpop_co")
    private Integer maleF40t44LvpopCo;
    
    @Column(name = "male_f45t49_lvpop_co")
    private Integer maleF45t49LvpopCo;
    
    @Column(name = "male_f50t54_lvpop_co")
    private Integer maleF50t54LvpopCo;
    
    @Column(name = "male_f55t59_lvpop_co")
    private Integer maleF55t59LvpopCo;
    
    @Column(name = "male_f60t64_lvpop_co")
    private Integer maleF60t64LvpopCo;
    
    @Column(name = "male_f65t69_lvpop_co")
    private Integer maleF65t69LvpopCo;
    
    @Column(name = "male_f70t74_lvpop_co")
    private Integer maleF70t74LvpopCo;
    
    @Column(name = "female_f0t9_lvpop_co")
    private Integer femaleF0t9LvpopCo;
    
    @Column(name = "female_f10t14_lvpop_co")
    private Integer femaleF10t14LvpopCo;
    
    @Column(name = "female_f15t19_lvpop_co")
    private Integer femaleF15t19LvpopCo;
    
    @Column(name = "female_f20t24_lvpop_co")
    private Integer femaleF20t24LvpopCo;
    
    @Column(name = "female_f25t29_lvpop_co")
    private Integer femaleF25t29LvpopCo;
    
    @Column(name = "female_f30t34_lvpop_co")
    private Integer femaleF30t34LvpopCo;
    
    @Column(name = "female_f35t39_lvpop_co")
    private Integer femaleF35t39LvpopCo;
    
    @Column(name = "female_f40t44_lvpop_co")
    private Integer femaleF40t44LvpopCo;
    
    @Column(name = "female_f45t49_lvpop_co")
    private Integer femaleF45t49LvpopCo;
    
    @Column(name = "female_f50t54_lvpop_co")
    private Integer femaleF50t54LvpopCo;
    
    @Column(name = "female_f55t59_lvpop_co")
    private Integer femaleF55t59LvpopCo;
    
    @Column(name = "female_f60t64_lvpop_co")
    private Integer femaleF60t64LvpopCo;
    
    @Column(name = "female_f65t69_lvpop_co")
    private Integer femaleF65t69LvpopCo;
    
    @Column(name = "female_f70t74_lvpop_co")
    private Integer femaleF70t74LvpopCo;
    
    @Column(name = "stdr_de_id")
    private String stdrDeId;
    
    @Column(name = "tmzon_pd_se")
    private String tmzonPdSe;
    
    @Column(name = "adstrd_code_se")
    private String adstrdCodeSe;
}
