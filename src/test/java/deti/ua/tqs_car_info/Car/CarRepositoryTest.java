package deti.ua.tqs_car_info.Car;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

	@Test
	public void whenFindAll_returnAllCars() {
		Car c1 = new Car("Audi", "Q3");
        Car c2 = new Car("Audi", "Q5");
        Car c3 = new Car("Mitsubishi", "Galant");

        entityManager.persist(c1);
        entityManager.persist(c2);
        entityManager.persist(c3);
        entityManager.flush();

		List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(c1.getMaker(), c2.getMaker(), c3.getMaker());
	}

	@Test
	public void whenFindCarById_returnCar() {
		Car c1 = new Car("Audi", "Q3");

		entityManager.persistAndFlush(c1);

		Car found = carRepository.findByCarId(c1.getCarId());
		assertThat( found ).isEqualTo( c1 );
	}

	@Test
	public void whenFindCarByInvalidId_returnNull() {
		Car found = carRepository.findByCarId(-99L);
		assertThat( found ).isNull();
	}
}