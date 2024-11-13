package DAO;

import java.sql.Date;
import java.util.ArrayList;

/**
 * The interface Gestionale dao.
 */
//ogni l_ fa riferimento al fatto che è una variabile lista -> l_cf = lista codici fiscali
public interface GestionaleDAO {

    /**
     * Gets impiegati.
     *
     * @param l_CF             the l cf
     * @param l_Nomi           the l nomi
     * @param l_Cognomi        the l cognomi
     * @param l_DateNascita    the l date nascita
     * @param l_DateAssunzioni the l date assunzioni
     * @param l_Contratti      the l contratti
     * @param l_Merito         the l merito
     * @param l_Salari         the l salari
     * @param l_Categorie      the l categorie
     * @param l_Eta            the l eta
     */
    void getImpiegati(ArrayList<String> l_CF, ArrayList<String> l_Nomi, ArrayList<String> l_Cognomi,
                             ArrayList<Date> l_DateNascita, ArrayList<Date> l_DateAssunzioni,
                             ArrayList<String> l_Contratti, ArrayList<Boolean> l_Merito,
                             ArrayList<Float> l_Salari, ArrayList<String> l_Categorie, ArrayList<Integer> l_Eta);

    /**
     * Gets promozioni.
     *
     * @param l_CF               the l cf
     * @param l_DatePassaggio    the l date passaggio
     * @param l_Contratti        the l contratti
     * @param l_VecchieCategorie the l vecchie categorie
     * @param l_NuoveCategorie   the l nuove categorie
     */
    void getPromozioni(ArrayList<String> l_CF, ArrayList<Date> l_DatePassaggio,
                              ArrayList<String> l_Contratti, ArrayList<String> l_VecchieCategorie,
                              ArrayList<String> l_NuoveCategorie);

    /**
     * Gets laboratori.
     *
     * @param l_Nomi            the l nomi
     * @param l_RespSci         the l resp sci
     * @param l_Topic           the l topic
     * @param l_NumeroAfferenti the l numero afferenti
     */
    void getLaboratori(ArrayList<String> l_Nomi, ArrayList<String> l_RespSci,
                              ArrayList<String> l_Topic, ArrayList<Integer> l_NumeroAfferenti);

    /**
     * Gets progetti.
     *
     * @param l_CUP    the l cup
     * @param l_RefSci the l ref sci
     * @param l_Resp   the l resp
     * @param l_Nomi   the l nomi
     * @param l_Budget the l budget
     */
    void getProgetti(ArrayList<String> l_CUP, ArrayList<String> l_RefSci, ArrayList<String> l_Resp,
                            ArrayList<String> l_Nomi, ArrayList<Float> l_Budget);

    /**
     * Gets afferenti.
     *
     * @param nomeLab the nome lab
     * @param l_CF    the l cf
     */
    void getAfferenti(String nomeLab, ArrayList<String> l_CF);
}
