package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Impiegato dao.
 */
public interface ImpiegatoDAO {

    /**
     * Inserisci impiegato.
     *
     * @param cf             the cf
     * @param nome           the nome
     * @param cognome        the cognome
     * @param dataNascita    the data nascita
     * @param dataAssunzione the data assunzione
     * @param codiceCon      the codice con
     * @param merito         the merito
     * @param salario        the salario
     * @param categoria      the categoria
     * @param eta            the eta
     * @throws SQLException the sql exception
     */
    void inserisciImpiegato(String cf, String nome, String cognome, Date dataNascita, Date dataAssunzione, String codiceCon,
                                   boolean merito, float salario, String categoria, int eta) throws SQLException;

    /**
     * Rimuovi impiegato.
     *
     * @param cf the cf
     * @throws SQLException the sql exception
     */
    void rimuoviImpiegato(String cf) throws SQLException;

    /**
     * Promuovi impiegato.
     *
     * @param cf     the cf
     * @param merito the merito
     * @throws SQLException the sql exception
     */
    void promuoviImpiegato(String cf, boolean merito) throws SQLException;

    /**
     * Gets afferenze imp.
     *
     * @param cf          the cf
     * @param laboratorio the laboratorio
     */
    void getAfferenzeImp(String cf, ArrayList<String> laboratorio);

    /**
     * Gets progetti lab.
     *
     * @param cf       the cf
     * @param progetto the progetto
     */
    void getProgettiLab(String cf, ArrayList<String> progetto);

    /**
     * Gets promozioni imp.
     *
     * @param cf           the cf
     * @param l_Promozioni the l promozioni
     * @param date         the date
     */
    void getPromozioniImp(String cf, ArrayList<String> l_Promozioni, ArrayList<Date> date);
}
