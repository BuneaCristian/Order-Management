package Presentation;

import BusinessLogic.Generic;
import DataAccess.DAO;
import Model.Client;
import Model.Product;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Aceasta clasa contine interfata grafica pentru tabelul product
 * Contine cele 3 campuri ale produsului (ID, Name, Quantity), precum si 5 butoane: insert (adauga un nou produs in baza de date),
 * update (modifica datele unui produs), delete (sterge un produs din baza de date), view (afiseaza tabelul din baza de date)
 * si back (ne trimite inapoi la interfata principala)
 */

public class GUI_Product {

    private final Generic<Product> product = new Generic<>(Product.class);

    public JTextField productID = new JTextField("", 10);
    public JTextField productName = new JTextField("", 10);
    public JTextField productQuantity = new JTextField("", 10);

    public JLabel pID = new JLabel("ID:");
    public JLabel productN = new JLabel("Name:");
    public JLabel productQ = new JLabel("Quantity:");

    public JButton view = new JButton("View");
    public JButton insert = new JButton("Insert");
    public JButton update = new JButton("Update");
    public JButton delete = new JButton("Delete");
    public JButton back = new JButton("Back");

    JPanel panel;

    public GUI_Product() {
        JFrame frame = new JFrame("Product");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        panel = new JPanel();

        JPanel p1 = new JPanel();
        p1.add(pID);
        p1.add(productID);
        p1.add(productN);
        p1.add(productName);
        p1.add(productQ);
        p1.add(productQuantity);

        JPanel p2 = new JPanel();
        p2.add(view);
        p2.add(insert);
        p2.add(delete);
        p2.add(update);
        p2.add(back);

        panel.add(p1);
        panel.add(p2);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_Program backButton = new GUI_Program();
                frame.dispose();
            }
        });

        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                product.insert(new Product(Integer.parseInt(productID.getText()), productName.getText(), Integer.parseInt(productQuantity.getText())));
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                product.delete(Integer.parseInt(productID.getText()));
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                product.update(new Product(Integer.parseInt(productID.getText()), productName.getText(), Integer.parseInt(productQuantity.getText())));
            }
        });

        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame productFrame = new JFrame("Products Table");
                productFrame.setVisible(true);
                productFrame.setSize(800, 600);

                JPanel panelP = new JPanel();
                panelP.setLayout(new BoxLayout(panelP, BoxLayout.Y_AXIS));
                productFrame.add(panelP);

                JTable table = new JTable();
                product.updateTable(table);
                JScrollPane scrollBar = new JScrollPane(table);
                scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                panelP.add(scrollBar);
            }
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.setVisible(true);
        frame.setContentPane(panel);
    }
}
