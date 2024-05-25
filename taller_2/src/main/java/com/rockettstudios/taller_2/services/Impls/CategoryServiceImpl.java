package com.rockettstudios.taller_2.services.Impls;

import com.rockettstudios.taller_2.domains.dtos.SaveCategoryDTO;
import com.rockettstudios.taller_2.domains.entities.Category;
import com.rockettstudios.taller_2.repositories.CategoryRepository;
import com.rockettstudios.taller_2.services.CategoryService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryByCode(String code) {
        return categoryRepository.findById(code).orElse(null);
    }

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(@Valid SaveCategoryDTO info) {

		Category category = this.findCategoryByCode(info.getCode());

        if (category == null) {
            category = new Category();
        }

		category.setCode(info.getCode());
		category.setName(info.getName());
		
		categoryRepository.save(category);
		
	}
	
	@Override
	public void delete(Category category) {
		
		categoryRepository.delete(category);
		
	}
	
}

	
