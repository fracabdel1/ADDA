package problemas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.iterables.Iterables;

public class Problema3 {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void problema3(String fichero) {

		List<List<String>> lista = lectura(fichero);
		// Vamos a convertirlos en pares de números
		for(List<String> e: lista) {
			System.out.println("Entrada: " + e);
			Integer a = Integer.parseInt(e.get(0));
			Integer b = Integer.parseInt(e.get(1));
			
			System.out.println("1. Iterativo (while):     " + EJ3Iterativo(a, b));
			System.out.println("2. Recursiva final:     " + EJ3Recursivo(a , b));
			System.out.println("3. Funcional:     " + EJ3Funcional(a , b));
		}
	}

	//Función de lectura, con iteraciones leemos y convertimos en lista de listas los datos

	@SuppressWarnings("rawtypes")
	public static List<List<String>> lectura (String file) {
		Iterator<String> fileIt = Iterables.file(file).iterator(); // Creamos el iterador del fichero
		List<List<String>> lista1 = new ArrayList<>(); // Creamos la lista donde guardaremos las otras listas
		while(fileIt.hasNext()) { // Devuelve true si hay más elementos
			String[] linea = fileIt.next().split(",");
			List<String> apoyoit = Arrays.asList(linea); // Convertimos los elementos de la línea en una lista
			lista1.add(apoyoit);
		}
		return lista1;
	}
	
	// Clase Par
	
	public static record Par(Integer v1, Integer v2) { // Función record Par
		
		public static Par of(Integer i, Integer a) { // Va dentro del record
			return new Par(i, a); // Es el valor que vamos a devolver
		}
	
		public Par next() { // Funcion para el siguiente elemento
			// Funcion donde hacemos las operaciones
			Par r; // Llamamos al Par actual
			if(v1%3 != 1) {
				r = Par.of(v1+1, v2+v1);
			}else {
				r = Par.of(v1+1, v2);
			}
			return r;
		}

	}
	
	// Iterativo
	
	public static List<Par> EJ3Iterativo(Integer a, Integer b) {
		List<Par> lista = new ArrayList<>();
		Integer i = 0;
		// Función que no usa Par
//		while(i < b) {
//			lista.add(String.format("Par[v1=%d, v2=%d]", i, a));
//			if(i%3 != 1) {
//				a = a + i;
//			}			
//			i++;
//		}
//		return lista;
		while(i < b) {
			Par res = Par.of(i, a);	// Añadimos el Par a la variable res		
			lista.add(res);		// Añadimos res a la lista	
			res = res.next(); // Hacemos las operaciones correspondientes al Par
			i = res.v1; // Sacamos v1 y v2 para evaluarlas en el bucle
			a = res.v2;
		}
		return lista;		
	}
	
	// Recursivo final
	
	public static List<Par> EJ3Recursivo (Integer a, Integer b) {
		List<Par> lista = new ArrayList<>();
		Par p = Par.of(0, a);
		return EJ3RecursivoAux( b, lista, p);
	}
	
	public static List<Par> EJ3RecursivoAux (Integer b, List<Par> lista, Par p){
		if(p.v1 < b) {
			lista.add(p); // Añadimos a la lista el Par
			p = p.next(); // Hacemos la operación del Par en next();
			EJ3RecursivoAux(b, lista, p); // Recursividad		
		}

		return lista;
	}
	
	// Función que no usa Par
//	public static List<String> EJ3RecursivoAux (Integer a, Integer b, List<Par> lista){
//		if(i < b) {
//			lista.add();
//			if(i%3 != 1) {
//				EJ3RecursivoAux(a+i, b, i+1, lista);
//			}else {
//				EJ3RecursivoAux(a, b, i+1, lista);
//			}
//		}
//		return lista;
//	}
	
	// Funcional
	
	public static String EJ3Funcional(Integer a, Integer limit) {
		return Stream
		.iterate(Par.of(0, a),
		t -> t.v1 < limit,
		t -> Par.of(t.v1+1, t.v1 % 3 == 1 ? t.v2 : t.v1+t.v2))
		.collect(Collectors.toList())
		.toString();
		}

}