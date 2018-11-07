package pl.sda.vending.util;

public class StringUtil {
    public static String duplicateText(String text, Integer count) {
        StringBuilder duplicatedText = new StringBuilder();
        for (int i = 0; i < count; i++) {
            duplicatedText.append(text);
        }
        return duplicatedText.toString();
    }

    public static String adjustText(String text, Integer length) {
        StringBuilder sb = new StringBuilder();
        final int middle = (length - text.length()) / 2;

        if (text.length() == length) {
            return text;
        } else if (text.length() > length) {
            return text.substring(0, length);
        } else {
            if (length % 2 == 0) {
                if (text.length() % 2 == 0) { // parz, parz
                    sb.append(duplicateText(" ", middle));
                } else { // nieparz, parz
                    sb.append(duplicateText(" ", middle + 1));
                }
            } else {
                if (text.length() % 2 == 0) { // parz, nieparz
                    sb.append(duplicateText(" ", middle + 1));
                } else { // nieparz, nieparz
                    sb.append(duplicateText(" ", middle));
                }
            }
            sb.append(text).append(duplicateText(" ", middle));
            return sb.toString();
        }
    }

    public static String adjustText_(String text, Integer expectedLength) {
        String expandedText = text;
        while (expandedText.length() < expectedLength) {
//            expandedText = String.join("", " ", expandedText, " ");
            expandedText = " ".concat(expandedText).concat(" ");
        }
        return expandedText.substring(0, expectedLength);
    }

    public static String formatMoney(Long amount) {
        StringBuilder amountAsSB = new StringBuilder();
        Long centsAmount = amount % 100;
        Long dolarsAmount = amount / 100;
        Long temp = dolarsAmount;

        if (centsAmount > 9 && centsAmount < 100) {
            amountAsSB.append(String.valueOf(centsAmount).charAt(1)).append(String.valueOf(centsAmount).charAt(0));
        }
        if (centsAmount < 10) {
            amountAsSB.append(String.valueOf(centsAmount));
            amountAsSB.append("0");
        }

        amountAsSB.append(",");

        if (dolarsAmount == 0) {
            amountAsSB.append("0");
        } else {
            while (dolarsAmount != 0) {
                temp = temp / 1000;
                dolarsAmount %= 1000;
                if (dolarsAmount > 99 && dolarsAmount < 1000) {
                    amountAsSB.append(String.valueOf(dolarsAmount).charAt(2));
                    amountAsSB.append(String.valueOf(dolarsAmount).charAt(1));
                    amountAsSB.append(String.valueOf(dolarsAmount).charAt(0));
                    amountAsSB.append(" ");
                }
                if (dolarsAmount > 9 && dolarsAmount < 100) {
                    amountAsSB.append(String.valueOf(dolarsAmount).charAt(1));
                    amountAsSB.append(String.valueOf(dolarsAmount).charAt(0));
                }
                if (dolarsAmount < 10) {
                    amountAsSB.append(String.valueOf(dolarsAmount));
                }
                dolarsAmount = temp;
            }
        }
        return amountAsSB.reverse().toString().trim();
    }

    public static String formatMoney_(Long amount) {
        return formatMoneyIntegrals(amount)
                .concat(",")
                .concat(formatMoneyDecimals(amount));
    }

    private static String formatMoneyIntegrals(Long amount) {
        String integrals = Long.toString(amount / 100);
        StringBuilder formattedMoney = new StringBuilder();
        int charactersTillLastSpace = 0;

        for (int charIndex = integrals.length() - 1; charIndex >= 0; charIndex--) {
            charactersTillLastSpace++;
            formattedMoney.append(integrals.charAt(charIndex));
            if (charactersTillLastSpace == 3) {
                formattedMoney.append(" ");
                charactersTillLastSpace = 0;
            }
        }
        return formattedMoney.reverse().toString().trim();
    }

    private static String formatMoneyDecimals(Long amount) {
        String decimals = Long.toString(amount % 100);
        if (decimals.length() < 2) {
            decimals = "0" + decimals;
        }
        return decimals;
    }
}
