package problemas;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.tiposrecursivos.BinaryTree;

public class Problema3 {
	
	/*
	 	Diseñe un algoritmo que, dado un árbol binario de enteros, determine el camino del
		árbol desde la raíz a una hoja no vacía tal que el producto de sus etiquetas sea máximo.
	 */

	public static void Ejercicio3(String fichero) {
		List<BinaryTree<Integer>> arboles = lectura(fichero);
		
		System.out.println("\nSOLUCION RECURSIVA\n");
		
		
		for(BinaryTree<Integer> arbol: arboles) {
			Recursivo(arbol);
		}
	}
	
	// Función de lectura
	
	public static List<BinaryTree<Integer>> lectura(String fichero){

		List<BinaryTree<Integer>> arboles;

		arboles = Files2.streamFromFile(fichero)
				.map(linea->BinaryTree.parse(linea, s->Integer.parseInt(s))) 
				.toList();

		return arboles;	
	}
	
	// Función Recursiva
	
	public static void Recursivo(BinaryTree<Integer> arbol){
		List<List<Integer>> lista = new ArrayList<>();
		List<Integer> apoyo = new ArrayList<>();
		List<List<Integer>> res = AuxRecursivo(arbol, lista, apoyo);
		int posicion = 0;
		int mul1 = 1;
		for(int i = 0; i < res.size(); i++) {
			int mul2 = 1;
			for(Integer e:res.get(i)) {
				mul2 = mul2 * e;
			}
			if(mul1 < mul2) {
				mul1 = mul2;
				posicion = i;
			}
		}
		System.out.println(arbol + ": " + res.get(posicion) + " " + mul1);
	}
	
	public static List<List<Integer>> AuxRecursivo(BinaryTree<Integer> arbol, List<List<Integer>> lista, List<Integer> apoyo){
		
		List<Integer> aux1 = new ArrayList<>();
		List<Integer> aux2 = new ArrayList<>();
		
		switch(arbol.getType()) {
		case Empty:
			apoyo.add(0); // no queremos los vacios, así que multiplicarán por cero, si entran...
			break;
		case Binary:
			apoyo.add(arbol.getLabel());
			aux1 = List2.copy(apoyo); // Usamos copy, porque sino se borra el valor de apoyo
			aux2 = List2.copy(apoyo); // en la siguiente recursividad
			AuxRecursivo(arbol.getLeft(), lista, aux1);
			AuxRecursivo(arbol.getRight(), lista, aux2);
			break;
		case Leaf:
			apoyo.add(arbol.getLabel());
			lista.add(apoyo);
			break;
		default:
			throw new IllegalArgumentException("El arbol dado es erroneo");
		}
		
		return lista;
	}
	
}
