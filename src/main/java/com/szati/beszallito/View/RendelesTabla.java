package com.szati.beszallito.View;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.szati.beszallito.Controller.Controller;
import com.szati.beszallito.Model.RendeltTermek;

class RendelesTabla extends AbstractTableModel {
    private final String[] columnNames = {"név", "ár x darab"};

    List<RendeltTermek> rendeltTermekek;
    List<SimpleEntry<Integer, Integer>> kedvezmenyek;
    Controller controller;
    public RendelesTabla(Controller controller, List<RendeltTermek> rendeltTermekek, List<SimpleEntry<Integer, Integer>> kedvezmenyek) {
        this.controller = controller;
        this.rendeltTermekek = rendeltTermekek;
        this.kedvezmenyek = kedvezmenyek;
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return rendeltTermekek.size() + kedvezmenyek.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                if (row < rendeltTermekek.size())
                    return rendeltTermekek.get(row).getTermek().getNev();
                else
                    return controller.getModel()
                            .getKedvezmenyById(kedvezmenyek.get(row - rendeltTermekek.size()).getKey())
                            .getLeiras();
            case 1:
                if (row < rendeltTermekek.size())
                    return View.penznemreValtas(rendeltTermekek.get(row).getAr())
                            + " x " + rendeltTermekek.get(row).getDb();
                else
                    return View.penznemreValtas(-kedvezmenyek
                            .get(row - rendeltTermekek.size()).getValue());
        }
        return null;
    }

    @Override
    public Class getColumnClass(int c) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}    