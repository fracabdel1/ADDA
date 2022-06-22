package geneticos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datos_ejemplos.datos_ficheros;
import datos_ejemplos.datos_ficheros.*;
import us.lsi.common.List2;
import us.lsi.gurobi.GurobiSolution;

public class SolucionFichero {
	
	private Map<Memoria, List<Fichero>> dicc;

	public static SolucionFichero create(List<Integer> value) {
		return new SolucionFichero(value);
	}
	
	public static SolucionFichero create(GurobiSolution gs) {
		return new SolucionFichero(gs.objVal, gs.values);
	}
	
	public SolucionFichero(Double vo, Map<String, Double> vbles) {
		// Hacemos un diccionario donde vamos a poner de claves las memorias y de valores los ficheros
		dicc = new HashMap<Memoria, List<Fichero>>();
		
		for(var datos: vbles.entrySet()) { // Recorremos cada uno del par de gs.values(String, Double)
			// Nos quedamos con los valores que empiezan por x y que sean 1
			if(datos.getKey().startsWith("x") && (datos.getValue() == 1.0)) {
				// Spliteamos por "_" y se nos quedan 3 elementos
				String apoyo[] = datos.getKey().split("_");
				// Añadimos a fichero-memoria, los ficheros correspondientes ENTEROS de las listas publicas
				var fichero = datos_ficheros.ficheros.get(Integer.parseInt(apoyo[1]));
				var memoria = datos_ficheros.memorias.get(Integer.parseInt(apoyo[2]));
				// Añadimos al dicc
				if(dicc.containsKey(memoria)) {
					var lista = dicc.get(memoria);
					lista.add(fichero);
					dicc.put(memoria, lista);
				}else {
					dicc.put(memoria, List2.empty());
					dicc.get(memoria).add(fichero);
				}
			}
		}
	}

	public SolucionFichero(List<Integer> value) {
		// TODO Auto-generated constructor stub
		// Hacemos un diccionario donde vamos a poner de claves las memorias y de valores los ficheros
		dicc = new HashMap<Memoria, List<Fichero>>();
		// Recorremos la lista
		for(int i = 0; i < value.size(); i++) {
			if(value.get(i) < datos_ficheros.getNumMemorias()) {
				var fichero = datos_ficheros.ficheros.get(i); // Guardamos el fichero
				var memoria = datos_ficheros.memorias.get(value.get(i)); // Guardamos las memorias
				// hacemos el diccionario
				if(dicc.containsKey(memoria)) {
					var lista = dicc.get(memoria);
					lista.add(fichero);
					dicc.put(memoria, lista);
				}else {
					dicc.put(memoria, List2.empty());
					dicc.get(memoria).add(fichero);
				}
			}
		}
	}

	@Override
	public String toString() {
		
		String res = "";
		Integer cont = 0;
		
		 for(Memoria a: dicc.keySet()) {
			 List<String> ls = new ArrayList<>();
			 for(Fichero f: dicc.get(a)) {
				 ls.add(f.nombre());
				 cont++;
			 }
			 res += a.nombre() + ": " + ls + "\n";		 
		 }
		
		return  "Reparto obtenido:\n" + res + "Nº de archivos: " + cont;
	}

	
}
