package it.polimi.ingsw.PSP47.Enumerations;

import java.io.Serializable;

public enum GodName implements Serializable {
    APOLLO ("Apollo"),
    ARTEMIS ("Arthemis"),
    ATHENA ("Athena"),
    ATLAS ("Atlas"),
    DEMETER ("Demeter"),
    HEPHAESTUS ("Hephaestus"),
    MINOTAUR ("Minotaur"),
    PAN ("Pan"),
    PROMETHEUS("Prometheus"),
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
            default :
                return WRONGGODNAME;

        }
    }

}
