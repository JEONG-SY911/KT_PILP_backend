package KT.PLIP.population.dto;

import lombok.Data;

@Data
public class DayNightPopulationDto {
    private Double dayPopulation;
    private Double nightPopulation;
    private Double dayNightRatio;
}
