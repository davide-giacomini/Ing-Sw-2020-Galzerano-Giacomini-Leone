package it.polimi.ingsw.PSP47.Enumerations;

/**
 * Enumeration which represents the possible genders of a worker.
 */
public enum Gender {

    MALE (0),
    FEMALE (1),
    WRONGGENDER(2);

    private final int gender;
    Gender(int gender) {
        this.gender = gender;
    }

    /**
     * method used to convert strings into enum
     * @param name is the name as a string of the Gender
     * @return enum of the Gender
     */
    public static Gender getGenderByName(String name)  {


        switch (name.toUpperCase()) {
            case "MALE" :
                return MALE;
            case "FEMALE" :
                return FEMALE;
            default :
                return WRONGGENDER;

        }
    }

}

