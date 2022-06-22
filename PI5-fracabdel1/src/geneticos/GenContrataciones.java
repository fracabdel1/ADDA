package geneticos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datos_ejemplos.datos_contrataciones;
import datos_ejemplos.datos_contrataciones.Contrato;
import us.lsi.ag.BinaryData;

public class GenContrataciones implements BinaryData<SolucionContrataciones>{
	
	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return datos_contrataciones.contratos.size();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		
		// Nuestro objetivo es maximizar la valoracion
		Double objetivo = 0.;
		Integer esCompatible;
		Integer cualidadCubierta = 0;
		Double presupuesto = (double) datos_contrataciones.getPresupuestoMax();
		Set<String> cualidadesTotales = new HashSet<>();
		Double restricciones = 0.;
		
		for (int i=0; i<value.size(); i++) {
			if (value.get(i)>0) {
				
				Contrato c = datos_contrataciones.contratos.get(i);
				presupuesto -= c.sueldoMin();
				
				// Penalizo si el candidato no tiene ninguna de las cualidades que buscamos
				
				for (int k=0; k<datos_contrataciones.cualidadesDeseadas.size(); k++) {
					cualidadCubierta = datos_contrataciones.getContieneCualidades(i, k);
					if (cualidadCubierta == 1)
						cualidadesTotales.addAll(datos_contrataciones.contratos.get(i).cualidades());
						break;
				}
				
				restricciones += -145689*(cualidadCubierta - 1);
				
				for (int j=1; j<value.size() && j!=i; j++) {
					// esCompatible será 1 si son incompatibles y 0 en caso contrario
					if (value.get(j)>0) {
						esCompatible = datos_contrataciones.sonCompatibles(i, j) ? 1 : 0;
						restricciones += -145689*esCompatible;
					}
				}
				objetivo += c.valorCV();
			}
		}
		
		cualidadCubierta = cualidadesTotales.stream().toList()
				.equals(datos_contrataciones.cualidadesDeseadas) ? 1 : 0;
		
		restricciones += -145689*(cualidadCubierta - 1);
		
		restricciones += - Math.pow(Math.abs(presupuesto), 2);
		return objetivo + restricciones;
	}

	@Override
	public SolucionContrataciones solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return SolucionContrataciones.create(value);
	}
	public static GenContrataciones of() {
		return new GenContrataciones();
	}

}
