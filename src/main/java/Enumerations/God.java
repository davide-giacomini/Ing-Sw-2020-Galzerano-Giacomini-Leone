package Enumerations;

public enum God {
    APOLLO ("Apollo"),
    ARTHEMIS ("Arthemis"),
    ATHENA ("Athena"),
    ATLAS ("Atlas"),
    DEMETER ("Demeter"),
    HEPHAESTUS ("Hephaestus"),
    HERMES("Hermes"),
    MINOTAUR ("Minotaur"),
    PAN ("Pan"),
    PROMETHEUS("Prometheus");

    private final String god;
    God(String god) {
        this.god = god;
    }

    public String getGod(){ return this.god ;}
}
