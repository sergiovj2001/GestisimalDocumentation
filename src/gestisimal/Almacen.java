package gestisimal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import excepciones.AlmacenCSVException;
import excepciones.AlmacenXMLException;
import excepciones.ArticuloInexistenteException;
import excepciones.ArticuloRepetidoException;
import excepciones.CodigoNoValidoException;
import excepciones.NumeroNegativoException;
/**
 * Clase Almacén que realice el alta, baja, modificación, entrada de mercanc�a (incrementa
 * unidades), salida de mercanc�a (decrementa unidades). El estado será un ArrayList de art�culos.
 * Esta clase es un envoltorio de un ArrayList. Su comportamiento será: añadir artículos (no puede
 * haber dos artículos iguales), eliminar artículos, incrementar las existencias de un artículo (se
 * delega en la clase Artículo), decrementar las existencias de un art�culo (nunca por debajo de
 * cero, se delega en la clase Artículo), devolver un artículo (para mostrarlo). Para listar el
 * almacén podrá devolverse una cadena con todos los artículos del almac�n (toString).
 */

public class Almacen {

  private static final String CSV_CABECERA =
      "Descripcion,Precio compra,Precio venta,Numero unidades,Stock maximo,Stock seguridad";
  private static ArrayList<Articulo> almacen = new ArrayList<Articulo>();

  /**
   * Da de alta un artículo
   * @param precioCompra
   * @param precioVenta
   * @param descripcion
   * @param numeroUnidades
   * @param stockSeguridad
   * @param stockMaximo
   * @throws NumeroNegativoException
   * @throws StockMaximoExcedidoException
   * @throws StockMinimoIncumplidoException
   * @throws ArticuloRepetidoException
   */

  void alta(double precioCompra, double precioVenta, String descripcion, int numeroUnidades,
      int stockSeguridad, int stockMaximo)
      throws NumeroNegativoException, ArticuloRepetidoException {
    Articulo articulo = new Articulo(precioCompra, precioVenta, descripcion, numeroUnidades,
        stockSeguridad, stockMaximo);
    if (!almacen.contains(articulo))
      almacen.add(articulo);
    else
      throw new ArticuloRepetidoException("El articulo introducido ya existe");

  }

  /**
   * Elimina un articulo previamente introducido
   * 
   * @param articulo
   * @return
   * @throws CodigoNoValidoException
   */
  boolean eliminar(int codigo) throws CodigoNoValidoException {
    return almacen.remove(new Articulo(codigo));

  }

  /**
   * metodo que busca un artículo y llama al metodo incrementar de 
   * la clase articulo
   * @see Articulo
   * @param cantidad
   * @param codigo
   * @throws NumeroNegativoException
   * @throws StockMaximoExcedidoException
   * @throws StockMinimoIncumplidoException
   */

  void incrementarCantidad(int cantidad, int codigo)
      throws NumeroNegativoException, ArticuloInexistenteException {
    buscarArticulo(codigo).incrementar(cantidad);


  }

  /**
   * metodo que busca un artículo y llama al metodo decrementar de 
   * la clase articulo
   * @see Articulo 
   * @param cantidad
   * @param codigo
   * @return
   * @throws NumeroNegativoException
   * @throws StockMaximoExcedidoException
   * @throws StockMinimoIncumplidoException
   * @throws ArticuloInexistenteException
   */
  void decrementarCantidad(int cantidad, int codigo)
      throws NumeroNegativoException, ArticuloInexistenteException {
    buscarArticulo(codigo).decrementar(cantidad);
  }

  /**
   * Devuelve la posición del articulo en el arraylist segun el codigo pasado
   * 
   * @param codigo
   */
  Articulo buscarArticulo(int codigo) throws ArticuloInexistenteException {
    try {
      return almacen.get(almacen.indexOf((new Articulo(codigo))));
    } catch (IndexOutOfBoundsException e) {
      throw new ArticuloInexistenteException(
          "El codigo introducido no pertenece a ningun art�culo");
    }
  }


  @Override
  public String toString() {
    return "[almacen=" + almacen + "]";
  }

  /**
   * metodo que guarda los datos introducidos en un arhivo CSV
   * @param fileName
   * @throws IOException
   */

  void guardarCSV(String fileName) throws IOException {
    var file = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.CREATE);
    guardarCabeceraCSV(file);
    for (Articulo articulo : almacen) {
      guardarArticuloCSV(articulo, file);
    }
    file.close();
  }

  /**
   * metodo que escribe la cabecera del CSV
   * @param file
   * @throws IOException
   */

  private void guardarCabeceraCSV(BufferedWriter file) throws IOException {
    file.write(CSV_CABECERA);
    file.newLine();
  }

  /**
   * metodo que guarda las distintas variables de los articulos
   * @param almacen
   * @param file
   * @param i
   * @throws IOException
   */

  private void guardarArticuloCSV(Articulo articulo, BufferedWriter file)
      throws IOException {
    file.write("\"" + articulo.getDescripcion() + "\",");
    file.write("\"" + articulo.getPrecioCompra() + "\",");
    file.write("\"" + articulo.getPrecioVenta() + "\",");
    file.write("\"" + articulo.getNumeroUnidades() + "\",");
    file.write("\"" + articulo.getStockMaximo() + "\",");
    file.write("\"" + articulo.getStockSeguridad() + "\"");
    file.newLine();
  }

  /**
   * metodo que carga un archivo csv previamente especificado
   * @param fileName
   * @throws IOException
   * @throws AlmacenCSVException
   * @throws NumeroNegativoException
   */
  static void cargarCSV(String fileName)
      throws AlmacenCSVException, NumeroNegativoException, IOException {
    var file = Files.newBufferedReader(Paths.get(fileName));
    validadCabeceraCSV(file);

    String line;
    while ((line = file.readLine()) != null) {
      Articulo articulo = nuevoArticuloCSV(line);
      almacen.add(articulo);
    }
    file.close();
  }

  /**
   * metodo que valida la cabecera del csv que se va ha cargar
   * @param file
   * @throws IOException
   * @throws AlmacenCSVException
   */
  private static void validadCabeceraCSV(BufferedReader file)
      throws IOException, AlmacenCSVException {
    String cabecera = file.readLine().trim();
    if (!cabecera.equalsIgnoreCase(CSV_CABECERA)) {
      throw new AlmacenCSVException("Cabecera errónea en el CSV.");
    }
  }

  /**
   * metodo que lee el csv introducido y crea el articulo correspondiente
   * @param line
   * @throws AlmacenCSVException
   * @throws NumeroNegativoException
   */
  private static Articulo nuevoArticuloCSV(String line)
      throws AlmacenCSVException, NumeroNegativoException {
    validarArticulosCSV(line);
    String[] guardarArticulos = line.split("\",");

    String descripcion = guardarArticulos[0].replace("\"", "");
    double precioCompra = Double.parseDouble(guardarArticulos[1].replace("\"", ""));
    double precioVenta = Double.parseDouble(guardarArticulos[2].replace("\"", ""));
    int numeroUnidades = Integer.parseInt(guardarArticulos[3].replace("\"", ""));
    int stockMaximo = Integer.parseInt(guardarArticulos[4].replace("\"", ""));
    int stockSeguridad = Integer.parseInt(guardarArticulos[5].replace("\"", ""));

    return new Articulo(precioCompra, precioVenta, descripcion, numeroUnidades, stockSeguridad,
        stockMaximo);

  }

  /**
   * metodo que valida que las variables del csv sean las adecuadas
   * @param line
   * @throws AlmacenCSVException
   */
  private static void validarArticulosCSV(String line) throws AlmacenCSVException {
    int numeroDeCampos = line.split("\",").length;
    int numeroDeCamposHead = CSV_CABECERA.split(",").length;

    if (numeroDeCampos != numeroDeCamposHead) {
      throw new AlmacenCSVException(
          line + ": no es un formato válido para convertirlo en Articulo.");
    }
  }

  /**
   * metodo que guarda los artículos creados en un archivo xml
   * @param fileName
   * @throws AlmacenXMLException
   * @throws IOException
   */
  void guardarXML(String fileName) throws AlmacenXMLException, IOException {
    try {
      Document xml = crearDocumentXML();
      guardarRootXML(xml);
      for (Articulo articulo: almacen) {
        guardarArticuloXML(articulo, xml);
      }

      guardarArchivoXML(xml, fileName);

    } catch (ParserConfigurationException | TransformerException e) {
      throw new AlmacenXMLException("Error al generar XML");
    }
  }

  /**
   * metodo que crea el documento Xml
   * @return
   * @throws ParserConfigurationException
   */

  private Document crearDocumentXML() throws ParserConfigurationException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.newDocument();
    return document;
  }

  /**
   * metodo que crea el elemento root en el xml
   * @param xml
   */

  private void guardarRootXML(Document xml) {
    Element root = xml.createElement("Almacen");
    xml.appendChild(root);
  }

  /**
   * metodo que guarda las distintas variables del artículo en el xml
   * @param almacen
   * @param xml
   */

  private void guardarArticuloXML(Articulo articulo, Document xml) {
    Element root = xml.getDocumentElement();

    Element articuloElement = xml.createElement("Articulo");
    root.appendChild(articuloElement);

    guardarCampoArticulo("Descripcion", articulo.getDescripcion(), articuloElement);
    guardarCampoArticulo("PrecioCompra", String.valueOf(articulo.getPrecioCompra()),
        articuloElement);
    guardarCampoArticulo("PrecioVenta", String.valueOf(articulo.getPrecioVenta()),
        articuloElement);
    guardarCampoArticulo("NumeroUnidades", String.valueOf(articulo.getNumeroUnidades()),
        articuloElement);
    guardarCampoArticulo("StockMaximo", String.valueOf(articulo.getStockMaximo()),
        articuloElement);
    guardarCampoArticulo("StockSeguridad", String.valueOf(articulo.getStockSeguridad()),
        articuloElement);

  }

  /**
   * Metodo que crea el campo en el xml
   * @param atributo
   * @param valorCampo
   * @param articuloElement
   */

  private void guardarCampoArticulo(String atributo, String valorCampo, Element articuloElement) {
    Document xml = articuloElement.getOwnerDocument();
    Element attrElement = xml.createElement(atributo);
    attrElement.appendChild(xml.createTextNode(valorCampo));
    articuloElement.appendChild(attrElement);
  }

  /**
   * Metodo que guarda el xml con el nombre especificado
   * @param xml
   * @param fileName
   * @throws IOException
   * @throws TransformerException
   */

  private void guardarArchivoXML(Document xml, String fileName)
      throws IOException, TransformerException {
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    DOMSource xmlSource = new DOMSource(xml);
    StreamResult output = new StreamResult(new FileWriter(fileName));
    transformer.transform(xmlSource, output);
  }

  /**
   * Metodo que carga el xml especificado
   * @param fileName
   * @throws IOException
   * @throws AlmacenXMLException
   * @throws NumeroNegativoException
   */

  static void cargarXml(String fileName)
      throws IOException, AlmacenXMLException, NumeroNegativoException {
    try {
      Document xml = nuevoDocumentoXML(fileName);
      NodeList articulos = xml.getElementsByTagName("Articulo");
      for (int i = 0; i < articulos.getLength(); i++) {
        Articulo articulo = nuevoArticuloXML(articulos.item(i));
        almacen.add(articulo);
      }
    } catch (ParserConfigurationException | SAXException e) {
      throw new AlmacenXMLException("Error al cargar XML ");
    }
  }

  /**
   * metodo que crea el archivo xml
   * @param fileName
   * @return
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   */

  private static Document nuevoDocumentoXML(String fileName)
      throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(new File(fileName));
    document.getDocumentElement().normalize();
    return document;
  }

  /**
   * metodo que crea el articulo en base a los datos del xml
   * @param itemArticulo
   * @return
   * @throws NumeroNegativoException
   */
  private static Articulo nuevoArticuloXML(Node itemArticulo) throws NumeroNegativoException {
    String descripcion = getCampoArticulo("Descripcion", itemArticulo);
    double precioCompra = Double.parseDouble(getCampoArticulo("PrecioCompra", itemArticulo));
    double precioVenta = Double.parseDouble(getCampoArticulo("PrecioVenta", itemArticulo));
    int numeroUnidades = Integer.parseInt(getCampoArticulo("NumeroUnidades", itemArticulo));
    int stockMaximo = Integer.parseInt(getCampoArticulo("StockMaximo", itemArticulo));
    int stockSeguridad = Integer.parseInt(getCampoArticulo("StockSeguridad", itemArticulo));

    return new Articulo(precioCompra, precioVenta, descripcion, numeroUnidades, stockSeguridad,
        stockMaximo);
  }

  /**
   * metodo que obtiene los datos del nodo del xml
   * @param field
   * @param itemArticulo
   * @return
   */

  private static String getCampoArticulo(String field, Node itemArticulo) {
    Element elementoArticulo = (Element) itemArticulo;
    return elementoArticulo.getElementsByTagName(field).item(0).getTextContent();
  }
}

