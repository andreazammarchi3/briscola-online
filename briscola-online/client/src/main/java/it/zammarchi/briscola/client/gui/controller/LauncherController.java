package it.zammarchi.briscola.client.gui.controller;

import it.zammarchi.briscola.client.gui.utils.AlertLauncher;
import it.zammarchi.briscola.client.gui.utils.SceneSwapper;
import it.zammarchi.briscola.client.gui.view.HomeView;
import it.zammarchi.briscola.client.gui.view.LauncherView;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import javafx.scene.control.Alert;

/**
 * Controller of the login window.
 */
public class LauncherController {
    private final LauncherView launcherView;

    public LauncherController(LauncherView launcherView) {
        this.launcherView = launcherView;
    }

    /**
     * When user enters his nickname, login to the server.
     * The server must be online, the nickname must not be blank and must not be already in use.
     */
    public void okNicknameButtonPress() {
        try {
            launcherView.getClient().createUser(launcherView.getNickname());
            new SceneSwapper().swapScene(
                    new HomeView(launcherView.getStage(), launcherView.getClient(), launcherView.getNickname()),
                    "HomeView.fxml",
                    launcherView.getStage()
            );
            SceneSwapper.setDim(launcherView.getStage(), 1000, 600);
        } catch (ConflictException e) {
            AlertLauncher.showOkAlert(Alert.AlertType.ERROR,
                    "Username gia' in uso. Sceglierne un altro.\n\nDettagli:\n" + e.getMessage());
        } catch (IllegalArgumentException e) {
            AlertLauncher.showOkAlert(Alert.AlertType.ERROR,
                    "Inserire un valido username.\n\nDettagli:\n" + e.getMessage());
        } catch (Exception e) {
            AlertLauncher.showOkAlert(Alert.AlertType.ERROR,
                    "Il server sembra non essere online.\n\nDettagli:\n" + e.getMessage());
        }
    }
}
