package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Laboratorio dao.
 */
public interface LaboratorioDAO {

    /**
     * Inserisci laboratorio.
     *
     * @param nomeLab the nome lab
     * @param respSci the resp sci
     * @param topic   the topic
     * @throws SQLException the sql exception
     */
    void inserisciLaboratorio(String nomeLab, String respSci, String topic)
            throws SQLException;

    /**
     * Rimuovi laboratorio.
     *
     * @param nomeLab the nome lab
     * @throws SQLException the sql exception
     */
    void rimuoviLaboratorio(String nomeLab) throws SQLException;

    /**
     * Aggiungi afferente.
     *
     * @param nomeLab the nome lab
     * @param cf      the cf
     * @throws SQLException the sql exception
     */
    void aggiungiAfferente(String nomeLab, String cf) throws SQLException;

    /**
     * Afferenze lab.
     *
     * @param nomelab the nomelab
     * @param l_CF    the l cf
     */
    void afferenzeLab(String nomelab, ArrayList<String> l_CF);

    /**
     * Gets resp sci.
     *
     * @param nomelab the nomelab
     * @param resp    the resp
     */
    void getRespSci(String nomelab, ArrayList<String> resp);

    /**
     * Gets prog lavora.
     *
     * @param nomelab the nomelab
     * @param CUP     the cup
     */
    void getProgLavora(String nomelab, ArrayList<String> CUP);
}