package clases;

public class Calle {
	
	public static Calle of(String s) {
		return new Calle(s);
	}
	
	public static Calle ofFormat(String[] format) {
		return new Calle(format);
	}
	
	// Elementos de Calle
	private Integer id;
	private Interseccion int1;
	private Interseccion int2;
	private Integer duracion;
	private Integer esfuerzo;
	//private static int num=0;
	
	// Constructores
	public static Calle ofVertex(Interseccion i1, Interseccion i2) {
		return new Calle(i1, i2);
	}

	
	private Calle(String[] s) {
		this.int1 = Interseccion.of(Integer.valueOf(s[0]));
		this.int2 = Interseccion.of(Integer.valueOf(s[1]));
		this.duracion = Integer.valueOf(s[2].replace("min", ""));
		this.esfuerzo = Integer.valueOf(s[3].replace("esf", ""));
		//this.id = num;
		//num++;
	}
	private Calle(Interseccion i1, Interseccion i2) {
		this.int1 = i1;
		this.int2 = i2;
		this.esfuerzo = null;
		this.duracion = null;
		//this.id = num;
		//num++;
	}
	private Calle(String s) {
		this.id = Integer.valueOf(s.replace("c", ""));
		this.int1 = null;
		this.int2 = null;
		this.duracion = null;
		this.esfuerzo = null;
	} 
	
	//Getter y Setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Interseccion getInt1() {
		return int1;
	}

	public void setInt1(Interseccion int1) {
		this.int1 = int1;
	}

	public Interseccion getInt2() {
		return int2;
	}

	public void setInt2(Interseccion int2) {
		this.int2 = int2;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Integer getEsfuerzo() {
		return esfuerzo;
	}

	public void setEsfuerzo(Integer esfuerzo) {
		this.esfuerzo = esfuerzo;
	}
/*
	public static int getNum() {
		return num;
	}

	public static void setNum(int num) {
		Calle.num = num;
	}
*/
	// Métodos HashCode y Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((duracion == null) ? 0 : duracion.hashCode());
		result = prime * result + ((esfuerzo == null) ? 0 : esfuerzo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((int1 == null) ? 0 : int1.hashCode());
		result = prime * result + ((int2 == null) ? 0 : int2.hashCode());
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
		Calle other = (Calle) obj;
		if (duracion == null) {
			if (other.duracion != null)
				return false;
		} else if (!duracion.equals(other.duracion))
			return false;
		if (esfuerzo == null) {
			if (other.esfuerzo != null)
				return false;
		} else if (!esfuerzo.equals(other.esfuerzo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (int1 == null) {
			if (other.int1 != null)
				return false;
		} else if (!int1.equals(other.int1))
			return false;
		if (int2 == null) {
			if (other.int2 != null)
				return false;
		} else if (!int2.equals(other.int2))
			return false;
		return true;
	}

	// ToString
	@Override
	public String toString() {
		return "Calle [id=" + id + ", int1=" + int1 + ", int2=" + int2 + ", duracion=" + duracion + ", esfuerzo="
				+ esfuerzo + "]";
	}
		

}
