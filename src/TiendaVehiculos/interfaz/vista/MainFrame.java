package TiendaVehiculos.interfaz.vista;

import TiendaVehiculos.interfaz.controlador.GestorVehiculos;
import TiendaVehiculos.interfaz.modelo.Vehiculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

    private void listarPlacasDeVehiculosALaVenta() {
        List<String> placas = gestor.listarPlacasDeVehiculosALaVenta();
        if (placas.isEmpty()) {
            textArea.setText("No hay vehículos a la venta.");
        } else {
            textArea.setText("Placas de vehículos a la venta:\n" + String.join("\n", placas));
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

