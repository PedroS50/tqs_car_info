package deti.ua.tqs_car_info.Car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CarManagerServiceTest {

    // lenient is required because we load some expectations in the setup
    // that are not used in all the tests. As an alternative, the expectations
    // could move into each test method and be trimmed: no need for lenient
    @Mock( lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carService;

    @BeforeEach
    public void setUp() {
        Car c1 = new Car("Audi", "Q3");
        c1.setCarId(111L);

        Car c2 = new Car("Audi", "Q5");
        Car c3 = new Car("Mitsubishi", "Galant");

        List<Car> allCars = Arrays.asList(c1, c2, c3);

        Mockito.when(carRepository.findByCarId(c1.getCarId())).thenReturn(c1);
		Mockito.when(carRepository.findByCarId(-99L)).thenReturn(null);
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
    }

	@Test
	public void whenValidCarId_returnCar() {
		String maker = "Audi";
		Car found = carService.getCarDetails(111L);

		assertThat( found.getMaker() ).isEqualTo( maker );
	}

	@Test
	public void whenInvalidCarId_returnCarNotFound() {
		Car fromDb = carService.getCarDetails(-99L);

		verifyFindByCarIdIsCalledOnce();

		assertThat( fromDb ).isNull();
	}

	@Test
	public void whenGetAllCars_returnCarsCollection() {
		List<Car> allCars = carService.getAllCars();
		
		verifyFindAllIsCalledOnce();
		
		assertThat( allCars ).hasSize( 3 ).extracting( Car::getMaker ).contains("Audi", "Audi", "Mitsubishi");
	}

	private void verifyFindByCarIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(Mockito.anyLong());
    }

	private void verifyFindAllIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
