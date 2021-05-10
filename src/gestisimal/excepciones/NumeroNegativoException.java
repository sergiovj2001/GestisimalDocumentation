package gestisimal.excepciones;

/**
 * Salta si el numero es negativo
 * @author sergio Vera Jurado
 */
public class NumeroNegativoException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -7619411719334351922L;

  public NumeroNegativoException(String string) {
    super(string);
  }

}
