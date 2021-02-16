package com.wenance.challenge.transformer;

import com.wenance.challenge.dto.BitcoinDTO;
import com.wenance.challenge.model.Bitcoin;

public class BitcoinTransformer {

	public static final Transformer<BitcoinDTO, Bitcoin> FROM_BITCOIN_DTO_TO_MODEL;
	public static final Transformer<Bitcoin, BitcoinDTO> FROM_BITCOIN_MODEL_TO_DTO;

	   static {
		   FROM_BITCOIN_DTO_TO_MODEL = Transformer.mapping(BitcoinDTO.class, Bitcoin.class).constructor(Bitcoin::new)
	                               .fields(BitcoinDTO::getId, Bitcoin::setId)
	                               .fields(BitcoinDTO::getCurr1, Bitcoin::setMoneda1)
	                               .fields(BitcoinDTO::getCurr2, Bitcoin::setMoneda2)
	                               .fields(BitcoinDTO::getLprice, Bitcoin::setValor)
	                               .fields(BitcoinDTO::getCreateDate, Bitcoin::setFechaCreacion);
		   
		   FROM_BITCOIN_MODEL_TO_DTO = Transformer.mapping(Bitcoin.class, BitcoinDTO.class).constructor(BitcoinDTO::new)
                   .fields(Bitcoin::getId, BitcoinDTO::setId)
                   .fields(Bitcoin::getMoneda1, BitcoinDTO::setCurr1)
                   .fields(Bitcoin::getMoneda2, BitcoinDTO::setCurr2)
                   .fields(Bitcoin::getValor, BitcoinDTO::setLprice)
                   .fields(Bitcoin::getFechaCreacion, BitcoinDTO::setCreateDate);
	   }
	   
}