package com.szati.beszallito.View;

import com.szati.beszallito.Controller.Controller;
import com.szati.beszallito.Controller.DAO;
import com.szati.beszallito.Model.Termek;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

public class TermekekPanel extends JPanel {
    
    boolean tolt;
    public TermekekPanel(Controller controller) {
        this.setLayout(new BorderLayout());

        int hely_nevnek = 275;
        
        JPanel fentiPanel = new JPanel();
        JComboBox markaBox = new JComboBox();
        JTextField keresoText = new JTextField();
        JComboBox kategoriaBox = new JComboBox();
        JPanel boxosPanel = new JPanel();
        JPanel fentiLentiPanel = new JPanel();
        JCheckBox akciosCheck = new JCheckBox("Akciós");
        JCheckBox ujCheck = new JCheckBox("Új");
        JButton szurButton = new JButton("Szűr");

        fentiPanel.setLayout(new BorderLayout());
        boxosPanel.setLayout(new BoxLayout(boxosPanel, BoxLayout.X_AXIS));
        fentiLentiPanel.setLayout(new BorderLayout());

        this.add(fentiPanel, BorderLayout.NORTH);           
        fentiPanel.add(markaBox, BorderLayout.WEST);
        fentiPanel.add(keresoText, BorderLayout.CENTER);
        fentiPanel.add(kategoriaBox, BorderLayout.EAST);
        fentiPanel.add(fentiLentiPanel, BorderLayout.SOUTH);
        fentiLentiPanel.add(boxosPanel, BorderLayout.CENTER);
        fentiLentiPanel.add(szurButton, BorderLayout.EAST);
        boxosPanel.add(akciosCheck);
        boxosPanel.add(ujCheck);

        JPanel tablazatPanel = new JPanel();

        markaBox.addItem("-");
        controller
                .getModel()
                .getMarkak()
                .values()
                .stream()
                .forEach((s) -> markaBox.addItem(s));

        kategoriaBox.addItem("-");
        controller
                .getModel()
                .getKategoriak()
                .values()
                .stream()
                .forEach((s) -> kategoriaBox.addItem(s));

        JTable tablazat = new JTable(new TermekTabla(controller, DAO.getTermekek(true)));
        tablazat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //tablazat.setCellSelectionEnabled(true);
        tablazat.getColumn("név").setMinWidth(hely_nevnek);

        JScrollPane sp = new JScrollPane(tablazat, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tablazatPanel.setLayout(new BoxLayout(tablazatPanel, BoxLayout.X_AXIS));
        tablazatPanel.add(sp);

        JPanel lentiKartyaPanel = new JPanel(new CardLayout());
        lentiKartyaPanel.add(tablazatPanel, "tablazat");
        lentiKartyaPanel.add(new JLabel("kérem várjon...",
                SwingConstants.CENTER), "tolt");
        this.add(lentiKartyaPanel, BorderLayout.CENTER);
        
        tolt = false;
        
        ActionListener updateTable = (ActionEvent e) -> {
            if (tolt)
                    return;
            
            SwingWorker<Void, Void>  worker = new SwingWorker<Void, Void>() {  
                
                @Override
                protected Void doInBackground() throws Exception {    
                    tolt = true;
                    
                    CardLayout cl = (CardLayout)lentiKartyaPanel.getLayout();
                    keresoText.setEnabled(false);
                    szurButton.setEnabled(false);
                    cl.show(lentiKartyaPanel, "tolt");
                    
                    int sMarka = -1;
                    if (markaBox.getSelectedIndex() != 0) {
                        for (Entry<Integer, String> ent :
                                controller.getModel().getMarkak().entrySet()) {
                            if (ent.getValue().equals(markaBox.getSelectedItem())) {
                                sMarka = ent.getKey();
                                break;
                            }
                        }
                    }

                    int sKat = -1;
                    if (kategoriaBox.getSelectedIndex() != 0) {
                        for (Entry<Integer, String> ent :
                                controller.getModel().getKategoriak().entrySet()) {
                            if (ent.getValue().equals(kategoriaBox.getSelectedItem())) {
                                sKat = ent.getKey();
                                break;
                            }
                        }
                    }

                    tablazat.setModel(new TermekTabla(controller,
                            DAO.getTermekekFeltetelel(
                                    keresoText.getText(),
                                    sMarka,
                                    sKat,
                                    ujCheck.isSelected(),
                                    akciosCheck.isSelected(),
                                    true
                            )));
                    tablazat.getColumn("név").setMinWidth(hely_nevnek);

                    
                    validate();
                    tolt = false;
                    keresoText.setEnabled(true);
                    szurButton.setEnabled(true);
                    cl.show(lentiKartyaPanel, "tablazat");                    
                    
                    return null;
                }
            };
            worker.execute();
        };
        
        keresoText.addActionListener(updateTable);
        szurButton.addActionListener(updateTable);
    }

    class TermekTabla extends AbstractTableModel {
        private final String[] columnNames = {"név",
                                        "db",
                                        "db.",
                                        "zsugor",
                                        "ár",
                                        "akciós",
                                        "új"};

        List<Termek> termekek;
        Controller controller;
        TermekTabla(Controller controller, List<Termek> termekek) {
            this.controller = controller;
            this.termekek = termekek;            
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return termekek.size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            switch (col) {
                case 0:
                    return termekek.get(row).getNev();
                case 1:
                    return controller.getDarab(termekek.get(row).getId())
                            / termekek.get(row).getZsugor();
                case 2:
                    return controller.getDarab(termekek.get(row).getId());
                case 3:
                    return termekek.get(row).getZsugor();
                case 4:
                    return termekek.get(row).isAkcios() ?
                            termekek.get(row).getAkciosAr() :
                            termekek.get(row).getAr();
                case 5:
                    return termekek.get(row).isAkcios();
                case 6:
                    return termekek.get(row).isUj();
            }
            return null;
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 1 || col == 2;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == 1)
                controller.setDarab(termekek.get(row).getId(),
                        (int)value * termekek.get(row).getZsugor());
            if (col == 2)
                controller.setDarab(termekek.get(row).getId(),
                        ((int)value / termekek.get(row).getZsugor()) * 
                                termekek.get(row).getZsugor());

            fireTableCellUpdated(row, col);
            validate();
        }
    }
}