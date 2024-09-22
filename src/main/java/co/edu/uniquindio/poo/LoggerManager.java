package co.edu.uniquindio.poo;

import java.io.IOException;
import java.util.logging.*;

public class LoggerManager {

    private static final Logger logger = Logger.getLogger(LoggerManager.class.getName());
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("registro.log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL); 

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al configurar el logger", e);
        }
    }

    public static void logInfo(String mensaje) {
        logger.log(Level.INFO, mensaje);
    }

    public static void logWarning(String mensaje) {
        logger.log(Level.WARNING, mensaje);
    }

    public static void logError(String mensaje) {
        logger.log(Level.SEVERE, mensaje);
    }

    public static void logError(String mensaje, Exception ex) {
        logger.log(Level.SEVERE, mensaje, ex);
    }
}
