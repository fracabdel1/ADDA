package Ejercicio1;

import java.util.*;

import Ejercicio1.datos_ficheros.Fichero;
import Ejercicio1.datos_ficheros.Memoria;
import us.lsi.common.String2;

public class SolucionFichero {
	
	private Map<Memoria, List<Fichero>> map = new HashMap<>();
	private Integer tamaño = 0;
	
	public Integer getTamaño() {
		return tamaño;
	}
	
	public static SolucionFichero of(FicheroProblem v1, List<Integer> ls) {
		
		return new SolucionFichero(v1, ls);
	}
		
	private SolucionFichero(FicheroProblem v1, List<Integer> alternativas) {
		FicheroProblem v = v1;
		
		Memoria memoria = null;
		Fichero fichero = null;
		
		for (int i=0; i<alternativas.size(); i++) {
			
			Integer a = alternativas.get(i);
			if (a < datos_ficheros.memorias.size()) {
				
				Integer idMemoria = alternativas.get(i) + 1;
				memoria = datos_ficheros.memorias.stream().filter(m -> Integer.valueOf(m.nombre().replace("MEM", "").trim()).equals(idMemoria)).findFirst().get();
				fichero = datos_ficheros.ficheros.get(i);
				
				if (map.containsKey(memoria)) {
					map.get(memoria).add(fichero);
					tamaño++;
				} else {
					List<Fichero> lsAux = new ArrayList<>();
					lsAux.add(fichero);
					map.put(memoria, lsAux);
					tamaño++;
				}
			}
			v = v.neighbor(a);
		}
	}
	
	public String toString() {
		
		List<String> ls = new ArrayList<>();
		
		for (Map.Entry<Memoria, List<Fichero>> par: map.entrySet()) {

			String s = String.format("%s : %s; %s: %s", par.getKey(), par.getKey().capacidad(), par.getKey().tamMax(), par.getValue());
			ls.add(s);
			
		}
			
		String finalToString = "Reparto obtenido:\n";
		
		for (int i=0; i<ls.size(); i++)
			finalToString = finalToString + ls.get(i) + "\n";
		
		finalToString += "Se almacenaron " + tamaño.toString() + " archivos.\n";
		return  finalToString;
		
	}
	
}
