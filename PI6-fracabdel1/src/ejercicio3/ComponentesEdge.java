package ejercicio3;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record ComponentesEdge(ComponentesVertex source, ComponentesVertex target, Integer action, Double weight)implements SimpleEdgeAction<ComponentesVertex, Integer> {

	public static ComponentesEdge of(ComponentesVertex c1, ComponentesVertex c2, Integer action) {
		Double w = 1. * (action*datos_componentes.productos.get(c1.index()).precio());
		return new ComponentesEdge(c1, c2, action, w);
	}

}
