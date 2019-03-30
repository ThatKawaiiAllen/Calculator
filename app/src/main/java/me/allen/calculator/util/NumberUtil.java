package me.allen.calculator.util;

public class NumberUtil {

    public static boolean isNumber(String base) {
        try {
            Integer.parseInt(base);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
