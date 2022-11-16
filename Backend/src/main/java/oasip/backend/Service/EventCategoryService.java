package oasip.backend.Service;

import oasip.backend.DTOs.Category.CategoryCreateDto;
import oasip.backend.DTOs.Category.CategoryListCreateDto;
import oasip.backend.DTOs.Category.CategoryEditDto;
import oasip.backend.DTOs.Category.CategoryListAllDto;
import oasip.backend.DTOs.Category.CategoryListFilterDto;
import oasip.backend.DTOs.User.UserDetailDto;
import oasip.backend.Enitities.Event;
import oasip.backend.Enitities.Eventcategory;
import oasip.backend.ListMapper;
import oasip.backend.repositories.CategoryRepository;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class EventCategoryService {
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    public ResponseEntity<?> getAllCategory() {
        List<Eventcategory> categoryList = repository.findAll();
        Collections.reverse(categoryList);
        return ResponseEntity.ok(listMapper.maplist(categoryList, CategoryListAllDto.class, modelMapper));
    }

    public ResponseEntity<?> getAllCategoryForCreate() {
        List<Eventcategory> categoryList = repository.findAll();
        return ResponseEntity.ok(listMapper.maplist(categoryList, CategoryListCreateDto.class, modelMapper));
    }
    public ResponseEntity<?> getAllCategoryForFilter() {
        List<Eventcategory> categoryList = repository.findAll();
        return ResponseEntity.ok(listMapper.maplist(categoryList, CategoryListFilterDto.class, modelMapper));
    }

    public ResponseEntity<?> getCategory(Integer categoryId){
        Eventcategory category = repository.findById(categoryId).orElseThrow(
                () -> new ResponseStatusException( HttpStatus.NOT_FOUND,categoryId + " Does not Exist !!!" ));
        return ResponseEntity.ok(modelMapper.map(category , CategoryCreateDto.class));
    }

    public void deleteCategory(Integer categoryId){
        repository.findById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,categoryId + " Does not Exist !!!" ));
        repository.deleteById(categoryId);
    }
    public ResponseEntity<?> updateCategory(CategoryEditDto updateCategory , Integer categoryId){
        Eventcategory newCategory = modelMapper.map(updateCategory,Eventcategory.class);
        Eventcategory category = repository.findById(categoryId).map(o -> mapCategory(o,newCategory)).orElseGet(() -> {
            newCategory.setId(categoryId);
            return newCategory;
        });
        Set<ConstraintViolation<Eventcategory>> violations = validator.validate(category);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
        return ResponseEntity.ok(repository.saveAndFlush(category));
    }
    private Eventcategory mapCategory(Eventcategory existingCategory, Eventcategory updateCategory) {
        if (updateCategory.getEventCategoryName() != null){
            existingCategory.setEventCategoryName(updateCategory.getEventCategoryName().trim());
        }
        if (updateCategory.getEventCategoryDescription() != null){
            existingCategory.setEventCategoryDescription(updateCategory.getEventCategoryDescription().trim());
        }
        if(updateCategory.getEventCategoryDuration() != null){
            existingCategory.setEventCategoryDuration(updateCategory.getEventCategoryDuration());
        }

        return existingCategory; }

}
