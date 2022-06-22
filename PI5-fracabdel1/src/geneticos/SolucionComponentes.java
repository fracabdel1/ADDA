package geneticos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datos_ejemplos.datos_componentes;
import datos_ejemplos.datos_componentes.Producto;
import us.lsi.gurobi.GurobiSolution;

public class SolucionComponentes {
private Map<Producto, Integer> map = new HashMap<>();
	
	public static SolucionComponentes create(List<Integer> value) {
		return new SolucionComponentes(value);
	}
	
	public static SolucionComponentes create(GurobiSolution gs) {
		return new SolucionComponentes(gs.objVal, gs.values);
	}
	
	public SolucionComponentes(Double vo, Map<String, Double> variables) {
		
		Producto producto = null;
		
		for (Map.Entry<String, Double> par: variables.entrySet()) {
			
			if (par.getValue()>0 && par.getKey().startsWith("x")) {
				System.out.println(par);
				System.out.println("//////////////////////////////////////" + par.getKey().substring(2));
				String[] apoyo = par.getKey().split("_");
				Integer numeroComponente = Integer.parseInt(apoyo[1]) + 1;
				String nombre = String.format("%02d", numeroComponente);
				producto = datos_componentes.productos.stream().filter(p -> p.nombre().replace("P", "").trim().equals(nombre)).findFirst().get();
				
				map.put(producto, par.getValue().intValue());
			}
		}
	}
	
	private SolucionComponentes(List<Integer> ls) {
		
		Producto producto = null;
		
		for (int i=0; i<ls.size(); i++) {
			
			if (ls.get(i)>0) {
				
				Integer e = i;
				producto = datos_componentes.productos.stream().filter(p -> Integer.valueOf(p.nombre().replace("P", "").trim()).equals(e + 1)).findFirst().get();
				
				map.put(producto, ls.get(i));
			}
		}
	}
	
	public String toString() {

		String finalToString = "Productos Seleccionados:\n";
		Double beneficio = 0.;
		
		for (Map.Entry<Producto, Integer> par: map.entrySet()) {
			finalToString += par.getKey() + ": " + par.getValue() + " unidades" + "\n";
			Integer uds = par.getValue();
			beneficio += uds * par.getKey().precio();
		}
		finalToString += "\nBeneficio: " + String.format("%.1f", beneficio);
		return  finalToString;
	}
}
