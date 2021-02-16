package com.wenance.challenge.service;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.wenance.challenge.dao.BitcoinDAO;
import com.wenance.challenge.dto.BitcoinDTO;
import com.wenance.challenge.dto.ConsultaPromedioYDiferenciaPorcentualDTO;
import com.wenance.challenge.model.Bitcoin;
import com.wenance.challenge.transformer.BitcoinTransformer;

@Service
public class BitcoinService {
	
	@Value("${url.servicio}")
	private String URLServicioBitcoin;
	
	@Value("${endpoint.servicio}")
	private String endpointServicioBitcoin;
	
	@Autowired
	private BitcoinDAO bitcoinDAO;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BitcoinService.class);
	
	private BitcoinDTO consultarValorBitcoin() {
		URI url = UriComponentsBuilder.fromHttpUrl(URLServicioBitcoin)
				.path(endpointServicioBitcoin)
				.build().encode().toUri();

		LOGGER.debug("consultarValorBitcoin - url [{}]", url);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent", "Application");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request,body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });

		BitcoinDTO bitcoinDTO = null;
		try {
			ResponseEntity<BitcoinDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<BitcoinDTO>() {});
			
			if (HttpStatus.NO_CONTENT.equals(response.getStatusCode())) {
				LOGGER.debug("consultarValorBitcoin - No se obtuvieron resultados");
			}
			bitcoinDTO = response.getBody();
		} catch (Exception e) {
			LOGGER.error("consultarValorBitcoin - Error al intentar consultar el servicio [" + url + "]", e);
		}

		return bitcoinDTO;
	}
	
	
	public void consultarYGuardarValorBitcoin() {
		BitcoinDTO bitcoinDTO = consultarValorBitcoin();
		
		bitcoinDAO.save(BitcoinTransformer.FROM_BITCOIN_DTO_TO_MODEL.apply(bitcoinDTO));
		LOGGER.info("consultarYGuardarValorBitcoin - guardado OK");
	}
 

	public BitcoinDTO consultarValorBitcoin(Timestamp fecha) {
		Bitcoin bitcoin = bitcoinDAO.findByFechaCreacion(fecha).orElse(null);
		return bitcoin != null ? BitcoinTransformer.FROM_BITCOIN_MODEL_TO_DTO.apply(bitcoin) : null;
	}


	public ConsultaPromedioYDiferenciaPorcentualDTO consultarPromedioYDiferenciaPorcentual(Timestamp fechaDesde,
			Timestamp fechaHasta) {
		List<Bitcoin> lista = bitcoinDAO.findByFechaCreacionBetween(fechaDesde, fechaHasta);
		Double promedio = lista.stream().mapToDouble(b -> b.getValor()).average().orElse(0);
		Double maximo = lista.stream().mapToDouble(b -> b.getValor()).max().orElse(0);
		
		ConsultaPromedioYDiferenciaPorcentualDTO consulta = new ConsultaPromedioYDiferenciaPorcentualDTO(
										promedio,
										maximo,
										(promedio != 0 ? (maximo-promedio)/maximo*100 : 0) );
		
		return consulta;
	}


	public List<BitcoinDTO> consultarValoresBitcoin(Timestamp fechaDesde, Timestamp fechaHasta,
			Integer numeroPagina, Integer resultadosPorPagina) {
		List<Bitcoin> lista = null;
		
		if(fechaDesde != null && fechaHasta != null) {
			lista= bitcoinDAO.findByFechaCreacionBetween(fechaDesde, fechaHasta, PageRequest.of(numeroPagina, resultadosPorPagina));
		} else {
			Page<Bitcoin> bitcoins = bitcoinDAO.findAll(PageRequest.of(numeroPagina, resultadosPorPagina));
			lista = bitcoins.stream().map( p -> p ).collect(Collectors.toList());
		}
		List<BitcoinDTO> listaDTO = lista.stream().map( b-> BitcoinTransformer.FROM_BITCOIN_MODEL_TO_DTO.apply(b)).collect(Collectors.toList());	

		return listaDTO;
	}
}
