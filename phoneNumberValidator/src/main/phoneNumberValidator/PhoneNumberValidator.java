package phoneNumberValidator;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.NumberParseException;

import java.util.Scanner;

/**
 *
 * @author maximilian
 */
public class PhoneNumberValidator {

    private static final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Dieses Programm validiert internationale Telefonnummern");
        System.out.println("Bitte nutzen Sie eine internationale Schreibweise und '+' oder '00' als VAZ.");

        System.out.println("Bitte geben Sie ihr Land an (z.B. DE, CH, AT,...):");
        String country;
        if(args[0].isEmpty()) country = input.nextLine();
        else country = args[0];

        System.out.println("Bitte geben Sie nun ihre Telefonnummer ein:");
        String number;
        if(args[0].isEmpty()) number = input.nextLine();
        else number = args[1];

        if (validInput(number, country)) System.out.println("Gültige Nummer!");
        else System.out.println("Ungültige Nummer!");
    }
    
    public static boolean validInput(String inputNumber, String inputCountry) {
        try {
            Phonenumber.PhoneNumber number = util.parseAndKeepRawInput(inputNumber, inputCountry);
            PhoneNumberUtil.ValidationResult possibleResult = util.isPossibleNumberWithReason(number);
            switch (possibleResult) {
                case IS_POSSIBLE:
                    System.out.println("Diese Nummer ist möglich.");
                    break;
                case IS_POSSIBLE_LOCAL_ONLY:
                    System.out.println("Diese Nummer ist nur innerhalb einer bestimmten Region möglich und erfüllt nicht alle Kriterien einer internationalen Rufnummer.");
                    break;
                default:
                    System.out.println(possibleResult);
            }
            return util.isValidNumber(number);
        } catch (NumberParseException e) {
            System.out.println("FEHLER");
            switch (e.getErrorType()) {
                case INVALID_COUNTRY_CODE:
                    System.out.println("Ungültiger Ländercode");
                    break;
                case NOT_A_NUMBER:
                    System.out.println("Die Eingabe entspricht nicht den Mindestanforderungen einer Telefonnummer!");
                    break;
                case TOO_SHORT_NSN:
                case TOO_SHORT_AFTER_IDD:
                    System.out.println("Zu kurz!");
                    break;
                case TOO_LONG:
                    System.out.println("Zu lang!");
                    break;
            }
        }
        return false;
    }
}
