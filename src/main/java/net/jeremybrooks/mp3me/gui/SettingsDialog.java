/*
 * Created by JFormDesigner on Fri Sep 02 22:17:37 PDT 2022
 */

package net.jeremybrooks.mp3me.gui;

import net.jeremybrooks.mp3me.App;
import net.jeremybrooks.mp3me.model.Settings;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.Serial;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * @author Jeremy Brooks
 */
public class SettingsDialog extends JDialog {

    @Serial
    private static final long serialVersionUID = 7047110326808972160L;
    private final Settings settings;

    public SettingsDialog(Frame owner) {
        super(owner, true);
        initComponents();
        settings = App.getSettings();
        txtFfmpegBinary.setText(settings.getFfmpegBinary());
        txtDestination.setText(settings.getDestination());
        cmbBitrate.setSelectedItem(settings.getBitrate());
        cmbAiff.setSelectedItem(settings.getFileTypeActionMap().get("aiff"));
        cmbFlac.setSelectedItem(settings.getFileTypeActionMap().get("flac"));
        cmbM4a.setSelectedItem(settings.getFileTypeActionMap().get("m4a"));
        cmbM4v.setSelectedItem(settings.getFileTypeActionMap().get("m4v"));
        cmbMp3.setSelectedItem(settings.getFileTypeActionMap().get("mp3"));
        cmbMp4.setSelectedItem(settings.getFileTypeActionMap().get("mp4"));
        cmbOgg.setSelectedItem(settings.getFileTypeActionMap().get("ogg"));
        cmbPdf.setSelectedItem(settings.getFileTypeActionMap().get("pdf"));
        cmbWav.setSelectedItem(settings.getFileTypeActionMap().get("wav"));
        cmbOther.setSelectedItem(settings.getFileTypeActionMap().get("other"));
    }

    private void cancel() {
        setVisible(false);
        dispose();
    }

    private void ok() {
        Path binary = Paths.get(txtFfmpegBinary.getText().trim());
        Path dest = Paths.get(txtDestination.getText().trim());
        if (!Files.exists(binary) || !Files.isRegularFile(binary)) {
            JOptionPane.showMessageDialog(this,
                    "Invalid ffmpeg binary. Please provide a path to the binary.",
                    "Invalid Binary",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!Files.exists(dest) || !Files.isDirectory(dest)) {
            JOptionPane.showMessageDialog(this,
                    "Invalid destination directory.",
                    "Invalid Directory",
                    JOptionPane.ERROR_MESSAGE);

        } else {
            settings.setFfmpegBinary(txtFfmpegBinary.getText().trim());
            settings.setDestination(txtDestination.getText().trim());
            settings.setBitrate(cmbBitrate.getSelectedItem().toString());
            settings.getFileTypeActionMap().put("m4a", cmbM4a.getSelectedItem().toString());
            settings.getFileTypeActionMap().put("m4v", cmbM4v.getSelectedItem().toString());
            settings.getFileTypeActionMap().put("mp3", cmbMp3.getSelectedItem().toString());
            settings.getFileTypeActionMap().put("mp4", cmbMp4.getSelectedItem().toString());
            settings.getFileTypeActionMap().put("pdf", cmbPdf.getSelectedItem().toString());
            settings.getFileTypeActionMap().put("wav", cmbWav.getSelectedItem().toString());
            settings.getFileTypeActionMap().put("other", cmbOther.getSelectedItem().toString());
            App.saveSettings(settings);
            setVisible(false);
            dispose();
        }
    }

    private void btnBrowse() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Select FFMPEG Binary");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(false);
        int option = jfc.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            txtFfmpegBinary.setText(jfc.getSelectedFile().getAbsolutePath());
        }
    }

    private void btnBrowseDestination() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Select Destination");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setMultiSelectionEnabled(false);
        int option = jfc.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            txtDestination.setText(jfc.getSelectedFile().getAbsolutePath());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("settings");
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        txtFfmpegBinary = new JTextField();
        panel1 = new JPanel();
        btnBrowse = new JButton();
        label10 = new JLabel();
        txtDestination = new JTextField();
        panel3 = new JPanel();
        btnBrowseDestination = new JButton();
        label2 = new JLabel();
        cmbBitrate = new JComboBox<>();
        panel2 = new JPanel();
        label11 = new JLabel();
        cmbAiff = new JComboBox<>();
        label12 = new JLabel();
        cmbFlac = new JComboBox<>();
        label3 = new JLabel();
        cmbM4a = new JComboBox<>();
        label4 = new JLabel();
        cmbM4v = new JComboBox<>();
        label5 = new JLabel();
        cmbMp3 = new JComboBox<>();
        label6 = new JLabel();
        cmbMp4 = new JComboBox<>();
        label13 = new JLabel();
        cmbOgg = new JComboBox<>();
        label7 = new JLabel();
        cmbPdf = new JComboBox<>();
        label8 = new JLabel();
        cmbWav = new JComboBox<>();
        label9 = new JLabel();
        cmbOther = new JComboBox<>();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setTitle("Settings");
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText(bundle.getString("SettingsDialog.label1.text"));
                contentPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(txtFfmpegBinary, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== panel1 ========
                {
                    panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));

                    //---- btnBrowse ----
                    btnBrowse.setText(bundle.getString("SettingsDialog.btnBrowse.text"));
                    btnBrowse.addActionListener(e -> btnBrowse());
                    panel1.add(btnBrowse);
                }
                contentPanel.add(panel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label10 ----
                label10.setText(bundle.getString("SettingsDialog.label10.text"));
                contentPanel.add(label10, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(3, 3, 8, 8), 0, 0));
                contentPanel.add(txtDestination, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== panel3 ========
                {
                    panel3.setLayout(new FlowLayout(FlowLayout.RIGHT));

                    //---- btnBrowseDestination ----
                    btnBrowseDestination.setText(bundle.getString("SettingsDialog.btnBrowseDestination.text"));
                    btnBrowseDestination.addActionListener(e -> btnBrowseDestination());
                    panel3.add(btnBrowseDestination);
                }
                contentPanel.add(panel3, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label2 ----
                label2.setText(bundle.getString("SettingsDialog.label2.text"));
                contentPanel.add(label2, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- cmbBitrate ----
                cmbBitrate.setModel(new DefaultComboBoxModel<>(new String[] {
                    "256k",
                    "192k",
                    "160k",
                    "128k"
                }));
                contentPanel.add(cmbBitrate, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== panel2 ========
                {
                    panel2.setBorder(new TitledBorder(bundle.getString("SettingsDialog.panel2.border")));
                    panel2.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
                    ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                    ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                    ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                    //---- label11 ----
                    label11.setText(bundle.getString("SettingsDialog.label11.text"));
                    panel2.add(label11, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(3, 3, 8, 8), 0, 0));

                    //---- cmbAiff ----
                    cmbAiff.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Convert",
                        "Copy",
                        "Ignore"
                    }));
                    panel2.add(cmbAiff, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label12 ----
                    label12.setText(bundle.getString("SettingsDialog.label12.text"));
                    panel2.add(label12, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(3, 3, 8, 8), 0, 0));

                    //---- cmbFlac ----
                    cmbFlac.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Convert",
                        "Copy",
                        "Ignore"
                    }));
                    panel2.add(cmbFlac, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label3 ----
                    label3.setText(bundle.getString("SettingsDialog.label3.text"));
                    panel2.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(3, 3, 8, 8), 0, 0));

                    //---- cmbM4a ----
                    cmbM4a.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Convert",
                        "Copy",
                        "Ignore"
                    }));
                    panel2.add(cmbM4a, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label4 ----
                    label4.setText(bundle.getString("SettingsDialog.label4.text"));
                    panel2.add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(3, 3, 8, 8), 0, 0));

                    //---- cmbM4v ----
                    cmbM4v.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Convert",
                        "Copy",
                        "Ignore"
                    }));
                    panel2.add(cmbM4v, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label5 ----
                    label5.setText(bundle.getString("SettingsDialog.label5.text"));
                    panel2.add(label5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(3, 3, 8, 8), 0, 0));

                    //---- cmbMp3 ----
                    cmbMp3.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Convert",
                        "Copy",
                        "Ignore"
                    }));
                    panel2.add(cmbMp3, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label6 ----
                    label6.setText(bundle.getString("SettingsDialog.label6.text"));
                    panel2.add(label6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(3, 3, 8, 8), 0, 0));

                    //---- cmbMp4 ----
                    cmbMp4.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Convert",
                        "Copy",
                        "Ignore"
                    }));
                    panel2.add(cmbMp4, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label13 ----
                    label13.setText(bundle.getString("SettingsDialog.label13.text"));
                    panel2.add(label13, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(3, 3, 8, 8), 0, 0));

                    //---- cmbOgg ----
                    cmbOgg.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Convert",
                        "Copy",
                        "Ignore"
                    }));
                    panel2.add(cmbOgg, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label7 ----
                    label7.setText(bundle.getString("SettingsDialog.label7.text"));
                    panel2.add(label7, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(3, 3, 8, 8), 0, 0));

                    //---- cmbPdf ----
                    cmbPdf.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Copy",
                        "Ignore"
                    }));
                    panel2.add(cmbPdf, new GridBagConstraints(1, 7, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label8 ----
                    label8.setText(bundle.getString("SettingsDialog.label8.text"));
                    panel2.add(label8, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(3, 3, 8, 8), 0, 0));

                    //---- cmbWav ----
                    cmbWav.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Convert",
                        "Copy",
                        "Ignore"
                    }));
                    panel2.add(cmbWav, new GridBagConstraints(1, 8, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label9 ----
                    label9.setText(bundle.getString("SettingsDialog.label9.text"));
                    panel2.add(label9, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(3, 3, 3, 8), 0, 0));

                    //---- cmbOther ----
                    cmbOther.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Copy",
                        "Ignore"
                    }));
                    panel2.add(cmbOther, new GridBagConstraints(1, 9, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                contentPanel.add(panel2, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(3, 3, 8, 3), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText(bundle.getString("SettingsDialog.okButton.text"));
                okButton.addActionListener(e -> ok());
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText(bundle.getString("SettingsDialog.cancelButton.text"));
                cancelButton.addActionListener(e -> cancel());
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(600, 615);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JTextField txtFfmpegBinary;
    private JPanel panel1;
    private JButton btnBrowse;
    private JLabel label10;
    private JTextField txtDestination;
    private JPanel panel3;
    private JButton btnBrowseDestination;
    private JLabel label2;
    private JComboBox<String> cmbBitrate;
    private JPanel panel2;
    private JLabel label11;
    private JComboBox<String> cmbAiff;
    private JLabel label12;
    private JComboBox<String> cmbFlac;
    private JLabel label3;
    private JComboBox<String> cmbM4a;
    private JLabel label4;
    private JComboBox<String> cmbM4v;
    private JLabel label5;
    private JComboBox<String> cmbMp3;
    private JLabel label6;
    private JComboBox<String> cmbMp4;
    private JLabel label13;
    private JComboBox<String> cmbOgg;
    private JLabel label7;
    private JComboBox<String> cmbPdf;
    private JLabel label8;
    private JComboBox<String> cmbWav;
    private JLabel label9;
    private JComboBox<String> cmbOther;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
