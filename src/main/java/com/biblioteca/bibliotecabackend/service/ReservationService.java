package com.biblioteca.bibliotecabackend.service;

import com.biblioteca.bibliotecabackend.dto.ReservationDTO;
import com.biblioteca.bibliotecabackend.exception.BadRequestException;
import com.biblioteca.bibliotecabackend.exception.ResourceNotFoundException;
import com.biblioteca.bibliotecabackend.model.Book;
import com.biblioteca.bibliotecabackend.model.Reservation;
import com.biblioteca.bibliotecabackend.model.User;
import com.biblioteca.bibliotecabackend.repository.BookRepository;
import com.biblioteca.bibliotecabackend.repository.ReservationRepository;
import com.biblioteca.bibliotecabackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ReservationDTO> getActiveReservations() {
        return reservationRepository.findByActiveTrue()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ReservationDTO> getReservationsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId);
        }
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ReservationDTO createReservation(Long userId, Long bookId) {

        // 1. Verificar que el usuario existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));

        // 2. Verificar que el libro existe
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + bookId));

        // 3. REGLA CLAVE: un usuario solo puede tener UNA reserva activa
        reservationRepository.findByUserIdAndActiveTrue(userId)
                .ifPresent(r -> {
                    throw new BadRequestException(
                            "User already has an active reservation");
                });

        // 4. Verificar que el libro está disponible
        if (!book.isAvailable()) {
            throw new BadRequestException(
                    "Book is not available: " + book.getTitle());
        }

        // 5. Crear la reserva
        Reservation reservation = Reservation.builder()
                .user(user)
                .book(book)
                .active(true)
                .build();

        // 6. Marcar el libro como no disponible
        book.setAvailable(false);
        bookRepository.save(book);

        return toDTO(reservationRepository.save(reservation));
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reservation not found with id: " + reservationId));

        if (!reservation.isActive()) {
            throw new BadRequestException("Reservation is already cancelled");
        }

        // Desactivar reserva y devolver el libro
        reservation.setActive(false);
        reservation.setReturnDate(LocalDate.now());
        reservation.getBook().setAvailable(true);
        bookRepository.save(reservation.getBook());

        return toDTO(reservationRepository.save(reservation));
    }

    // Mapper
    private ReservationDTO toDTO(Reservation r) {
        return ReservationDTO.builder()
                .id(r.getId())
                .userId(r.getUser().getId())
                .userName(r.getUser().getName())
                .bookId(r.getBook().getId())
                .bookTitle(r.getBook().getTitle())
                .reservationDate(r.getReservationDate())
                .returnDate(r.getReturnDate())
                .active(r.isActive())
                .build();
    }
}