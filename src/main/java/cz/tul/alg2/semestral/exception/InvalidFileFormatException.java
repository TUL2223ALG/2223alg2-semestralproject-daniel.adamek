package cz.tul.alg2.semestral.exception;

class InvalidFileFormatException  extends Exception {
    public InvalidFileFormatException (String str) {
        // calling the constructor of parent Exception
        super(str);
    }
}
