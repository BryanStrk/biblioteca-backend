package com.biblioteca.bibliotecabackend.controller;

import com.biblioteca.bibliotecabackend.dto.ReservationDTO;
import com.biblioteca.bibliotecabackend.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "Reservation management endpoints")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @Operation(summary = "Get all reservations")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active reservations")
    public ResponseEntity<List<ReservationDTO>> getActiveReservations() {
        return ResponseEntity.ok(reservationService.getActiveReservations());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all reservations by user")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
                reservationService.getReservationsByUser(userId));
    }

    @PostMapping
    @Operation(summary = "Create a new reservation")
    public ResponseEntity<ReservationDTO> createReservation(
            @RequestParam Long userId,
            @RequestParam Long bookId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.createReservation(userId, bookId));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel an active reservation")
    public ResponseEntity<ReservationDTO> cancelReservation(
            @PathVariable Long id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }
}