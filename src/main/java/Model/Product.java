package Model;

/**
 * Aceasta clasa este varianta produsului din baza de date cu atributele specifice fiecarei coloane din tabel.
 * Contine getter-ele si setter-ele fiecarui atribut, plus metoda toString().
 */

public class Product {
    private int id;
    private String name;
    private int quantity;

    public Product() {

    }

    public Product(int id_product, String name, int quantity) {
        this.id = id_product;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + id +
                ", nameP='" + name + '\'' +
                ", cantitate=" + quantity +
                '}';
    }
}
