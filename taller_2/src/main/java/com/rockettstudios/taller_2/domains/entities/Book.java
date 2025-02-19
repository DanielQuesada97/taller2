package com.rockettstudios.taller_2.domains.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books",schema = "taller")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID code;

    private String isbn;
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
