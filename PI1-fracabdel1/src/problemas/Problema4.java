package problemas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.common.Files2;
import us.lsi.math.Math2;

public class Problema4 {
	
	// Ejercicio 4
	
	public static void problema4 (String fichero) {
		List<List<Double>> lista = Lectura(fichero); // Función de lectura
		
		// Como tenemos que operar varias veces con los mismos elementos vamos a crear un bucle for que recorra la lista
		for(List<Double> e:lista) {
			System.out.println("Entrada: " + e);
			System.out.println("1. Iterativo (while): " + EJ4Iterativo(e));
			System.out.println("2. Recursivo final: " + EJ4Recursivo(e));
			System.out.println("3. Funcional: " + EJ4Funcional(e));
		}		
	}
	
	// Función de lectura usando Files2.streamFromFile(File)

	public static List<List<Double>> Lectura(String File){	
		
		Function<String, List<Double>> separar = line -> // Le entra un String y va a retornar una Lista de Doubles
		Arrays.stream(line.split(",")) // Separamos los elementos que tengan ","
		.map(e -> Double.parseDouble(e)) // Convertimos los String en Doubles
		.collect(Collectors.toList()); // Los añadimos a una lista usando Collectors
		
		List<List<Double>> Ej1 = Files2.streamFromFile(File) // Vamos a crear una lista de listas de Doubles
				.map(separar) // Siempre que modifiquemos un dato usaremos .map (llama a la función separar)
				.collect(Collectors.toList());	// La lista recibida la añadiremos a otra lista que es la que retornaremos
		
		return Ej1;
	}
	
	
	// Iterativo
	
	public static Double EJ4Iterativo(List<Double> lista) {
		
		Double j = lista.get(0); // Objetivo
		Double i = 0.;
		Double error = lista.get(1);
		Double k = (j + i)/2; // Media
		while(Math.abs(lista.get(0) - (Math2.pow(k, 3))) > error) { // false si la resta en valor absoluto es mayor al error
			if(Math2.pow(k, 3) > lista.get(0)) {
				j = k;
				k = (j + i)/2; // Caso de i    k    j ->     i k j  (j)     (j) antigua
			}else {
				i = k;
				k = (j + i)/2; // Caso de i    k    j ->     (i)  i k j     (i) antigua
			}
		}
		return k;
	}
	
	
	//Recursivo final
	
	public static Double EJ4Recursivo(List<Double> lista) {
		Double j = lista.get(0); // Objetivo
		Double i = 0.;
		Double error = lista.get(1);
		Double k = (j + i)/2; // Media
//		List<Double> lis = new ArrayList<>();
		Double res = EJ4RecursivoAux(i, j, k, j, error);
		return res;
	}
	
	public static Double EJ4RecursivoAux(Double i, Double j, Double k, Double objetivo, Double error) {
		if(Math.abs(objetivo - (Math2.pow(k, 3))) > error) {
			if(Math2.pow(k, 3) > objetivo) {
				return EJ4RecursivoAux(i, k, (j + i)/2, objetivo, error);
			}else {
				return EJ4RecursivoAux(k, j, (j + i)/2, objetivo, error);
			}
		}
		return k;
	}
	
	
	// Funcional 1
	
	public static Double EJ4Funcional(List<Double>entrada) {
		 Double res = Stream
			.iterate(0., a -> Math.abs(entrada.get(0) - Math.pow(a, 3)) >= entrada.get(1), a -> a + 0.00001)
			.max(Double::compare)
			.get();
		 return res;
	}

	
	
	// Funcional 2
	
//	public static Double EJ4Funcional(List<Double>entrada) {
//		 double[] res = Stream
//			.iterate(new double[] {0., entrada.get(0), entrada.get(0)/2},
//					//iterador -> Math.abs(Math.pow(iterador[2], 3)) - entrada.get(0) > entrada.get(1),
//					iterador -> Math.pow(iterador[2], 3) > entrada.get(0) ? 
//							new double[] {iterador[0], iterador[2], (iterador[0] + iterador[2])/2} : 
//								new double[] {iterador[2], iterador[1], (iterador[0] + iterador[2])/2}) 
//			.filter(iterador -> Math.abs(Math.pow(iterador[2], 3)) - entrada.get(0) < entrada.get(1))
//			.findFirst()
//			.get();
//			//.collect(Collectors.toList());
//		 return res[2];
//	}

}





