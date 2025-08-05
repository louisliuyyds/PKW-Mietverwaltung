package adapter;

import java.util.List;

public interface ZugriffInterface<T> {
    List<T> getAlleDaten();
    void update(T daten);
    void delete(int id);
    void add(T daten);
    String[] getSpaltennamen();
    T getByID(int id);
    List<T> getByName(String vorname, String name);
}
