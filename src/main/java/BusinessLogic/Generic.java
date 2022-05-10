package BusinessLogic;

import DataAccess.DAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Aceasta clasa contine logica programului.
 * Sunt metode generice pe care le poate apela orice obiect de tipul claselor din pachetul Model.
 */

public class Generic<T> {
    private final Class<T> type;
    private final DAO<T> dao;

    public Generic(Class<T> type) {
        this.type = type;
        dao = new DAO<>(type);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public void insert(T obj) {
        dao.insert(obj);
    }

    public void update(T obj) {
        dao.update(obj);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public T findById(int id) {
        return dao.findById(id);
    }

    /**
     * Aceasta metoda creeaza un prim obiect in tabel si pe urma populeaza tabelul folosind date din baza de date cu ajutorul tehnicilor de reflexie
     */

    public void updateTable(JTable table) {
        Object[] Head;
        Field[] fields = type.getDeclaredFields();
        Head = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            String n = fields[i].getName();
            Head[i] = n;
        }
        DefaultTableModel tableModel = new DefaultTableModel(Head, 0);
        ArrayList<T> list = (ArrayList<T>) dao.findAll();
        for (T current : list) {
            tableModel.addRow(dao.getObjectData(current));
        }
        table.setModel(tableModel);
    }
}
