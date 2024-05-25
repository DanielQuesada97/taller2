package com.rockettstudios.taller_2.repositories;

import com.rockettstudios.taller_2.domains.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, String> { // El esquema de datos y el tipo de ID

}
