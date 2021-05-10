package deti.ua.tqs_car_info.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {
    @Autowired
    private CarManagerService carManagerService;

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carManagerService.save(car);

        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
		return carManagerService.getAllCars();
    }

	@GetMapping("/car/{carId}")
	public ResponseEntity<Car> getCarById(@PathVariable(value = "carId") Long carId) {
        Car c = carManagerService.getCarDetails(carId);
		return ResponseEntity.ok().body(c);
	}

}