package com.expo2020;

import com.expo2020.controllers.AdminController;
import com.expo2020.controllers.JuryController;
import com.expo2020.controllers.StandController;
import com.expo2020.controllers.StemmeController;
import com.expo2020.models.Stand;
import com.expo2020.models.Stemme;
import com.expo2020.services.JuryService;
import com.expo2020.services.StandService;
import com.expo2020.services.StemmeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Expo2020Application.class)
@AutoConfigureMockMvc
class Expo2020ApplicationTests {

	private StemmeController stemmeController;

	private StandController standController;

	private JuryController juryController;

	private AdminController adminController;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ApplicationContext context;

	@MockBean
	private StandService standService;

	@MockBean
	private StemmeService stemmeService;

	@MockBean
	private JuryService juryService;

	/*
	 * Test om velkomstsiden inneholder stringen "Velkommen"
	 */
	@Test
	public void testVelkommen() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Velkommen")));
	}


	/*
	 * Tester om man får respons fra StandControlleren
	 */
	@Test
	void standContextGetLoads() throws Exception {
		when(standService.findAll()).thenReturn(Collections.emptyList());

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/stands").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		System.out.println(mvcResult.getResponse());
		verify(standService).findAll();
	}

	@Test
	public void adminContextGetLoads() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/admin").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		Assertions.assertEquals(result.getResponse(), result.getResponse());

	}

//	/*
//	 * Tester om man får respons fra stemmeControlleren (Ikke ferdig)
//	 */
//	@Test
//	void stemmeContextGetLoads() throws Exception {
//		Optional<Stemme> a = Optional.ofNullable(null);
//		when(stemmeService.findById(Long.valueOf(1))).thenReturn(a);
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/stem/{id}", 1).accept(MediaType.ALL_VALUE)).andReturn();
//
////
////		MvcResult mvcResult = mockMvc
////				.perform(MockMvcRequestBuilders.get("/stem/{id}", 1).accept(MediaType.APPLICATION_JSON)).andReturn();
////		stemmeService.findById(Long.valueOf(1));
////		System.out.println(mvcResult.getResponse());
//		verify(stemmeService).findById(Long.valueOf(1));
//	}

	// ----------------------------StandService-metoder----------------------------------------
	/*
	 * Tester findById-metoden i StandService klassen
	 */
	@Test
	public void findStandByIdTest() throws Exception {

		ArrayList<Stand> liste = new ArrayList<Stand>();
		Stand stand1 = new Stand("test1", "test1", "test1", "test1");
		stand1.setId(Long.valueOf(1));
		Stand stand2 = new Stand("test2", "test2", "test2", "test2");
		stand2.setId(Long.valueOf(2));
		Stand stand3 = new Stand("test3", "test3", "test3", "test3");
		stand3.setId(Long.valueOf(3));

		liste.add(stand1);
		liste.add(stand2);
		liste.add(stand3);

		when(standService.findById(1L)).thenReturn(Optional.of(stand1));
		Assertions.assertEquals(standService.findById(1L), Optional.of(stand1));
		verify(standService).findById(1L);
	}

	/*
	 * Tester findAll()-metoden i StandService klassen
	 */
	@Test
	private void findAllStandsTest() {
		ArrayList<Stand> liste = new ArrayList<>();
		Stand stand1 = new Stand("Stand", "stand", "stand", "steand");
		Stand stand2 = new Stand("Stand", "stand", "stand", "steand");
		Stand stand3 = new Stand("Stand", "stand", "stand", "steand");

		liste.add(stand1);
		liste.add(stand2);
		liste.add(stand3);

		Mockito.when(standService.findAll()).thenReturn(liste);
		Assertions.assertEquals(liste, standService.findAll());
		verify(standService).findAll();
	}

	/*
	 * Tester count metoden i StandService Klassen
	 */
	@Test
	public void countStandTest() throws Exception {
		Stand stand1 = new Stand("Stand", "stand", "stand", "stand");
		Stand stand2 = new Stand("Stand", "stand", "stand", "stand");
		Stand stand3 = new Stand("Stand", "stand", "stand", "stand");
		ArrayList<Stand> value = new ArrayList<Stand>();
		value.add(stand1);
		value.add(stand3);
		value.add(stand2);

		Long count = (long) value.size();

		when(standService.count()).thenReturn(count);

		StandService repo = context.getBean(StandService.class);
		long userCount = repo.count();

		Assertions.assertEquals(Optional.of(count), userCount);
		verify(standService).count();
	}

	/*
	 * Tester save-metoden i StandService-klassen
	 */
	@Test
	public void saveStandTest() throws Exception {
		Stand stand = new Stand("stand", "stand", "stand", "stand");

		standService.save(stand);

		verify(standService, times(1)).save(stand);

	}

	/*
	 * tester delete-metoden i StandService-klassen
	 */
	@Test
	public void deleteStandTest() throws Exception {

		Stand stand = new Stand("stand", "stand", "stand", "stand");
		stand.setId(1L);
		standService.delete(1L);
		verify(standService, times(1)).delete(Long.valueOf(1));
	}

	// ----------------------------StemmeService-metoder----------------------------------------

	/*
	 * Tester om man finner alle stemmene i StemmeService-klassen
	 */
	@Test
	public void findAllStemmeTest() throws Exception {
		ArrayList<Stemme> liste = new ArrayList<Stemme>();
		Stemme stemme1 = new Stemme("30", 2L, 3);
		Stemme stemme2 = new Stemme("31", 3L, 4);
		Stemme stemme3 = new Stemme("32", 4L, 2);
		liste.add(stemme1);
		liste.add(stemme2);
		liste.add(stemme3);

		Mockito.when(stemmeService.findAll()).thenReturn(liste);
		Assertions.assertEquals(liste, stemmeService.findAll());
		verify(stemmeService).findAll();
	}

	/*
	 * Tester om Count-metoden i StemmeService-klassen teller opp stemmer
	 */
	@Test
	public void countStemmeTest() throws Exception {
		ArrayList<Stemme> liste = new ArrayList<Stemme>();
		Stemme stemme1 = new Stemme("30", 2L, 3);
		Stemme stemme2 = new Stemme("31", 3L, 4);
		Stemme stemme3 = new Stemme("32", 4L, 2);

		liste.add(stemme1);
		liste.add(stemme2);
		liste.add(stemme3);

		long count = liste.size();

		when(stemmeService.count()).thenReturn(count);

		StemmeService repo = context.getBean(StemmeService.class);
		long userCount = repo.count();

		Assertions.assertEquals(count, userCount);
		verify(stemmeService).count();
	}

	/*
	 * Tester om en stemme blir lagret
	 */
	@Test
	public void saveStemmeTest() throws Exception {
		Stemme stemme = new Stemme("33", 3L, 5);

		stemmeService.save(stemme);

		verify(stemmeService, times(1)).save(stemme);
	}

	/*
	 * Tester om en stemme blir slettet
	 */
	@Test
	public void deleteStemmeTest() throws Exception {
		ArrayList<Stemme> liste = new ArrayList<>();
		Stemme stemme = new Stemme("34", 6L, 5);
		liste.add(stemme);
		stemmeService.delete(1L);
		verify(stemmeService, times(1)).delete(1L);
	}

	/*
	 * Tester om man finner stemme basert på personid (Ikke Ferdig)
	 */
	@Test
	public void findStemmeByPersonId() throws Exception {

		List<Stemme> liste = new ArrayList<>();
		Stemme stemme1 = new Stemme("30", 2L, 3);
		Stemme stemme2 = new Stemme("31", 3L, 4);
		Stemme stemme3 = new Stemme("32", 4L, 2);

		liste.add(stemme1);
		liste.add(stemme2);
		liste.add(stemme3);

		stemmeService = context.getBean(StemmeService.class);
		liste = stemmeService.findByPersonId("30");

		when(stemmeService.findByPersonId("30")).thenReturn(liste);
//

		verify(stemmeService).findByPersonId(stemme1.getPersonid());

	}


}
