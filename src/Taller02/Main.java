// Integrante1: Alvaro Elgueta - Rut: 21.806.097-8 - Carrera: ICCI
// Integrante2: Catalina Fica  - Rut: 21.779.663-6 - Carrera: ICCI

package Taller02;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	private static Scanner s;

	private static int contParticipantes;
	private static Participante[] participantes = new Participante[200];

	private static int contConsumos;
	private static Consumo[] consumos = new Consumo[200];

	public static void main(String[] args) {
		cargarParticipantes();
		cargarConsumos();
		System.out.println("Archivos cargados con exito!\r\n"
				+ "- " + contParticipantes + " participantes.\r\n"
				+ "- " + contConsumos + " consumos.");
		s = new Scanner(System.in);
		menu();
		s.close();
	}

	//se encarga de mostrar el menu principal
	private static void menu() {
		boolean salir = false;
		do {
			System.out.println("\r\n===== LA FONDA DEL PAPU =====\r\n"
					+ "Registros cargados: " + contConsumos + "\r\n"
					+ "1) Ver registros\r\n"
					+ "2) Actualizar registros\r\n"
					+ "3) Calcular metricas\r\n"
					+ "4) Salir\r\n");
			int opcion = leerOpcion();
			switch (opcion) {
			case 1: {
				menuVerRegistros();
				break;
			}
			case 2: {
				System.out.println("Opcion aun no disponible\r\n");
				break;
			}
			case 3: {
				System.out.println("Opcion aun no disponible\r\n");
				break;
			}
			case 4: {
				System.out.println("Hasta luego...");
				salir = true;
				break;
			}
			default: {
				System.out.println("Debe ser una opcion valida(1/2/3/4)\r\n");
				break;
			}
			}
		} while (!salir);
	}

	//se encarga de mostrar el menu de registros
	private static void menuVerRegistros() {
		boolean salir = false;
		do {
			System.out.println("\r\n===== VER REGISTROS =====\r\n"
					+ "1) Choripanes\r\n"
					+ "2) Terremotos\r\n"
					+ "3) Volver al menu principal\r\n");
			int opcion = leerOpcion();
			switch (opcion) {
			case 1: {
				mostrarChoripanes();
				break;
			}
			case 2: {
				mostrarTerremotos();
				break;
			}
			case 3: {
				salir = true;
				break;
			}
			default: {
				System.out.println("Debe ser una opcion valida(1/2/3)\r\n");
				break;
			}
			}
		} while (!salir);
	}

	//muestra todos los choripanes registrados
	private static void mostrarChoripanes() {
		int cantidad = 0;
		System.out.println("\r\n===== CHORIPANES =====");
		for (int i = 0; i < contConsumos; i++) {
			if (consumos[i] instanceof Choripan) {
				Choripan choripan = (Choripan) consumos[i];
				cantidad++;
				System.out.println(cantidad + ") " + choripan.getConsumidor().getNombre()
						+ " | " + choripan.getLargo() + " cm x " + choripan.getAncho()
						+ " cm | " + choripan.getFecha() + " | "
						+ redondear(choripan.calcularKcal()) + " KCal");
			}
		}
		System.out.println("Total: " + cantidad + " choripanes registrados\r\n");
	}

	//muestra todos los terremotos registrados
	private static void mostrarTerremotos() {
		int cantidad = 0;
		double totalLitros = 0;
		System.out.println("\r\n===== TERREMOTOS =====");
		for (int i = 0; i < contConsumos; i++) {
			if (consumos[i] instanceof Terremoto) {
				Terremoto terremoto = (Terremoto) consumos[i];
				cantidad++;
				totalLitros += terremoto.getLitros();
				System.out.println(cantidad + ") " + terremoto.getConsumidor().getNombre()
						+ " | " + terremoto.getLitros() + " L | " + terremoto.getFecha()
						+ " | " + redondear(terremoto.calcularKcal()) + " KCal");
			}
		}
		System.out.println("Total: " + cantidad + " terremotos registrados ("
				+ redondear(totalLitros) + " L)\r\n");
	}

	//redondea un numero a dos decimales
	private static double redondear(double numero) {
		return Math.round(numero * 100.0) / 100.0;
	}

	//valida que el usuario ingrese un numero
	private static int leerOpcion() {
		try {
			System.out.print("Ingrese opcion: ");
			return Integer.parseInt(s.nextLine());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	//se encarga de cargar los participantes
	private static void cargarParticipantes() {
		contParticipantes = 0;
		try {
			s = new Scanner(new File("Participantes.txt"));
			while (s.hasNextLine()) {
				String nombre = s.nextLine().trim();
				if (!nombre.equalsIgnoreCase("")) {
					if (contParticipantes < participantes.length) {
						participantes[contParticipantes] = new Participante(nombre);
						contParticipantes++;
					} else {
						System.out.println("No hay espacio para guardar mas participantes");
						break;
					}
				}
			}
			s.close();
		} catch (IOException e) {
			System.out.println("Carga de Participantes.txt fallida");
		}
	}

	//se encarga de cargar los consumos
	private static void cargarConsumos() {
		contConsumos = 0;
		try {
			s = new Scanner(new File("Consumido.txt"));
			while (s.hasNextLine()) {
				String[] datos = s.nextLine().split(";");
				if (contConsumos >= consumos.length) {
					System.out.println("No hay espacio para guardar mas consumos");
					break;
				}
				try {
					if (datos.length == 5 && datos[0].equalsIgnoreCase("Choripan")) {
						Participante consumidor = buscarParticipante(datos[1]);
						int largo = Integer.parseInt(datos[2]);
						int ancho = Integer.parseInt(datos[3]);
						String fecha = datos[4];
						if (consumidor != null && largo > 0 && ancho > 0 && !fecha.equalsIgnoreCase("")) {
							consumos[contConsumos] = new Choripan(consumidor, fecha, largo, ancho);
							contConsumos++;
						} else {
							System.out.println("Linea con formato incorrecto en Consumido.txt");
						}
					} else if (datos.length == 4 && datos[0].equalsIgnoreCase("Terremoto")) {
						Participante consumidor = buscarParticipante(datos[1]);
						double litros = Double.parseDouble(datos[2]);
						String fecha = datos[3];
						if (consumidor != null && litros > 0 && !fecha.equalsIgnoreCase("")) {
							consumos[contConsumos] = new Terremoto(consumidor, fecha, litros);
							contConsumos++;
						} else {
							System.out.println("Linea con formato incorrecto en Consumido.txt");
						}
					} else if (datos.length > 1) {
						System.out.println("Linea con formato incorrecto en Consumido.txt");
					}
				} catch (NumberFormatException e) {
					System.out.println("Linea con formato incorrecto en Consumido.txt");
				}
			}
			s.close();
		} catch (IOException e) {
			System.out.println("Carga de Consumido.txt fallida");
		}
	}

	//busca un participante por su nombre
	private static Participante buscarParticipante(String nombre) {
		for (int i = 0; i < contParticipantes; i++) {
			if (nombre.trim().equalsIgnoreCase(participantes[i].getNombre())) {
				return participantes[i];
			}
		}
		return null;
	}
}
