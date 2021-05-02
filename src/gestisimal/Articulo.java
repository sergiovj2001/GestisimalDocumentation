package gestisimal;

import excepciones.NumeroNegativoException;


/**
 * Clase Artículo que representa a los art�culos del almac�n. Su estado ser�: c�digo, descripci�n,
 * precio de compra, precio de venta, n�mero de unidades (nunca negativas), stock de seguridad y
 * stock máximo. Como comportamiento: Consideramos que el c�digo va a generarse de forma autom�tica
 * en el constructor, asi no puede haber dos artículos con el mismo c�digo. Esto implica que no
 * puede modificarse el código de un artículo, si el resto de las propiedades. Podremos mostrar el
 * artículo, por lo que necesito una representación del art�culo en forma de cadena (toString).
 */

/**
 * 
 * @author Sergio Vera Jurado
 * @version 1.1
 * 
 */
public class Articulo {
  private static int codigoGenerar = 0;
  private int codigo;
  private double precioCompra;
  private double precioVenta;
  private String descripcion;
  private int numeroUnidades;
  private int stockSeguridad;
  private int stockMaximo;

  /**
   * 
   * @param precioCompra
   * @param precioVenta
   * @param descripcion
   * @param numeroUnidades
   * @param stockSeguridad
   * @param stockMaximo
   * @throws NumeroNegativoException
   * @throws StockMaximoExcedidoException
   * @throws StockMinimoIncumplidoException
   */
  Articulo(double precioCompra, double precioVenta, String descripcion, int numeroUnidades,
      int stockSeguridad, int stockMaximo) throws NumeroNegativoException {
    setCodigo();
    setPrecioCompra(precioCompra);
    setPrecioVenta(precioVenta);
    setDescripcion(descripcion);
    setStockSeguridad(stockSeguridad);
    setStockMaximo(stockMaximo);
    setNumeroUnidades(numeroUnidades);
  }
/**
 * 
 * @param codigo
 */
  Articulo(int codigo) {
    this.codigo = codigo;
  }
/**
 * 
 * @return codigo
 */
  int getCodigo() {
    return codigo;
  }
/**
 * establece la variable codigo del artículo
 */
  private void setCodigo() {
    codigoGenerar += 1;
    this.codigo = codigoGenerar;
  }
/**
 * @return double precioCompra
 */
  double getPrecioCompra() {
    return precioCompra;
  }
/**
 * 
 * @param precioCompra
 * @throws NumeroNegativoException
 */
  private void setPrecioCompra(double precioCompra) throws NumeroNegativoException {
    if (precioCompra < 0)
      throw new IllegalArgumentException("El precio de compra no puede ser negativo");
    this.precioCompra = precioCompra;
  }
/**
 * 
 * @return precioVenta
 */
  double getPrecioVenta() {
    return precioVenta;
  }
/**
 * 
 * @param precioVenta
 */
  private void setPrecioVenta(double precioVenta) {
    if (precioVenta < 0)
      throw new IllegalArgumentException("El precio de venta no puede ser negativo");
    this.precioVenta = precioVenta;
  }
/**
 * 
 * @return descripcion
 */
  String getDescripcion() {
    return descripcion;
  }
/**
 * 
 * @param descripcion
 */
  private void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
/**
 * 
 * @return numeroUnidades
 */
  int getNumeroUnidades() {
    return numeroUnidades;
  }
/**
 * @param cantidad
 * @throws NumeroNegativoException
 */
  private void setNumeroUnidades(int cantidad) throws NumeroNegativoException {
    if (cantidad < 0) {
      throw new IllegalArgumentException("No puedes introducir una cantidad inferior a cero");
    }
    this.numeroUnidades = cantidad;
  }

/**
 * 
 * @return stockSeguridad
 */
  int getStockSeguridad() {
    return stockSeguridad;
  }
/**
 * 
 * @param stockSeguridad
 */
  private void setStockSeguridad(int stockSeguridad) {
    this.stockSeguridad = stockSeguridad;
  }
/**
 * 
 * @return stockMaximo
 */
  int getStockMaximo() {
    return stockMaximo;
  }
/**
 * 
 * @param stockMaximo
 */
  private void setStockMaximo(int stockMaximo) {
    this.stockMaximo = stockMaximo;
  }

  /**
   * Este metodo incrementa el numero de unidades de un artículo
   * 
   * @param cantidadAIncrementar
   * @throws NumeroNegativoException
   * @throws StockMaximoExcedidoException
   * @throws StockMinimoIncumplidoException
   */

  void incrementar(int cantidadAIncrementar) throws NumeroNegativoException {
    if (cantidadAIncrementar < 0)
      throw new NumeroNegativoException("No puedes incrementar usando numeros negativos");
    setNumeroUnidades(getNumeroUnidades() + cantidadAIncrementar);

  }

  /**
   * Este metodo decrementa las unidades disponibles de un artículo
   * 
   * @param cantidadADecrementar
   * @throws NumeroNegativoException
   * @throws StockMaximoExcedidoException
   * @throws StockMinimoIncumplidoException
   */

  void decrementar(int cantidadADecrementar) throws NumeroNegativoException {
    if (cantidadADecrementar < 0)
      throw new NumeroNegativoException("No puedes decrementar usando numeros negativos");
    setNumeroUnidades(getNumeroUnidades() - cantidadADecrementar);

  }


  @Override
  public String toString() {
    return "Articulo [codigo=" + codigo + ", precioCompra=" + precioCompra + ", precioVenta="
        + precioVenta + ", descripcion=" + descripcion + ", numeroUnidades=" + numeroUnidades
        + ", stockSeguridad=" + stockSeguridad + ", stockMaximo=" + stockMaximo + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + codigo;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Articulo other = (Articulo) obj;
    if (codigo != other.codigo)
      return false;
    return true;
  }

}
