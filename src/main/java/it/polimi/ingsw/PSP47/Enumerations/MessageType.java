package it.polimi.ingsw.PSP47.Enumerations;

/**
 * Enumeration which represents all the message to send during the game.
 */
public enum MessageType {
    REQUEST_CONNECTION,
    CONNECTION_FAILED,
    REQUEST_PLAYERS_NUMBER,
    CONNECTION_ACCEPTED,
    RANDOM_PLAYER,
    LIST_OF_GODS,
    PLAYERS_NUMBER,
    PUBLIC_INFORMATION,
    UPDATE_SLOT, CHALLENGER,
    ERROR,
    IMPORTANT,
    //important message in specific
    START_GAME,
    TURN,
    WINNING,
    LOSING,
    //
    CHOOSE_WORKER,
    CHOOSE_ACTION,
    ASK_WORKER_POSITION,
    WAIT_CONNECTION_OPPONENT_PLAYER,
    OPPONENT_PLAYER_DISCONNECTION,
    REQUEST_DISCONNECTION,
    FIRST_CONNECTION,
    FIRST_PLAYER_CONNECTION,
    GAME_STARTED,
    WRONG_PARAMETERS,
    REQUEST_NUMBER_OF_PLAYERS,
    WAIT_CHOOSE_NUMBER_PLAYERS,
    GOD_CHOSEN,
    PING;
}
