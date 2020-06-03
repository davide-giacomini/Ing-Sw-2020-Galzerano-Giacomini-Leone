package it.polimi.ingsw.PSP47.Enumerations;

import it.polimi.ingsw.PSP47.Model.Gods.*;
import it.polimi.ingsw.PSP47.Model.Player;

import java.io.IOException;
import java.io.Serializable;

/**
 * Enumerations which represents all the available gods.
 */
public enum GodName implements Serializable {
    APOLLO ("Apollo"),
    ARTEMIS ("Arthemis"), //TODO ATTENZIONE! ARTEMIS E' SCRITTA MALE (con la h), MA NON MODIFICATELA! SE LA MODIFICATE, DITEMELO (davide)
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
    CHRONUS("Chronus"), //TODO stessa cosa di artemis.
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

    /**
     * This method convert the enumeration into the corrispondent class.
     * @param god the god that has to be converted
     * @param player the player who chose this god
     * @return the converted god
     * @throws IOException if the god is not one of the enumeration.
     */
    public God chooseGod(GodName god, Player player) throws IOException {
        switch (god) {
            case PAN: return new Pan(player, "Pan");
            case ATLAS: return new Atlas(player, "Atlas");
            case APOLLO: return new Apollo(player, "Apollo");
            case ATHENA: return new Athena(player, "Athena");
            case ARTEMIS: return new Artemis(player, "Artemis");
            case DEMETER: return new Demeter(player, "Demeter");
            case MINOTAUR: return new Minotaur(player, "Minotaur");
            case HEPHAESTUS: return new Hephaestus(player, "Hephaestus");
            case PROMETHEUS: return new Prometheus(player, "Prometheus");
            case HERA: return new Hera(player, "Hera");
            case ZEUS: return new Zeus(player, "Zeus");
            case HESTIA: return new Hestia(player, "Hestia");
            case TRITON: return new Triton(player, "Triton");
            case CHRONUS: return new Chronus(player, "Chronus");
            default: throw new IOException();
        }
    }

    public String getPower(){
        return god;
    }

}
