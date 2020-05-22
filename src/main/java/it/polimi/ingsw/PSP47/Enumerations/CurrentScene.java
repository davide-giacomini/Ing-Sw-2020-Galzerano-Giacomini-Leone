package it.polimi.ingsw.PSP47.Enumerations;

/**
 * Enum used to distinguish inside the client where we are in the game, it is particularly important for the DuringGame part
 */ //TODO CHANGE NAME?
public enum CurrentScene {
    START,
    CHOOSE_PLAYERS,
    CHALLENGER,
    CHOOSE_CARD,
    SET_WORKERS,
    ASK_INITIAL_POSITION,
    ASK_WHICH_WORKER,
    WAIT,
    CHOOSE_ACTION,
    ACTION_CHOSEN,
    END,
    QUIT,
    WIN,
    LOSE;

}
