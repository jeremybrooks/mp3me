package net.jeremybrooks.mp3me;

import java.awt.Desktop;

public class MacOSSetup {

    public MacOSSetup() {
        Desktop.getDesktop().setAboutHandler(ae -> new AboutDialog(MainWindow.getMainWindow()).setVisible(true));

        Desktop.getDesktop().setQuitHandler((qe, qr) -> {
            // todo make the user confirm if busy
            System.exit(0);
        });

        Desktop.getDesktop().setPreferencesHandler(pe -> new SettingsDialog(MainWindow.getMainWindow()).setVisible(true));
    }
}