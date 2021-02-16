package com.wenance.challenge.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wenance.challenge.model.Bitcoin;

@Repository
public interface BitcoinDAO extends JpaRepository<Bitcoin, Long> {

	public Optional<Bitcoin> findByFechaCreacion(Timestamp fechaCreacion);
	
	public List<Bitcoin> findByFechaCreacionBetween(Timestamp fechaDesde, Timestamp fechaHasta);
	
	public List<Bitcoin> findByFechaCreacionBetween(Timestamp fechaDesde, Timestamp fechaHasta, Pageable pageable);
	
	
}
