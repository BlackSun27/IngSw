package Model;

import java.util.ArrayList;

/**
 * The type Laboratorio.
 */
public class Laboratorio {
    private String nome;
    private String resp_sci;
    private String topic;
    private int n_afferenti = 1;

    private ArrayList<String> listAfferenti = new ArrayList<>();
    private String cup = new String();

    /**
     * Instantiates a new Laboratorio.
     *
     * @param nome        the nome
     * @param resp_sci    the resp sci
     * @param topic       the topic
     * @param n_afferenti the n afferenti
     */
    public Laboratorio(String nome, String resp_sci, String topic, int n_afferenti){
        this.nome = nome;
        this.resp_sci = resp_sci;
        this.topic = topic;
        this.n_afferenti = n_afferenti;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Gets resp sci.
     *
     * @return the resp sci
     */
    public String getResp_sci() {
        return resp_sci;
    }

    /**
     * Sets resp sci.
     *
     * @param resp_sci the resp sci
     */
    public void setResp_sci(String resp_sci) {
        this.resp_sci = resp_sci;
    }

    /**
     * Sets topic.
     *
     * @param topic the topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Gets topic.
     *
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Sets n afferenti.
     *
     * @param n_afferenti the n afferenti
     */
    public void setN_afferenti(int n_afferenti) {
        this.n_afferenti = n_afferenti;
    }

    /**
     * Gets n afferenti.
     *
     * @return the n afferenti
     */
    public int getN_afferenti() {
        return n_afferenti;
    }

    /**
     * Aggiungi aff.
     *
     * @param cf the cf
     */
    public void aggiungiAff(String cf){
        listAfferenti.add(cf);
        this.n_afferenti += 1;
    }

    /**
     * Rimuovi aff.
     *
     * @param cf the cf
     */
    public void rimuoviAff(String cf){
        listAfferenti.remove(cf);
        this.n_afferenti -= 1;
    }

    /**
     * Sets list afferenti.
     *
     * @param listAfferenti the list afferenti
     */
    public void setListAfferenti(ArrayList<String> listAfferenti) {
        this.listAfferenti = listAfferenti;
    }

    /**
     * Gets list afferenti.
     *
     * @return the list afferenti
     */
    public ArrayList<String> getListAfferenti() {
        return listAfferenti;
    }

    /**
     * Sets cup.
     *
     * @param cup the cup
     */
    public void setCup(String cup) {
        this.cup = cup;
    }

    /**
     * Gets cup.
     *
     * @return the cup
     */
    public String getCup() {
        return cup;
    }


}