package Taller02;

public abstract class Consumo {

	private Participante consumidor;
	private String fecha;

	public Consumo(Participante consumidor, String fecha) {
		this.consumidor = consumidor;
		this.fecha = fecha;
	}

	public Participante getConsumidor() {
		return consumidor;
	}

	public void setConsumidor(Participante consumidor) {
		this.consumidor = consumidor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public abstract double calcularKcal();
}
