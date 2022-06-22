package ejercicio2;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record ContratoEdge(ContratoVertex source, ContratoVertex target, Integer action, Double weight)
implements SimpleEdgeAction<ContratoVertex, Integer>{

	public static ContratoEdge of(ContratoVertex c1, ContratoVertex c2, Integer action) {
		Double w = (double) (action*datos_contrataciones.contratos.get(c1.index()).valorCV());
		return new ContratoEdge(c1, c2, action, w);
	}

}
