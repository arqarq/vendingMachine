package pl.sda.vending.util;

public class StringUtil {
    public static String duplicateText(String text, Integer count){
        StringBuilder duplicatedText = new StringBuilder();
        for (int i = 0; i < count; i++) {
            duplicatedText.append(text);
        }
        return duplicatedText.toString();
    }

    public static String adjustText(String text, Integer length) {
        StringBuilder sb = new StringBuilder();
        String temp = text;

        if (text.length() == length) {
            return text;
        } else if (text.length() > length) {
            temp = text.substring(0, length);
        }

        if (length % 2 == 0 && text.length() % 2 == 0) // parz
        {
            for (int i = 0; i < text.length() / 2; i++) {
                sb.append(" ");
            }
            sb.append(temp);
            for (int i = 0; i < text.length() / 2; i++) {
                sb.append(" ");
            }
        } else if (length % 2 == 0 && text.length() % 2 != 0) { // nieparz
            for (int i = 0; i < length / 2 - text.length() / 2; i++) {
                sb.append(" ");
            }
            sb.append(temp);
            for (int i = 0; i < length / 2 - text.length() / 2 - 1; i++) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static String adjustText_(String text, Integer expectedLength) {
        String expandedText = text;
        while (expandedText.length() < expectedLength) {
            expandedText = " " + expandedText + " ";
        }
        return expandedText.substring(0, expectedLength);
    }

    public static String formatMoney_(Long amount) {
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

    public static String formatMoney(Long amount) {
        return formatMoneyIntegrals(amount)
                + ","
                + formatMoneyDecimals(amount);
    }

    private static String formatMoneyIntegrals(Long amount) {
        String integrals = Long.toString(amount / 100);
        StringBuilder formattedMoney = new StringBuilder();
        Integer charactersTillLastSpace = 0;
        for (int charIndex = integrals.length() - 1; charIndex >= 0; charIndex--) {
            charactersTillLastSpace++;
            formattedMoney = formattedMoney.append(integrals.charAt(charIndex));
            if (charactersTillLastSpace >= 3) {
                formattedMoney = formattedMoney.append(" ");
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
