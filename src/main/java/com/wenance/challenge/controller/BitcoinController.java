package com.wenance.challenge.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wenance.challenge.dto.BitcoinDTO;
import com.wenance.challenge.dto.ConsultaPromedioYDiferenciaPorcentualDTO;
import com.wenance.challenge.service.BitcoinService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bitcoins")
public class BitcoinController {
	
	@Autowired
	private BitcoinService bitcoinService;
	
	@ApiOperation(value = "a) Obtiene el precio del Bitcoin en cierto timestamp. (Ejemplo de fecha: 2021-02-11T16:41:15.928Z")
	@GetMapping("/consultarValorBitcoinEnCiertoTimestamp")
	public ResponseEntity<BitcoinDTO> consultarValorBitcoinEnCiertoTimestamp(
			@RequestParam(name="fechaHora") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date fechaHora) {
		BitcoinDTO bitcoinDTO = bitcoinService.consultarValorBitcoin(new Timestamp(fechaHora.getTime()));
		return new ResponseEntity<>(bitcoinDTO, HttpStatus.OK);
	}
	
	@ApiOperation(value = "b) Conocer el promedio de valor entre dos timestamps así como la	diferencia porcentual\n"+ 
			"entre ese valor promedio y el valor máximo almacenado para toda la serie temporal disponible. (Ejemplo de fecha: 2021-02-11T16:41:15.928Z")
	@GetMapping("/consultarValorPromedioYDiferenciaPorcentual")
	public ResponseEntity<ConsultaPromedioYDiferenciaPorcentualDTO> consultarValorPromedioYDiferenciaPorcentual(
			@RequestParam(name="fechaHoraDesde") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date fechaHoraDesde,
			@RequestParam(name="fechaHoraHasta") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date fechaHoraHasta) {
		ConsultaPromedioYDiferenciaPorcentualDTO consultaDTO = bitcoinService.consultarPromedioYDiferenciaPorcentual(
				new Timestamp(fechaHoraDesde.getTime()),
				new Timestamp(fechaHoraHasta.getTime()));
		return new ResponseEntity<>(consultaDTO, HttpStatus.OK);
	}
	
	@ApiOperation(value = "c) Devolver en forma paginada los datos almacenados con o sin filtro de timestamp. (Ejemplo de fecha: 2021-02-11T16:41:15.928Z")
	@GetMapping("/consultarValoresBitcoinAlmacenados")
	public ResponseEntity<List<BitcoinDTO>> consultarValoresBitcoin(
			@RequestParam(name="fechaHoraDesde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date fechaHoraDesde,
			@RequestParam(name="fechaHoraHasta", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date fechaHoraHasta,
			@RequestParam(value = "numeroPagina", required = true) Integer numeroPagina,
			@RequestParam(value = "resultadosPorPagina", required = true) Integer resultadosPorPagina) {
		List<BitcoinDTO> consultaBitcoinsDTO = bitcoinService.consultarValoresBitcoin(
				fechaHoraDesde != null ? new Timestamp(fechaHoraDesde.getTime()) : null,
				fechaHoraHasta != null ? new Timestamp(fechaHoraHasta.getTime()) : null,
				numeroPagina,
				resultadosPorPagina);
		return new ResponseEntity<>(consultaBitcoinsDTO, HttpStatus.OK);
	}			
}
