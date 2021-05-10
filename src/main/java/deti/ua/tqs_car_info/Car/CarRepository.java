package deti.ua.tqs_car_info.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CarRepository  extends JpaRepository<Car, Long> {

    public Car findByCarId(Long carId);
	public List<Car> findAll();
	
}