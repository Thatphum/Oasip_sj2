package oasip.backend.Exception.Category;

import oasip.backend.BeanUtil;
import oasip.backend.Enitities.Eventcategory;
import oasip.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CategoryUniqueNameValidator implements ConstraintValidator<CategoryUniqueName, Eventcategory> {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void initialize(CategoryUniqueName constraintAnnotation) {
        categoryRepository = BeanUtil.getBean(CategoryRepository.class);
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Eventcategory eventcategory, ConstraintValidatorContext constraintValidatorContext) {

        if(eventcategory.getEventCategoryName() != null){
            Integer id = eventcategory.getId();
            List<Eventcategory> result = categoryRepository.findAllByEventCategoryNameAndAndIdFalse(eventcategory.getEventCategoryName() , id);
            if (!result.isEmpty()){
                // disable exception because cannot set field name so create new exception and field name;
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate()).addNode("eventCategoryName").addConstraintViolation();
                return false;
            }
        }
            return true;
    }
}


