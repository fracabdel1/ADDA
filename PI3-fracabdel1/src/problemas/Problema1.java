package problemas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.Tree;

/*
 1. Diseñe un algoritmo que dado un árbol n-ario de tipo genérico y un predicado sobre
	dicho tipo, devuelva un conjunto que contenga las etiquetas de las hojas de dicho árbol
	que cumplan el predicado.
 */

public class Problema1 {

	public static void Ejercicio1(String fichero) {
		List<Tree<Integer>> arboles = lectura(fichero);
		
		System.out.println("\nRECURSIVO PAR\n");
		for(Tree<Integer> arbol: arboles) {
			System.out.println(arbol + ": " + RecursivoPar(arbol));
		}
		System.out.println("\nRECURSIVO MENOR QUE 5\n");
		for(Tree<Integer>arbol: arboles){			
			System.out.println(arbol + ": " + RecursivoMenorQueCinco(arbol));
		}
		
		System.out.println("\nIterativo Par\n");
		for(Tree<Integer>arbol: arboles){			
			System.out.println(arbol + ": " + FuncionalPar(arbol));
		}
		
		System.out.println("\nIterativo Menor que 5\n");
		for(Tree<Integer>arbol: arboles){			
			System.out.println(arbol + ": " + FuncionalMenor5(arbol));
		}
	}

	
	// Función de lectura

	public static List<Tree<Integer>> lectura(String fichero) { 

		List<Tree<Integer>> arboles;

		arboles = Files2.streamFromFile(fichero)
				.map(linea->Tree.parse(linea, s->Integer.parseInt(s))) 
				.toList();

		return arboles;
	}
	
	
	// Predicados
	
	public static Predicate<Integer> Par = r -> Math2.esPar(r);
	public static Predicate<Integer> M5 = r -> r < 5;
	
	// Recursiva Par // Arbol N-Ario : Empty,Leaf,Nary
	
	public static <E> Set<E> RecursivoPar (Tree<E> arbol){
		Set<E> lista = new HashSet<>();
		return AuxRecursivoPar(arbol, lista);
	}
	
	public static <E> Set<E> AuxRecursivoPar (Tree<E> arbol, Set<E> lista){

		switch(arbol.getType()) {
		case Empty:
			break;
		case Leaf:
			if(Par.test((Integer) arbol.getLabel())) lista.add(arbol.getLabel());
			break;
		case Nary:
			for(int i = 0; i < arbol.getNumOfChildren(); i++) {
				AuxRecursivoPar(arbol.getChild(i), lista);
			}
			break;
		default:
			throw new IllegalArgumentException("Arbol mal construido");
		}

		return lista;
	}
	
	// Recursivo menor que cinco
	
	public static <E> Set<E> RecursivoMenorQueCinco (Tree<E> arbol){
		Set<E> lista = new HashSet<>();
		return AuxRecursivoMenorQueCinco(arbol, lista);
	}
	
	public static <E> Set<E> AuxRecursivoMenorQueCinco (Tree<E> arbol, Set<E> lista){

		switch(arbol.getType()) {
		case Empty:
			break;
		case Leaf:
			if(M5.test((Integer) arbol.getLabel())) lista.add(arbol.getLabel());
			break;
		case Nary:
			for(int i = 0; i < arbol.getNumOfChildren(); i++) {
				AuxRecursivoMenorQueCinco(arbol.getChild(i), lista);
			}
			break;
		default:
			System.out.println("Error, el árbol está mal construido.");
		}

		return lista;
	}
	
	// Funcional Par
	
	public static <E> Set<E> FuncionalPar(Tree<E> arbol){
		return arbol.stream()
				.filter(s -> !s.isEmpty() && s.isLeaf() && !s.isNary() && Par.test((Integer) s.getLabel()))
				.map(s -> s.getLabel())
				.collect(Collectors.toSet());
	}
	
	// Funcional menor que 5
	
	public static <E> Set<E> FuncionalMenor5(Tree<E> arbol){
		return arbol.stream()
				.filter(s -> !s.isEmpty() && s.isLeaf() && !s.isNary() && M5.test((Integer) s.getLabel()))
				.map(s -> s.getLabel())
				.collect(Collectors.toSet());
	}
}
