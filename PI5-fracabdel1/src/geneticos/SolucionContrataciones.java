package geneticos;

import java.util.List;
import java.util.Map;

import datos_ejemplos.datos_contrataciones;
import datos_ejemplos.datos_contrataciones.Contrato;
import us.lsi.common.List2;
import us.lsi.gurobi.GurobiSolution;

public class SolucionContrataciones {
	
	private List<Contrato> list;
	
	public static SolucionContrataciones create(GurobiSolution gs) {
		return new SolucionContrataciones(gs.objVal, gs.values);
	}
	
	public static SolucionContrataciones create(List<Integer> value) {
		return new SolucionContrataciones(value);
	}

	public SolucionContrataciones(Double objVal, Map<String, Double> values) {
		// TODO Auto-generated constructor stub
		list = List2.empty(); // Limpiamos la lista
		// Recorremos cada par values
		for(var datos: values.entrySet()) {
			if(datos.getValue() > 0. && datos.getKey().startsWith("x")) { // Los valores son 1
				String[] contrato = datos.getKey().split("_");
				list.add(datos_contrataciones.contratos.get(Integer.parseInt(contrato[1])));
			}
		}	
	}

	public SolucionContrataciones(List<Integer> value) {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public String toString() {
		String res = "";
		Double valoracion = 0.;
		Double gasto = 0.;
		for(Contrato c: list) {
			res += c.nombre() + ": " + c.cualidades() + ";" + c.sueldoMin() + ";" + c.valorCV() + ";" + c.incompatibilidad() + "\n";
			valoracion += c.valorCV();
			gasto += c.sueldoMin();
		}
		
		return res + "Valoración total: " + valoracion + "; Gasto: " + gasto + "\n\n\n";
	}
	
	

}
