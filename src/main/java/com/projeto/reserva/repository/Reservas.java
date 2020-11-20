package com.projeto.reserva.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.reserva.model.Reserva;

public interface Reservas extends JpaRepository<Reserva, Long>{
	
	List<Reserva> findByNomeContaining(String nome);
	List<Reserva> findByHorarioContaining(Date date);
	List<Reserva> findByDataContaining(Date data);


}
