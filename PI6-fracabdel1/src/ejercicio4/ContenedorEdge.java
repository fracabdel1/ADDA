package ejercicio4;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record ContenedorEdge(ContenedorVertex source, ContenedorVertex target, Integer action, Double weight)
implements SimpleEdgeAction<ContenedorVertex, Integer>{

	public static ContenedorEdge of(ContenedorVertex c1, ContenedorVertex c2, Integer action) {

		Double w = 0.;

		if (action < datos_contenedores.contenedores.size()) {
			w = 1.0;
		}
		return new ContenedorEdge(c1, c2, action, w);
	}

}
