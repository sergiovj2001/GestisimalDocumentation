package gestisimal;

import gestisimal.excepciones.NumeroNegativoException;

/**
 * Clase Articulo que representa a los articulos del almacen. Su estado sera: codigo, descripcion,
 * precio de compra, precio de venta, numero de unidades (nunca negativas), stock de seguridad y
 * stock maximo. Como comportamiento: Consideramos que el codigo va a generarse de forma automatica
 * en el constructor, asi no puede haber dos artículos con el mismo codigo. Esto implica que no
 * puede modificarse el codigo de un articulo, si el resto de las propiedades. Podremos mostrar el
 * articulo, por lo que necesito una representación del artículo en forma de cadena (toString).
 * @author Sergio Vera Jurado
 * @version 1.1
 * 
 */
public class Articulo {
  /**
   * Incremento que permite identificar al articulo de forma inequivoca
   */
  private static int codigoGenerar = 0;
  /**
   * Identifica al articulo
   */
  private int codigo;
  /**
   * Precio al que se compra un articulo
   */
  private double precioCompra;
  /**
   * Precio al que se vende un articulo
   */
  private double precioVenta;
  /**
   * una pequeña descripcion del articulo
   */
  private String descripcion;
  /**
   * Numero de unidades disponibles de un articulo
   */
  private int numeroUnidades;
  /**
   * Stock minimo de un articulo (No es obligatorio que sea inferior al numero de unidades)
   */
  private int stockSeguridad;
  /**
   * Stock maximo de un articulo (No es obligatorio que sea inferior al numero de unidades)
   */
  private int stockMaximo;

  /**
   * Construtor de la clase Articulo
   * @param precioCompra El precio de compra de un articulo
   * @param precioVenta El precio de venta de un articulo
   * @param descripcion Descripción de un articulo
   * @param numeroUnidades Cantidades disponibles del nuevo articulo
   * @param stockSeguridad Stock minimo permitido de un nuevo articulo
   * @param stockMaximo Stock maximo permitido de un nuevo articulo
   * @throws NumeroNegativoException Salta cuando el numero de unidades o el precio de compra es negativo
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
 * Constructor de la clase articulo cuando solo especificamos el codigo
 * @param codigo numero entero para identificar un artículo
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
 * establece la variable codigo del articulo
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
 * Establece el precio de compra del articulo
 * @param precioCompra Lo que le cuesta un artículo al almacen
 * @throws NumeroNegativoException Cuando el nuemro introducido es negativo
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
 * Establece el precio de venta de un articulo
 * @param precioVenta cantidad que se recibe por un artículo
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
 * Establece la descripcion de un articulo
 * @param descripcion cadena con una breve explicación sobre el producto
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
 * establece el numero de unidades
 * @param cantidad numero de existencias de un artículo
 * @throws NumeroNegativoException cuando la cantidad pasada es un numero negativo
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
 * establece el stock de seguridad
 * @param stockSeguridad cantidad minima que debe haber de un articulo
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
 * Establece el stock maximo
 * @param stockMaximo cantidad maxima que debe haber de un articulo
 */
  private void setStockMaximo(int stockMaximo) {
    this.stockMaximo = stockMaximo;
  }

  /**
   * Este metodo incrementa el numero de unidades de un artículo
   * 
   * @param cantidadAIncrementar la cantidad en la que se va a incrementar el valor
   * @throws NumeroNegativoException si la cantidad en la que se va a aumentar es negativa
   */

  void incrementar(int cantidadAIncrementar) throws NumeroNegativoException {
    if (cantidadAIncrementar < 0)
      throw new NumeroNegativoException("No puedes incrementar usando numeros negativos");
    setNumeroUnidades(getNumeroUnidades() + cantidadAIncrementar);

  }

  /**
   * Este metodo decrementa las unidades disponibles de un artículo
   * 
   * @param cantidadADecrementar la cantidad en la que se va a decrementar el valor
   * @throws NumeroNegativoException si el numero con el que se va a decrementar es negativo
   */

  void decrementar(int cantidadADecrementar) throws NumeroNegativoException {
    if (cantidadADecrementar < 0)
      throw new NumeroNegativoException("No puedes decrementar usando numeros negativos");
    setNumeroUnidades(getNumeroUnidades() - cantidadADecrementar);

  }

/**
 * To string del articulo
 */
  @Override
  public String toString() {
    return "Articulo [codigo=" + codigo + ", precioCompra=" + precioCompra + ", precioVenta="
        + precioVenta + ", descripcion=" + descripcion + ", numeroUnidades=" + numeroUnidades
        + ", stockSeguridad=" + stockSeguridad + ", stockMaximo=" + stockMaximo + "]";
  }
/**
 * hashCode del articulo
 */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + codigo;
    return result;
  }
/**
 * equals de articulo, 2 articulos son iguales si tienen el mismo codigo
 */
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
