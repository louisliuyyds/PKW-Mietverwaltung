package defaults;


public class Mietvertragmethoden {

    private final Mietvertrag vertrag;

    public Mietvertragmethoden(Mietvertrag vertrag) {
        this.vertrag = vertrag;
    }

    public double berechneGesamtpreis() {
        double extrasSumme = 0.0;

        if (vertrag.getExtrasList() != null) {
            extrasSumme = vertrag.getExtrasList()
                    .stream()
                    .mapToDouble(Extras::getPreis)
                    .sum();
        }

        long tage = vertrag.getEnddatum().toEpochDay() - vertrag.getStartdatum().toEpochDay();
        return extrasSumme + vertrag.getFahrzeug().getPreis() * tage;
    }

}
