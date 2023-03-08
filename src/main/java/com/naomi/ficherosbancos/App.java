package com.naomi.ficherosbancos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import entidades.Cuenta;
import entidades.CuentaCaixa;
import entidades.CuentaSabadell;
import entidades.CuentaSantander;

public class App {
	static String path = "/Users/Naomi/Desktop/FULLSTACK/CursoFullStackEOI/ficheros-fechas";

	public static String getDni(String[] columns) {
		return columns[0];
	}

	// Usar HashMap para decidir si escribimos esto en ingles o español.
	public static String getDniUserInput() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Escriba su DNI o CIF: \n");
		String dni = sc.nextLine();
		return dni; // después del return nada se ejecuta.
	}

	public static ArrayList<Cuenta> readAccountsFromFile() {

		ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();

		File directory = new File(path);
		File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

		for (File file : files) {
			String filename = file.getName().toLowerCase();

			try {
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line;

				while ((line = bufferedReader.readLine()) != null) {
					String[] columns = line.split(";");

					// crear una nueva instancia de la clase Cuenta correspondiente
					Cuenta c = null;
					if (filename.contains("caixa")) {
						c = new CuentaCaixa(columns[0], getName(columns), getBirthdate(columns), getPostcode(columns),
								getBalance(columns), "caixa");
					}
					if (filename.contains("sabadell")) {
						c = new CuentaSabadell(columns[0], getName(columns), getBirthdate(columns),
								getPostcode(columns), getBalance(columns), "sabadell");
					}
					if (filename.contains("santander")) {
						c = new CuentaSantander(columns[0], getName(columns), getBirthdate(columns),
								getPostcode(columns), getBalance(columns), "santander");
					}

					if (c != null) {
						cuentas.add(c);
					}
				}

				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return cuentas;
	}

	public static Cuenta getAccountByDni(ArrayList<Cuenta> cuentas, String dni) {

		for (Cuenta cuenta : cuentas) {
			if (cuenta.getDni().equals(dni)) {
				return cuenta;
			}
		}

		// si no se encontró la cuenta, devolver null
		return null;
	}

	public static String getDate(String postcode) {
		LocalDate date = LocalDate.now();
		if (postcode.equals("ES")) {
			DateTimeFormatter formatSpanish = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy",
					new Locale("ES"));
			String dateSpanish = date.format(formatSpanish);

			return dateSpanish;
		} else {

			String dayOrdinal = getDayOrdinal(date.getDayOfMonth());
			DateTimeFormatter dateEnglish = DateTimeFormatter.ofPattern("EEEE, MMMM d'" + dayOrdinal + "', yyyy",
					Locale.ENGLISH);
			return date.format(dateEnglish);
		}
	}

	private static String getDayOrdinal(int day) {
		if (day >= 11 && day <= 13) {
			return "th";
		}
		switch (day % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	public static String getName(String[] columns) {
		// line= 555;Dani;18/03/1970;ES;100000
		// country = "ES";
		return columns[1];
	}

	public static String getBirthdate(String[] columns) {
		return columns[2];
	}

	public static String getPostcode(String[] columns) {
		return columns[3];
	}

	public static double getBalance(String[] columns) {
		return Double.parseDouble(columns[4]);
	}

	public static void welcomeMessage(Cuenta user) {

		if (user.getLanguage().equals("en")) {
			System.out.println("Welcome " + user.getName() + "! Today is  " + getDate(user.getPostalCode()) + ".");

		} else {
			System.out.println("Bienvenido/a " + user.getName() + "! Hoy es " + getDate(user.getPostalCode()) + ".");
		}
	}

//	public static void showUserInfo(ArrayList<Cuenta> userInfo) {
//
//		for (Cuenta info : userInfo) {
//			System.out.println(info.getName());
//		}
//	}

//	public static Cuenta getUser(String dni, ArrayList<Cuenta> cuentas) {
//		for (Cuenta a : cuentas) {
//			if (a.getDni() == dni) {
//				return a;
//			}
//		}
//		return null;
//	}

	public static boolean moreThanOneBirthdate(ArrayList<Cuenta> cuentas, String dni) {
		Cuenta user = getAccountByDni(cuentas, dni);
		for (Cuenta cuenta : cuentas) {
			if (user.getDni().equals(cuenta.getDni()) && !user.getBirthdate().equals(cuenta.getBirthdate())) {
				return true;
			}

		}

		return false;
	}

	public static List<String> getDifferentBirthdates(ArrayList<Cuenta> cuentas, String dni) {
		Set<String> differentBirthdates = new HashSet<String>();

		for (Cuenta cuenta : cuentas) {
			if (cuenta.getDni().equals(dni)) {
				String birthdate = cuenta.getBirthdate();
				differentBirthdates.add(birthdate);
			}
		}

		if (differentBirthdates.size() == 1) {
			return null;
		} else {
			return new ArrayList<String>(differentBirthdates);
		}
	}

	public static void printBirthdates(ArrayList<Cuenta> cuentas, String dni) {
		if (moreThanOneBirthdate(cuentas, dni)) {
			System.out.println("Su cuenta tiene más de una fecha de nacimiento registrada.");
		} 
	}

	public static void getCorrectBirthdateFromUser(List<String> birthdates, ArrayList<Cuenta> cuentas, String dni) {
		Scanner sc = new Scanner(System.in);
		boolean moreThanOneBirthdate = moreThanOneBirthdate(cuentas, dni);
		if (moreThanOneBirthdate) {
			System.out.println("Introduzca el número correspondiente a la fecha de nacimiento correcta:");
			for (int i = 0; i < birthdates.size(); i++) {
				System.out.println((i + 1) + ". " + birthdates.get(i));

			}
		}
		int chosenBirthdate = Integer.parseInt(sc.nextLine());
		if (chosenBirthdate < 1 || chosenBirthdate > birthdates.size()) {
			System.out.println("Opción inválida.");
		} else {
			System.out.println("Se ha actualizado su cuenta con la fecha de nacimiento "
					+ birthdates.get(chosenBirthdate - 1) + ".");
			/**
			 * chosenBirthdate = 1------memoria == posicion 0 chosenBirthdate = 1 - 1
			 * chosenBirthdate = 0 birthdates.get(0) birthdates[0]
			 * 
			 * 
			 */
		}

	}

	public static void main(String[] args) {
		String dni = getDniUserInput();
		ArrayList<Cuenta> cuentas = readAccountsFromFile();
		Cuenta user = getAccountByDni(cuentas, dni);
		welcomeMessage(user);
		List<String> birthdates = getDifferentBirthdates(cuentas, dni);
		printBirthdates(cuentas, dni);
		getCorrectBirthdateFromUser(birthdates, cuentas, dni);
	}
}