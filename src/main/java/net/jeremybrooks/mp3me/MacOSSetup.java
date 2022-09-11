package net.jeremybrooks.mp3me;

import java.awt.Desktop;

public class MacOSSetup {

    public MacOSSetup() {
        Desktop.getDesktop().setAboutHandler(ae -> new AboutDialog(MainWindow.getMainWindow()).setVisible(true));

        Desktop.getDesktop().setQuitHandler((qe, qr) -> {
            MainWindow.getMainWindow().confirmAndExit(qr);
        });

        Desktop.getDesktop().setPreferencesHandler(pe -> new SettingsDialog(MainWindow.getMainWindow()).setVisible(true));
    }
}
