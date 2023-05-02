package it.zammarchi.briscola.client.gui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AlertLauncher {
    /**
     * Show an alert, generally an information or an error alert.
     * @param alertType the type of the alert
     * @param msg the message to show
     */
    public static void showOkAlert(Alert.AlertType alertType, String msg) {
        Alert alert = new Alert(alertType,
                msg,
                ButtonType.OK);
        alert.show();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    /**
     * Show an alert when server is offline
     * @param msg
     */
    public static void showServerOfflineAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                "Il server sembra essere offline. Riprovare piu' tardi.\n\nDettagli: " + msg,
                ButtonType.CLOSE);
        alert.show();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        if (alert.getResult() == ButtonType.CLOSE) {
            alert.close();
            // System.exit(0);
        }
    }
}
