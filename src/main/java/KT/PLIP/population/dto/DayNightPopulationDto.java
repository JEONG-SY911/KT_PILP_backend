package KT.PLIP.population.dto;

import lombok.Data;

@Data
public class DayNightPopulationDto {
    private Integer dayPopulation;
    private Integer nightPopulation;
    private Double dayNightRatio;
}
