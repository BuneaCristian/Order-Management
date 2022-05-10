package Model;

/**
 * Aceasta clasa este varianta comenzii din baza de date cu atributele specifice fiecarei coloane din tabel.
 * Contine getter-ele si setter-ele fiecarui atribut, plus metoda toString().
 * id_client face referire la clientul care va cumpara un produs, marcat de id_product
 */

public class Orders {
    private int id;
    private int id_client;
    private int id_product;
    private int quantity;

    public Orders() {

    }

    public Orders(int id_order, int id_client, int id_product, int quantity) {
        this.id = id_order;
        this.id_client = id_client;
        this.id_product = id_product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "idOrder=" + id +
                ", idClient=" + id_client +
                ", idProduct=" + id_product +
                ", quantity=" + quantity +
                '}';
    }
}
