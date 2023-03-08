package entidades;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cuenta {

	private String dni;
	private String name;
	private String birthDate;
	private String postalCode;
	private double balance;
	private String type;

	public Cuenta() {}

	// FUNCION CONSTRUCTOR- SE EJECUTA CUANDO INSTANCIAS UNA CLASE (con new).
	public Cuenta(String dni, String name, String birthDate, String postalCode, double balance, String type) {
		super();
		this.dni = dni;
		this.name = name;
		this.birthDate = birthDate;
		this.postalCode = postalCode;
		this.balance = balance;
		this.type = type; // caixa, santander
	}

	public Cuenta(Cuenta c) {
		super();
		this.dni = c.dni;
		this.name = c.name;
		this.birthDate = c.birthDate;
		this.postalCode = c.postalCode;
		this.balance = c.balance;
		this.type = c.type;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthdate() {
		return birthDate;
	}

	public void setFechaNacimiento(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setCodigoPostal(String postalCode) {
		this.postalCode = postalCode;
	}

	public double getbalance() {
		return balance;
	}

	public void setbalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [dni=" + dni + ", name=" + name + ", birthDate=" + birthDate + ", postalCode=" + postalCode
				+ ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		return Objects.equals(dni, other.dni) && Objects.equals(name, other.name);
	}

	public String getLanguage() {

		// voy recorriendo cada línea del archivo que me dieron.

		Map<String, String> countryLanguage = new HashMap<String, String>();
		countryLanguage.put("UK", "en");
		countryLanguage.put("ES", "es");
		return countryLanguage.get(this.getPostalCode());
	}

	public String getType() {
		return this.type;
	}
}
