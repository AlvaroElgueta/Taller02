package Taller02;

public class Choripan extends Consumo {

	private int largo;
	private int ancho;

	public Choripan(Participante consumidor, String fecha, int largo, int ancho) {
		super(consumidor, fecha);
		this.largo = largo;
		this.ancho = ancho;
	}

	public int getLargo() {
		return largo;
	}

	public void setLargo(int largo) {
		this.largo = largo;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	@Override
	public double calcularKcal() {
		return (double) (largo * ancho) / 3;
	}
}
