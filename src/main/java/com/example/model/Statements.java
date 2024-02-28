package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Entity
@Table(name = "statements")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Statements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Size(max = 10, message = "Регистрационный номер должен содержать не более 10 символов")
    private String registrationNumberCar;
    @Enumerated(EnumType.STRING)
    private StatementStatus status = StatementStatus.NEW;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}