package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for patient */
    public static final Prefix PREFIX_NAME = new Prefix("-n");
    public static final Prefix PREFIX_PHONE = new Prefix("-ph");
    public static final Prefix PREFIX_EMAIL = new Prefix("-em");
    public static final Prefix PREFIX_ADDRESS = new Prefix("-ad");
    public static final Prefix PREFIX_DOB = new Prefix("-dob");
    public static final Prefix PREFIX_DRUG_ALLERGY = new Prefix("-da");
    public static final Prefix PREFIX_EMERGENCY_CONTACT = new Prefix("-ec");
    public static final Prefix PREFIX_GENDER = new Prefix("-g");
    public static final Prefix PREFIX_IC = new Prefix("-ic");

    /* Prefix definitions for drug*/
    public static final Prefix PREFIX_ACTIVE_INGREDIENT = new Prefix("-ai");
    public static final Prefix PREFIX_DIRECTION = new Prefix("-dir");
    public static final Prefix PREFIX_EXPIRY_DATE = new Prefix("-ed");
    public static final Prefix PREFIX_PURPOSE = new Prefix("-pur");
    public static final Prefix PREFIX_SIDE_EFFECT = new Prefix("-se");
    public static final Prefix PREFIX_STORAGE_COUNT = new Prefix("-sc");
    public static final Prefix PREFIX_TRADE_NAME = new Prefix("-tn");


}
