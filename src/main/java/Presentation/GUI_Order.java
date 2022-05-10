package Presentation;

import BusinessLogic.GenerateBILL;
import BusinessLogic.Generic;
import Model.Client;
import Model.Orders;
import Model.Product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Aceasta clasa contine interfata grafica pentru tabelul order
 * Contine cele 4 campuri ale comenzii (ID_Order, ID_Client, ID_Product, Quantity), precum si 3 butoane: insert (adauga o noua comanda in baza de date),
 * view (afiseaza tabelul din baza de date) si back (ne trimite inapoi la interfata principala)
 */

public class GUI_Order {

    private final Generic<Orders> order = new Generic<>(Orders.class);
    private final Generic<Client> orderClient = new Generic<>(Client.class);
    private final Generic<Product> orderProduct = new Generic<>(Product.class);

    GenerateBILL bill;

    public JTextField orderID = new JTextField("", 10);
    public JTextField orderClientID = new JTextField("", 10);
    public JTextField orderProductID = new JTextField("", 10);
    public JTextField orderQuantity = new JTextField("", 10);

    public JLabel oID = new JLabel("ID Comanda:");
    public JLabel orderC = new JLabel("ID Client:");
    public JLabel orderP = new JLabel("ID Produs:");
    public JLabel orderQ = new JLabel("Cantitate:");

    public JButton insert = new JButton("Insert");
    public JButton back = new JButton("Back");
    public JButton view = new JButton("View");

    JPanel panel;

    public GUI_Order() {
        JFrame frame = new JFrame("Orders");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        panel = new JPanel();

        JPanel p1 = new JPanel();
        p1.add(oID);
        p1.add(orderID);
        p1.add(orderC);
        p1.add(orderClientID);
        p1.add(orderP);
        p1.add(orderProductID);
        p1.add(orderQ);
        p1.add(orderQuantity);

        JPanel p2 = new JPanel();
        p2.add(view);
        p2.add(insert);
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
                if (orderClientID.getText().equals("") || orderProductID.getText().equals("") || orderQuantity.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Invalid data!");
                }
                else {
                    Orders order1 = new Orders(Integer.parseInt(orderID.getText()), Integer.parseInt(orderClientID.getText()),
                                             Integer.parseInt(orderProductID.getText()), Integer.parseInt(orderQuantity.getText()));
                    Product product1 = orderProduct.findById(order1.getId_product());
                    Client client1 = orderClient.findById(order1.getId_client());
                    if (product1.getQuantity() < order1.getQuantity()) {
                        JOptionPane.showMessageDialog(null, "You need more produces!");
                    }
                    else {
                        bill = new GenerateBILL(client1, product1, order1);
                        product1.setQuantity(product1.getQuantity() - order1.getQuantity());
                        orderProduct.update(product1);
                        order.insert(order1);
                    }
                }
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
                order.updateTable(table);
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
