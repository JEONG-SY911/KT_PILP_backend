package KT.PLIP.population.dto;

import lombok.Data;

@Data
public class DailyStatsDto {
    private Double averagePopulation;
    private Double maxPopulation;
    private Double minPopulation;
    private String date;
}