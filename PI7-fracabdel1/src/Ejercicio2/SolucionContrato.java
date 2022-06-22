package Ejercicio2;

import java.util.ArrayList;
import java.util.List;

import Ejercicio2.datos_contrataciones.Candidato;
import us.lsi.common.String2;

public class SolucionContrato {
	
	private List<Candidato> seleccionados = new ArrayList<>();
	private Double valoracionTotal;
	
	public Double getValoracionTotal() {
		return valoracionTotal;
	}

	public static SolucionContrato of(ContratoProblem v1, List<Integer> ls) {
		return new SolucionContrato(v1, ls);
	}
	
	private SolucionContrato(ContratoProblem v1, List<Integer> alternativas) {
		ContratoProblem v = v1;
		
		Candidato candidato = null;
		
		for (int i=0; i<alternativas.size(); i++) {
			
			Integer a = alternativas.get(i);
			if (a>0) {
				
				Integer e = i;
				candidato = datos_contrataciones.candidatos.stream().filter(c -> Integer.valueOf(c.id().replace("C", "").trim()).equals(e + 1)).findFirst().get();
				
				seleccionados.add(candidato);
			}
			v = v.vecino(a);
		}
		valoracionTotal = Double.valueOf(seleccionados.stream().mapToDouble(c -> (double)c.valoracion()).sum());
	}
	
	
	public String toString() {

		String finalToString = "Candidatos Seleccionados:\n";
		
		for (int i=0; i<seleccionados.size(); i++)
			finalToString = finalToString + seleccionados.get(i) + "\n";
		
		Double gasto = seleccionados.stream().mapToDouble(c -> c.sueldoMin()).sum();
		finalToString += "\nSueldos totales: " + gasto.toString();
		finalToString += "\nValores totales: " + valoracionTotal.toString() + "\n";
		
		return finalToString;
	}
	
}