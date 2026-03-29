package com.biblioteca.bibliotecabackend.repository;

import com.biblioteca.bibliotecabackend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Todas las reservas de un usuario
    List<Reservation> findByUserId(Long userId);

    // Todas las reservas de un libro
    List<Reservation> findByBookId(Long bookId);

    // Busca si un usuario ya tiene una reserva activa
    Optional<Reservation> findByUserIdAndActiveTrue(Long userId);

    // Todas las reservas activas
    List<Reservation> findByActiveTrue();
}