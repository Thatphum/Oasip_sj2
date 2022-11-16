package oasip.backend;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.HibernateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public ListMapper listMapper() { return ListMapper.getInstance(); }
//    @Bean
//    public Validator validator (final AutowireCapableBeanFactory autowireCapableBeanFactory) {
//
//        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
//                .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
//                .buildValidatorFactory();
//        Validator validator = validatorFactory.getValidator();
//
//        return validator;
//    }

}
