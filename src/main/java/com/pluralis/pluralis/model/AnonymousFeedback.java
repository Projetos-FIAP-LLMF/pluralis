package com.pluralis.pluralis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "anonymous_feedback")
public class AnonymousFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(name = "sent_date")
    @CreatedDate
    private LocalDate sentDate;
}