package excepciones;
/**
 * Si da error al llamar al xml del almacen
 * @author Sergio Vera Jurado
 *
 */
public class AlmacenXMLException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1689646165076599118L;

  public AlmacenXMLException(String string) {
    super(string);
  }

}
