package problemas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class Problema5 {

	public static void Ejercicio5(String fichero) {
		List<List<Integer>> entrada = Lectura(fichero);
		for(List<Integer> e: entrada) {
			Integer a = e.get(0);
			Integer b = e.get(1);
			Integer c = e.get(2);
			System.out.println(String.format("\n(a, b, c) = (%d, %d, %d)", a, b, c));
			System.out.println("Sol Rec. sin memoria: " + Ej5RecursividadSinMemoria(a, b, c));
			System.out.println("Sol. Res. con memoria: " + Ej5RecursividadConMemoria(a, b, c));
			System.out.println("Sol. iterativa: " + Ej5Iterativo(a, b, c));
		}
	}
	
	// Función de lectura

	public static List<List<Integer>> Lectura (String File){

		Function<String, List<Integer>> separar = line -> // Función para separar elementos
		Arrays.stream(line.split(","))
		.map(e -> Integer.parseInt(e))
		.collect(Collectors.toList());

		List<List<Integer>> entrada = Files2.streamFromFile(File) // Función Stream
				.map(separar)
				.collect(Collectors.toList());

		return entrada;
	}
		
	// Función Recursiva sin memoria
	
	public static Integer Ej5RecursividadSinMemoria(Integer a, Integer b, Integer c) {
		Integer res = AuxEj5RecursividadSinMemoria(a, b, c);
		return res;
	}
	
	public static Integer AuxEj5RecursividadSinMemoria(Integer a, Integer b, Integer c) {
		if(!(a<3 || b<3 || c<3)) {
			if(a%b == 0) {
				return AuxEj5RecursividadSinMemoria(a-1, b/2, c/2) + AuxEj5RecursividadSinMemoria(a-3, b/3, c/3);
			}else {
				return AuxEj5RecursividadSinMemoria(a/3, b-3, c-3) + AuxEj5RecursividadSinMemoria(a/2, b-2, c-2);
			}
		}else {
			Integer res = a + (b*b) + (2*c);
			return res;
		}
	}
	
	// Función recursiva con memoria
	
	public static Integer Ej5RecursividadConMemoria( Integer a, Integer b, Integer c) {
		HashMap<List<Integer>, Integer> mem = new HashMap<>();
		Integer res = AuxEj5RecursividadConMemoria(a, b, c, mem);
		return res;
	}
	
	public static Integer AuxEj5RecursividadConMemoria(Integer a, Integer b, Integer c, HashMap<List<Integer>, Integer> mem) {
		List<Integer> lista = new ArrayList<>();
		lista.add(a);
		lista.add(b);
		lista.add(c);
		if(mem.containsKey(lista)) { // Si contiene la clave, devolvemos el valor
			return mem.get(lista);
		}else { // Si no contiene la clave, calculamos el valor y lo devolvemos
			if(!(a<3 || b<3 || c<3)) {
				if(a%b == 0) {
					return AuxEj5RecursividadConMemoria(a-1, b/2, c/2, mem) + AuxEj5RecursividadConMemoria(a-3, b/3, c/3, mem);
				}else {
					return AuxEj5RecursividadConMemoria(a/3, b-3, c-3, mem) + AuxEj5RecursividadConMemoria(a/2, b-2, c-2, mem);
				}
			}else {
				mem.put(lista, a + (b*b) + (2*c));
			}
		}
		return mem.get(lista);
	}
	
	// Función Iterativo
	
	public static Integer Ej5Iterativo(Integer a, Integer b, Integer c) {
		HashMap<List<Integer>, Integer> dicc = new HashMap<>();
		List<Integer> lista = new ArrayList<>();
		lista.add(a);
		lista.add(b);
		lista.add(c);
		
		Integer i = 0; // Vamos recorriendo desde 0 hasta el número añadiendo los valores al diccionario
		while(i<=a) {
			Integer j = 0;
			while(j<=b) {
				Integer k = 0;
				while(k<=c) {
					List<Integer> bucles = new ArrayList<>();
					bucles.add(i);
					bucles.add(j);
					bucles.add(k);
					if(i<3 || j<3 || k<3) {
						dicc.put(bucles, i+j*j+2*k);
					}else {
						if(a%b == 0) {
							dicc.put(bucles, dicc.get(List.of(i-1, j/2, k/2)) + dicc.get(List.of(i-3, j/3, k/3)));
						}else {
							dicc.put(bucles, dicc.get(List.of(i/3, j-3, k-3)) + dicc.get(List.of(i/2, j-2, k-2)));
						}
					}
					k++;
				}
				j++;
			}
			i++;
		}
		return dicc.get(lista);
	}
}
