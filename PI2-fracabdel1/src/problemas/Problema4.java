package problemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class Problema4 {

	public static void Ejercicio4(String fichero) {
		List<String> datos = Lectura(fichero);
		Integer i = 0;
		for(String e : datos) {
			///////////////////////////////////////////////
			//Soluciones de los ejercicios
			Integer n = Integer.parseInt(e);
			System.out.println("Entero de entrada: " + e);
			System.out.println("Sol. Rec. sin memoria: " + Ej4RecirsivoSinMemoria(n));
			System.out.println("Sol. Rec. con memoria: " + Ej4RecirsivoConMemoria(n));
			System.out.println("Sol. Iterativo: " + Ej4Iterativo(n));
			///////////////////////////////////////////////
			i++;
		}
	}

	// Función de lectura

	public static List<String> Lectura(String fichero){

		List<String> entrada = Files2.streamFromFile(fichero) // Función Stream
				.map(linea -> linea.replace("n=", ""))
				.collect(Collectors.toList());

		return entrada;
	}

	// Función recursiva sin memoria

	public static Long Ej4RecirsivoSinMemoria(Integer n) {
		Long res = AuxEj4RecirsivoSinMemoria(n);
		return res;
	}

	public static Long AuxEj4RecirsivoSinMemoria(Integer n) {

		if(n>2) {
			return 2*AuxEj4RecirsivoSinMemoria(n-1)+4*AuxEj4RecirsivoSinMemoria(n-2)+6*AuxEj4RecirsivoSinMemoria(n-3);
		}else if(n == 2){
			return (long) 6;
		}else if(n == 1) {
			return (long) 4;
		}else {
			return (long) 2;
		}
	}

	// Función recursiva con memoria

	public static Long Ej4RecirsivoConMemoria(Integer n) {

		Map<Integer, Long> dicc = new HashMap<>();
		// Inicializamos los valores
		dicc.put(2, (long) 6);
		dicc.put(1, (long) 4);
		dicc.put(0, (long) 2);
		return AuxEj4RecirsivoConMemoria(n, dicc);
	}

	public static Long AuxEj4RecirsivoConMemoria(Integer n, Map<Integer, Long> mem) {

		if(mem.containsKey(n)) {// si contiene fn lo devolvemos como valor
			return mem.get(n);
		}else { // Si no contiene la clave de fn la añadimos al diccionario
			mem.put(n, 2*AuxEj4RecirsivoConMemoria(n-1, mem)+4*AuxEj4RecirsivoConMemoria(n-2, mem)+6*AuxEj4RecirsivoConMemoria(n-3, mem));
			return mem.get(n);
		}	
	}

	// Función Iterativa

	public static Long Ej4Iterativo(Integer n) {
		List<Long> lista = new ArrayList<>();
		lista.add((long) 2);
		lista.add((long) 4);
		lista.add((long) 6);

		Integer i = 3;

		while(n >= i) {
			lista.add(2*lista.get(i-1)+4*lista.get(i-2)+6*lista.get(i-3));
			i++;
		}
		return lista.get(n);
	}
}
