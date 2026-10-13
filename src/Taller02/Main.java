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
