package deti.ua.tqs_car_info.Car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

	@Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService carService;

    @BeforeEach
    public void setUp() {
    }

	@AfterEach
	public void tearDown() {
	}

	@Test
	void whenCreateCar_returnSavedCar() throws Exception {
		Car c1 = new Car("Audi", "Q3");
		when( carService.save( Mockito.any()) ).thenReturn( c1 );

		mvc.perform( post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(c1)) )
			.andExpect( status().isCreated() )
			.andExpect( jsonPath("$.maker", is("Audi")) )
			.andExpect( jsonPath("$.model", is("Q3")) );

		verify( carService, VerificationModeFactory.times(1) ).save( Mockito.any() );
	}

	@Test
	void whenGetCarByValidId_returnCar() throws Exception {
		Car c1 = new Car("Audi", "Q3");
        when( carService.getCarDetails( Mockito.any() ) ).thenReturn(c1);

        mvc.perform(get("/api/car/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is("Audi")))
                .andExpect(jsonPath("$.model", is("Q3")));   //Note: For this to work you have to define the getters and setters!
        
		verify( carService, VerificationModeFactory.times(1) ).getCarDetails( 1L );
        
	}

	@Test
    public void whenGetAllCars_returnCarCollection() throws Exception {
        Car c1 = new Car("Audi", "Q3");
        Car c2 = new Car("Audi", "Q5");
        Car c3 = new Car("Mitsubishi", "Galant");

        List<Car> allCars = Arrays.asList(c1, c2, c3);

        when( carService.getAllCars() ).thenReturn(allCars);

        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[0].maker", is(c1.getMaker())))
			.andExpect(jsonPath("$[1].maker", is(c2.getMaker())))
			.andExpect(jsonPath("$[2].maker", is(c3.getMaker())));
        verify( carService, VerificationModeFactory.times(1) ).getAllCars();

    }
}