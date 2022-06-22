package problemas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.iterables.Iterables;

public class Problema2 {
	
	// Ejercicio 2
	
	public static void problema2 (String file) {
		
		List<List<String>> datos = lectura(file);
		EJ2Iterativo(datos);
		EJ2Recursivo(datos);
		EJ2Funcional(datos);
	}
	
	// Función de lectura, con iteraciones leemos y convertimos en lista de listas los datos
	
	public static List<List<String>> lectura (String file) {
		Iterator<String> fileIt = Iterables.file(file).iterator(); // Creamos el iterador del fichero
		List<List<String>> lista1 = new ArrayList<>(); // Creamos la lista donde guardaremos las otras listas
		while(fileIt.hasNext()) { // Devuelve true si hay más elementos
			String[] linea = fileIt.next().split(",");
			List<String> apoyoit = Arrays.asList(linea); // Convertimos los elementos de la línea en una lista
			lista1.add(apoyoit);
		}
		System.out.println("Entrada: " + lista1);
		return lista1;
	}
	
	//Iterativo
	
	public static void EJ2Iterativo (List<List<String>> Entrada) {
		
		// Vamos a crear un diccionario cuyas claves sean enteros (tamaño de la palabra) y 
		// Los valores sean List (listas de palabras con igual tamaño)
		HashMap<Integer, List> dicc = new HashMap<>();
		
		// Vamos a crear una lista con todas las palabras
		List<String> lista = new ArrayList<>();
		for(List<String> e: Entrada) {
			for(String palabras: e) {
				lista.add(palabras);
			}
		}
		
		// Usando un bucle while vamos a recorrer la lista
		Integer i = 0;
		while(i < lista.size()) {
			
			String palabra = lista.get(i);
			// Si no está creada la entrada del diccionario se crea, sino añadimos el valor  palabra.length()
			if(dicc.containsKey(palabra.length())) {
				List<String> valores = dicc.get(palabra.length()); // nos devuelve los valores dandole una clave
				valores.add(palabra);
				dicc.put(palabra.length(), valores);
			}else { // Si no está la clave, añadimos el par clave-valor
				List<String> apoyo = new ArrayList<>();//Lista vacia
				apoyo.add(palabra);
				dicc.put(palabra.length(), apoyo);
			}
			i++;
		}
		System.out.println("1. Iterativo: " + dicc);		
	}
	
	// Recursivo final
	
	public static void EJ2Recursivo(List<List<String>> Entrada) {
		
		Integer i = 0;
		HashMap<Integer, List> dicc = new HashMap<>();
		
		// Vamos a crear una lista con todas las palabras
		List<String> lista = new ArrayList<>();
		for(List<String> e: Entrada) {
			for(String palabras: e) {
				lista.add(palabras);
			}
		}
		
		System.out.println("2. Recursivo (final): " + EJ2RecursivoAux(lista, i, dicc));
		
	}
	
	public static HashMap<Integer, List> EJ2RecursivoAux(List<String> lista, Integer i, HashMap<Integer, List> dicc){
		
		if(i < lista.size()) {
			String palabra = lista.get(i);
			// Si no está creada la entrada del diccionario se crea, sino añadimos el valor
			if(dicc.containsKey(palabra.length())) {
				List<String> valores = dicc.get(palabra.length());
				valores.add(palabra);
				dicc.put(palabra.length(), valores);
			}else { // Si no está la clave, añadimos el par clave-valor
				List<String> apoyo = new ArrayList<>();//Lista vacia
				apoyo.add(palabra);
				dicc.put(palabra.length(), apoyo);
			}
			EJ2RecursivoAux(lista, i+1, dicc);
		}
		return dicc;
	}
	
	// Funcional
	
	public static void EJ2Funcional (List<List<String>> listas) {
		var salida = listas.stream()
		.flatMap(lista -> lista.stream())
		.collect(Collectors.groupingBy(String::length));
		
		System.out.println("3. Funcional: " + salida);
		}
	
}
