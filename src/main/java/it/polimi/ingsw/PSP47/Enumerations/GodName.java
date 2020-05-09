package it.polimi.ingsw.PSP47.Enumerations;

import java.io.Serializable;

/**
 * Enumerations which represents all the available gods.
 */
public enum GodName implements Serializable {
    APOLLO (""),
    ARTEMIS ("Arthemis"),
    ATHENA ("Athena"),
    ATLAS ("Atlas"),
    DEMETER ("Demeter"),
    HEPHAESTUS ("Hephaestus"),
    MINOTAUR ("Minotaur"),
    PAN ("Pan"),
    PROMETHEUS("Prometheus"),
    //advanced gods
    HERA("Hera"),
    HESTIA("Hestia"),
    TRITON("Triton"),
    CHRONUS("Chronus"),
    ZEUS("Zeus"),
    WRONGGODNAME("");

    private final String god;

    GodName(String god) {
        this.god = god;
        //this.power = power;
    }

    public String getGod(){ return this.god ;}

    /**
     * method used to convert strings into enum
     * @param name is the name as a string of the Gender
     * @return enum of the Gender
     */
    public static GodName getGodsNameByName(String name)  {

        switch (name.toUpperCase()) {
            case "APOLLO" :
                return APOLLO;
            case "ARTEMIS" :
                return ARTEMIS;
            case "ATHENA" :
                return ATHENA;
            case "ATLAS" :
                return ATLAS;
            case "DEMETER" :
                return DEMETER;
            case "HEPHAESTUS" :
                return HEPHAESTUS;
            case "MINOTAUR" :
                return MINOTAUR;
            case "PAN" :
                return PAN;
            case "PROMETHEUS" :
                return PROMETHEUS;
            case "HESTIA" :
                return HESTIA;
            case "HERA":
                return HERA;
            case "TRITON":
                return TRITON;
            case "CHRONUS":
                return CHRONUS;
            case "ZEUS" :
                return ZEUS;
            default :
                return WRONGGODNAME;

        }
    }

    public String getPower(){
        return god;
    }

}
