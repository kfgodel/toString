package ar.com.kfgodel.stringer.api.exceptions;

/**
 * This class represents and error on a stringer component
 * Date: 30/03/18 - 16:39
 */
public class StringerException extends RuntimeException {

  public StringerException(String message) {
    super(message);
  }

  public StringerException(String message, Throwable cause) {
    super(message, cause);
  }

  public StringerException(Throwable cause) {
    super(cause);
  }
}
