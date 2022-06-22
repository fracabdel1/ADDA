package clases;

public class Interseccion {
	
	public static Interseccion of() {
		return new Interseccion("");
	}
	
	public static Interseccion ofFormat(String[] format) {
		return new Interseccion(format);
	}

	// Elementos de Interseccion
	private Integer id;
	private boolean esRelevante;
	private String nombreMonumento;
	private Integer relevancia;
		
	//Constructores
	public static Interseccion of(Integer id) {
		return new Interseccion(id);
	}
	public static Interseccion ofMonumento(String monumento) {
		return new Interseccion(monumento);
	}
	
	private Interseccion(String[] formato) {
		
		this.id = Integer.valueOf(formato[0]);
		this.esRelevante = Boolean.valueOf(formato[1]);
		this.nombreMonumento = formato[2];
		String relevancia = formato[3].replace("int", "");
		this.relevancia = Integer.valueOf(relevancia);
		
	}
	private Interseccion(Integer id) {
		this.id = id;
		this.esRelevante = false;
		this.nombreMonumento = null;
		this.relevancia = 0;
	}
	private Interseccion(String monumento) {
		this.id = null;
		this.esRelevante = false;
		this.nombreMonumento = monumento;
		this.relevancia = null;
	}

	// Getter y Seter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEsRelevante() {
		return esRelevante;
	}

	public void setEsRelevante(boolean esRelevante) {
		this.esRelevante = esRelevante;
	}

	public String getNombreMonumento() {
		return nombreMonumento;
	}

	public void setNombreMonumento(String nombreMonumento) {
		this.nombreMonumento = nombreMonumento;
	}

	public Integer getRelevancia() {
		return relevancia;
	}

	public void setRelevancia(Integer relevancia) {
		this.relevancia = relevancia;
	}

	// Métodos HashCode y Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (esRelevante ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombreMonumento == null) ? 0 : nombreMonumento.hashCode());
		result = prime * result + ((relevancia == null) ? 0 : relevancia.hashCode());
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
		Interseccion other = (Interseccion) obj;
		if (esRelevante != other.esRelevante)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombreMonumento == null) {
			if (other.nombreMonumento != null)
				return false;
		} else if (!nombreMonumento.equals(other.nombreMonumento))
			return false;
		if (relevancia == null) {
			if (other.relevancia != null)
				return false;
		} else if (!relevancia.equals(other.relevancia))
			return false;
		return true;
	}

	// ToString
	@Override
	public String toString() {
		return "Interseccion [id=" + id + ", esRelevante=" + esRelevante + ", nombreMonumento=" + nombreMonumento
				+ ", relevancia=" + relevancia + "]";
	}
	
}
