package br.com.dennon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {
	
	@GetMapping(value = "/sum/{numberOne}/{numberTwo}")
	public ResponseEntity<Double> sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo")  String numberTwo
			) {
		
		CalculatorServices.validationNumber(numberOne, numberTwo);
		
		return ResponseEntity.ok().body(CalculatorServices.sum(numberOne, numberTwo));
	}
	
	@GetMapping(value = "/sub/{numberOne}/{numberTwo}")
	public ResponseEntity<Double> sub(
			@PathVariable(value = "numberOne") String numberOne, 
			@PathVariable(value = "numberTwo") String numberTwo) {
		
		CalculatorServices.validationNumber(numberOne, numberTwo);
		
		return ResponseEntity.ok().body(CalculatorServices.sub(numberOne, numberTwo));
	}
	
	@GetMapping(value = "/mul/{numberOne}/{numberTwo}")
	public ResponseEntity<Double> mul(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) {
		
		CalculatorServices.validationNumber(numberOne, numberTwo);
		
		return ResponseEntity.ok().body(CalculatorServices.mul(numberOne, numberTwo));
	}
	
	@GetMapping(value = "/div/{numberOne}/{numberTwo}")
	public ResponseEntity<Double> div(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) {
		
		CalculatorServices.validationNumber(numberOne, numberTwo);
		
		return ResponseEntity.ok().body(CalculatorServices.div(numberOne, numberTwo));
	}
	
	@GetMapping(value = "/avg/{numberOne}/{numberTwo}")
	public ResponseEntity<Double> avg(
			@PathVariable(value = "numberOne") String numberOne, 
			@PathVariable(value = "numberTwo") String numberTwo) {
		
		CalculatorServices.validationNumber(numberOne, numberTwo);
		
		return ResponseEntity.ok().body(CalculatorServices.avg(numberOne, numberTwo));
	}
	
	@GetMapping(value = "/raiz/{number}")
	public ResponseEntity<Double> raiz(
			@PathVariable(value = "number") String number) {
		
		CalculatorServices.validationNumber(number, null);
		
		return ResponseEntity.ok().body(CalculatorServices.raiz(number));
	}
}
