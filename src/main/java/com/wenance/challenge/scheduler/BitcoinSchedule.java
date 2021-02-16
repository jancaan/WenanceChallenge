package com.wenance.challenge.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.wenance.challenge.service.BitcoinService;

@Configuration
public class BitcoinSchedule {

	private static final Logger LOGGER = LoggerFactory.getLogger(BitcoinSchedule.class);
	
	@Autowired
	private BitcoinService bitcoinService;
	
	@Scheduled(fixedDelay=10000)
	public void consultarYGuardarValorBitcoin() {
		try {
			LOGGER.info("consultarYGuardarValorBitcoin - INICIO SCHEDULE");
			bitcoinService.consultarYGuardarValorBitcoin();
			LOGGER.info("consultarYGuardarValorBitcoin - FIN SCHEDULE");
		} catch (Exception e) {
			LOGGER.error("Error en consultarYGuardarValorBitcoin. ", e);
		}	
	}
}
