package com.szati.beszallito.View;

import com.szati.beszallito.Controller.Controller;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;

public class View extends JFrame {
    
    private static final NumberFormat currencyFormatter =
            NumberFormat.getCurrencyInstance(new Locale("hu", "HU"));
    
    public View(String nev, Controller controller) {
        super(nev);
        
        setSize(750, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        EmptyBorder keret = new EmptyBorder(5, 5, 5, 5);
        tabbedPane.setBorder(keret);
        
        TermekekPanel termekekPanel = new TermekekPanel(controller);
        tabbedPane.addTab("Termékek", termekekPanel);
        
        KosarPanel kosarPanel = new KosarPanel(controller);
        tabbedPane.addTab("Kosár", kosarPanel);
        
        RendelesekPanel rendelesekPanel = new RendelesekPanel(controller);
        tabbedPane.addTab("Rendelések", rendelesekPanel);
        
        KedvezmenyekPanel kedvezmenyek = new KedvezmenyekPanel(controller);
        tabbedPane.addTab("Kedvezmények", kedvezmenyek);
        
        JPanel foPanel = new JPanel(new CardLayout());
        foPanel.add(tabbedPane, "tab");
        foPanel.add(new JLabel("kérem várjon...",
                SwingConstants.CENTER), "tolt");
        this.add(foPanel);
        
        tabbedPane.addChangeListener((ChangeEvent e) -> {
            SwingWorker<Void, Void>  worker = new SwingWorker<Void, Void>() {  

                @Override
                protected Void doInBackground() throws Exception {
                    CardLayout cl = (CardLayout)foPanel.getLayout();
                    cl.show(foPanel, "tolt");
                    
                    switch (tabbedPane.getSelectedIndex()) {
                        case 1:
                            kosarPanel.updateTable(controller);
                            break;
                        case 2:
                            rendelesekPanel.updateTable();
                            break;
                    }
                    cl.show(foPanel, "tab");
                    
                    return null;
                }
                
            };
            worker.execute();
        });
    }
    
    public static String penznemreValtas(int szam) {
        if (currencyFormatter.getCurrency().getNumericCode() != 999)
            return currencyFormatter.format(szam);
        else
            return szam + "";
    }
}