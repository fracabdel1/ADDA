package ejercicio1;

import us.lsi.graphs.virtual.SimpleEdgeAction;


public record FicheroEdge (FicheroVertex source, FicheroVertex target, Integer action, Double weight) 
implements SimpleEdgeAction<FicheroVertex, Integer>{
	
	public static FicheroEdge of(FicheroVertex origen, FicheroVertex destino, Integer action) {
		
		Double peso = 0.;
		
		if(action < datos_ficheros.memorias.size()) {
			peso = 1.;
		}
		
		return new FicheroEdge(origen, destino, action, peso);	
	}
}
