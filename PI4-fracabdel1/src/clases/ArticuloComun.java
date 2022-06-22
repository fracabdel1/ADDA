package clases;

public class ArticuloComun {
	
	// Creamos una nueva arista
	public static ArticuloComun of() {
		return new ArticuloComun(0);
	}
	
	// Creamos nueva arista usando ofFormat()
	public static ArticuloComun ofFormat(String[] format) {
		return new ArticuloComun(format);
	}
	
	// Constructor
	
	// Elementos de la arista
	
	private Integer comun;
	private Integer origen;
	private Integer destino;

	//Constructor autogenerado
	public ArticuloComun(Integer comun) {
		super();
		this.comun = comun;
	}

	public ArticuloComun(String[] format) {
		// TODO Auto-generated constructor stub
		super();
		this.origen = Integer.parseInt(format[0]);
		this.destino = Integer.parseInt(format[1]);
		this.comun = Integer.parseInt(format[2]);
	}

	
	//Getters u Setters
	public Integer getComun() {
		return comun;
	}

	public void setComun(Integer comun) {
		this.comun = comun;
	}

	public Integer getOrigen() {
		return origen;
	}

	public Integer getDestino() {
		return destino;
	}

	// HashCode y Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comun == null) ? 0 : comun.hashCode());
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result + ((origen == null) ? 0 : origen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticuloComun other = (ArticuloComun) obj;
		if (comun == null) {
			if (other.comun != null)
				return false;
		} else if (!comun.equals(other.comun))
			return false;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		if (origen == null) {
			if (other.origen != null)
				return false;
		} else if (!origen.equals(other.origen))
			return false;
		return true;
	}

	//toString
	@Override
	public String toString() {
		return "ArticuloComun [comun=" + comun + ", origen=" + origen + ", destino=" + destino + "]";
	}
	
}
