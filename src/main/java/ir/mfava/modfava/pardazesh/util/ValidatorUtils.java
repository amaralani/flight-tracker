package ir.mfava.modfava.pardazesh.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author Drago
 *         <p>
 *         General Validations
 */
public class ValidatorUtils {
    public static boolean isValidNationalCode(Long _nationalCode) {
        if (_nationalCode == null)
            return false;

        String nationalCode = _nationalCode.toString();
        int length = nationalCode.length();

        if (length > 10)
            return false;

        if (length < 10) {//add zero to the head of string
            int numberOfZero = 10 - length;
            for (int i = 0; i < numberOfZero; i++)
                nationalCode = "0" + nationalCode;
        }


        if (nationalCode.substring(0, 2).equals("000")) {
            return false;
        }
        int check = 0;
        for (int i = 0; i < 9; i++) {

            check += Integer.valueOf(nationalCode.substring(i, i + 1)) * (10 - i);
        }
        int remainder = check % 11;
        int controlDigit = Integer.valueOf(nationalCode.substring(9, 10));

        return remainder < 2 ? controlDigit == remainder : controlDigit == 11 - remainder;

    }

    public static boolean isValidMobileNumber(String number) {
        return StringUtils.isNotBlank(number) && StringUtils.isNumeric(number) && number.length() >= 11 && number.length() <= 12;
    }

    public static boolean isValidPassword(String password, Integer passwordComplexityLevel) {

        switch (passwordComplexityLevel) {
            case 1:
                if (!password.matches("^(?=.*[0-9]).*$")) {
                    throw new IllegalArgumentException("must-contain-number");
                } else {
                    break;
                }
            case 2:
                if (!password.matches("^(?=.*[a-z])(?=.*[A-Z]).*$")) {
                    throw new IllegalArgumentException("must-contain-upper-lower");
                } else {
                    break;
                }
            case 3:
                if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$")) {
                    throw new IllegalArgumentException("must-contain-upper-lower-number");
                } else {
                    break;
                }
            case 4:
                if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=]).*$")) {
                    throw new IllegalArgumentException("must-contain-upper-lower-number-special");
                } else {
                    break;
                }
            default: {
                if (!password.matches("^.+$")) {
                    throw new IllegalArgumentException("must-not-be-empty");
                }
            }
        }
        return true;
    }
}
