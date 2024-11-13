package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Progetto dao.
 */
public interface ProgettoDAO {

    /**
     * Inserisci progetto.
     *
     * @param cup    the cup
     * @param refSci the ref sci
     * @param resp   the resp
     * @param nome   the nome
     * @param budget the budget
     * @throws SQLException the sql exception
     */
    void inserisciProgetto(String cup, String refSci, String resp, String nome, float budget)
            throws SQLException;

    /**
     * Rimuovi progetto.
     *
     * @param cup the cup
     * @throws SQLException the sql exception
     */
    void rimuoviProgetto(String cup) throws SQLException;

    /**
     * Aggiungi laboratorio.
     *
     * @param cup     the cup
     * @param nomeLab the nome lab
     * @throws SQLException the sql exception
     */
    void aggiungiLaboratorio(String cup, String nomeLab) throws SQLException;

    /**
     * Gets prog imp.
     *
     * @param cup the cup
     * @return the prog imp
     */
    ArrayList<String> getProgImp(String cup);

    /**
     * Gets lab prog.
     *
     * @param cup   the cup
     * @param l_Lab the l lab
     */
    void getLabProg(String cup, ArrayList<String> l_Lab);
}