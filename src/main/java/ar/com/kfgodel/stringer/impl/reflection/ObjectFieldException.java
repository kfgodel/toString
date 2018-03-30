package ar.com.kfgodel.stringer.impl.reflection;

import ar.com.kfgodel.stringer.api.exceptions.StringerException;

/**
 * This class represents a reflection error accesing a property
 * Date: 30/03/18 - 16:38
 */
public class ObjectFieldException extends StringerException {

  public ObjectFieldException(String message) {
    super(message);
  }

  public ObjectFieldException(String message, Throwable cause) {
    super(message, cause);
  }

  public ObjectFieldException(Throwable cause) {
    super(cause);
  }
}
