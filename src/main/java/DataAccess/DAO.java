package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;

/**
 * Aceasta clasa foloseste tehnici de reflexie pentru comenzile SQL
 * Ea este clasa care transmite datele introduse bazei de date
 */

public class DAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(DAO.class.getName());

    private final Class<T> genericClass;


    public DAO(Class<T> genericClass) {
        this.genericClass = genericClass;

    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(genericClass.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Aceasta metoda arata valoarea unui obiect folosind tehnici de reflexie
     */

    public Object[] getObjectData(Object obj) {
        Object[] toReturn = null;
        try {
            Field[] fields = genericClass.getDeclaredFields();
            toReturn = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                String n = fields[i].getName();
                Method method = genericClass.getDeclaredMethod("get" + n.substring(0, 1).toUpperCase() + n.substring(1));
                toReturn[i] = method.invoke(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    /**
     * Aceasta metoda afiseaza toate datele din tabel
     */

    public List<T> findAll() {

        Connection connection = null;
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        StringBuilder display = new StringBuilder();
        display.append("SELECT ");
        display.append(" * ");
        display.append(" FROM ");
        display.append(genericClass.getSimpleName());

        try {
            connection = ConnectionFactory.getConnection();
            findStatement = connection.prepareStatement(display.toString());
            rs = findStatement.executeQuery();
            return createObjects(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Aceasta clasa permite cautarea unui element dupa ID-ul acestuia
     * Este folositoare la inserarea unei noi comenzi
     */

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, genericClass.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor[] ctors = genericClass.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : genericClass.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, genericClass);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Aceasta metoda este folosita pentru a insera un obiect intr-un tabel folosind tehnici de reflexie
     */

    public void insert(T obj) {
        Connection connection = null;
        PreparedStatement insertStatement = null;
        StringBuilder display = new StringBuilder();
        display.append("INSERT INTO ");
        display.append(genericClass.getSimpleName());
        display.append("(");
        try {
            connection = ConnectionFactory.getConnection();
            Field[] fields = genericClass.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                display.append(fields[i].getName()).append(',');
            }
            display.deleteCharAt(display.length() - 1);
            display.append(") VALUES (");
            Object[] values = getObjectData(obj);
            for (int i = 0; i < values.length; i++) {
                if (fields[i].getType() == String.class) {
                    display.append('\'').append(values[i].toString()).append('\'').append(',');
                } else {
                    display.append(values[i].toString()).append(',');
                }
            }
            display.deleteCharAt(display.length() - 1);
            display.append(");");

            System.out.println(display);

            insertStatement = connection.prepareStatement(display.toString());
            insertStatement.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Aceasta metoda este folosita pentru a modifica un obiect intr-un tabel folosind tehnici de reflexie
     */

    public void update(T obj) {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        StringBuilder display = new StringBuilder();
        display.append("UPDATE ");
        display.append(genericClass.getSimpleName());
        display.append(" SET ");
        try {
            connection = ConnectionFactory.getConnection();
            Field[] fields = genericClass.getDeclaredFields();
            Object[] values = getObjectData(obj);

            for (int i = 1; i < fields.length; i++) {
                display.append(fields[i].getName()).append(" = ");
                if (fields[i].getType() == String.class) {
                    display.append('\'').append(values[i].toString()).append('\'').append(',');
                } else {
                    display.append(values[i].toString()).append(',');
                }
            }

            display.deleteCharAt(display.length() - 1);
            display.append(" WHERE ").append(fields[0].getName()).append(" = ").append(values[0].toString()).append(";");
            updateStatement = connection.prepareStatement(display.toString());
            updateStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Aceasta metoda este folosita pentru a sterge un obiect intr-un tabel folosind tehnici de reflexie
     */

    public void delete(int id) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        StringBuilder display = new StringBuilder();
        display.append("DELETE FROM ");
        display.append(genericClass.getSimpleName());
        display.append(" WHERE id= " + id);
        try {
            connection = ConnectionFactory.getConnection();
            deleteStatement = connection.prepareStatement(display.toString());
            deleteStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(connection);
        }
    }
}
