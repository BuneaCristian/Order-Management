package Presentation;

import Model.Product;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Aceasta clasa contine interfata grafica a programului principal si contine 3 butoane (Client, Product, Order)
 * care ne trimit la interfetele aferente fiecarui tabel
 */

public class GUI_Program {

    public JButton client = new JButton("Client");
    public JButton product = new JButton("Product");
    public JButton order = new JButton("Order");

    public GUI_Program() {
        JFrame frame = new JFrame("Orders Management");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JPanel panel;
        panel = new JPanel();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();


        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_Client c = new GUI_Client();
                frame.dispose();
            }
        });

        product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_Product c = new GUI_Product();
                frame.dispose();
            }
        });

        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_Order c = new GUI_Order();
                frame.dispose();
            }
        });

        p1.add(client);
        p2.add(product);
        p3.add(order);

        panel.add(p1);
        panel.add(p2);
        panel.add(p3);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.setVisible(true);
        frame.setContentPane(panel);
    }
}
