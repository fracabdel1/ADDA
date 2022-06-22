package problemas;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;

public class Problema2 {
	
	/*
	 	Dado un árbol binario ordenado de enteros, diseñe un algoritmo que devuelva un
		conjunto con todos los elementos mayores o iguales que uno dado.
	 */
	
	public static void Ejercicio2(String fichero) {
		List<List<String>> lineas = lectura(fichero);
		System.out.println("\nSOLUCION RECURSIVA\n");
		for(List<String> linea: lineas) {
			System.out.println(linea.get(0) + ": " + Recursivo(BinaryTree.parse(linea.get(0),
					s->Integer.parseInt(s)), Integer.parseInt(linea.get(1))));
		}
		
	}
	
	// Función de lectura
	
	public static List<List<String>> lectura (String fichero){

		Function<String, List<String>> separar = line -> // Función para separar elementos
		Arrays.stream(line.split("#"))
		.map(e -> e.toString())
		.collect(Collectors.toList());

		List<List<String>> salida = Files2.streamFromFile(fichero) // Función Stream
				.map(separar)
				.collect(Collectors.toList());

		return salida;
	}
	
	// Función recursivo
	
	public static Set<Integer> Recursivo(BinaryTree<Integer> arbol, Integer num){
		Set<Integer> conjunto = new HashSet<>();
		return AuxRecursivo(arbol, num, conjunto);
	}
	
	public static Set<Integer> AuxRecursivo(BinaryTree<Integer> arbol, Integer num, Set<Integer> conjunto){
		
		if(arbol.getLabel() >= num) conjunto.add(arbol.getLabel());
		
		switch(arbol.getType()) {
		case Empty:
			break;
		case Leaf:
			break;
		case Binary:
			AuxRecursivo(arbol.getLeft(), num, conjunto);
			AuxRecursivo(arbol.getRight(), num, conjunto);
			break;
		default:
			throw new IllegalArgumentException("Arbol mal construido");
		}
		return conjunto;
	}

}
