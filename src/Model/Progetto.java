package Model;

import java.util.ArrayList;

/**
 * The type Progetto.
 */
public class Progetto {
    private String cup;
    private String ref_sci;
    private String resp;
    private String nome;
    private float budget;

    private ArrayList<String> listaLab = new ArrayList<>();
    private ArrayList<String> listaInfoImp = new ArrayList<>();

    /**
     * Instantiates a new Progetto.
     *
     * @param cup     the cup
     * @param ref_sci the ref sci
     * @param resp    the resp
     * @param nome    the nome
     * @param budget  the budget
     */
    public Progetto(String cup, String ref_sci, String resp, String nome, float budget){
        this.cup = cup;
        this.ref_sci = ref_sci;
        this.resp = resp;
        this.nome = nome;
        this.budget = budget;
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

    /**
     * Sets ref sci.
     *
     * @param ref_sci the ref sci
     */
    public void setRef_sci(String ref_sci) {
        this.ref_sci = ref_sci;
    }

    /**
     * Gets ref sci.
     *
     * @return the ref sci
     */
    public String getRef_sci() {
        return ref_sci;
    }

    /**
     * Sets resp.
     *
     * @param resp the resp
     */
    public void setResp(String resp) {
        this.resp = resp;
    }

    /**
     * Gets resp.
     *
     * @return the resp
     */
    public String getResp() {
        return resp;
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
     * Sets budget.
     *
     * @param budget the budget
     */
    public void setBudget(float budget) {
        this.budget = budget;
    }

    /**
     * Gets budget.
     *
     * @return the budget
     */
    public float getBudget() {
        return budget;
    }

    /**
     * Aggiungi lab.
     *
     * @param lab the lab
     */
    public void aggiungiLab(String lab) {
        listaLab.add(lab);
    }

    /**
     * Rimuovi lab.
     *
     * @param lab the lab
     */
    public void rimuoviLab(Laboratorio lab) {
        listaLab.remove(lab);
    }

    /**
     * Sets lista lab.
     *
     * @param listaLab the lista lab
     */
    public void setListaLab(ArrayList<String> listaLab) {
        this.listaLab = listaLab;
    }

    /**
     * Gets lista lab.
     *
     * @return the lista lab
     */
    public ArrayList<String> getListaLab() {
        return listaLab;
    }

    /**
     * Sets lista info imp.
     *
     * @param listaInfoImp the lista info imp
     */
    public void setListaInfoImp(ArrayList<String> listaInfoImp) {
        this.listaInfoImp = listaInfoImp;
    }

    /**
     * Gets lista info imp.
     *
     * @return the lista info imp
     */
    public ArrayList<String> getListaInfoImp() {
        return listaInfoImp;
    }
}