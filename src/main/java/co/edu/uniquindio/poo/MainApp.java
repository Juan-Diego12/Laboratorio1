package co.edu.uniquindio.poo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private ClubDeportivo club;
    private ObservableList<String> deportesObservableList;

    private ResourceBundle bundle;
    private Locale currentLocale;

   
   

    @Override
    public void start(Stage primaryStage) {
        club = new ClubDeportivo();
        deportesObservableList = FXCollections.observableArrayList();

        currentLocale = new Locale("es", "ES");
        bundle = ResourceBundle.getBundle("locale.messages", currentLocale);

        mostrarPantallaInicial(primaryStage);
        LoggerManager.logInfo("Aplicacion iniciada.");
    }


    private void cambiarIdioma(Locale nuevoLocale, Stage stage) {
        currentLocale = nuevoLocale;
        bundle = ResourceBundle.getBundle("locale.messages", currentLocale);
        mostrarPantallaInicial(stage); 
        if (currentLocale.getLanguage().equals("en")) {
            LoggerManager.logInfo("Idioma cambiado a Espanol");
        } else {
            LoggerManager.logInfo("Idioma cambiado a Ingles");
        }
        
    }

    private void gestionArchivosMiembros(){
        String formatoMiembros = "El miembro %s con email %s y número de identificación %s se encuentra inscrito al club";
        String formatoDeportes = "El deporte %s con su descripcion %S se encuentra disponible en el club";
        // Lista de entidades ejemplo
        List<MiembroClub> listaMiembros = club.getMiembros();
        List<Deporte> listaDeportes = club.getDeportes();

        // Directorio en C://
        File directorio = new File("Reportes_Java");
        File archivoMiembros = new File("Reportes_Java/Miembros.txt");
        File archivoDeportes = new File("Reportes_Java/Deportes.txt");

        if (!directorio.exists()) {
            if (directorio.mkdir()) {
                System.out.println("Directorio creado: " + directorio.getPath());
            } else {
                System.out.println("No se pudo crear el directorio.");
                return;
            }
        }
        // Almacenar datos en archivo
        try {
            Utilidades.getInstance();
            Utilidades.escribirMiembrosTxt(archivoMiembros, listaMiembros, formatoMiembros);
            Utilidades.escribirDeportesTxt(archivoDeportes, listaDeportes, formatoDeportes);
            JOptionPane.showMessageDialog(null, "El archivo se creó correctamente");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al manipular el archivo");
        }
    }

    private void mostrarPantallaInicial(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        Button adminButton = new Button(bundle.getString("button.admin"));
        Button registerButton = new Button(bundle.getString("button.register"));

        adminButton.setOnAction(e -> mostrarPantallaAdministrador(stage));
        registerButton.setOnAction(e -> mostrarPantallaRegistro(stage));

        Button changeLanguageButton = new Button(bundle.getString("button.changeLanguage"));
        changeLanguageButton.setOnAction(e -> {
            if (currentLocale.getLanguage().equals("es")) {
                cambiarIdioma(new Locale("en", "US"), stage);  
            } else {
                cambiarIdioma(new Locale("es", "ES"), stage); 
            }
        });

        root.getChildren().addAll(adminButton, registerButton, changeLanguageButton);

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle(bundle.getString("app.title"));
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarPantallaRegistro(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");

        TextField emailField = new TextField();
        emailField.setPromptText("Correo Electrónico");

        TextField idField = new TextField();
        idField.setPromptText("ID");

        ComboBox<String> tipoMiembroBox = new ComboBox<>();
        tipoMiembroBox.getItems().addAll("Juvenil", "Adulto");
        tipoMiembroBox.setPromptText("Tipo de Miembro");

        ComboBox<String> deportesBox = new ComboBox<>(deportesObservableList);
        deportesBox.setPromptText("Selecciona un Deporte");

        Button registrarButton = new Button("Registrar");
        Label registroLabel = new Label();

        registrarButton.setOnAction(e -> {
            String nombre = nombreField.getText();
            String email = emailField.getText();
            String id = idField.getText();
            String tipo = tipoMiembroBox.getValue();
            String deporteSeleccionado = deportesBox.getValue();

            if (nombre.isEmpty() || email.isEmpty() || id.isEmpty() || tipo == null || deporteSeleccionado == null) {
                registroLabel.setText("Por favor, completa todos los campos.");
                LoggerManager.logWarning("Intento de registro fallido: Campos vacios.");


            } else {
                MiembroClub miembro;
                if (tipo.equals("Juvenil")) {
                    miembro = new Juvenil(nombre, email, id);
                } else {
                    miembro = new Adulto(nombre, email, id);
                }

                Deporte deporte = club.obtenerDeportePorNombre(deporteSeleccionado);

                if (deporte != null && miembro.puedeInscribirse(deporte)) {
                    Entrenador entrenador = deporte.getEntrenadores().get(0);
                    SesionEntrenamiento sesion = entrenador.getSesiones().get(0);
                    sesion.setEstado(EstadoSesion.PROGRAMADA);

                    club.registrarMiembro(miembro);
                    registroLabel.setText("Miembro registrado e inscrito en las sesiones de entrenamiento de " + deporte.getNombre());
                    LoggerManager.logInfo("Registro exitoso del miembro: " + nombre + " en el deporte " + deporteSeleccionado);

                } else {
                    registroLabel.setText("No puedes inscribirte en este deporte.");
                    LoggerManager.logError("Error al registrar al miembro: " + nombre + ". No se puede inscribir en el deporte " + deporteSeleccionado);

                }
    
            }
        });

        Button backButton = new Button("Atrás");
        backButton.setOnAction(e -> mostrarPantallaInicial(stage));

        root.getChildren().addAll(
            new Label("Registrar Miembro:"),
            nombreField, emailField, idField, tipoMiembroBox, deportesBox, registrarButton, registroLabel,
            backButton
        );

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
    }

    private void mostrarPantallaAdministrador(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        ComboBox<String> deportesBox = new ComboBox<>(deportesObservableList);
        deportesBox.setPromptText(bundle.getString("label.selectDeporte"));

        Button crearDeporteButton = new Button(bundle.getString("button.createSport"));
        Button crearSesionButton = new Button(bundle.getString("button.createSession"));
        Button editarSesionButton = new Button(bundle.getString("button.editSession"));
        Button eliminarSesionButton = new Button(bundle.getString("button.deleteSession"));

        crearDeporteButton.setOnAction(e -> crearDeporte());
        crearSesionButton.setOnAction(e -> crearSesion(deportesBox.getValue()));
        editarSesionButton.setOnAction(e -> editarSesion(deportesBox.getValue()));
        eliminarSesionButton.setOnAction(e -> eliminarSesion(deportesBox.getValue()));

        Button backButton = new Button(bundle.getString("button.back"));
        backButton.setOnAction(e -> mostrarPantallaInicial(stage));

        root.getChildren().addAll(
            new Label(bundle.getString("label.manageSessions")),
            deportesBox, crearDeporteButton, crearSesionButton, editarSesionButton, eliminarSesionButton,
            backButton
        );

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
    }

    private void crearDeporte() {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre del Deporte");

        TextField descripcionField = new TextField();
        descripcionField.setPromptText("Descripción del Deporte");

        ComboBox<NivelDificultad> dificultadBox = new ComboBox<>();
        dificultadBox.getItems().addAll(NivelDificultad.BAJO, NivelDificultad.MEDIO, NivelDificultad.ALTO);
        dificultadBox.setPromptText("Nivel de Dificultad");

        TextField entrenadorField = new TextField();
        entrenadorField.setPromptText("Nombre del Entrenador");

        Button crearButton = new Button("Crear Deporte");

        crearButton.setOnAction(e -> {
            String nombreDeporte = nombreField.getText();
            String descripcion = descripcionField.getText();
            NivelDificultad dificultad = dificultadBox.getValue();
            String nombreEntrenador = entrenadorField.getText();

            if (nombreDeporte.isEmpty() || descripcion.isEmpty() || dificultad == null || nombreEntrenador.isEmpty()) {
                mostrarAlerta("Error", "Por favor, completa todos los campos.");
            } else {
                Deporte nuevoDeporte = new Deporte(nombreDeporte, descripcion, dificultad);
                Entrenador entrenador = new Entrenador(nombreEntrenador, nuevoDeporte);
                nuevoDeporte.agregarEntrenador(entrenador);
                club.registrarDeporte(nuevoDeporte);

                deportesObservableList.add(nuevoDeporte.getNombre());

                mostrarAlerta("Éxito", "Deporte creado exitosamente.");
                stage.close();
            }
        });

        root.getChildren().addAll(
            new Label("Crear Nuevo Deporte:"),
            nombreField, descripcionField, dificultadBox, entrenadorField, crearButton
        );

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }


    private void crearSesion(String deporteNombre) {
        if (deporteNombre == null) {
            mostrarAlerta("Error", "Selecciona un deporte antes de crear una sesión.");
            return;
        }

        Deporte deporte = club.obtenerDeportePorNombre(deporteNombre);
        if (deporte.getEntrenadores().isEmpty()) {
            mostrarAlerta("Error", "No hay entrenadores disponibles para este deporte.");
            return;
        }

        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        DatePicker datePicker = new DatePicker();
        TextField duracionField = new TextField();
        duracionField.setPromptText("Duración en minutos");

        Button crearButton = new Button("Crear Sesión");

        crearButton.setOnAction(e -> {
            LocalDateTime fecha = datePicker.getValue().atStartOfDay();
            int duracion = Integer.parseInt(duracionField.getText());

            Entrenador entrenador = deporte.getEntrenadores().get(0);
            SesionEntrenamiento nuevaSesion = new SesionEntrenamiento(fecha, duracion, EstadoSesion.PROGRAMADA, deporte, entrenador);
            entrenador.agregarSesion(nuevaSesion);

            mostrarAlerta("Éxito", "Sesión creada exitosamente.");
            stage.close();
        });

        root.getChildren().addAll(
            new Label("Crear Nueva Sesión:"),
            new Label("Fecha de la Sesión:"), datePicker,
            duracionField,
            crearButton
        );

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void editarSesion(String deporteNombre) {
        if (deporteNombre == null) {
            mostrarAlerta("Error", "Selecciona un deporte antes de editar una sesión.");
            return;
        }

        Deporte deporte = club.obtenerDeportePorNombre(deporteNombre);
        if (deporte.getEntrenadores().isEmpty()) {
            mostrarAlerta("Error", "No hay entrenadores disponibles para este deporte.");
            return;
        }

        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        List<SesionEntrenamiento> sesiones = deporte.getEntrenadores().get(0).getSesiones();

        ComboBox<SesionEntrenamiento> sesionBox = new ComboBox<>();
        sesionBox.getItems().addAll(sesiones);
        sesionBox.setPromptText("Selecciona una Sesión");

        DatePicker datePicker = new DatePicker();
        TextField duracionField = new TextField();
        duracionField.setPromptText("Nueva Duración en minutos");

        Button editarButton = new Button("Editar Sesión");

        editarButton.setOnAction(e -> {
            SesionEntrenamiento sesionSeleccionada = sesionBox.getValue();
            if (sesionSeleccionada != null) {
                sesionSeleccionada.setFecha(datePicker.getValue().atStartOfDay());
                sesionSeleccionada.setDuracion(Integer.parseInt(duracionField.getText()));

                mostrarAlerta("Éxito", "Sesión editada exitosamente.");
                stage.close();
            } else {
                mostrarAlerta("Error", "Selecciona una sesión para editar.");
            }
        });

        root.getChildren().addAll(
            new Label("Editar Sesión:"),
            sesionBox,
            new Label("Nueva Fecha de la Sesión:"), datePicker,
            duracionField,
            editarButton
        );

        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
    }
    private void eliminarSesion(String deporteNombre) {
        if (deporteNombre == null) {
            mostrarAlerta("Error", "Selecciona un deporte antes de eliminar una sesión.");
            return;
        }

        Deporte deporte = club.obtenerDeportePorNombre(deporteNombre);
        if (deporte.getEntrenadores().isEmpty()) {
            mostrarAlerta("Error", "No hay entrenadores disponibles para este deporte.");
            return;
        }

        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        List<SesionEntrenamiento> sesiones = deporte.getEntrenadores().get(0).getSesiones();

        ComboBox<SesionEntrenamiento> sesionBox = new ComboBox<>();
        sesionBox.getItems().addAll(sesiones);
        sesionBox.setPromptText("Selecciona una Sesión");

        Button eliminarButton = new Button("Eliminar Sesión");

        eliminarButton.setOnAction(e -> {
            SesionEntrenamiento sesionSeleccionada = sesionBox.getValue();
            if (sesionSeleccionada != null) {
                deporte.getEntrenadores().get(0).getSesiones().remove(sesionSeleccionada);

                mostrarAlerta("Éxito", "Sesión eliminada exitosamente.");
                stage.close();
            } else {
                mostrarAlerta("Error", "Selecciona una sesión para eliminar.");
            }
        });

        root.getChildren().addAll(
            new Label("Eliminar Sesión:"),
            sesionBox,
            eliminarButton
        );

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}