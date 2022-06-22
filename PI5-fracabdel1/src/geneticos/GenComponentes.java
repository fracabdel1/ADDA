package geneticos;

import java.util.List;

import datos_ejemplos.datos_componentes;
import datos_ejemplos.datos_componentes.Componente;
import datos_ejemplos.datos_componentes.Producto;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class GenComponentes implements ValuesInRangeData<Integer, SolucionComponentes>{
	
	private List<Componente> componentes = datos_componentes.componentes;
	private List<Producto> productos = datos_componentes.productos;
		
	@Override
	public Integer size() {
		return datos_componentes.componentes.size();
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Range;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double objetivo = 0.;
		Integer restricciones = 0;
		Integer tiempoTotalProd = datos_componentes.getTotalProd();
		Integer tiempoTotalElab = datos_componentes.getTotalManual();
		Integer tiempoProd = 0;
		Integer tiempoElab = 0; 
		
		for (int i=0; i<value.size(); i++) {
			
			if (value.get(i) > 0) {
			
				objetivo += value.get(i)*productos.get(i).precio();
			
				for (int j=0; j<componentes.size(); j++) {
					
					if (datos_componentes.tieneComponente(i, j)) {
						
						tiempoProd += datos_componentes.getTiempoProdTotalProducto(i, j) * value.get(i);
						tiempoElab += datos_componentes.getTiempoElabTotalProducto(i, j) * value.get(i); 
						
					}
				}
				restricciones += tiempoTotalProd < tiempoProd ? 1 : 0;
				restricciones += tiempoTotalElab < tiempoElab ? 1 : 0;
			}
		}
		return restricciones < 1 ? objetivo : objetivo - 145689.0*(restricciones);
	}

	@Override
	public SolucionComponentes solucion(List<Integer> value) {
		return SolucionComponentes.create(value);
	}

	@Override
	public Integer max(Integer i) {
		return productos.get(i).maxUds();
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}

}
