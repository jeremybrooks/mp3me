package net.jeremybrooks.mp3me;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.jeremybrooks.mp3me.gui.MainWindow;
import net.jeremybrooks.mp3me.model.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public class App {
    private static final Path CONFIG_DIR = Paths.get(System.getProperty("user.home"), ".mp3me");
    private static final Path CONFIG_FILE = Paths.get(CONFIG_DIR.toString(), "config.json");

    private static final Logger logger = LogManager.getLogger();
    public static String version;
    public static final String appName = "mp3me";

    public static void main( String[] args ) throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ImageRotator");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            try {
                Class.forName("net.jeremybrooks.mp3me.MacOSSetup").getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                logger.error("Could not find class.", e);
            }
            Package p = App.class.getPackage();
            if (p != null && p.getImplementationVersion() != null) {
                version = p.getImplementationVersion();
            } else {
                version = "0.0.0";
            }
        }

        if (!Files.exists(CONFIG_DIR)) {
            Files.createDirectories(CONFIG_DIR);
        }
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }

    public static Settings getSettings() {
        Settings settings;
        try {
            settings = new Gson().fromJson(Files.readString(CONFIG_FILE), Settings.class);
        } catch (Exception e) {
            logger.warn("Could not read config file {}, using default values", CONFIG_FILE, e);
            settings = new Settings();
            saveSettings(settings);
        }
        return settings;
    }

    public static void saveSettings(Settings settings) {
        try {
            Files.writeString(CONFIG_FILE, new GsonBuilder().setPrettyPrinting()
                    .create().toJson(settings));
        } catch (Exception e) {
            logger.error("Error saving config to {}", CONFIG_FILE, e);
        }
    }

}
