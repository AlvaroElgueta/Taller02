package Taller02;

public class Terremoto extends Consumo {

	private double litros;

	public Terremoto(Participante consumidor, String fecha, double litros) {
		super(consumidor, fecha);
		this.litros = litros;
	}

	public double getLitros() {
		return litros;
	}

	public void setLitros(double litros) {
		this.litros = litros;
	}

	@Override
	public double calcularKcal() {
		return (litros * 10) / 3;
	}
}
