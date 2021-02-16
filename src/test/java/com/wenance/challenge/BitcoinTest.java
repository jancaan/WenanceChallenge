package com.wenance.challenge;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@TestPropertySource(properties = "application.properties")
@RunWith(SpringRunner.class)
public class BitcoinTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void consultarValorBitcoinEnCiertoTimestampTest() throws Exception {
		String fecha = "2021-02-12T15:00:10.000Z";

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/bitcoins/consultarValorBitcoinEnCiertoTimestamp")
				.queryParam("fechaHora", fecha)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		this.mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.curr1", is("BTC")))
				.andExpect(jsonPath("$.curr2", is("USD")))
				.andExpect(jsonPath("$.lprice", is(50000.0)))
				.andExpect(jsonPath("$.createDate", is("2021-02-12 15:00:10.000")));
	}

	@Test
	public void consultarValorPromedioYDiferenciaPorcentualTest() throws Exception {
		String fechaHoraDesde = "2021-02-12T15:00:00.000Z";
		String fechaHoraHasta = "2021-02-12T15:00:10.000Z";

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/bitcoins/consultarValorPromedioYDiferenciaPorcentual")
				.queryParam("fechaHoraDesde", fechaHoraDesde)
				.queryParam("fechaHoraHasta", fechaHoraHasta)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		this.mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.valorMaximo", is(50000.0)))
				.andExpect(jsonPath("$.valorPromedio", is(45000.0)))
				.andExpect(jsonPath("$.diferenciaPorcentualEntreValorPromedioYMaximo", is(10.0)));
	}

	@Test
	public void consultarValoresBitcoinAlmacenadosTest() throws Exception {
		Integer numeroPagina = 0;
		Integer resultadosPorPagina = 3;

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/bitcoins/consultarValoresBitcoinAlmacenados")
				.queryParam("numeroPagina", numeroPagina.toString())
				.queryParam("resultadosPorPagina", resultadosPorPagina.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		this.mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(jsonPath("$.length()").value(resultadosPorPagina));
	}

}
