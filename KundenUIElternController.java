package ui;

import defaults.Fahrzeug;

import java.time.LocalDate;

public interface KundenUIElternController {
    void zeigeFahrzeugDetails(Fahrzeug fahrzeug, LocalDate start, LocalDate ende);
}
