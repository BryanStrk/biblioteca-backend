package com.biblioteca.bibliotecabackend.service;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long bookId;
    private String bookTitle;
    private LocalDate reservationDate;
    private LocalDate returnDate;
    private boolean active;
}