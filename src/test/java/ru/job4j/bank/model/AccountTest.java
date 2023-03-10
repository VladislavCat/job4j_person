package ru.job4j.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {
    private static Validator validator;
    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Test
    public void createAccountWithNullRequisite() {
        Account account = new Account(null, 0.0D);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void createAccountWithEmptyRequisite() {
        Account account = new Account("", 0.0D);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void createAccountWithRequisitesWithUser() {
        Account account = new Account("123123124151", 0.0D);
        account.setUser(new User("ggggg", "User Userovich"));
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertThat(violations.size()).isEqualTo(0);
    }
}
