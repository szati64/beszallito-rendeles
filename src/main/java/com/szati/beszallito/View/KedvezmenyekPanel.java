package com.szati.beszallito.View;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.szati.beszallito.Controller.Controller;
import com.szati.beszallito.Model.Kedvezmeny;

public class KedvezmenyekPanel extends JPanel {

    public KedvezmenyekPanel(Controller controller) {
        this.setLayout(new BorderLayout());
        JTextArea kedvezmenyekArea = new JTextArea();
        JScrollPane kedvezmenyekScroll = new JScrollPane(kedvezmenyekArea);
        
        StringBuilder sb = new StringBuilder();
        
         for (Kedvezmeny k : controller.getModel().getKedvezmenyek()) {
             if (k.isAktiv()) {
                sb.append(k.getLeiras());
                sb.append("\n\n");
             }
         }
        
        kedvezmenyekArea.setText(sb.toString());
        kedvezmenyekArea.setLineWrap(true);
        Font font = new Font(kedvezmenyekArea.getFont().getFontName(),
                Font.PLAIN, 12);
        kedvezmenyekArea.setFont(font);
        kedvezmenyekArea.setEditable(false);
        
        this.add(kedvezmenyekScroll, BorderLayout.CENTER);
    }
}
