package oasip.backend.Exception.Category;

import oasip.backend.DTOs.Category.CategoryEditDto;
import oasip.backend.Enitities.Eventcategory;
import oasip.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryUniqueNameEditValidator implements ConstraintValidator<CategoryUniqueNameEdit, CategoryEditDto> {
    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public boolean isValid(CategoryEditDto categoryEditDto, ConstraintValidatorContext constraintValidatorContext) {
        Eventcategory result = categoryRepository.findByEventCategoryName(categoryEditDto.getEventCategoryName());
        if(result == null ||  result.getId().equals(categoryEditDto.getId()))
            return true;
        return false;
    }
}
