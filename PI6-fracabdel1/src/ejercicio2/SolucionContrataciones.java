package ejercicio2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

import ejercicio2.datos_contrataciones;
import ejercicio2.datos_contrataciones.Contrato;
import ejercicio3.ComponentesEdge;
import ejercicio3.ComponentesVertex;
import us.lsi.common.List2;
import us.lsi.common.String2;
import us.lsi.gurobi.GurobiSolution;

public class SolucionContrataciones {
	
	private List<Contrato> seleccionados = new ArrayList<>();
	private List<Contrato> list;

	public static SolucionContrataciones create(GraphPath<ContratoVertex, ContratoEdge> graphPath) {
		List<Integer> la = graphPath.getEdgeList().stream().map(e -> e.action()).toList();
		return new SolucionContrataciones(la);
	}

	public SolucionContrataciones(List<Integer> ls) {

		Contrato candidato;

		for (int i=0; i<ls.size(); i++) {

			if (ls.get(i)>0) {

				Integer e = i;
				candidato = datos_contrataciones.contratos.stream().filter(c -> Integer.valueOf(c.nombre().replace("C", "").trim()).equals(e + 1)).findFirst().get();

				seleccionados.add(candidato);
			}
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////
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
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public String toString() {

		String res = "";
		Double valoracion = 0.;
		Double gasto = 0.;
		for(Contrato c: seleccionados) {
			res += c.nombre() + ": " + c.cualidades() + ";" + c.sueldoMin() + ";" + c.valorCV() + ";" + c.incompatibilidad() + "\n";
			valoracion += c.valorCV();
			gasto += c.sueldoMin();
		}

		return res + "Valoración total: " + valoracion + "; Gasto: " + gasto + "\n\n\n";
	}



}
