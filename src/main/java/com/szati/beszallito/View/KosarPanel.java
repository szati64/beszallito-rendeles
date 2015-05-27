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
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public final class KosarPanel extends JPanel {
    private final JLabel osszegzes;
    private final JTable table;
    Rendeles jelenlegiRendeles;

    KosarPanel(View view, Controller controller) {      
        this.setLayout(new BorderLayout());

        JPanel kosarPanel = new JPanel();
        kosarPanel.setLayout(new BorderLayout());
        
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

        kosarPanel.add(sp, BorderLayout.CENTER);

        JPanel boxosPanel = new JPanel();
        JPanel lentiPanel = new JPanel();
        JButton urit = new JButton("Kiürít");
        JButton elkuld = new JButton("Elküld");

        boxosPanel.setLayout(new BoxLayout(boxosPanel, BoxLayout.X_AXIS));
        lentiPanel.setLayout(new BorderLayout());

        kosarPanel.add(lentiPanel, BorderLayout.SOUTH);
        lentiPanel.add(osszegzes, BorderLayout.CENTER);
        lentiPanel.add(boxosPanel, BorderLayout.EAST);
        boxosPanel.add(urit);
        boxosPanel.add(elkuld);

        JPanel foPanel = new JPanel(new CardLayout());
        foPanel.add(kosarPanel, "kosar");
        foPanel.add(new JLabel("kérem várjon...",
                SwingConstants.CENTER), "tolt");
        this.add(foPanel);
        
        urit.addActionListener((ActionEvent e) -> {
            SwingWorker<Void, Void>  worker = new SwingWorker<Void, Void>() {  
                @Override
                protected Void doInBackground() throws Exception {
                    CardLayout cl = (CardLayout)foPanel.getLayout();
                    cl.show(foPanel, "tolt");
                    
                    controller.getMostaniRendeles().clear();
                    updateTable(controller);
                    validate();
                    
                    cl.show(foPanel, "kosar");
                    return null;
                }
            };
            worker.execute();
        });

        
       
        elkuld.addActionListener((ActionEvent e) -> {
            
            SwingWorker<Void, Void>  worker = new SwingWorker<Void, Void>() {  
                @Override
                protected Void doInBackground() throws Exception {
                    CardLayout cl = (CardLayout)foPanel.getLayout();
                    cl.show(foPanel, "tolt");
                    
                    if (DAO.rendelesElkuldese(jelenlegiRendeles)) {
                        controller.getMostaniRendeles().clear();
                        updateTable(controller);
                        validate();

                        cl.show(foPanel, "kosar");
                        JOptionPane.showMessageDialog(view,
                                "A rendelés sikeresen elküldve!",
                                "Rendelés elküldve", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        cl.show(foPanel, "kosar");
                        JOptionPane.showMessageDialog(view,
                                "A rendelés elküldése közben probléma lépett fel!",
                                "Rendelés elküldése sikertelen", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    return null;
                }
            };
            worker.execute();
            
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
