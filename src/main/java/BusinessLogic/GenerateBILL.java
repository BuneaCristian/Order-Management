package BusinessLogic;

import Model.Client;
import Model.Product;
import Model.Orders;

import java.io.FileWriter;

/**
 * Aceasta clasa creeaza factura pentru o comanda
 * Va afisa numarul de ordine al comenzii, numele si cantitatea produsului cumparat, numele clientului, precum si adresa clientului
 */

public class GenerateBILL {
    public GenerateBILL(Client c, Product p, Orders o) {
        try {
            FileWriter file = new FileWriter("Bill.txt");
            String myWriter = "Bill";
            myWriter +=  "\n\nOrder: " + o.getId() + "\nProduct: " + p.getName() +
                        "\nQuantity: " + o.getQuantity() + "\nName: " + c.getName();
            file.write(myWriter);
            file.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
