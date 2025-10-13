package backend.utils;

public class Validation {

    /**
     * Valida un número de tarjeta en formato con espacios, utiliza el algoritmo de Luhn, que sirve para validar números de identificación.
     *
     * @param cardNumber es un String que contendrá el texto que representa un número de tarjeta.
     * @return true si es válido, false si no.
     */
    public boolean validateUserCard(String cardNumber) {
        if (cardNumber == null) return false;

        String regex = "^\\d{4}( \\d{4}){2,4}\\d{1,4}$";
        if (!cardNumber.matches(regex)) return false;

        String digits = cardNumber.replaceAll(" ", "");
        if (digits.length() < 13 || digits.length() > 19) return false;

        int sum = 0;
        boolean alternate = false;
        for (int i = 0; i < digits.length(); i++) {
            int n = digits.charAt(i) - '0';
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }

    /**
     * Valida que un código de seguridad de una tarjeta sea el correcto según el tipo de tarjeta, valida si es una tarjeta de American Expresss, Visa o MasterCard.
     *
     * @param cvv      es el código que se validará, es un String.
     * @param cardType es el tipo de tarjeta con la que se realizará la validación.
     * @return true si cumple o false si no.
     */
    public boolean isValidCVV(String cvv, String cardType) {
        if (cvv == null || !cvv.matches("\\d+")) return false;

        int length = cvv.length();
        switch (cardType.toLowerCase()) {
            case "amex":
                return length == 4;
            default:
                return length == 3;
        }
    }
}
