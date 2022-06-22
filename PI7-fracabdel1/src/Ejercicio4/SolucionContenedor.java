package Ejercicio4;

import java.util.*;

import Ejercicio4.datos_contenedores.Contenedor;
import Ejercicio4.datos_contenedores.Elemento;
import us.lsi.common.String2;

public class SolucionContenedor {
	 
	private Map<Contenedor, List<Elemento>> map = new HashMap<>();
	private Integer tamaño = 0;
	
	public static SolucionContenedor of(ContenedorProblem v1, List<Integer> ls) {
		return new SolucionContenedor(v1, ls);
	}
	
	private SolucionContenedor(ContenedorProblem v1, List<Integer> alternativas) {
		
		ContenedorProblem v = v1;
		
		Contenedor contenedor = null;
		Elemento elemento = null;
		
		for (int i=0; i<alternativas.size(); i++) {
			
			Integer a = alternativas.get(i);
			if (a != datos_contenedores.contenedores.size()) {
				
				Integer idCont = alternativas.get(i);
				
				contenedor = datos_contenedores.contenedores.stream().filter(c -> Integer.valueOf(c.id().replace("CONT", "").trim()).equals(idCont + 1)).findFirst().get();
				elemento = datos_contenedores.elementos.get(i);
				
				if (map.containsKey(contenedor)) {
					map.get(contenedor).add(elemento);
					tamaño++;
				} else {
					List<Elemento> alternativasAux = new ArrayList<>();
					alternativasAux.add(elemento);
					map.put(contenedor, alternativasAux);
					tamaño++;
				}
			}
			v = v.vecino(a);
		}
	}
	
	public String toString() {

		List<String> alternativas = new ArrayList<>();
		
		for (Map.Entry<Contenedor, List<Elemento>> par: map.entrySet())
			alternativas.add(par.getKey() + ": " + par.getValue());
		
		String finalToString = "Reparto obtenido:\n";
		
		for (int i=0; i<alternativas.size(); i++)
			finalToString = finalToString + alternativas.get(i) + "\n";
		
		finalToString += "Numero de contenedores " + map.keySet().size() + "\n";
		
		return finalToString;
	}
}
