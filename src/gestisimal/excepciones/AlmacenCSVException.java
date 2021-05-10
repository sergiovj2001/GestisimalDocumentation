package gestisimal.excepciones;
/**
 * Si da error al llamar al csv del almacen
 * @author Sergio Vera Jurado
 *
 */
public class AlmacenCSVException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 4760983661709172079L;

  public AlmacenCSVException(String string) {
    super(string);
  }

}
