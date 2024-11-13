package Model;

import java.sql.Date;
import java.util.ArrayList;

/**
 * The type Impiegato.
 */
public class Impiegato {
    private String cf;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private Date dataAssunzione;
    private String codiceCon;
    private boolean merito;
    private float salario;
    private String categoria;
    private int eta;

    private ArrayList<Promozione> promozioni = new ArrayList<>();

    private String laboratorio = new String();

    private String progetto = new String();

    /**
     * Instantiates a new Impiegato.
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
     */
    public Impiegato(String cf, String nome, String cognome, Date dataNascita, Date dataAssunzione,
                     String codiceCon, boolean merito, float salario, String categoria, int eta){
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.dataAssunzione = dataAssunzione;
        this.codiceCon = codiceCon;
        this.merito = merito;
        this.salario = salario;
        this.categoria = categoria;
        this.eta = eta;
    }

    /**
     * Sets cf.
     *
     * @param cf the cf
     */
    public void setCf(String cf) {
        this.cf = cf;
    }

    /**
     * Gets cf.
     *
     * @return the cf
     */
    public String getCf() {
        return cf;
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
     * Sets cognome.
     *
     * @param cognome the cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Gets cognome.
     *
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets data assunzione.
     *
     * @param dataAssunzione the data assunzione
     */
    public void setDataAssunzione(Date dataAssunzione) {
        this.dataAssunzione = dataAssunzione;
    }

    /**
     * Gets data assunzione.
     *
     * @return the data assunzione
     */
    public Date getDataAssunzione() {
        return dataAssunzione;
    }

    /**
     * Set data nascita.
     *
     * @param dataNascita the data nascita
     */
    public void setDataNascita(Date dataNascita){this.dataNascita = dataNascita;}

    /**
     * Get data nascita date.
     *
     * @return the date
     */
    public Date getDataNascita(){return dataNascita;}

    /**
     * Sets codice con.
     *
     * @param codiceCon the codice con
     */
    public void setCodiceCon(String codiceCon) {
        this.codiceCon = codiceCon;
    }

    /**
     * Gets codice con.
     *
     * @return the codice con
     */
    public String getCodiceCon() {
        return codiceCon;
    }

    /**
     * Sets merito.
     *
     * @param merito the merito
     */
    public void setMerito(boolean merito) {
        this.merito = merito;
    }

    /**
     * Has merito boolean.
     *
     * @return the boolean
     */
    public boolean hasMerito() {
        return merito;
    }

    /**
     * Sets salario.
     *
     * @param salario the salario
     */
    public void setSalario(float salario) {
        this.salario = salario;
    }

    /**
     * Gets salario.
     *
     * @return the salario
     */
    public float getSalario() {
        return salario;
    }

    /**
     * Sets categoria.
     *
     * @param categoria the categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Gets categoria.
     *
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Sets eta.
     *
     * @param eta the eta
     */
    public void setEta(int eta) {
        this.eta = eta;
    }

    /**
     * Gets eta.
     *
     * @return the eta
     */
    public int getEta() {
        return eta;
    }

    /**
     * Sets promozioni.
     *
     * @param promozioni the promozioni
     */
    public void setPromozioni(ArrayList<Promozione> promozioni) {
        this.promozioni = promozioni;
    }

    /**
     * Gets promozioni.
     *
     * @return the promozioni
     */
    public ArrayList<Promozione> getPromozioni() {
        return promozioni;
    }

    /**
     * Sets laboratorio.
     *
     * @param laboratorio the laboratorio
     */
    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    /**
     * Gets laboratorio.
     *
     * @return the laboratorio
     */
    public String getLaboratorio() {
        return laboratorio;
    }

    /**
     * Sets progetto.
     *
     * @param progetto the progetto
     */
    public void setProgetto(String progetto) {
        this.progetto = progetto;
    }

    /**
     * Gets progetto.
     *
     * @return the progetto
     */
    public String getProgetto() {
        return progetto;
    }
}