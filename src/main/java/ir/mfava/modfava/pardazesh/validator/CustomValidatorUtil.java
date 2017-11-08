//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ir.mfava.modfava.pardazesh.validator;

public class CustomValidatorUtil {
    public CustomValidatorUtil() {
    }

    public static boolean isValidNationalCode(Long _nationalCode) {
        if(_nationalCode == null) {
            return false;
        } else {
            String nationalCode = _nationalCode.toString();
            int length = nationalCode.length();
            if(length > 10) {
                return false;
            } else {
                int check;
                int remainder;
                if(length < 10) {
                    check = 10 - length;

                    for(remainder = 0; remainder < check; ++remainder) {
                        nationalCode = "0" + nationalCode;
                    }
                }

                if(nationalCode.substring(0, 2).equals("000")) {
                    return false;
                } else {
                    check = 0;

                    for(remainder = 0; remainder < 9; ++remainder) {
                        check += Integer.valueOf(nationalCode.substring(remainder, remainder + 1)).intValue() * (10 - remainder);
                    }

                    remainder = check % 11;
                    int controlDigit = Integer.valueOf(nationalCode.substring(9, 10)).intValue();
                    return remainder < 2?controlDigit == remainder:controlDigit == 11 - remainder;
                }
            }
        }
    }
}
