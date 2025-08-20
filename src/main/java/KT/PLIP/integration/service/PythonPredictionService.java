package KT.PLIP.integration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PythonPredictionService {
	private final RestTemplate restTemplate;

	public String getPrediction(String baseUrl, String dataCsv) {
		String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
				.path("/predict")
				.queryParam("data", dataCsv)
				.toUriString();
		return restTemplate.getForObject(url, String.class);
	}
}
