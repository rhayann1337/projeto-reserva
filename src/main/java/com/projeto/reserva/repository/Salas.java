package com.projeto.reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.reserva.model.Sala;

import java.util.List;

public interface Salas extends JpaRepository<Sala, Long> {


	List<Sala> findAll();
}
