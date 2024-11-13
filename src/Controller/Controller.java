package Controller;

import DAO.*;
import ImplementazionePGresDAO.*;
import Model.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

/**
 * The type Controller.
 */
public class Controller {
    //l_ prima di ogni variabile fa riferimento al fatto che sia una lista,
    // esempio: l_Impiegati = lista impiegati
    private final List<Impiegato> l_Impiegati = new ArrayList<>();
    private final List<Promozione> l_Promozioni = new ArrayList<>();
    private final List<Laboratorio> l_Laboratori = new ArrayList<>();
    private final List<Progetto> l_Progetti = new ArrayList<>();

    /**
     * Instantiates a new Controller.
     */
    public Controller(){
        getImpiegatiDB();
        getLaboratoriDB();
        getPromozioniDB();
        getProgettiDB();
    }

    /**
     * Get impiegati db list.
     *
     * @return the list
     */
    public List<Impiegato> getImpiegatiDB(){
        GestionaleDAO gestionaleDAO = new ImplementazioneGestionaleDAO();

        ArrayList<String> l_CF = new ArrayList<>();
        ArrayList<String> l_Nomi = new ArrayList<>();
        ArrayList<String> l_Cognomi = new ArrayList<>();
        ArrayList<Date> l_DateNascita = new ArrayList<>();
        ArrayList<Date> l_DateAssunzioni = new ArrayList<>();
        ArrayList<String> l_Contratti = new ArrayList<>();
        ArrayList<Boolean> l_Merito = new ArrayList<>();
        ArrayList<Float> l_Salari = new ArrayList<>();
        ArrayList<String> l_Categorie = new ArrayList<>();
        ArrayList<Integer> l_Eta = new ArrayList<>();

        gestionaleDAO.getImpiegati(l_CF, l_Nomi, l_Cognomi, l_DateNascita, l_DateAssunzioni,
                l_Contratti, l_Merito, l_Salari, l_Categorie, l_Eta);

        int i=0;
        for(; i<l_CF.size(); i++){
            try {
                l_Impiegati.add(new Impiegato(l_CF.get(i), l_Nomi.get(i), l_Cognomi.get(i),
                        l_DateNascita.get(i), l_DateAssunzioni.get(i), l_Contratti.get(i), l_Merito.get(i),
                        l_Salari.get(i), l_Categorie.get(i), l_Eta.get(i)));
            }catch (NullPointerException e){
                System.err.println("Eccezione del puntatore! ");
                break;
            }catch (IndexOutOfBoundsException e){
                System.err.println("Eccezione dell'indice! ");
                break;
            }
        }
        return l_Impiegati;
    }

    /**
     * Get laboratori db list.
     *
     * @return the list
     */
    public List<Laboratorio> getLaboratoriDB(){
        GestionaleDAO gestionaleDAO = new ImplementazioneGestionaleDAO();

        ArrayList<String> l_Nomi = new ArrayList<>();
        ArrayList<String> l_RespSci = new ArrayList<>();
        ArrayList<String> l_Topic = new ArrayList<>();
        ArrayList<Integer> l_NumeroAfferenti = new ArrayList<>();

        gestionaleDAO.getLaboratori(l_Nomi, l_RespSci, l_Topic, l_NumeroAfferenti);

        int i=0;
        for(; i<l_Nomi.size(); i++){
            l_Laboratori.add(new Laboratorio(l_Nomi.get(i), l_RespSci.get(i), l_Topic.get(i),
                    l_NumeroAfferenti.get(i)));
        }
        return l_Laboratori;
    }

    /**
     * Get promozioni db list.
     *
     * @return the list
     */
    public List<Promozione> getPromozioniDB(){
        GestionaleDAO gestionaleDAO = new ImplementazioneGestionaleDAO();

        ArrayList<String> l_CF = new ArrayList<>();
        ArrayList<Date> l_DatePassaggio = new ArrayList<>();
        ArrayList<String> l_Contratti = new ArrayList<>();
        ArrayList<String> l_VecchieCategorie = new ArrayList<>();
        ArrayList<String> l_NuoveCategorie = new ArrayList<>();

        gestionaleDAO.getPromozioni(l_CF, l_DatePassaggio, l_Contratti, l_VecchieCategorie, l_NuoveCategorie);

        int i=0;
        for(; i<l_CF.size(); i++)
            l_Promozioni.add(new Promozione(l_CF.get(i), l_DatePassaggio.get(i), l_Contratti.get(i),
                    l_VecchieCategorie.get(i), l_NuoveCategorie.get(i)));

        return l_Promozioni;
    }

    /**
     * Get progetti db list.
     *
     * @return the list
     */
    public List<Progetto> getProgettiDB(){
        GestionaleDAO gestionaleDAO = new ImplementazioneGestionaleDAO();

        ArrayList<String> l_CUP = new ArrayList<>();
        ArrayList<String> l_RefSci = new ArrayList<>();
        ArrayList<String> l_Resp = new ArrayList<>();
        ArrayList<String> l_Nomi = new ArrayList<>();
        ArrayList<Float> l_Budget = new ArrayList<>();

        gestionaleDAO.getProgetti(l_CUP, l_RefSci, l_Resp, l_Nomi, l_Budget);

        int i =0;

        for(; i<l_CUP.size(); i++)
            l_Progetti.add(new Progetto(l_CUP.get(i), l_RefSci.get(i), l_Resp.get(i),
                    l_Nomi.get(i), l_Budget.get(i)));

        return l_Progetti;
    }

    /**
     * Get lista cf array list.
     *
     * @return the array list
     */
//METODI PER SVILUPPARE LA GUI DI IMPIEGATO
    public ArrayList<String> getListaCF(){
        ArrayList<String> l_CF = new ArrayList<>();

        for(Impiegato i : l_Impiegati)
            l_CF.add(i.getCf());

        return l_CF;
    }

    /**
     * Get lista nomi imp array list.
     *
     * @return the array list
     */
    public ArrayList<String> getListaNomiImp(){
        ArrayList<String> l_Nomi = new ArrayList<>();

        for(Impiegato i : l_Impiegati)
            l_Nomi.add(i.getNome());

        return l_Nomi;
    }

    /**
     * Get lista cognomi array list.
     *
     * @return the array list
     */
    public ArrayList<String> getListaCognomi(){
        ArrayList<String> l_Cognomi = new ArrayList<>();

        for(Impiegato i : l_Impiegati)
            l_Cognomi.add(i.getCognome());

        return l_Cognomi;
    }

    /**
     * Get lista promozioni array list.
     *
     * @param cf the cf
     * @return the array list
     */
    public ArrayList<String> getListaPromozioni(String cf){
        ArrayList<String> promozioni = new ArrayList<>();
        ArrayList<Date> date = new ArrayList<>();

        ImpiegatoDAO impDao = new ImplementazioneImpiegatoDAO();
        impDao.getPromozioniImp(cf, promozioni, date);

        ArrayList<String> info = new ArrayList<>();
        int n_Ele = promozioni.size() == date.size() ? promozioni.size() : 0;
        if(n_Ele>0){
            for(int i = 0; i<n_Ele; i++){
                String data = date.get(i).toString();
                info.add(promozioni.get(i));
                info.add(data);
            }
        }
        return info;
    }

    /**
     * Get afferenze imp array list.
     *
     * @param cf the cf
     * @return the array list
     */
    public ArrayList<String> getAfferenzeImp(String cf){
        ImpiegatoDAO impiegatoDAO = new ImplementazioneImpiegatoDAO();

        Impiegato impiegato = null;

        for(int i=0; i<l_Impiegati.size(); i++){
            if(l_Impiegati.get(i).getCf().equals(cf)) {
                impiegato = l_Impiegati.get(i);
                break;
            }
        }

        ArrayList<String> nomelab = new ArrayList<>();
        impiegatoDAO.getAfferenzeImp(cf, nomelab);

        if(impiegato != null && !nomelab.isEmpty()) {
            impiegato.setLaboratorio(nomelab.get(0));
            return nomelab;
        }

        return null;
    }

    /**
     * Get progetti imp array list.
     *
     * @param cf the cf
     * @return the array list
     */
    public ArrayList<String> getProgettiImp(String cf){
        ImpiegatoDAO impiegatoDAO = new ImplementazioneImpiegatoDAO();

        Impiegato impiegato = null;

        for(int i=0; i<l_Impiegati.size(); i++){
            if(l_Impiegati.get(i).getCf().equals(cf)){
                impiegato = l_Impiegati.get(i);
                break;
            }
        }

        ArrayList<String> progetto = new ArrayList<>();
        impiegatoDAO.getProgettiLab(cf, progetto);

        if(impiegato != null) {
            impiegato.setProgetto(progetto.get(0));
            return progetto;
        }

        return null;
    }

    /**
     * Aggiungi imp.
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
    public void aggiungiImp(String cf, String nome, String cognome, Date dataNascita, Date dataAssunzione,
                            String codiceCon, boolean merito, float salario, String categoria, int eta) throws SQLException {

        ImpiegatoDAO impiegatoDAO = new ImplementazioneImpiegatoDAO();

        if(merito) {
            categoria = "Dirigente";
            impiegatoDAO.inserisciImpiegato(cf, nome, cognome, dataNascita,
                    dataAssunzione, codiceCon, merito, salario, categoria, eta);
        }else
            impiegatoDAO.inserisciImpiegato(cf, nome, cognome, dataNascita,
                    dataAssunzione, codiceCon, merito, salario, categoria, eta);

        l_Impiegati.clear();
        getImpiegatiDB();
    }

    /**
     * Rimuovi imp.
     *
     * @param cf the cf
     * @throws SQLException the sql exception
     */
    public void rimuoviImp(String cf) throws SQLException {
        ImpiegatoDAO impiegatoDAO = new ImplementazioneImpiegatoDAO();

        impiegatoDAO.rimuoviImpiegato(cf);

        l_Impiegati.removeIf(i -> i.getCf().equals(cf));
    }

    /**
     * Promuovi imp.
     *
     * @param cf     the cf
     * @param merito the merito
     * @throws SQLException the sql exception
     */
    public void promuoviImp(String cf, boolean merito) throws SQLException {
        ImpiegatoDAO impiegatoDAO = new ImplementazioneImpiegatoDAO();

        impiegatoDAO.promuoviImpiegato(cf,merito);

        l_Impiegati.clear();
        getImpiegatiDB();
    }

    /**
     * Get lista nomi lab array list.
     *
     * @return the array list
     */
//METODI PER SVILUPPARE LA GUI DI LABORATORIO
    public ArrayList<String> getListaNomiLab(){
        ArrayList<String> l_Nomi = new ArrayList<>();

        for(Laboratorio l : l_Laboratori)
            l_Nomi.add(l.getNome());

        return l_Nomi;
    }

    /**
     * Get lista resp sci array list.
     *
     * @return the array list
     */
    public ArrayList<String> getListaRespSci(){
        ArrayList<String> l_RespSci = new ArrayList<>();

        for(Laboratorio l : l_Laboratori)
            l_RespSci.add(l.getResp_sci());

        return l_RespSci;
    }

    /**
     * Get lista topic lab array list.
     *
     * @return the array list
     */
    public ArrayList<String> getListaTopicLab(){
        ArrayList<String> l_Topic = new ArrayList<>();

        for(Laboratorio l : l_Laboratori)
            l_Topic.add(l.getTopic());

        return l_Topic;
    }

    /**
     * Get lista n afferenti array list.
     *
     * @return the array list
     */
    public ArrayList<Integer> getListaN_Afferenti(){
        ArrayList<Integer> l_Afferenti = new ArrayList<>();

        for(Laboratorio l : l_Laboratori)
            l_Afferenti.add(l.getN_afferenti());

        return l_Afferenti;
    }

    /**
     * Gets resp sci lab.
     *
     * @param nome the nome
     * @return the resp sci lab
     */
    public ArrayList<String> getRespSciLab(String nome) {
        LaboratorioDAO laboratorioDAO = new ImplementazioneLaboratorioDAO();

        Laboratorio laboratorio = null;

        for (int i = 0; i<l_Laboratori.size(); i++) {
            if (l_Laboratori.get(i).getNome().equals(nome)) {
                laboratorio = l_Laboratori.get(i);
                break;
            }
        }

        ArrayList<String> cf_Resp = new ArrayList<>();
        laboratorioDAO.getRespSci(nome, cf_Resp);

        if (laboratorio != null) {
            laboratorio.setResp_sci(cf_Resp.get(0));
            return cf_Resp;
        }

        return null;
    }

    /**
     * Gets afferenze lab.
     *
     * @param nome the nome
     * @return the afferenze lab
     */
    public ArrayList<String> getAfferenzeLab(String nome) {
        LaboratorioDAO laboratorioDAO = new ImplementazioneLaboratorioDAO();

        Laboratorio laboratorio = null;

        for (Laboratorio l : l_Laboratori) {
            if (l.getNome().equals(nome))
                laboratorio = l;
            break;
        }

        ArrayList<String> l_Afferenti = new ArrayList<>();
        laboratorioDAO.afferenzeLab(nome, l_Afferenti);

        if (laboratorio != null) {
            laboratorio.setListAfferenti(l_Afferenti);
            return l_Afferenti;
        }

        return null;
    }

    /**
     * Gets cu pfrom lab.
     *
     * @param nome the nome
     * @return the cu pfrom lab
     */
    public String getCUPfromLab(String nome) {
        LaboratorioDAO laboratorioDAO = new ImplementazioneLaboratorioDAO();

        Laboratorio laboratorio = null;

        for(int i=0; i<l_Laboratori.size() ; i++){
            laboratorio = l_Laboratori.get(i);
            if(laboratorio.getNome().equals(nome))
                break;
        }

        ArrayList<String> ProgettoCup = new ArrayList<>();
        laboratorioDAO.getProgLavora(nome, ProgettoCup);
        if(ProgettoCup.size() != 0) {
            if (laboratorio != null) {
                laboratorio.setCup(ProgettoCup.get(0));
                return ProgettoCup.get(0);
            }
        }

        return null;
    }

    /**
     * Aggiungi lab.
     *
     * @param nomeLab the nome lab
     * @param respSci the resp sci
     * @param topic   the topic
     * @throws SQLException the sql exception
     */
    public void aggiungiLab(String nomeLab, String respSci, String topic) throws SQLException {
        LaboratorioDAO laboratorioDAO = new ImplementazioneLaboratorioDAO();

        laboratorioDAO.inserisciLaboratorio(nomeLab, respSci, topic);

        l_Laboratori.clear();
        getLaboratoriDB();
    }

    /**
     * Rimuovi lab.
     *
     * @param nome the nome
     * @throws SQLException the sql exception
     */
    public void rimuoviLab(String nome) throws SQLException {
        LaboratorioDAO laboratorioDAO = new ImplementazioneLaboratorioDAO();

        laboratorioDAO.rimuoviLaboratorio(nome);

        l_Laboratori.removeIf(l -> l.getNome().equals(nome));
    }

    /**
     * Aggiungi afferente lab.
     *
     * @param nome the nome
     * @param cf   the cf
     * @throws SQLException the sql exception
     */
    public void aggiungiAfferenteLab(String nome, String cf) throws SQLException {
        LaboratorioDAO laboratorioDAO = new ImplementazioneLaboratorioDAO();

        laboratorioDAO.aggiungiAfferente(nome, cf);

        l_Laboratori.clear();
        getLaboratoriDB();
    }

    /**
     * Get lista cup array list.
     *
     * @return the array list
     */
//METODI PER SVILUPPARE LA GUI DI PROGETTO
    public ArrayList<String> getListaCUP(){
        ArrayList<String> l_CUP = new ArrayList<>();

        for(Progetto p : l_Progetti)
            l_CUP.add(p.getCup());

        return l_CUP;
    }

    /**
     * Get lista ref sci array list.
     *
     * @return the array list
     */
    public ArrayList<String> getListaRefSci(){
        ArrayList<String> l_RefSci = new ArrayList<>();

        for(Progetto p : l_Progetti)
            l_RefSci.add(p.getRef_sci());

        return l_RefSci;
    }

    /**
     * Get lista resp array list.
     *
     * @return the array list
     */
    public ArrayList<String> getListaResp(){
        ArrayList<String> l_Resp = new ArrayList<>();

        for(Progetto p : l_Progetti)
            l_Resp.add(p.getResp());

        return l_Resp;
    }

    /**
     * Get lista nomi prog array list.
     *
     * @return the array list
     */
    public ArrayList<String> getListaNomiProg(){
        ArrayList<String> l_Nomi = new ArrayList<>();

        for(Progetto p : l_Progetti)
            l_Nomi.add(p.getNome());

        return l_Nomi;
    }

    /**
     * Gets lab from cup.
     *
     * @param cup the cup
     * @return the lab from cup
     */
    public ArrayList<String> getLabFromCUP(String cup) {
        ProgettoDAO progettoDAO = new ImplementazioneProgettoDAO();

        Progetto progetto = null;

        for (Progetto p : l_Progetti) {
            if (p.getCup().equals(cup))
                progetto = p;
            break;
        }

        ArrayList<String> l_Lab = new ArrayList<>();
        progettoDAO.getLabProg(cup, l_Lab);

        if (progetto != null) {
            progetto.setListaLab(l_Lab);
        }

        return l_Lab;
    }

    /**
     * Get info ref resp array list.
     *
     * @param cup the cup
     * @return the array list
     */
    public ArrayList<String> getInfoRefResp(String cup){
        ProgettoDAO progettoDAO = new ImplementazioneProgettoDAO();

        Progetto progetto = null;
        for(Progetto p : l_Progetti){
            if(p.getCup().equals(cup)) {
                progetto = p;
                break;
            }
        }
        ArrayList<String> info = progettoDAO.getProgImp(cup);
        if(progetto!=null){
            progetto.setListaInfoImp(info);
            return info;
        }

        return null;
    }

    /**
     * Aggiungi progetto.
     *
     * @param cup    the cup
     * @param refSci the ref sci
     * @param resp   the resp
     * @param nome   the nome
     * @param budget the budget
     * @throws SQLException the sql exception
     */
    public void aggiungiProgetto(String cup, String refSci, String resp, String nome, float budget)
        throws SQLException{
        ProgettoDAO progettoDAO = new ImplementazioneProgettoDAO();

        progettoDAO.inserisciProgetto(cup, refSci, resp, nome, budget);

        l_Progetti.clear();
        getProgettiDB();
    }

    /**
     * Rimuovi progetto.
     *
     * @param cup the cup
     * @throws SQLException the sql exception
     */
    public void rimuoviProgetto(String cup) throws SQLException{
        ProgettoDAO progettoDAO = new ImplementazioneProgettoDAO();

        progettoDAO.rimuoviProgetto(cup);

        l_Progetti.removeIf(p -> p.getCup().equals(cup));
    }

    /**
     * Aggiungi laboratorio.
     *
     * @param cup     the cup
     * @param nomelab the nomelab
     * @throws SQLException the sql exception
     */
    public void aggiungiLaboratorio(String cup, String nomelab) throws SQLException{
        ProgettoDAO progettoDAO = new ImplementazioneProgettoDAO();

        progettoDAO.aggiungiLaboratorio(cup, nomelab);

        l_Progetti.clear();
        getProgettiDB();
    }
}
