package Presentation;

import BusinessLogic.Generic;
import Model.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Aceasta clasa contine interfata grafica pentru tabelul client
 * Contine cele 3 campuri ale clientului (ID, Name, Address), precum si 5 butoane: insert (adauga un nou client in baza de date),
 * update (modifica datele unui client), delete (sterge un client din baza de date), view (afiseaza tabelul din baza de date)
 * si back (ne trimite inapoi la interfata principala)
 */

public class GUI_Client extends JFrame {
    private final Generic<Client> client = new Generic<>(Client.class);

    public JTextField clientID = new JTextField("", 10);
    public JTextField clientName = new JTextField("", 10);
    public JTextField clientAddress = new JTextField("", 10);

    public JLabel cID = new JLabel("ID:");
    public JLabel clientN = new JLabel("Name:");
    public JLabel clientA = new JLabel("Address:");

    public JButton view = new JButton("View");
    public JButton insert = new JButton("Insert");
    public JButton update = new JButton("Update");
    public JButton delete = new JButton("Delete");
    public JButton back = new JButton("Back");

    JPanel panel;

    public GUI_Client() {
        JFrame frame = new JFrame("Client");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        panel = new JPanel();

        JPanel p1 = new JPanel();
        p1.add(cID);
        p1.add(clientID);
        p1.add(clientN);
        p1.add(clientName);
        p1.add(clientA);
        p1.add(clientAddress);


        JPanel p2 = new JPanel();
        p2.add(view);
        p2.add(insert);
        p2.add(delete);
        p2.add(update);
        p2.add(back);

        panel.add(p1);
        panel.add(p2);

        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame clientFrame = new JFrame("Clients Table");
                clientFrame.setVisible(true);
                clientFrame.setSize(800, 600);

                JPanel panelC = new JPanel();
                panelC.setLayout(new BoxLayout(panelC, BoxLayout.Y_AXIS));
                clientFrame.add(panelC);

                JTable table = new JTable();
                client.updateTable(table);
                JScrollPane scrollBar = new JScrollPane(table);
                scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                panelC.add(scrollBar);
            }
        });

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
                client.insert(new Client(Integer.parseInt(clientID.getText()), clientName.getText(), clientAddress.getText()));
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.delete(Integer.parseInt(clientID.getText()));
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.update(new Client(Integer.parseInt(clientID.getText()), clientName.getText(), clientAddress.getText()));
            }
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.setVisible(true);
        frame.setContentPane(panel);
    }
}
