package geneticos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datos_ejemplos.datos_contenedores;
import datos_ejemplos.datos_contenedores.Contenedor;
import datos_ejemplos.datos_contenedores.Elemento;
import us.lsi.gurobi.GurobiSolution;

public class SolucionContenedores {
	
	private Map<Contenedor, List<Elemento>> dicc = new HashMap<Contenedor, List<Elemento>>();
	private Integer tama?o = 0;
	
	public static SolucionContenedores create(List<Integer> value) {
		return new SolucionContenedores(value);
	}
	
	public static SolucionContenedores create(GurobiSolution gs) {
		return new SolucionContenedores(gs.objVal, gs.values);
	}
	
	public SolucionContenedores(Double vo, Map<String, Double> variables) {
		
		Elemento elemento;
		Contenedor contenedor;
		
		for (Map.Entry<String, Double> par: variables.entrySet()) {
			
			if (par.getValue()>0 && par.getKey().startsWith("x")) {
				
				Integer elementoId = Integer.valueOf(par.getKey().substring(2, par.getKey().indexOf("_", 2))) + 1;
				
				String nombre = String.format("%02d",  elementoId);
				
				if (par.getKey().contains("99")) {
					String apoyo = String.format("%03d", Integer.valueOf(par.getKey().substring(2, par.getKey().indexOf("_", 2)))); //para que el 100 no de problemas
					elemento = datos_contenedores.elementos.stream().filter(f -> f.nombre().replace("E", "").trim().equals(apoyo + 1)).findFirst().get();
				} else {
					elemento = datos_contenedores.elementos.stream().filter(f -> f.nombre().replace("E", "").trim().equals(nombre)).findFirst().get();
				}
				
				// Parseamos el contenedor
				
				Integer cont = Integer.parseInt(par.getKey().substring(4).replace("_", "").trim()); 
				contenedor = datos_contenedores.contenedores.stream().filter(c -> Integer.valueOf(c.nombre().replace("CONT", "").trim()).equals(cont + 1)).findFirst().get();
				
				if (dicc.containsKey(contenedor)) {
					dicc.get(contenedor).add(elemento);
					tama?o++;
				} else {
					List<Elemento> ls = new ArrayList<>();
					ls.add(elemento);
					dicc.put(contenedor, ls);
					tama?o++;
				}
			}
		}
	}
	
	private SolucionContenedores(List<Integer> ls) {
		
		Contenedor contenedor = null;
		Elemento elemento = null;
		
		for (int i=0; i<ls.size(); i++) {
			
			if (ls.get(i) < datos_contenedores.contenedores.size()) {
				
				Integer idCont = ls.get(i);
				
				contenedor = datos_contenedores.contenedores.stream().filter(c -> Integer.valueOf(c.nombre().replace("CONT", "").trim()).equals(idCont + 1)).findFirst().get();
				elemento = datos_contenedores.elementos.get(i);
				
				if (dicc.containsKey(contenedor)) {
					dicc.get(contenedor).add(elemento);
					tama?o++;
				} else {
					List<Elemento> lsAux = new ArrayList<>();
					lsAux.add(elemento);
					dicc.put(contenedor, lsAux);
					tama?o++;
				}
			}
		}
	}
	
	public String toString() {

		List<String> ls = new ArrayList<>();
		
		for (Map.Entry<Contenedor, List<Elemento>> par: dicc.entrySet())
			ls.add(par.getKey() + ": " + par.getValue());
		
		String finalToString = "Reparto obtenido:\n";
		
		for (int i=0; i<ls.size(); i++)
			finalToString = finalToString + ls.get(i) + "\n";
		
		return  finalToString;
	}

}
