package pl.sda.vending.util;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class StringUtilTest {
    public static Object[] validObjects() {
        return new Object[]{
                new Object[]{"Product A1", " Product A1 ", 12},
                new Object[]{"Product A1", "Produ", 5},
                new Object[]{"Product A1", "Product A1", 10},
                new Object[]{"Product A1", " Product A1", 11},
                new Object[]{"--", "     --     ", 12},
                new Object[]{"5,37", "    5,37    ", 12},
                new Object[]{"5,37", "    5,37   ", 11},
                new Object[]{"abc", "   abc   ", 9}
        };
    }

    @Test
    public void shouldReturnUnmodifiedIfLength() {
        // Given
        String textToAdjust = "Ala ma kota";
        Integer expectedLength = 11;
        // When
        String adjustedText = StringUtil.adjustText(textToAdjust, expectedLength);
        // Then
        assertEquals("Ala ma kota", adjustedText);
    }
    // "abcd" -> 8 -> "  abcd  "
    // "abc" -> 8 -> "   abc  "

    @Test
    public void shouldTrimToLengthText() {
        // Given
        String textToAdjust = "Ala ma kota";
        Integer expectedLength = 6;
        // When
        String adjustedText = StringUtil.adjustText(textToAdjust, expectedLength);
        // Then
        assertEquals("Ala ma", adjustedText);
    }

    @Test
    @Parameters(method = "validObjects")
    public void shouldCenterEvenText(String textToAdjust, String expectedString, Integer expectedLength) {
        // Given
//        String textToAdjust = "abcd";
//        String textToAdjust = "Product A1";
//        Integer expectedLength = 8;
//        Integer expectedLength = 8;
        // When
        String adjustedText = StringUtil.adjustText(textToAdjust, expectedLength);
        // Then
//        assertEquals("  abcd  ", adjustedText);
        assertEquals(expectedString, adjustedText);
    }

//    public Object[] parametersForShouldCenterEvenText(){
//        return new Object[]{
//                new Object[]{"Product A1"," Product A1 ",12}
//        };
//    }

    @Test
    public void shouldCenterOddText() {
        // Given
        String textToAdjust = "abc";
        Integer expectedLength = 8;
        // When
        String adjustedText = StringUtil.adjustText(textToAdjust, expectedLength);
        // Then
        assertEquals("   abc  ", adjustedText);
    }

    @Test
    @Parameters
    public void shouldProperlyFormatMoney(Long amountToFormat, String expectedResult) {
        // Given - w parametrach
        // When
        String formattedMoney = StringUtil.formatMoney(amountToFormat);
        // Then
        assertEquals(expectedResult, formattedMoney);
    }

    public Object[] parametersForShouldProperlyFormatMoney() {
        return new Object[]{
                new Object[]{0L, "0,00"},
                new Object[]{5L, "0,05"},
                new Object[]{12L, "0,12"},
                new Object[]{123L, "1,23"},
                new Object[]{1234L, "12,34"},
                new Object[]{12345L, "123,45"},
                new Object[]{123456L, "1 234,56"},
                new Object[]{1234567L, "12 345,67"},
                new Object[]{12345678L, "123 456,78"},
                new Object[]{123456789L, "1 234 567,89"},
                new Object[]{1234567890123456789L, "12 345 678 901 234 567,89"}
        };
    }
}
