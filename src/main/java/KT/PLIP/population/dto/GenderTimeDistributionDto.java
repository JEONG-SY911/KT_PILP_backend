package KT.PLIP.population.dto;

import lombok.Data;
import java.util.Map;

@Data
public class GenderTimeDistributionDto {
    private Map<String, Double> male;
    private Map<String, Double> female;
}
