package problemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import enumerados.Paridad;
import us.lsi.common.Files2;
import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.BinaryTree;

public class Problema5 {
	
	/*
	 	Diseñe un algoritmo que dado un árbol binario de enteros devuelva un
		Map<Paridad,List<Integer>> que incluya las etiquetas de los nodos que tengan 2 hijos
		no vacíos, y que cumplan que dicha etiqueta sea mayor que la etiqueta de su hijo izquierdo
		y menor que la de su hijo derecho, agrupados teniendo en cuenta si son pares o no. Paridad
		es un enumerado con los valores Par e Impar.
	 */

	public static void Ejercicio5(String fichero) {
		List<BinaryTree<Integer>> arboles = lectura(fichero);
		
		System.out.println("\nSOLUCION RECURSIVA\n");
		for(BinaryTree<Integer> arbol: arboles) {
			System.out.println(arbol + ": " + Recursivo(arbol));
		}
		
		System.out.println("\nSOLUCION FUNCIONAL\n");
		for(BinaryTree<Integer> arbol: arboles) {
			System.out.println(arbol + ": " + Funcional(arbol));
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
	
	// Predicados
	public static Predicate<Integer> Par = e -> Math2.esPar(e);
	
	// Función recursiva
	
	public static Map<Paridad, List<Integer>> Recursivo(BinaryTree<Integer> arbol){
		Map<Paridad, List<Integer>> dicc = new HashMap<>();
		return AuxRecursivo(arbol, dicc);
	}
	
	public static Map<Paridad, List<Integer>> AuxRecursivo(BinaryTree<Integer> arbol, Map<Paridad, List<Integer>> dicc){
		
		switch (arbol.getType()) {
		case Empty:
			break;
		case Binary:
			if(!arbol.getLeft().isEmpty() && !arbol.getRight().isEmpty()) {
				if(arbol.getLabel() > arbol.getLeft().getLabel() && arbol.getLabel() < arbol.getRight().getLabel()) {
					List<Integer> lista = new ArrayList<>();
					lista.add(arbol.getLabel());
					if(Par.test(arbol.getLabel())) { // Si es Par
						if(dicc.containsKey(Paridad.Par)) {
							List<Integer> apoyo = dicc.get(Paridad.Par);
							apoyo.add(arbol.getLabel());
							dicc.put(Paridad.Par, apoyo);
						}else {
							dicc.put(Paridad.Par, lista);
						}
					}else { // Si es Impar
						if(dicc.containsKey(Paridad.Impar)) {
							List<Integer> apoyo = dicc.get(Paridad.Impar);
							apoyo.add(arbol.getLabel());
							dicc.put(Paridad.Impar, apoyo);
						}else {
							dicc.put(Paridad.Impar, lista);
						}
					}
				}
			}
			AuxRecursivo(arbol.getLeft(), dicc);
			AuxRecursivo(arbol.getRight(), dicc);
			break;
		case Leaf:
			break;
		default:
			throw new IllegalArgumentException("El árbol dado es erroneo");
		}
		
		return dicc;
	}
	
	// Funcional
	
	public static Map<Paridad, List<Integer>> Funcional(BinaryTree<Integer> arbol){

		return arbol.stream()
				.filter(s -> s.isBinary() && !s.getLeft().isEmpty() && !s.getRight().isEmpty() && // Tenemos que añadir isBinary, para saber que no es hoja
						(s.getLabel() > s.getLeft().getLabel() && s.getLabel() < s.getRight().getLabel()))
				.map(e -> e.getLabel())
				.collect(Collectors.groupingBy(paridad -> Par.test(paridad)?Paridad.Par:Paridad.Impar));			
	}
	
}
