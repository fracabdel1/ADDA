package problemas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.iterables.Iterables;
import us.lsi.math.Math2;

public class Problema1 {

	public static void Ejercicio1(String fichero) {
		List<List<String>> entrada = lectura(fichero);

		for(List<String> e: entrada) {
			System.out.println("Entrada: " + e);
			System.out.println("1. Iterativo (while): " + EJ1Iterativo(e));
			System.out.println("2. Recursivo final: " + EJ1Recursivo(e));
			System.out.println("3. Funcional: " + EJ1Funcional(e, pS, pI, f) + "\n");	
					
		}
	}
	
	// Función de lectura
	
	public static List<List<String>> lectura (String file){
		Function<String, List<String>> separar = line ->
		Arrays.stream(line.split(","))
		.collect(Collectors.toList());
		
		List<List<String>> entrada = Files2.streamFromFile(file)
		.filter(linea -> !linea.startsWith("//")) // Filtra por dichos elementos, en este caso es el negado para no coger las primeras lineas
		.map(separar)      // Para modificar algo, simpre usamos map
		.collect(Collectors.toList());
		return entrada;
	}
	
//Predicate <Par> condicion = r -> r.inicial() = r.fin();
	
	// El predicado sobre String devuelve cierto si dicho String contiene alguna vocal abierta (es decir, a, e ó o)
	// El predicado sobre Integer devuelve cierto si ese entero es par
	// La función String -> Integer devuelve la longitud de la cadena 
	
	public static Predicate<String> pS = r -> r.contains( "a") || r.contains( "e") || r.contains( "ó") || r.contains( "o");
	public static Predicate<Integer> pI = r -> Math2.esPar(r);
	public static Function<String,Integer> f = r -> r.length();
	
	
	// Iterativo
	
	public static Boolean EJ1Iterativo(List<String> entrada) {
		int i = 0;
		while(i < entrada.size()) { // Mientras i < tamaño lista
			if(pS.test(entrada.get(i)) && pI.test(f.apply(entrada.get(i)))) { // Comprobamos predicates
				return true;
			}
			i++;
		}
		return false;	
	}
	
	
	//Recursivo
	public static Boolean EJ1Recursivo(List<String> entrada) {
		List<Boolean> mem = new ArrayList<>();
		mem.add(0, false);
		Boolean res = EJ1RecursivoAux(entrada, 0, mem);
		return res;
	}
	
	public static Boolean EJ1RecursivoAux(List<String> entrada, Integer i, List<Boolean> mem) {
		if(i < entrada.size()) {
			if(pS.test(entrada.get(i)) && pI.test(f.apply(entrada.get(i)))) {
				mem.add(0, true);
				EJ1RecursivoAux(entrada, i+1, mem);
			}else {
				EJ1RecursivoAux(entrada, i+1, mem);
			}
		}
		return mem.get(0);
	}
	
	
	
	// Funcional
	
	public static Boolean EJ1Funcional(List<String> ls, Predicate<String> pS, Predicate<Integer> pI, Function<String,Integer> f){
			return ls.stream()
			.filter(pS)
			.map(f)
			.anyMatch(pI);
			}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Funcion de lectura usando iteradores
	
//	public static List<List<String>> lectura (String file) {
//		Iterator<String> fileIt = Iterables.file(file).iterator(); // Creamos el iterador del fichero
//		List<List<String>> salida = new ArrayList<>();
//		while(fileIt.hasNext()) { // Devuelve true si hay más elementos
//			String[] linea = fileIt.next().split("," + " ," + "//");// Como hay más elementos, avanzamos de línea
//			List<String> apoyoit = Arrays.asList(linea); // Añadimos los trozos en una lista
//			for(String e:apoyoit) {
//				if(e.length() != 1) {
//					salida.add(apoyoit);
//				}
//			}
//			// Como no nos interesan las 3 primeras líneas y empiezan por //...
//			
//			salida.add(apoyoit);
//		}
//		System.out.println(salida);
//		return null;
//	}
	

	
	// Funcion usando Files2.linesFromFile(null) NO SIRVE PARA ESTE EJERCICIO
	
//	public static List<List<String>> lectura(String fichero) {
//	List<String> file = Files2.linesFromFile(fichero);
//	System.out.println(file);
//	
//	List<List<String>> trozos = new ArrayList<>();
//	
//	for(String linea:file) {
//		String[] input = linea.split(",");
//		List<String> apoyo = new ArrayList<>();
//		
//	}
//	return null;
//	}
	
	//Función de lectura convertimos el fichero en una lista
	
//	public static List<String> Fichero(String File){	
//		List<String> Ej = Files2.streamFromFile(File).collect(Collectors.toList());	
//		return Ej;
//	}
	

	
//	public static Integer lineas(String file) {
//		Iterator<String> fileIt = Iterables.file(file).iterator();
//		Integer suma = 0;
//		while(fileIt.hasNext()){
//			String linea = fileIt.next();
//			Iterator<String> lineaIt = Iterables.split(linea,"[ ,]").iterator();
//			while(lineaIt.hasNext()){
//	     		String p = lineaIt.next();
//				System.out.println(p);			
//	 		}
//		}
//	 	return suma;
//	}
	
//	public static Predicate<String> predicadoString(String s){
//		Boolean res;
//		
//		return null;
//	}
	
	
}
