package it.polito.tdp.borders.model;

public class Country {
	
	private Integer codC;
	private String stateABB;
	private String nome;
	
	public Country(Integer codC, String stateABB, String nome) {
		super();
		this.codC = codC;
		this.stateABB = stateABB;
		this.nome = nome;
	}

	public Integer getCodC() {
		return codC;
	}

	public void setCodC(Integer codC) {
		this.codC = codC;
	}

	public String getStateABB() {
		return stateABB;
	}

	public void setStateABB(String stateABB) {
		this.stateABB = stateABB;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String toString() {
		return this.nome+"\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codC == null) ? 0 : codC.hashCode());
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
		Country other = (Country) obj;
		if (codC == null) {
			if (other.codC != null)
				return false;
		} else if (!codC.equals(other.codC))
			return false;
		return true;
	}

}
