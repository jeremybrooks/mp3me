/*
 * Created by JFormDesigner on Wed Apr 27 19:18:03 PDT 2022
 */

package net.jeremybrooks.mp3me;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

/**
 * @author Jeremy Brooks
 */
public class ConversionJobListRenderer extends JPanel implements ListCellRenderer<ConversionJob> {
    private static final ImageIcon ICON_PENDING = new ImageIcon(ConversionJobListRenderer.class.getResource("/ClockN.png"));
    private static final ImageIcon ICON_DONE = new ImageIcon(ConversionJobListRenderer.class.getResource("/GreenCheck.png"));
    private static final ImageIcon ICON_ERROR = new ImageIcon(ConversionJobListRenderer.class.getResource("/Error.png"));
    private static final List<ImageIcon> ICON_CLOCK = List.of(
            new ImageIcon(ConversionJobListRenderer.class.getResource("/ClockN.png")),
            new ImageIcon(ConversionJobListRenderer.class.getResource("/ClockNE.png")),
            new ImageIcon(ConversionJobListRenderer.class.getResource("/ClockE.png")),
            new ImageIcon(ConversionJobListRenderer.class.getResource("/ClockSE.png")),
            new ImageIcon(ConversionJobListRenderer.class.getResource("/ClockS.png")),
            new ImageIcon(ConversionJobListRenderer.class.getResource("/ClockSW.png")),
            new ImageIcon(ConversionJobListRenderer.class.getResource("/ClockW.png")),
            new ImageIcon(ConversionJobListRenderer.class.getResource("/ClockNW.png"))
    );
    public ConversionJobListRenderer() {
        initComponents();
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends ConversionJob> list, ConversionJob value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        switch (value.getStatus()) {
            case PENDING -> {
                lblPath.setText(value.getSourcePath().toString());
                lblIcon.setIcon(ICON_PENDING);
                lblCount.setText(String.format("%d files waiting", value.getFileCount()));
            }
            case ACTIVE -> {
                lblPath.setText(value.getMessage());
                lblIcon.setIcon(ICON_CLOCK.get(value.getClockIndex()));
                lblCount.setText(String.format("Converting file %d of %d", value.getFilesConverted() + 1, value.getFileCount()));
            }
            case DONE -> {
                lblPath.setText(value.getSourcePath().toString());
                if (value.isError()) {
                    lblIcon.setIcon(ICON_ERROR);
                    lblCount.setText("There were errors. Check the logs.");
                } else {
                    lblIcon.setIcon(ICON_DONE);
                    lblCount.setText(String.format("Converted %d files", value.getFileCount()));
                }
            }
        }
        if (isSelected) {
            setBackground(Color.YELLOW);
            setForeground(Color.WHITE);
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }
        return this;
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblIcon = new JLabel();
        lblPath = new JLabel();
        lblCount = new JLabel();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

        //---- lblIcon ----
        lblIcon.setIcon(new ImageIcon(getClass().getResource("/ClockN.png")));
        add(lblIcon, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- lblPath ----
        lblPath.setText("text");
        add(lblPath, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- lblCount ----
        lblCount.setText("100 Songs");
        add(lblCount, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblIcon;
    private JLabel lblPath;
    private JLabel lblCount;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
