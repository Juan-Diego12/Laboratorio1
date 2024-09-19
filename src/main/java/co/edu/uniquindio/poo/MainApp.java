package co.edu.uniquindio.poo;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
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
    }

    private void cambiarIdioma(Locale nuevoLocale, Stage stage) {
        currentLocale = nuevoLocale;
        bundle = ResourceBundle.getBundle("locale.messages", currentLocale);
        mostrarPantallaInicial(stage); 
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
        nombreField.setPromptText(bundle.getString("label.name"));

        TextField emailField = new TextField();
        emailField.setPromptText(bundle.getString("label.email"));

        TextField idField = new TextField();
        idField.setPromptText(bundle.getString("label.id"));

        ComboBox<String> tipoMiembroBox = new ComboBox<>();
        tipoMiembroBox.getItems().addAll(bundle.getString("label.juvenil"), bundle.getString("label.adulto"));
        tipoMiembroBox.setPromptText(bundle.getString("label.memberType"));

        ComboBox<String> deportesBox = new ComboBox<>(deportesObservableList);
        deportesBox.setPromptText(bundle.getString("label.selectDeporte"));

        Button registrarButton = new Button(bundle.getString("button.register"));
        Label registroLabel = new Label();

        registrarButton.setOnAction(e -> {
            String nombre = nombreField.getText();
            String email = emailField.getText();
            String id = idField.getText();
            String tipo = tipoMiembroBox.getValue();
            String deporteSeleccionado = deportesBox.getValue();

            if (nombre.isEmpty() || email.isEmpty() || id.isEmpty() || tipo == null || deporteSeleccionado == null) {
                registroLabel.setText(bundle.getString("message.fillAllFields"));
            } else {
                MiembroClub miembro;
                if (tipo.equals(bundle.getString("label.juvenil"))) {
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
                    registroLabel.setText(bundle.getString("message.registerSuccess") + " " + deporte.getNombre());
                } else {
                    registroLabel.setText(bundle.getString("message.cannotRegister"));
                }
            }
        });

        Button backButton = new Button(bundle.getString("button.back"));
        backButton.setOnAction(e -> mostrarPantallaInicial(stage));

        root.getChildren().addAll(
            new Label(bundle.getString("label.registerMember")),
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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString("button.createSport"));
        dialog.setHeaderText(bundle.getString("message.enterSportName"));
        dialog.setContentText(bundle.getString("label.name") + ":");

        dialog.showAndWait().ifPresent(deporteNombre -> {
            if (!deporteNombre.isEmpty()) {
                Deporte nuevoDeporte = new Deporte(deporteNombre, "Descripción", NivelDificultad.BAJO);
                club.registrarDeporte(nuevoDeporte);
                deportesObservableList.add(deporteNombre);
                mostrarAlerta(bundle.getString("button.createSport"), bundle.getString("message.sportCreated"));
            } else {
                mostrarAlerta(bundle.getString("button.createSport"), bundle.getString("message.enterSportName"));
            }
        });
    }

    private void crearSesion(String deporteNombre) {
        Deporte deporte = club.obtenerDeportePorNombre(deporteNombre);
        Entrenador entrenador = deporte.getEntrenadores().get(0);

        if (deporte != null) {
            SesionEntrenamiento sesion = new SesionEntrenamiento(LocalDateTime.now(), 60, EstadoSesion.PROGRAMADA, deporte, entrenador);
            deporte.getEntrenadores().get(0).agregarSesion(sesion);
            mostrarAlerta(bundle.getString("button.createSession"), bundle.getString("message.sessionCreated"));
        } else {
            mostrarAlerta(bundle.getString("button.createSession"), bundle.getString("message.selectSport"));
        }
    }

    private void editarSesion(String deporteNombre) {
        Deporte deporte = club.obtenerDeportePorNombre(deporteNombre);
        if (deporte != null) {
            SesionEntrenamiento sesion = deporte.getEntrenadores().get(0).getSesiones().get(0);
            sesion.setDuracion(90); 
            mostrarAlerta(bundle.getString("button.editSession"), bundle.getString("message.sessionEdited"));
        } else {
            mostrarAlerta(bundle.getString("button.editSession"), bundle.getString("message.selectSport"));
        }
    }

    private void eliminarSesion(String deporteNombre) {
        Deporte deporte = club.obtenerDeportePorNombre(deporteNombre);
        if (deporte != null) {
            deporte.getEntrenadores().get(0).getSesiones().remove(0);
            mostrarAlerta(bundle.getString("button.deleteSession"), bundle.getString("message.sessionDeleted"));
        } else {
            mostrarAlerta(bundle.getString("button.deleteSession"), bundle.getString("message.selectSport"));
        }
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
