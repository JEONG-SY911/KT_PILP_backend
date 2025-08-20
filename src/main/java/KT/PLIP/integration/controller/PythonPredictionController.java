package KT.PLIP.integration.controller;

import KT.PLIP.integration.service.PythonPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integration/python")
@RequiredArgsConstructor
public class PythonPredictionController {
	private final PythonPredictionService pythonPredictionService;

	// 예: GET /integration/python/predict?data=1,2,3&base=http://localhost:5000
	@GetMapping("/predict")
	public ResponseEntity<String> proxyPredict(
			@RequestParam String data,
			@RequestParam(defaultValue = "http://localhost:5000") String base
	) {
		String result = pythonPredictionService.getPrediction(base, data);
		return ResponseEntity.ok(result);
	}
}
