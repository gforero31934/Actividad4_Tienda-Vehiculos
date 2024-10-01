package TiendaVehiculos.interfaz.modelo;

public class Vehiculo {
    private String tipo;
    private String marca;
    private String modelo;
    private int anio;
    private int numEjes;
    private double cilindrada;
    private double valor;
    private String placa;
    private boolean aLaVenta;// validar si esta o no a la venta

    public Vehiculo(String tipo, String marca, String modelo, int anio, int numEjes, double cilindrada, double valor, String placa) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.numEjes = numEjes;
        this.cilindrada = cilindrada;
        this.valor = valor;
        this.placa = placa;
        this.aLaVenta = true; // Por defecto, todos los vehículos se agregan a la venta
    }

    // Getters
    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnio() {
        return anio;
    }

    public int getNumEjes() {
        return numEjes;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public double getValor() {
        return valor;
    }

    public String getPlaca() {
        return placa;
    }
    public boolean isALaVenta() {
        return aLaVenta;
    }


    // Setters
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setNumEjes(int numEjes) {
        this.numEjes = numEjes;
    }

    public void setCilindrada(double cilindrada) {
        this.cilindrada = cilindrada;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public void setALaVenta(boolean aLaVenta) {
        this.aLaVenta = aLaVenta;
    }


    @Override
    public String toString() {
        return String.format("Tipo: %s\nMarca: %s\nModelo: %s\nAño: %d\nNúmero de Ejes: %d\nCilindrada: %.2f\nValor: %.2f\nPlaca: %s,\n A la venta: %s",
                tipo, marca, modelo, anio, numEjes, cilindrada, valor, placa,aLaVenta ? "Sí" : "No");
    }
}
