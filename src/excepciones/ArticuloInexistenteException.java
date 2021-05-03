package excepciones;
/**
 * Si el articulo no existe
 * @author sergio Vera Jurado
 *
 */
public class ArticuloInexistenteException extends Exception {
  public ArticuloInexistenteException(String string) {
    super(string);
  }
  

  /**
   * 
   */
  private static final long serialVersionUID = -2308106173277932565L;

}
