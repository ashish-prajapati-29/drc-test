package com.drc.test.Util;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;

public class Validation {

    public static boolean emailValidation(String emailAddress) {
        return EmailValidator.getInstance().isValid(emailAddress);
    }

    public static boolean ageValidation(Integer age) {
        return IntegerValidator.getInstance().isInRange(age, 0, 200);
    }
}
