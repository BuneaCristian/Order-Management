package Model;

/**
 * Aceasta clasa este varianta clientului din baza de date cu atributele specifice fiecarei coloane din tabel.
 * Contine getter-ele si setter-ele fiecarui atribut, plus metoda toString().
 */

public class Client {
    private int id;
    private String name;
    private String address;

    public Client() {

    }

    public Client(int id_client, String name, String address) {
        this.id = id_client;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
