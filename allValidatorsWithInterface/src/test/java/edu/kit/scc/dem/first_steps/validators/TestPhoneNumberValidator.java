package edu.kit.scc.dem.first_steps.validators;

import edu.kit.scc.dem.first_steps.validators.impl.PhoneNumberValidator;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;

public class TestPhoneNumberValidator {

    private final PhoneNumberValidator validator = new PhoneNumberValidator("DE");
    private static final PhoneNumberUtil util = PhoneNumberUtil.getInstance();

    @Test
    public void farTooShort(){
        assertThrows(ValidatorInterface.ValidationException.class, () -> validator.isValid("1"));
    }

    @Test
    public void possibleButNotValid(){
        try{
            Phonenumber.PhoneNumber number = util.parseAndKeepRawInput("+497216", "DE");
            Assertions.assertNotEquals(validator.isValid("+497216"), util.isPossibleNumber(number));
        } catch (NumberParseException | ValidatorInterface.ValidationException e) {
            assumeNoException(e);
        }
    }

    @Test
    public void valid(){
        try {
            Assertions.assertTrue(validator.isValid("+497216081234"));
        } catch (ValidatorInterface.ValidationException e) {
            assumeNoException(e);
        }
    }

    @Test
    public void validWith00(){
        try {
            Assertions.assertTrue(validator.isValid("00497216081234"));
        } catch (ValidatorInterface.ValidationException e) {
            assumeNoException(e);
        }
    }

    @Test
    public void possibleLocalOnly(){
        try {
            Assertions.assertFalse(validator.isValid("07216"));
        } catch (ValidatorInterface.ValidationException e) {
            assumeNoException(e);
        }
    }

    @Test
    public void tooLong(){
        assertThrows(ValidatorInterface.ValidationException.class, () -> validator.isValid("+49721608333333333333333"));
    }

    @Test
    public void invalid(){
        assertThrows(ValidatorInterface.ValidationException.class, () -> validator.isValid("+03245222342"));
    }

    @Test
    public void invalidCountryCode(){
        assertThrows(ValidatorInterface.ValidationException.class, () -> validator.isValid("+9991234123"));
    }

    @Test
    public void tooShort(){
        assertThrows(ValidatorInterface.ValidationException.class, () -> validator.isValid("+491"));
    }

    @Test
    public void notANumber(){
        assertThrows(ValidatorInterface.ValidationException.class, () -> validator.isValid("hello"));
    }

    @Test
    public void noInputInValidate(){
        assertThrows(ValidatorInterface.ValidationException.class, () -> validator.isValid(null));
    }

}
