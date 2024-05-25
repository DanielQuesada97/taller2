package com.rockettstudios.taller_2.controllers;

import com.rockettstudios.taller_2.domains.dtos.GeneralResponse;
import com.rockettstudios.taller_2.domains.dtos.SaveCategoryDTO;
import com.rockettstudios.taller_2.domains.entities.Category;
import com.rockettstudios.taller_2.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/all")
    public ResponseEntity<GeneralResponse> findAllCategories(){
        return GeneralResponse.getResponse(
                categoryService.findAllCategories()
        );
    }
	
	@PostMapping("/save")
	public ResponseEntity<GeneralResponse> saveCategory(@RequestBody @Valid SaveCategoryDTO info){
		
		categoryService.save(info);
		return GeneralResponse.getResponse(HttpStatus.OK, "Categoria guardada");
		
	}
	
	@DeleteMapping("/delete/{code}")
	public ResponseEntity<GeneralResponse> deleteCategory(@PathVariable("code") String code){
		
		Category category = categoryService.findCategoryByCode(code);
		
		if (category == null){
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND);
        }
		
		categoryService.delete(category);
		
		return GeneralResponse.getResponse(HttpStatus.OK);
		
	}
	
	@GetMapping("/{code}")
    public ResponseEntity<GeneralResponse> findBooksbyCategory(@PathVariable("code") String code){

        Category category = categoryService.findCategoryByCode(code);

        if (category == null){
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND);
        }
        
        return GeneralResponse.getResponse(HttpStatus.OK, category);
        
    }
	
}
