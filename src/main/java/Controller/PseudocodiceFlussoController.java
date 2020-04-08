package Controller;

public class PseudocodiceFlussoController {
    
    // Ad ogni inizio turno il Turn controlla in automatico se uno dei due giocatori non si può più muovere.
    // Nel caso entrambi i giocatori non si possano muovere, il giocatore perde e i suoi worker vengono rimossi.
    // Ora inizia il Controller.
    // Il giocatore inizia a giocare selezionando il gender di un worker.
    // Appena seleziona il Gender deve essere fatto un controllo.
        // Se il worker può fare qualcosa, allora non succede nulla e il giocatore gioca senza problemi.
        // Se il worker è paralizzato, viene avvisato il giocatore e viene costretto a scegliere l'altro giocatore.
    // La prima mossa ovviamente la può fare tranquillamente senza problemi.
    // Una volta fatta la prima mossa, il controller chiama la validateEndTurn() di God.
    // Questo metodo controllerà se il turno può essere terminato.
    // Se il turno non può essere terminato
        // Il controller dovrà controllare se l'ultimo worker usato dal player si può ancora muovere/costruire, chiamando
        // la checkIfCanGoOn() di God.
            // NB: se il player può usare entrambi i giocatori, allora il controllo viene fatto su entrambi.
                // nel caso solo uno dei due worker si può muovere, si procederà come 10 righe sopra.
                // se entrambi non si possono muovere... beh è fottuto e si procede come di seguito.
        // Se il worker è paralizzato, il giocatore perde istantaneamente il gioco e i suoi worker vengono eliminati dalla board.
        // Altrimenti, il giocatore può ancora muovere.
    // Altrimenti, se il turno può essere terminato
        // Il controller verifica comunque se l'ultimo worker usato dal player può essere mosso oppure no.
        // Se il worker non può essere mosso (Anche qui considerare il caso di prima del NB)
            // Il controller termina automaticamente il turno dal player con un avviso e passa ad un altro player.
        // Altrimenti
            // Il controller permette al player di fare quello che vuole, e poi sono cazzi del giocatore se si incastra.
    // Quando il giocatore decide di terminare il turno, il controller controlla se il giocatore ha le facoltà di
    // terminarlo. Se non può, gli manda un avviso e gli dice di fare la prossima mossa.
    
    // Nel caso il turno si può terminare e viene terminato, il controllo verrà fatto all'inizio del turno successivo.
    // Questo poiché gli dei possono modificare particolari condizioni.
    
}
