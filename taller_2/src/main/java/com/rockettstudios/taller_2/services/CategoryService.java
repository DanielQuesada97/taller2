package com.rockettstudios.taller_2.services;


import com.rockettstudios.taller_2.domains.dtos.SaveCategoryDTO;
import com.rockettstudios.taller_2.domains.entities.Category;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category findCategoryByCode(String code);
	void save(@Valid SaveCategoryDTO info);
	void delete(Category category);
}
