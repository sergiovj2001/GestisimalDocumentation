package excepciones;

/**
 * si el codigo no es valido
 * @author Sergio Vera Jurado
 *
 */
public class CodigoNoValidoException extends Exception {
  /**
   * 
   */
  private static final long serialVersionUID = 4954135465152486030L;

  public CodigoNoValidoException(String string) {
    super(string);
  }
}
