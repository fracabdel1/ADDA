package geneticos;

import java.util.List;
import java.util.stream.Collectors;

import datos_ejemplos.datos_contenedores;
import datos_ejemplos.datos_contenedores.Contenedor;
import datos_ejemplos.datos_contenedores.Elemento;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class GenContenedores implements ValuesInRangeData<Integer, SolucionContenedores>{
	
	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return datos_contenedores.getN();
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Range;
	}

	private List<Contenedor> contenedores = datos_contenedores.contenedores;
	private List<Elemento> elementos = datos_contenedores.elementos;
	
	@Override
	public Double fitnessFunction(List<Integer> value) {

		Double objetivo = 0.;
		Integer m = datos_contenedores.contenedores.size();
		List<Integer> capacidadContenedores = contenedores.stream().map(c -> c.capacidad()).collect(Collectors.toList());
		Integer esCompatible = 0;
		Double restricciones = 0.;
		
		for (int i=0; i<value.size(); i++) {
			
			if (value.get(i)<m) {
			
					esCompatible = datos_contenedores.esCompatible(i, value.get(i)) ? 1 : 0;
					restricciones += -145689*(1 - esCompatible);
					
				if (capacidadContenedores.get(value.get(i)) > 0) {
						
					Integer cap = capacidadContenedores.get(value.get(i));
							cap -= elementos.get(i).tamaño();
								
					if (cap == 0) {
						objetivo++;
					}
					capacidadContenedores.set(value.get(i), cap);
				} 
			}
		}
		
		Integer numContLlenos = (int) capacidadContenedores.stream().filter(x -> x==0).count();
		
		if (numContLlenos < 1)
			restricciones += -145689.0;
		
		return objetivo + restricciones;
	}

	@Override
	public SolucionContenedores solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return SolucionContenedores.create(value);
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return datos_contenedores.getM() + 1;
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}

}
