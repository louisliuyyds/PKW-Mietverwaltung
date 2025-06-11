package ui;
import java.util.List;
import java.util.Map;

public interface ZugriffInterface {
    List<Object[]> getAlleDaten();
    void update(int id, Map<String, String> daten);
    void delete(int id);
    void add(Map<String, String> daten);
    String[] getSpaltennamen();
}
