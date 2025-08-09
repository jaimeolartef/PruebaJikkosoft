package com.example.pruebajikkosft;

import java.io.*;
import java.text.*;
import java.util.*;

public class TopClientesFrecuentes {

	static final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static class Transaccion {
		Date timestamp;
		String clienteId;
		double importe;

		public Transaccion(Date timestamp, String clienteId, double importe) {
			this.timestamp = timestamp;
			this.clienteId = clienteId;
			this.importe = importe;
		}
	}

	static Transaccion parsearLinea(String linea) throws ParseException {
		String[] partes = linea.split(",");
		Date timestamp = formatoFecha.parse(partes[0].trim());
		String clienteId = partes[1].trim();
		double importe = Double.parseDouble(partes[2].trim());
		return new Transaccion(timestamp, clienteId, importe);
	}

	public static List<Map.Entry<String, Integer>> top10Clientes(String rutaArchivo, Date inicio, Date fin) {
		Map<String, Integer> frecuenciaClientes = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				try {
					Transaccion t = parsearLinea(linea);
					if (!t.timestamp.before(inicio) && !t.timestamp.after(fin)) {
						frecuenciaClientes.put(t.clienteId, frecuenciaClientes.getOrDefault(t.clienteId, 0) + 1);
					}
				} catch (ParseException | ArrayIndexOutOfBoundsException e) {
					System.err.println("Error al procesar la línea: " + linea + " - " + e.getMessage());
					// Aquí podrías registrar el error en un log si fuera necesario
					// o manejarlo de otra forma según tus necesidades.
					continue; // Continuar con la siguiente línea
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		PriorityQueue<Map.Entry<String, Integer>> heap = new PriorityQueue<>(Map.Entry.comparingByValue());

		for (Map.Entry<String, Integer> entry : frecuenciaClientes.entrySet()) {
			if (heap.size() < 10) {
				heap.offer(entry);
			} else if (entry.getValue() > heap.peek().getValue()) {
				heap.poll();
				heap.offer(entry);
			}
		}

		List<Map.Entry<String, Integer>> resultado = new ArrayList<>(heap);
		resultado.sort((a, b) -> b.getValue() - a.getValue());

		return resultado;
	}

	public static void main(String[] args) throws Exception {
		String archivo = "transacciones.csv"; // Formato: yyyy-MM-dd HH:mm:ss,clienteId,importe
		Date inicio = formatoFecha.parse("2025-07-01 00:00:00");
		Date fin = formatoFecha.parse("2025-07-31 23:59:59");

		List<Map.Entry<String, Integer>> topClientes = top10Clientes(archivo, inicio, fin);

		System.out.println("Top 10 clientes más frecuentes:");
		for (Map.Entry<String, Integer> entry : topClientes) {
			System.out.println("Cliente: " + entry.getKey() + ", Frecuencia: " + entry.getValue());
		}
	}
}