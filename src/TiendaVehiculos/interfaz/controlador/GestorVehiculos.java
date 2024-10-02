package TiendaVehiculos.interfaz.controlador;

import TiendaVehiculos.interfaz.modelo.Vehiculo; // Asegúrate de importar correctamente

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GestorVehiculos {
    private List<Vehiculo> vehiculos;

    public GestorVehiculos() {
        vehiculos = new ArrayList<>();
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null && !vehiculos.contains(vehiculo)) {
            vehiculos.add(vehiculo);
        } else {
            throw new IllegalArgumentException("El vehículo ya existe o es nulo.");
        }
    }

    public List<String> listarPlacas() {
        List<String> placas = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            placas.add(v.getPlaca());
        }
        return placas;
    }

    public Vehiculo obtenerVehiculoPorPlaca(String placa) {
        return vehiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa)) // Ignora mayúsculas y minúsculas
                .findFirst()
                .orElse(null);
    }

    public void comprarVehiculo(String placa) {
        boolean removed = vehiculos.removeIf(v -> v.getPlaca().equals(placa));
        if (!removed) {
            throw new IllegalArgumentException("Vehículo con placa " + placa + " no encontrado.");
        }
    }

    public List<String> listarPlacasDeVehiculosALaVenta() {
        List<String> placas = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            if (v.isALaVenta()) {
                placas.add(v.getPlaca());
            }
        }
        return placas;
    }

    // Método para ordenar vehículos por modelo
    public List<Vehiculo> listarVehiculosPorModelo() {
        return vehiculos.stream()
                .sorted(Comparator.comparing(Vehiculo::getModelo))
                .toList(); // Usa .collect(Collectors.toList()) si no tienes Java 16+
    }

    // Método para ordenar vehículos por marca
    public List<Vehiculo> listarVehiculosPorMarca() {
        return vehiculos.stream()
                .sorted(Comparator.comparing(Vehiculo::getMarca))
                .toList(); // Usa .collect(Collectors.toList()) si no tienes Java 16+
    }

    // Método para ordenar vehículos por año
    public List<Vehiculo> listarVehiculosPorAnio() {
        return vehiculos.stream()
                .sorted(Comparator.comparingInt(Vehiculo::getAnio))
                .toList(); // Usa .collect(Collectors.toList()) si no tienes Java 16+
    }

    public Optional<Vehiculo> vehiculoMasAntiguo() {
        return vehiculos.stream().min(Comparator.comparingInt(Vehiculo::getAnio));
    }

    // Método para listar todos los vehículos
    public List<Vehiculo> listarVehiculos() {
        return new ArrayList<>(vehiculos); // Devuelve una copia de la lista
    }

    public List<Vehiculo> buscarVehiculosPorModeloYAnio(String modelo, int anio) {
        return vehiculos.stream()
                .filter(v -> v.getModelo().equalsIgnoreCase(modelo) && v.getAnio() == anio)
                .toList(); // Usa .collect(Collectors.toList()) si no tienes Java 16+
    }

    public Optional<Vehiculo> vehiculoMasPotente() {
        return vehiculos.stream().max(Comparator.comparingDouble(Vehiculo::getCilindrada));
    }

    public Optional<Vehiculo> vehiculoMasBarato() {
        return vehiculos.stream().min(Comparator.comparingDouble(Vehiculo::getValor));
    }

    public void disminuirValorDeVehiculosSiSuperan(double tope) {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getValor() > tope) {
                double nuevoValor = vehiculo.getValor() * 0.90; // Disminuye el valor en un 10%
                vehiculo.setValor(nuevoValor);
            }
        }
    }
}



