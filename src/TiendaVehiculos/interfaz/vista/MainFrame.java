package TiendaVehiculos.interfaz.vista;

import TiendaVehiculos.interfaz.controlador.GestorVehiculos;
import TiendaVehiculos.interfaz.modelo.Vehiculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class MainFrame extends JFrame {
    private GestorVehiculos gestor;
    private JTextArea textArea;
    private JTextField placaField, marcaField, modeloField, anioField, tipoField, cilindradaField, ejeField, valorField;

    public MainFrame() {
        gestor = new GestorVehiculos();
        initUI();
    }

    private void initUI() {
        setTitle("Gestor de Vehículos Usados");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        // Campos de entrada
        panel.add(new JLabel("Tipo:"));
        tipoField = new JTextField();
        panel.add(tipoField);

        panel.add(new JLabel("Marca:"));
        marcaField = new JTextField();
        panel.add(marcaField);

        panel.add(new JLabel("Número de Ejes:"));
        ejeField = new JTextField();
        panel.add(ejeField);

        panel.add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        panel.add(modeloField);

        panel.add(new JLabel("Año:"));
        anioField = new JTextField();
        panel.add(anioField);

        panel.add(new JLabel("Cilindrada:"));
        cilindradaField = new JTextField();
        panel.add(cilindradaField);

        panel.add(new JLabel("Valor:"));
        valorField = new JTextField();
        panel.add(valorField);

        JButton addButton = new JButton("Agregar Vehículo");
        addButton.addActionListener(new AddVehicleAction());
        panel.add(addButton);

        JButton listButton = new JButton("Listar Placas");
        listButton.addActionListener(e -> listarPlacas());
        panel.add(listButton);

        JButton searchButton = new JButton("Buscar por Placa");
        searchButton.addActionListener(e -> buscarPorPlaca());
        panel.add(searchButton);

        JButton buyButton = new JButton("Comprar Vehículo");
        buyButton.addActionListener(e -> comprarVehiculo());
        panel.add(buyButton);

        JButton listAllButton = new JButton("Listar Todos los Vehículos");
        listAllButton.addActionListener(e -> listarTodosLosVehiculos());
        panel.add(listAllButton);

        JButton listForSaleButton = new JButton("Listar Placas de Vehículos a la Venta");
        listForSaleButton.addActionListener(e -> listarPlacasDeVehiculosALaVenta());
        panel.add(listForSaleButton);

        JButton sortByModelButton = new JButton("Ordenar por Modelo");
        sortByModelButton.addActionListener(e -> listarVehiculosPorModelo());
        panel.add(sortByModelButton);

        JButton sortByBrandButton = new JButton("Ordenar por Marca");
        sortByBrandButton.addActionListener(e -> listarVehiculosPorMarca());
        panel.add(sortByBrandButton);

        JButton sortByYearButton = new JButton("Ordenar por Año");
        sortByYearButton.addActionListener(e -> listarVehiculosPorAnio());
        panel.add(sortByYearButton);

        JButton decreaseAllValuesButton = new JButton("Disminuir Valor de Vehículos");
        decreaseAllValuesButton.addActionListener(e -> disminuirValorDeVehiculos());
        panel.add(decreaseAllValuesButton);

        JButton searchByModelAndYearButton = new JButton("Buscar por Modelo y Año");
        searchByModelAndYearButton.addActionListener(e -> buscarPorModeloYAnio());
        panel.add(searchByModelAndYearButton);

        JButton findOldestVehicleButton = new JButton("Localizar Vehículo Más Antiguo");
        findOldestVehicleButton.addActionListener(e -> localizarVehiculoMasAntiguo());
        panel.add(findOldestVehicleButton);

        JButton findMostPowerfulVehicleButton = new JButton("Localizar Vehículo Más Potente");
        findMostPowerfulVehicleButton.addActionListener(e -> localizarVehiculoMasPotente());
        panel.add(findMostPowerfulVehicleButton);

        JButton findCheapestVehicleButton = new JButton("Localizar Vehículo Más Barato");
        findCheapestVehicleButton.addActionListener(e -> localizarVehiculoMasBarato());
        panel.add(findCheapestVehicleButton);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void listarPlacas() {
        List<String> placas = gestor.listarPlacas();
        textArea.setText("Placas:\n" + String.join("\n", placas));
    }

    private void buscarPorPlaca() {
        String placa = JOptionPane.showInputDialog(this, "Ingrese la placa del vehículo a buscar:");

        if (placa == null || placa.trim().isEmpty()) {
            textArea.setText("Por favor, ingrese una placa válida.");
            return;
        }

        Vehiculo vehiculo = gestor.obtenerVehiculoPorPlaca(placa.trim());
        if (vehiculo != null) {
            textArea.setText(vehiculo.toString());
        } else {
            textArea.setText("Vehículo no encontrado.");
        }
    }

    private void comprarVehiculo() {
        String placa = JOptionPane.showInputDialog(this, "Ingrese la placa del vehículo a comprar:");

        if (placa == null || placa.trim().isEmpty()) {
            textArea.setText("Por favor, ingrese una placa válida.");
            return;
        }

        try {
            gestor.comprarVehiculo(placa.trim());
            textArea.setText("Vehículo con placa " + placa + " ha sido comprado.");
        } catch (IllegalArgumentException e) {
            textArea.setText(e.getMessage());
        }
    }

    private void listarTodosLosVehiculos() {
        List<Vehiculo> todosVehiculos = gestor.listarVehiculos();
        textArea.setText("Vehículos en venta:\n");
        for (Vehiculo v : todosVehiculos) {
            textArea.append(v.toString() + "\n\n");
        }
    }
    private void disminuirValorDeVehiculos() {
        String topeText = JOptionPane.showInputDialog(this, "Ingrese el tope:");

        if (topeText == null || topeText.trim().isEmpty()) {
            textArea.setText("Por favor, ingrese un tope válido.");
            return;
        }

        try {
            double tope = Double.parseDouble(topeText.trim());
            gestor.disminuirValorDeVehiculosSiSuperan(tope);

            // Crear un mensaje con los nuevos precios
            StringBuilder mensaje = new StringBuilder("Nuevos precios de vehículos:\n");
            for (Vehiculo v : gestor.listarVehiculos()) {
                mensaje.append(String.format("Placa: %s - Nuevo Valor: %.2f\n", v.getPlaca(), v.getValor()));
            }

            textArea.setText(mensaje.toString());
        } catch (NumberFormatException e) {
            textArea.setText("Por favor, ingrese un tope válido.");
        }
    }

    private void localizarVehiculoMasAntiguo() {
        Optional<Vehiculo> vehiculoAntiguo = gestor.vehiculoMasAntiguo();

        if (vehiculoAntiguo.isPresent()) {
            textArea.setText("Vehículo más antiguo:\n" + vehiculoAntiguo.get().toString());
        } else {
            textArea.setText("No hay vehículos registrados.");
        }
    }

    private void listarPlacasDeVehiculosALaVenta() {
        List<String> placas = gestor.listarPlacasDeVehiculosALaVenta();
        if (placas.isEmpty()) {
            textArea.setText("No hay vehículos a la venta.");
        } else {
            textArea.setText("Placas de vehículos a la venta:\n" + String.join("\n", placas));
        }
    }

    private void buscarPorModeloYAnio() {
        String modelo = JOptionPane.showInputDialog(this, "Ingrese el modelo del vehículo:");
        String anioText = JOptionPane.showInputDialog(this, "Ingrese el año del vehículo:");

        if (modelo == null || modelo.trim().isEmpty() || anioText == null || anioText.trim().isEmpty()) {
            textArea.setText("Por favor, ingrese un modelo y un año válidos.");
            return;
        }

        try {
            int anio = Integer.parseInt(anioText.trim());
            List<Vehiculo> resultados = gestor.buscarVehiculosPorModeloYAnio(modelo.trim(), anio);

            if (resultados.isEmpty()) {
                textArea.setText("No se encontraron vehículos con el modelo " + modelo + " y año " + anio + ".");
            } else {
                textArea.setText("Resultados:\n");
                for (Vehiculo v : resultados) {
                    textArea.append(v.toString() + "\n\n");
                }
            }
        } catch (NumberFormatException e) {
            textArea.setText("Por favor, ingrese un año válido.");
        }
    }

    private void localizarVehiculoMasPotente() {
        Optional<Vehiculo> vehiculoPotente = gestor.vehiculoMasPotente();

        if (vehiculoPotente.isPresent()) {
            textArea.setText("Vehículo más potente:\n" + vehiculoPotente.get().toString());
        } else {
            textArea.setText("No hay vehículos registrados.");
        }
    }

    private void listarVehiculosPorModelo() {
        List<Vehiculo> vehiculosOrdenados = gestor.listarVehiculosPorModelo();
        mostrarListaVehiculos(vehiculosOrdenados);
    }

    private void listarVehiculosPorMarca() {
        List<Vehiculo> vehiculosOrdenados = gestor.listarVehiculosPorMarca();
        mostrarListaVehiculos(vehiculosOrdenados);
    }

    private void listarVehiculosPorAnio() {
        List<Vehiculo> vehiculosOrdenados = gestor.listarVehiculosPorAnio();
        mostrarListaVehiculos(vehiculosOrdenados);
    }

    // Método auxiliar para mostrar la lista de vehículos en el textArea
    private void mostrarListaVehiculos(List<Vehiculo> vehiculos) {
        textArea.setText("Vehículos:\n");
        for (Vehiculo v : vehiculos) {
            textArea.append(v.toString() + "\n\n");
        }
    }

    private void localizarVehiculoMasBarato() {
        Optional<Vehiculo> vehiculoBarato = gestor.vehiculoMasBarato();

        if (vehiculoBarato.isPresent()) {
            textArea.setText("Vehículo más barato:\n" + vehiculoBarato.get().toString());
        } else {
            textArea.setText("No hay vehículos registrados.");
        }
    }


    private class AddVehicleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String tipo = tipoField.getText().trim();
                String marca = marcaField.getText().trim();
                String modelo = modeloField.getText().trim();
                int anio = Integer.parseInt(anioField.getText().trim());
                int numEjes = Integer.parseInt(ejeField.getText().trim());
                double cilindrada = Double.parseDouble(cilindradaField.getText().trim());
                double valor = Double.parseDouble(valorField.getText().trim());
                String placa = JOptionPane.showInputDialog("Ingrese la placa del vehículo:");

                if (placa != null && !placa.trim().isEmpty()) {
                    int respuesta = JOptionPane.showConfirmDialog(null,
                            "¿El vehículo con placa " + placa + " está a la venta?",
                            "Seleccionar Estado de Venta",
                            JOptionPane.YES_NO_OPTION);

                    boolean aLaVenta = (respuesta == JOptionPane.YES_OPTION);

                    Vehiculo nuevoVehiculo = new Vehiculo(tipo, marca, modelo, anio, numEjes, cilindrada, valor, placa);
                    gestor.agregarVehiculo(nuevoVehiculo);
                    nuevoVehiculo.setALaVenta(aLaVenta); // Ajusta el estado de venta
                    textArea.setText("Vehículo agregado.");
                } else {
                    textArea.setText("La placa no puede estar vacía.");
                }
            } catch (NumberFormatException ex) {
                textArea.setText("Por favor, ingrese valores numéricos válidos.");
            } catch (Exception ex) {
                textArea.setText("Ocurrió un error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

