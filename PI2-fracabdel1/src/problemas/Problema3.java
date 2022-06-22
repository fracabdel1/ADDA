package problemas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.IntegerSet;
import us.lsi.math.Math2;

public class Problema3 {

	public static void Ejercicio3(String fichero) {
		List<List<String>> datos = Lectura(fichero);
		System.out.println(datos);

		for(List<String> d: datos) { // Recorremos la lista de listas
			Integer i = 0;
			List<Integer> listaInt = new ArrayList<>();
			List<Integer> rangoInt = new ArrayList<>();
			for(String e: d) { // Pasamos a una lista de enteros la lista
				String[] input = e.split(",");
				List<String> listaString = Arrays.asList(input);
				if(!Math2.esPar(i)) { // guardamos los elementos parseados
					for(String a: listaString) {
						rangoInt.add(Integer.parseInt(a));
						i++;
					}
				}else {
					for(String a: listaString) {
						listaInt.add(Integer.parseInt(a));
						i++;
					}

				}
			}
			
			// A partir de aquí hacemos el ejercicio 3
			System.out.println("Lista: "+listaInt);
			System.out.println("Rango: "+rangoInt);
			System.out.println("Conjunto: "+Ej3Recursivo(listaInt, rangoInt));
		}
	}
	
	// Función de lectura
	
	public static List<List<String>> Lectura(String fichero){

		List<String> file = Files2.linesFromFile(fichero);

		List<List<String>> trozos = new ArrayList<>();

		// Creamos una lista de listas con ambos trozos
		for(String linea:file) {
			String[] input = linea.split("#");
			trozos.add(Arrays.asList(input));
		}
		return trozos;
	}
	
	// Función recursiva
	
	public static IntegerSet Ej3Recursivo(List<Integer> lista, List<Integer> rango) {
		IntegerSet res = AuxEj3Recursivo(lista, rango, 0, IntegerSet.empty());
		return res;
	}
	
	public static IntegerSet AuxEj3Recursivo(List<Integer> lista, List<Integer> rango, Integer i, IntegerSet conjunto) {
		if(i < lista.size()) {
			if((lista.get(i) >= rango.get(0)) && (lista.get(i) < rango.get(1))) {
				return AuxEj3Recursivo(lista, rango, i=i+1, conjunto.addNew(lista.get(i-1))); // Ponemos get(i-1) porque ya le aplicamos antes el 1 al i++
			}else {
				return AuxEj3Recursivo(lista, rango, i=i+1, conjunto);
			}
		}else {
			return conjunto;
		}
	}

}
