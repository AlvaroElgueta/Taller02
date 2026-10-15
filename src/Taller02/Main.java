// Integrante1: Alvaro Elgueta - Rut: 21.806.097-8 - Carrera: ICCI

package Taller02;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
				menuActualizarRegistros();
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

	//se encarga de mostrar el menu para actualizar registros
	private static void menuActualizarRegistros() {
		boolean salir = false;
		do {
			System.out.println("\r\n===== ACTUALIZAR REGISTROS =====\r\n"
					+ "1) Agregar\r\n"
					+ "2) Editar\r\n"
					+ "3) Eliminar\r\n"
					+ "4) Volver al menu principal\r\n");
			int opcion = leerOpcion();
			switch (opcion) {
			case 1: {
				agregarRegistro();
				break;
			}
			case 2: {
				editarRegistro();
				break;
			}
			case 3: {
				eliminarRegistro();
				break;
			}
			case 4: {
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

	//se encarga de agregar un nuevo consumo
	private static void agregarRegistro() {
		if (contConsumos >= consumos.length) {
			System.out.println("No hay espacio para guardar mas consumos\r\n");
			return;
		}
		System.out.println("\r\nTipo de registro:\r\n"
				+ "1) Choripan\r\n"
				+ "2) Terremoto\r\n"
				+ "3) Cancelar\r\n");
		int opcion = leerOpcion();
		if (opcion == 3) {
			return;
		}
		if (opcion != 1 && opcion != 2) {
			System.out.println("Debe ser una opcion valida(1/2/3)\r\n");
			return;
		}

		Participante consumidor = seleccionarParticipante();
		if (consumidor == null) {
			return;
		}

		if (opcion == 1) {
			int largo = leerEnteroPositivo("Largo del choripan (cm): ");
			int ancho = leerEnteroPositivo("Ancho del choripan (cm): ");
			String fecha = leerFecha();
			consumos[contConsumos] = new Choripan(consumidor, fecha, largo, ancho);
		} else {
			double litros = leerDoublePositivo("Litros del terremoto: ");
			String fecha = leerFecha();
			consumos[contConsumos] = new Terremoto(consumidor, fecha, litros);
		}
		contConsumos++;

		if (guardarConsumos()) {
			System.out.println("Registro agregado y guardado en Consumido.txt\r\n");
		}
	}

	//se encarga de editar un consumo
	private static void editarRegistro() {
		int tipo = seleccionarTipoRegistro("editar");
		if (tipo == -1) {
			return;
		}
		int indice = seleccionarRegistro(tipo, "editar");
		if (indice == -1) {
			return;
		}

		Consumo consumo = consumos[indice];
		System.out.println("Registro actual -> " + textoRegistro(consumo));
		System.out.println("Deje el campo vacio para mantener el valor actual");
		consumo.setConsumidor(leerParticipanteOpcional(consumo.getConsumidor()));

		if (consumo instanceof Choripan) {
			Choripan choripan = (Choripan) consumo;
			choripan.setLargo(leerEnteroOpcional("Largo [" + choripan.getLargo() + "]: ",
					choripan.getLargo()));
			choripan.setAncho(leerEnteroOpcional("Ancho [" + choripan.getAncho() + "]: ",
					choripan.getAncho()));
		} else if (consumo instanceof Terremoto) {
			Terremoto terremoto = (Terremoto) consumo;
			terremoto.setLitros(leerDoubleOpcional("Litros [" + terremoto.getLitros() + "]: ",
					terremoto.getLitros()));
		}
		consumo.setFecha(leerFechaOpcional(consumo.getFecha()));

		if (guardarConsumos()) {
			System.out.println("Registro actualizado y guardado en Consumido.txt\r\n");
		}
	}

	//se encarga de eliminar un consumo
	private static void eliminarRegistro() {
		int tipo = seleccionarTipoRegistro("eliminar");
		if (tipo == -1) {
			return;
		}
		int indice = seleccionarRegistro(tipo, "eliminar");
		if (indice == -1) {
			return;
		}

		System.out.println("Registro seleccionado -> " + textoRegistro(consumos[indice]));
		while (true) {
			System.out.print("Confirma la eliminacion? (s/n): ");
			String respuesta = s.nextLine().trim();
			if (respuesta.equalsIgnoreCase("n")) {
				return;
			}
			if (respuesta.equalsIgnoreCase("s")) {
				break;
			}
			System.out.println("Debe responder con s o n");
		}

		for (int i = indice; i < contConsumos - 1; i++) {
			consumos[i] = consumos[i + 1];
		}
		contConsumos--;
		consumos[contConsumos] = null;

		if (guardarConsumos()) {
			System.out.println("Registro eliminado y guardado en Consumido.txt\r\n");
		}
	}

	//permite elegir el tipo de registro que se quiere modificar
	private static int seleccionarTipoRegistro(String accion) {
		System.out.println("\r\nTipo de registro a " + accion + ":\r\n"
				+ "1) Choripan\r\n"
				+ "2) Terremoto\r\n"
				+ "3) Cancelar\r\n");
		int opcion = leerOpcion();
		if (opcion == 3) {
			return -1;
		}
		if (opcion != 1 && opcion != 2) {
			System.out.println("Debe ser una opcion valida(1/2/3)\r\n");
			return -1;
		}
		return opcion;
	}

	//muestra los registros de un tipo y retorna la posicion seleccionada
	private static int seleccionarRegistro(int tipo, String accion) {
		int cantidad = 0;
		System.out.println();
		for (int i = 0; i < contConsumos; i++) {
			if ((tipo == 1 && consumos[i] instanceof Choripan)
					|| (tipo == 2 && consumos[i] instanceof Terremoto)) {
				cantidad++;
				System.out.println(cantidad + ") " + textoRegistro(consumos[i]));
			}
		}
		if (cantidad == 0) {
			System.out.println("No hay registros de este tipo\r\n");
			return -1;
		}

		while (true) {
			System.out.print("Seleccione el registro a " + accion + " (0 para cancelar): ");
			int opcion;
			try {
				opcion = Integer.parseInt(s.nextLine().trim());
			} catch (NumberFormatException e) {
				opcion = -1;
			}
			if (opcion == 0) {
				return -1;
			}
			if (opcion >= 1 && opcion <= cantidad) {
				int posicion = 0;
				for (int i = 0; i < contConsumos; i++) {
					if ((tipo == 1 && consumos[i] instanceof Choripan)
							|| (tipo == 2 && consumos[i] instanceof Terremoto)) {
						posicion++;
						if (posicion == opcion) {
							return i;
						}
					}
				}
			}
			System.out.println("No existe un registro con ese numero");
		}
	}

	//retorna la informacion de un consumo
	private static String textoRegistro(Consumo consumo) {
		if (consumo instanceof Choripan) {
			Choripan choripan = (Choripan) consumo;
			return "Choripan | " + choripan.getConsumidor().getNombre() + " | "
					+ choripan.getLargo() + " cm x " + choripan.getAncho() + " cm | "
					+ choripan.getFecha();
		}
		Terremoto terremoto = (Terremoto) consumo;
		return "Terremoto | " + terremoto.getConsumidor().getNombre() + " | "
				+ terremoto.getLitros() + " L | " + terremoto.getFecha();
	}

	//muestra los participantes y retorna el seleccionado
	private static Participante seleccionarParticipante() {
		System.out.println("\r\nParticipantes disponibles:");
		for (int i = 0; i < contParticipantes; i++) {
			System.out.println((i + 1) + ") " + participantes[i].getNombre());
		}
		System.out.println("0) Cancelar");
		while (true) {
			int opcion = leerOpcion();
			if (opcion == 0) {
				return null;
			}
			if (opcion >= 1 && opcion <= contParticipantes) {
				return participantes[opcion - 1];
			}
			System.out.println("Debe seleccionar un participante valido");
		}
	}

	//se encarga de guardar los consumos en el archivo
	private static boolean guardarConsumos() {
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter("Consumido.txt"));
			for (int i = 0; i < contConsumos; i++) {
				if (consumos[i] instanceof Choripan) {
					Choripan choripan = (Choripan) consumos[i];
					escritor.write("Choripan;" + choripan.getConsumidor().getNombre() + ";"
							+ choripan.getLargo() + ";" + choripan.getAncho() + ";"
							+ choripan.getFecha());
				} else if (consumos[i] instanceof Terremoto) {
					Terremoto terremoto = (Terremoto) consumos[i];
					escritor.write("Terremoto;" + terremoto.getConsumidor().getNombre() + ";"
							+ terremoto.getLitros() + ";" + terremoto.getFecha());
				}
				escritor.newLine();
			}
			escritor.close();
			return true;
		} catch (IOException e) {
			System.out.println("No se pudieron guardar los cambios en Consumido.txt\r\n");
			return false;
		}
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
			return Integer.parseInt(s.nextLine().trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	//lee un numero entero mayor a cero
	private static int leerEnteroPositivo(String mensaje) {
		while (true) {
			try {
				System.out.print(mensaje);
				int numero = Integer.parseInt(s.nextLine().trim());
				if (numero > 0) {
					return numero;
				}
			} catch (NumberFormatException e) {
			}
			System.out.println("Debe ingresar un numero mayor a cero");
		}
	}

	//lee un numero decimal mayor a cero
	private static double leerDoublePositivo(String mensaje) {
		while (true) {
			try {
				System.out.print(mensaje);
				double numero = Double.parseDouble(s.nextLine().trim());
				if (numero > 0) {
					return numero;
				}
			} catch (NumberFormatException e) {
			}
			System.out.println("Debe ingresar un numero mayor a cero");
		}
	}

	//lee un participante o mantiene el actual si queda vacio
	private static Participante leerParticipanteOpcional(Participante actual) {
		while (true) {
			System.out.print("Consumidor [" + actual.getNombre() + "]: ");
			String nombre = s.nextLine().trim();
			if (nombre.equalsIgnoreCase("")) {
				return actual;
			}
			Participante participante = buscarParticipante(nombre);
			if (participante != null) {
				return participante;
			}
			System.out.println("El participante no esta registrado");
		}
	}

	//lee un entero o mantiene el valor actual si queda vacio
	private static int leerEnteroOpcional(String mensaje, int actual) {
		while (true) {
			System.out.print(mensaje);
			String texto = s.nextLine().trim();
			if (texto.equalsIgnoreCase("")) {
				return actual;
			}
			try {
				int numero = Integer.parseInt(texto);
				if (numero > 0) {
					return numero;
				}
			} catch (NumberFormatException e) {
			}
			System.out.println("Debe ingresar un numero mayor a cero");
		}
	}

	//lee un decimal o mantiene el valor actual si queda vacio
	private static double leerDoubleOpcional(String mensaje, double actual) {
		while (true) {
			System.out.print(mensaje);
			String texto = s.nextLine().trim();
			if (texto.equalsIgnoreCase("")) {
				return actual;
			}
			try {
				double numero = Double.parseDouble(texto);
				if (numero > 0) {
					return numero;
				}
			} catch (NumberFormatException e) {
			}
			System.out.println("Debe ingresar un numero mayor a cero");
		}
	}

	//lee una fecha con el formato correcto
	private static String leerFecha() {
		while (true) {
			System.out.print("Fecha (YYYY-MM-DD): ");
			String fecha = s.nextLine().trim();
			if (fechaValida(fecha)) {
				return fecha;
			}
			System.out.println("La fecha debe tener el formato YYYY-MM-DD");
		}
	}

	//lee una fecha o mantiene la actual si queda vacio
	private static String leerFechaOpcional(String actual) {
		while (true) {
			System.out.print("Fecha [" + actual + "]: ");
			String fecha = s.nextLine().trim();
			if (fecha.equalsIgnoreCase("")) {
				return actual;
			}
			if (fechaValida(fecha)) {
				return fecha;
			}
			System.out.println("La fecha debe tener el formato YYYY-MM-DD");
		}
	}

	//valida el formato basico de una fecha
	private static boolean fechaValida(String fecha) {
		if (fecha.length() != 10 || fecha.charAt(4) != '-' || fecha.charAt(7) != '-') {
			return false;
		}
		try {
			int anio = Integer.parseInt(fecha.substring(0, 4));
			int mes = Integer.parseInt(fecha.substring(5, 7));
			int dia = Integer.parseInt(fecha.substring(8, 10));
			return anio > 0 && mes >= 1 && mes <= 12 && dia >= 1 && dia <= 31;
		} catch (NumberFormatException e) {
			return false;
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
