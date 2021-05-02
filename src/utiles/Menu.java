package utiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 * 
 * @author Sergio Vera Jurado
 * Clase usada como menu en el que se introducen distintas opciones
 */
public class Menu {
  
  String titulo;
  List<String> opciones;

  public Menu(String titulo, String... opciones) {
    
    this.titulo = titulo;
    this.opciones = new ArrayList<>(Arrays.asList(opciones));
  }

  public int elegir() {
    
    System.out.println(this.titulo);
    System.out.println("-".repeat(this.titulo.length()) +"\n");
    
    for (int i = 0; i < this.opciones.size(); i++) {
      System.out.println((i+1) + ". " + this.opciones.get(i));
    }
    System.out.print("\nIntroduce una opci�n: ");
    
    Scanner s = new Scanner(System.in);
    int opcion = s.nextInt();
    
    while (opcion <= 0 || opcion > this.opciones.size()) {
      System.out.print("Opci�n incorrecta, introduzca otra: ");
      opcion = s.nextInt();
    }
    
    return opcion;
  }

  @Override
  public String toString() {
    return "Menu [titulo=" + titulo + ", opciones=" + opciones + "]";
  }

}
