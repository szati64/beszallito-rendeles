package com.szati.beszallito.View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import com.szati.beszallito.Controller.DAO;
import com.szati.beszallito.Model.Rendeles;
import com.szati.beszallito.Controller.Controller;
import javax.swing.ListSelectionModel;

public class RendelesekPanel extends JPanel {
    private final JTable rendelesekTabla;
    
    public RendelesekPanel(Controller controller) {
        this.setLayout(new CardLayout());
        JPanel ez = (JPanel) this;
        
        //rendelesek card
        JScrollPane rendelesekScroll = new JScrollPane();
        rendelesekTabla = new JTable() {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                if (column == 2)
                    centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                else
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                return centerRenderer;
            }
        };
     
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        rendelesekTabla.setDefaultRenderer(Timestamp.class, centerRenderer);
        rendelesekTabla.setDefaultRenderer(Integer.class, centerRenderer);
        
        rendelesekTabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        rendelesekScroll.setViewportView(rendelesekTabla);
        
        this.add(rendelesekScroll, "rendelesek");
        
        //rendeltTermekek card
        JPanel rendeltTermekekPanel = new JPanel();
        rendeltTermekekPanel.setLayout(new BorderLayout());
        
        JButton visszaGomb = new JButton("Vissza");
        
        JScrollPane rendeltTermekekScroll = new JScrollPane();
        //JTable rendeltTermekekTable = new JTable();
        
        JTable rendeltTermekekTable = new JTable() {
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
        
        rendeltTermekekTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rendeltTermekekScroll.setViewportView(rendeltTermekekTable);
        
        rendeltTermekekPanel.add(rendeltTermekekScroll, BorderLayout.CENTER);
        rendeltTermekekPanel.add(visszaGomb, BorderLayout.SOUTH);
        
        this.add(rendeltTermekekPanel, "rendeltTermekek");
        
        rendelesekTabla.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (rendelesekTabla.getSelectedRow() != -1) {
                
                Rendeles r = (Rendeles)((RendelesekTabla)rendelesekTabla.getModel())
                                .getRendeles(rendelesekTabla.getSelectedRow());
                
                rendeltTermekekTable.setModel(new RendelesTabla(
                        controller,
                        DAO.getRendeltTermekek(controller, r.getId()),
                        DAO.getRendelesKedvezmenyek(r.getId())));
                
                rendelesekTabla.clearSelection();
                
                CardLayout cl = (CardLayout)getLayout();
                cl.show(ez, "rendeltTermekek");
            }
        });
        
        visszaGomb.addActionListener((ActionEvent ae) -> {
            CardLayout cl = (CardLayout)getLayout();
            cl.show(ez, "rendelesek");
        });
    }
    
    public void updateTable() {
        rendelesekTabla.setModel(new RendelesekTabla(
                DAO.getRendelesek()));
    }
            
    
    static class RendelesekTabla extends AbstractTableModel {
        private final String[] columnNames = {"azonosító",
                                        "dátum",
                                        "ár"};

        List<Rendeles> rendelesek;
        RendelesekTabla(List<Rendeles> rendelesek) {
            this.rendelesek = rendelesek;
        }

        public Rendeles getRendeles(int row) {
            return rendelesek.get(row);
        }
        
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return rendelesek.size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            switch (col) {
                case 0:
                    return rendelesek.get(row).getId();
                case 1:
                    return rendelesek.get(row).getDatum();
                case 2:
                    return View.penznemreValtas(rendelesek.get(row).getAr());
            }
            return null;
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }    
}