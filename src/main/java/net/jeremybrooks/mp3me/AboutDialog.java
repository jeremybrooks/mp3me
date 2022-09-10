/*
 * Created by JFormDesigner on Mon Sep 05 18:00:42 PDT 2022
 */

package net.jeremybrooks.mp3me;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * @author Jeremy Brooks
 */
public class AboutDialog extends JDialog {
    public AboutDialog(Window owner) {
        super(owner);
        initComponents();
    }

    private void ok() {
        setVisible(false);
        dispose();
    }

    private String readLicense() {
        String license;
        try {
            URL url = AboutDialog.class.getResource("/LICENSE");
            if (url == null) {
                license = "GNU GPLv3";
            } else {
                license = Files.readString(Paths.get(url.toURI()));
            }
        } catch (Exception e) {
            license = "GNU GPLv3";
        }
        return license;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("about");
        var dialogPane = new JPanel();
        var tabbedPane1 = new JTabbedPane();
        var scrollPane2 = new JScrollPane();
        var txtAbout = new JTextArea();
        var scrollPane1 = new JScrollPane();
        var txtLicense = new JTextArea();
        var buttonBar = new JPanel();
        var okButton = new JButton();

        //======== this ========
        setTitle("About");
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== tabbedPane1 ========
            {

                //======== scrollPane2 ========
                {

                    //---- txtAbout ----
                    txtAbout.setEditable(false);
                    txtAbout.setText("A utility to convert audio files to mp3.\n\nTo use mp3me, you must have ffmpeg installed. By default, mp3me will look for ffmpeg at /usr/bin/ffmpeg. If you have it installed in a different location, open the Preferences dialog and set the correct location.\n\nCoding: Jeremy Brooks\nSource Code: https://github.com/jeremybrooks/mp3me\nLibraries:\n    Log4j (https://logging.apache.org/log4j/2.x/)\n    pressplay (https://github.com/jeremybrooks/pressplay) \n\nInstaller built using Install4j (https://www.ej-technologies.com/products/install4j/overview.html)");
                    txtAbout.setWrapStyleWord(true);
                    txtAbout.setLineWrap(true);
                    scrollPane2.setViewportView(txtAbout);
                }
                tabbedPane1.addTab(bundle.getString("AboutDialog.scrollPane2.tab.title"), scrollPane2);

                //======== scrollPane1 ========
                {

                    //---- txtLicense ----
                    txtLicense.setText(readLicense());
                    txtLicense.setCaretPosition(1);
                    txtLicense.setEditable(false);
                    scrollPane1.setViewportView(txtLicense);
                }
                tabbedPane1.addTab(bundle.getString("AboutDialog.scrollPane1.tab.title"), scrollPane1);
            }
            dialogPane.add(tabbedPane1, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- okButton ----
                okButton.setText(bundle.getString("AboutDialog.okButton.text"));
                okButton.addActionListener(e -> ok());
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.PAGE_END);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(675, 500);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
