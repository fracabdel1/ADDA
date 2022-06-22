package problemas;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.Tree;

public class Problema4 {
	
	/*
	 	Diseñe un algoritmo que dado un árbol n-ario de caracteres devuelva un conjunto de
		cadenas de caracteres que contenga todas las cadenas palíndromas que se formen desde
		la raíz a una hoja no vacía.
	 */

	public static void Ejercicio4(String fichero) {
		List<Tree<String>> arboles = lectura(fichero);
		for(Tree<String> arbol: arboles) {
			System.out.println(arbol + ": " + Recursivo(arbol));
		}
	}
	
	// Función de lectura
	
	public static List<Tree<String>> lectura(String fichero){
		List<Tree<String>> arboles;

		arboles = Files2.streamFromFile(fichero)
				.map(linea->Tree.parse(linea)) 
				.toList();

		return arboles;
	}
	
	// Función recursiva
	
	public static List<String> Recursivo(Tree<String> arbol){
		List<String> lista = new ArrayList<>();
		return AuxRecursivo(arbol, lista, "");
	}
	
	public static List<String> AuxRecursivo(Tree<String> arbol, List<String> lista, String palabra){
		
		switch(arbol.getType()) {
		case Empty:
			break;
		case Nary:
			palabra = palabra.concat(arbol.getLabel()); // Concatenamos las letras
			for(int i = 0; i < arbol.getNumOfChildren(); i++) {
				AuxRecursivo(arbol.getChild(i), lista, palabra);
			}
			break;
		case Leaf:
			palabra = palabra.concat(arbol.getLabel());
			Boolean palindromo = true;
			for(int e = 0; e < palabra.length(); e++) {
				if((palabra.charAt(e) == palabra.charAt(palabra.length()-e-1)) && palindromo) {
					if(e == palabra.length() - 1) lista.add(palabra);
				}else {
					palindromo = false;
				}
			}
			break;
		default:
			throw new IllegalArgumentException("El árbol dado es erroneo");
		}

		return lista;
	}
	
}
