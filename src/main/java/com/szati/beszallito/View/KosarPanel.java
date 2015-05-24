package com.szati.beszallito.View;

import com.szati.beszallito.Controller.Controller;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import com.szati.beszallito.Controller.DAO;
import com.szati.beszallito.Model.Rendeles;

public final class KosarPanel extends JPanel {
    private final JLabel osszegzes;
    private final JTable table;
    Rendeles jelenlegiRendeles;

    KosarPanel(Controller controller) {      
        this.setLayout(new BorderLayout());

        osszegzes = new JLabel();
        table = new JTable() {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                if (column == 0)
                    centerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
                else
                    centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                return centerRenderer;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.add(sp, BorderLayout.CENTER);

        JPanel boxosPanel = new JPanel();
        JPanel lentiPanel = new JPanel();
        JButton urit = new JButton("Kiürít");
        JButton elkuld = new JButton("Elküld");

        boxosPanel.setLayout(new BoxLayout(boxosPanel, BoxLayout.X_AXIS));
        lentiPanel.setLayout(new BorderLayout());

        this.add(lentiPanel, BorderLayout.SOUTH);
        lentiPanel.add(osszegzes, BorderLayout.CENTER);
        lentiPanel.add(boxosPanel, BorderLayout.EAST);
        boxosPanel.add(urit);
        boxosPanel.add(elkuld);            

        urit.addActionListener((ActionEvent e) -> {
            controller.getMostaniRendeles().clear();
            updateTable(controller);
            validate();
        });    
        
        elkuld.addActionListener((ActionEvent e) -> {
            DAO.rendelesElkuldese(jelenlegiRendeles);
            controller.getMostaniRendeles().clear();
            updateTable(controller);
            validate();
        });            
    }
    
    public void updateTable(Controller controller) {
        jelenlegiRendeles = controller.getRendelesFromMostaniRendeles();
        table.setModel(new RendelesTabla(controller,
                                         jelenlegiRendeles.getRendeltTermekek(),
                                         jelenlegiRendeles.getKedvezmenyek()));
        
        osszegzes.setText("A rendelés összértéke: " +
                View.penznemreValtas(jelenlegiRendeles.getAr()));
    }
}
