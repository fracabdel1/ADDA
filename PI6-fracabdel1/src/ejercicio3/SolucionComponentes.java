package ejercicio3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

import ejercicio3.datos_componentes;
import ejercicio3.datos_componentes.Producto;
import us.lsi.common.String2;
import us.lsi.gurobi.GurobiSolution;

public class SolucionComponentes {
	
	private Map<Producto, Integer> map = new HashMap<>();

	public static SolucionComponentes create(GraphPath<ComponentesVertex, ComponentesEdge> gp) {
		List<Integer> la = gp.getEdgeList().stream().map(e -> e.action()).toList();
		return new SolucionComponentes(la);
	}

	public SolucionComponentes(List<Integer> ls) {

		Producto producto = null;

		for (int i=0; i<ls.size(); i++) {

			if (ls.get(i)>0) {

				Integer e = i;
				producto = datos_componentes.productos.stream().filter(p -> Integer.valueOf(p.id().replace("P", "").trim()).equals(e + 1)).findFirst().get();

				map.put(producto, ls.get(i));
			}
		}
	}

	public String toString() {

		Double beneficio = 0.;
		for (Map.Entry<Producto, Integer> par: map.entrySet()) {
			Integer uds = par.getValue();
			beneficio += uds * par.getKey().precio();
		}
		String finalToString = "Precio Total: " + beneficio;
		finalToString += "\nProductos Seleccionados:\n";

		for (Map.Entry<Producto, Integer> par: map.entrySet()) {
			finalToString += par.getKey() + ": " + par.getValue() + " unidades" + "\n";
		}

		return finalToString;
	}

}
