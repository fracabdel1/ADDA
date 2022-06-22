package ejercicio3;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record ComponentesVertex(Integer index, Integer tProdRestante, Integer tElabRestante) implements VirtualVertex<ComponentesVertex, ComponentesEdge, Integer>{

	public static Integer tProdInicial;
	public static Integer tElabInicial;


	public static void datosIniciales(String fichero) {

		datos_componentes.iniDatos(fichero);
		ComponentesVertex.tProdInicial = datos_componentes.TotalProd;
		ComponentesVertex.tElabInicial = datos_componentes.TotalManual;

	}

	public static ComponentesVertex of(Integer index, Integer tProdRestante, Integer tElabRestante) {
		return new ComponentesVertex(index, tProdRestante, tElabRestante);
	}
	public static ComponentesVertex initial() {
		return of(0, ComponentesVertex.tProdInicial, ComponentesVertex.tElabInicial);
	}
	public static Predicate<ComponentesVertex> goal(){
		return v -> v.index == datos_componentes.productos.size();
	}

	// Obtiene las unidades máximas que se pueden producir de un producto
	// teniendo en cuenta el tiempo de producción y elaboración

	public Integer getRatioUds(Integer i) {

		Integer tProdTotal = datos_componentes.getTiempoProdTotalProducto2(i);
		Integer tElabTotal = datos_componentes.getTiempoElabTotalProducto2(i);

		return Math.min(datos_componentes.getMaxUds(i), Math.min(tProdRestante/tProdTotal, tElabRestante/tElabTotal));		

	}

	// Alternativas: numero de unidades que se fabrican de cada producto

	public List<Integer> actions() {

		List<Integer> la = List2.empty();

		if (index >= datos_componentes.getN())
			return la;

		la = IntStream.rangeClosed(0, getRatioUds(index)).boxed().toList();

		return la;
	}

	public ComponentesVertex neighbor(Integer a) {

		Integer tProd2 = tProdRestante;
		Integer tElab2 = tElabRestante;
		for (int j = 0; j<datos_componentes.componentes.size(); j++) {
			if (datos_componentes.tieneComponente(index, j)) {
				tProd2 = tProd2 - a * datos_componentes.getTiempoProdTotalProducto(index, j);
				tElab2 = tElab2 - a * datos_componentes.getTiempoElabTotalProducto(index, j);
			}
		}
		return of(index + 1, tProd2, tElab2);
	}

	public ComponentesEdge edge(Integer a) {
		return ComponentesEdge.of(this, this.neighbor(a), a);
	}
}
