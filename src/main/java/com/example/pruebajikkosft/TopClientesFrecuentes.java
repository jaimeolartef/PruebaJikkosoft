package com.example.pruebajikkosft;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.*;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TopClientesFrecuentes {

	static final Logger logger = Logger.getLogger(TopClientesFrecuentes.class.getName());

	public static void main(String[] args) throws Exception {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date inicio = null;
		Date fin = null;
		String archivo = "1/TRANSACCIONES_ECOMMERCE_DATASET.txt";
		Scanner scanner = new Scanner(System.in);
		logger.info("Ingrese la fecha de inicio (yyyy-MM-dd HH:mm:ss - 2025-01-01 00:00:00): ");
		try {
			inicio = formatoFecha.parse(scanner.nextLine());
		} catch (ParseException e) {
			logger.warning("El formato ingresado es invalido. Por favor, use el formato yyyy-MM-dd HH:mm:ss");
			return;
		}

		scanner = new Scanner(System.in);
		logger.info("Ingrese la fecha final (yyyy-MM-dd HH:mm:ss - 2025-06-30 00:00:00): ");
		try {
			fin = formatoFecha.parse(scanner.nextLine());
		} catch (ParseException e) {
			logger.warning("El formato ingresado es invalido. Por favor, use el formato yyyy-MM-dd HH:mm:ss");
			return;
		}
		loadFile(archivo, inicio, fin);
	}

	public static void loadFile(String filePath, Date inicio, Date fin) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			Map<String, Long> lineas =  Files.lines(Paths.get(filePath))
				.map(linea -> linea.split(","))
				.filter(linea -> {
						try {
							return !linea[0].isEmpty() && !linea[0].contains("#") && (formatoFecha.parse(linea[0])).after(inicio) &&
									formatoFecha.parse(linea[0]).before(fin);
						} catch (ParseException e) {
							logger.severe("Error al validar la fecha: " + e.getMessage());
							return false;
						}
					}

				)
				.map(linea -> linea[1])
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

			if (lineas.isEmpty()){
				logger.info("No se encontraron transacciones en el rango de fechas especificado.");
				return;
			}

			StringBuilder imprimir = new StringBuilder();
			imprimir.append("Lista de los 10 clientes más frecuentes:\n");
			lineas.entrySet()
				.stream()
				.limit(10)
				.forEach(linea -> imprimir.append("* ").append(linea.getKey()).append("\n"));
			logger.info(imprimir.toString());
		} catch (Exception e) {
			logger.severe("Error al cargar el archivo: " + e.getMessage());
		} finally {
			logger.info("Carga de archivo finalizada.");
		}
	}
}