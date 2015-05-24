package com.szati.beszallito.View;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import com.szati.beszallito.Controller.Controller;
import com.szati.beszallito.Controller.DAO;

public class Bejelentkezes extends JFrame {

    boolean tolt;
    public Bejelentkezes(String title, Controller controller) {
        super(title);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(250, 150);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);
        
        JButton elkuldButton = new JButton("Elküld");
        JPanel panel = new JPanel();
        JTextField felhText = new JTextField();
        JPasswordField jelszoField = new JPasswordField();
        
        panel.setLayout(new GridLayout(3, 2, 5, 10));
        panel.add(new JLabel("Felhasznaló név:"));
        panel.add(felhText);
        panel.add(new JLabel("Jelszó:"));
        panel.add(jelszoField);
        panel.add(new JPanel());
        panel.add(elkuldButton);        
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        tolt = false;
        JLabel toltLabel = new JLabel();
        toltLabel.setHorizontalAlignment(SwingConstants.CENTER);
        toltLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel fopanel = new JPanel(new CardLayout());
        fopanel.add(panel, "bej");
        fopanel.add(toltLabel, "tolt");
        this.add(fopanel);
        
        this.setVisible(true);
        
        ActionListener elkuldAction = (ActionEvent ae) -> {
            if (tolt)
                return;
            
            SwingWorker<Void, Void>  worker = new SwingWorker<Void, Void>() {  
                @Override
                protected Void doInBackground() throws Exception {
                    tolt = true;

                    CardLayout cl = (CardLayout)fopanel.getLayout();
                    cl.show(fopanel, "tolt");
                    toltLabel.setText("bejelentkezés...");
                    
                    DAO.logIn(felhText.getText(),
                            new String(jelszoField.getPassword()));
                    
                    if (DAO.getFelhasznaloId() == -1)
                        JOptionPane.showMessageDialog(getContentPane(),
                                "Hibás felhasználónév vagy jelszó.",
                                "Hiba", JOptionPane.ERROR_MESSAGE);
                    else {
                        toltLabel.setText("<html>bejelentkezés sikeres<br>"
                                + "adatok lekérése az adatbázisból...</html>");
                        controller.getModel().setKategoriak(DAO.getKategoriak());
                        controller.getModel().setMarkak(DAO.getMarkak());
                        controller.getModel().setKedvezmenyek(DAO.getKedvezmenyek());
                        controller.getModel().setTermekek(DAO.getTermekek(false));
                        
                        setVisible(false);
                        View view = new View("Beszállító", controller);
                        view.setVisible(true);
                        return null;
                    }
                    
                    cl.show(fopanel, "bej");
                    tolt = false;
                    
                    return null;
                }
            };
            worker.execute();
        } ;
        
        felhText.addActionListener(elkuldAction);
        jelszoField.addActionListener(elkuldAction);
        elkuldButton.addActionListener(elkuldAction);
    }
}
