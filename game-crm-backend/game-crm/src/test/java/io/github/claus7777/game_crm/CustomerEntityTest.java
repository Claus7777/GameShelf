package io.github.claus7777.game_crm;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;


class CustomerEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenNameisBlank() {
        Customer customer = new Customer();
        customer.setName("");
        customer.setEmail("joao@silva.com");

        var violations = validator.validate(customer);

        Assertions.assertThat(violations).isNotEmpty();
        Assertions.assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("name");
    }
}