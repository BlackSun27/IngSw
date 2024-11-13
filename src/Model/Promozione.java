package Model;

import java.sql.Date;

/**
 * The type Promozione.
 */
public class Promozione {
    private String cf;
    private Date dataPassaggio;
    private String codiceCon;
    private String vecchiaCategoria;
    private String nuovaCategoria;

    /**
     * Instantiates a new Promozione.
     *
     * @param cf               the cf
     * @param dataPassaggio    the data passaggio
     * @param codiceCon        the codice con
     * @param vecchiaCategoria the vecchia categoria
     * @param nuovaCategoria   the nuova categoria
     */
    public Promozione(String cf, Date dataPassaggio, String codiceCon, String vecchiaCategoria,
                      String nuovaCategoria){
        this.cf = cf;
        this.dataPassaggio = dataPassaggio;
        this.codiceCon = codiceCon;
        this.vecchiaCategoria = vecchiaCategoria;
        this.nuovaCategoria = nuovaCategoria;
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
     * Sets data passaggio.
     *
     * @param dataPassaggio the data passaggio
     */
    public void setDataPassaggio(Date dataPassaggio) {
        this.dataPassaggio = dataPassaggio;
    }

    /**
     * Gets data passaggio.
     *
     * @return the data passaggio
     */
    public Date getDataPassaggio() {
        return dataPassaggio;
    }

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
     * Sets vecchia categoria.
     *
     * @param vecchiaCategoria the vecchia categoria
     */
    public void setVecchiaCategoria(String vecchiaCategoria) {
        this.vecchiaCategoria = vecchiaCategoria;
    }

    /**
     * Gets vecchia categoria.
     *
     * @return the vecchia categoria
     */
    public String getVecchiaCategoria() {
        return vecchiaCategoria;
    }

    /**
     * Sets nuova categoria.
     *
     * @param nuovaCategoria the nuova categoria
     */
    public void setNuovaCategoria(String nuovaCategoria) {
        this.nuovaCategoria = nuovaCategoria;
    }

    /**
     * Gets nuova categoria.
     *
     * @return the nuova categoria
     */
    public String getNuovaCategoria() {
        return nuovaCategoria;
    }
}
