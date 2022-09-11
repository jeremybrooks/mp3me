/*
 * Created by JFormDesigner on Fri Apr 22 19:23:03 PDT 2022
 */

package net.jeremybrooks.mp3me;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.desktop.QuitResponse;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Jeremy Brooks
 */
public class MainWindow extends JFrame {
//    private final Settings settings = App.getSettings();
    private final DefaultListModel<ConversionJob> listModel = new DefaultListModel<>();
    private static final Logger logger = LogManager.getLogger();
    private boolean busy = false;
    private static MainWindow mainWindow;

    public MainWindow() {
        initComponents();
        list1.setModel(listModel);
        list1.setCellRenderer(new ConversionJobListRenderer());
        new FileDrop(scrollPane1, this::addDirectoriesToList);
        Settings settings = App.getSettings();
        setSize(settings.getWindowWidth(), settings.getWindowHeight());
        setLocation(settings.getWindowPositionX(), settings.getWindowPositionY());
        mainWindow = this;

        // on macOS, hide the Preferences, About, and Quit menus
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            mnuFile.setVisible(false);
        }
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    private void showDirectorySelectionDialog() {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setMultiSelectionEnabled(true);
        jfc.setDialogTitle("Choose source directory");
        int result = jfc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            addDirectoriesToList(jfc.getSelectedFiles());
        }
    }

    private void addDirectoriesToList(File[] files) {
        Arrays.stream(files).filter(File::isDirectory).sorted(Comparator.comparing(File::getAbsolutePath))
                .forEach(f -> {
                    ConversionJob job = new ConversionJob();
                    job.setSourcePath(f.toPath());
                    job.setStatus(ConversionJob.Status.PENDING);
                    try {
                        Files.walkFileTree(job.getSourcePath(), new FileCounter(job, Long.MAX_VALUE));
                    } catch (IOException e) {
                        logger.error("Error counting files in {}", job.getSourcePath(), e);
                    }
                    listModel.add(listModel.size(), job);
                });
    }


    public void buttonsEnabled(boolean enabled) {
        btnGo.setEnabled(enabled);
        mnuFile.setEnabled(enabled);
        Arrays.stream(mnuFile.getMenuComponents()).forEach(component -> component.setEnabled(enabled));
        mnuJobs.setEnabled(enabled);
        Arrays.stream(mnuJobs.getMenuComponents()).forEach(component -> component.setEnabled(enabled));
        busy = !enabled;
    }

    private void btnGo() {
        if (listModel.size() == 0) {
            JOptionPane.showMessageDialog(this, "No conversion jobs in list.", "No Jobs",
                    JOptionPane.ERROR_MESSAGE);
        } else if (App.getSettings().getDestination().length() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No destination selected.\nPlease specify a destination in Preferences.",
                    "No Destination",
                    JOptionPane.ERROR_MESSAGE);

        } else {
            buttonsEnabled(false);
            List<ConversionJob> list = Collections.list(listModel.elements());
            new ConversionWorker(list, Long.MAX_VALUE, this).execute();
        }
    }

    void updateJob(Integer index) {
        ConversionJob job = listModel.remove(index);
        listModel.add(index, job);
    }

    private void thisComponentResized() {
        Settings settings = App.getSettings();
        settings.setWindowWidth(getWidth());
        settings.setWindowHeight(getHeight());
        App.saveSettings(settings);
    }

    private void thisComponentMoved() {
        Settings settings = App.getSettings();
        settings.setWindowPositionX(getX());
        settings.setWindowPositionY(getY());
        App.saveSettings(settings);
    }

    private void mnuSettings() {
        new SettingsDialog(this).setVisible(true);
    }

    private void mnuQuit() {
        confirmAndExit(null);
    }

    public void confirmAndExit(QuitResponse quitResponse) {
        int exit = JOptionPane.YES_OPTION;
        if (busy) {
            exit = JOptionPane.showConfirmDialog(this,
                    "Conversion jobs are in progress. Do you want to quit?",
                    "Quit?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
        }
        if (exit == JOptionPane.YES_OPTION) {
            if (quitResponse != null) {
                quitResponse.performQuit();
            } else {
                System.exit(0);
            }
        } else {
            if (quitResponse != null) {
                quitResponse.cancelQuit();
            }
        }
    }

    private void mnuAddJob() {
        showDirectorySelectionDialog();
    }

    private void mnuJobsMenuSelected() {
        mnuDeleteJob.setEnabled(list1.getSelectedIndex() != -1);
        mnuClear.setEnabled(listModel.size() > 0);
    }

    private void mnuDeleteJob() {
        int index = list1.getSelectedIndex();
        if (index != -1) {
            listModel.remove(index);
        }
    }

    private void mnuClear() {
        listModel.clear();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("main");
        menuBar1 = new JMenuBar();
        mnuFile = new JMenu();
        mnuSettings = new JMenuItem();
        mnuQuit = new JMenuItem();
        mnuJobs = new JMenu();
        mnuAddJob = new JMenuItem();
        mnuDeleteJob = new JMenuItem();
        mnuClear = new JMenuItem();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        list1 = new JList<>();
        panel3 = new JPanel();
        btnGo = new JButton();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/icons/mp3me_icon-256.png")).getImage());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                thisComponentMoved();
            }
            @Override
            public void componentResized(ComponentEvent e) {
                thisComponentResized();
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

        //======== menuBar1 ========
        {

            //======== mnuFile ========
            {
                mnuFile.setText(bundle.getString("MainWindow.mnuFile.text"));

                //---- mnuSettings ----
                mnuSettings.setText(bundle.getString("MainWindow.mnuSettings.text"));
                mnuSettings.addActionListener(e -> mnuSettings());
                mnuFile.add(mnuSettings);

                //---- mnuQuit ----
                mnuQuit.setText(bundle.getString("MainWindow.mnuQuit.text"));
                mnuQuit.addActionListener(e -> mnuQuit());
                mnuFile.add(mnuQuit);
            }
            menuBar1.add(mnuFile);

            //======== mnuJobs ========
            {
                mnuJobs.setText(bundle.getString("MainWindow.mnuJobs.text"));
                mnuJobs.addMenuListener(new MenuListener() {
                    @Override
                    public void menuCanceled(MenuEvent e) {}
                    @Override
                    public void menuDeselected(MenuEvent e) {}
                    @Override
                    public void menuSelected(MenuEvent e) {
                        mnuJobsMenuSelected();
                    }
                });

                //---- mnuAddJob ----
                mnuAddJob.setText(bundle.getString("MainWindow.mnuAddJob.text"));
                mnuAddJob.addActionListener(e -> mnuAddJob());
                mnuJobs.add(mnuAddJob);

                //---- mnuDeleteJob ----
                mnuDeleteJob.setText(bundle.getString("MainWindow.mnuDeleteJob.text"));
                mnuDeleteJob.addActionListener(e -> mnuDeleteJob());
                mnuJobs.add(mnuDeleteJob);

                //---- mnuClear ----
                mnuClear.setText(bundle.getString("MainWindow.mnuClear.text"));
                mnuClear.addActionListener(e -> mnuClear());
                mnuJobs.add(mnuClear);
            }
            menuBar1.add(mnuJobs);
        }
        setJMenuBar(menuBar1);

        //---- label1 ----
        label1.setText(bundle.getString("MainWindow.label1.text"));
        contentPane.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
            new Insets(3, 3, 8, 8), 0, 0));

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane1.setViewportView(list1);
        }
        contentPane.add(scrollPane1, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 3, 10, 3), 0, 0));

        //======== panel3 ========
        {
            panel3.setLayout(new FlowLayout(FlowLayout.RIGHT));

            //---- btnGo ----
            btnGo.setText(bundle.getString("MainWindow.btnGo.text"));
            btnGo.addActionListener(e -> btnGo());
            panel3.add(btnGo);
        }
        contentPane.add(panel3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu mnuFile;
    private JMenuItem mnuSettings;
    private JMenuItem mnuQuit;
    private JMenu mnuJobs;
    private JMenuItem mnuAddJob;
    private JMenuItem mnuDeleteJob;
    private JMenuItem mnuClear;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList<ConversionJob> list1;
    private JPanel panel3;
    private JButton btnGo;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
