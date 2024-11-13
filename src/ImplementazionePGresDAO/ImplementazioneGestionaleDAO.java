package ImplementazionePGresDAO;

import ConnectDB.DbConn;
import DAO.GestionaleDAO;

import java.sql.*;
import java.util.ArrayList;

/**
 * The type Implementazione gestionale dao.
 */
public class ImplementazioneGestionaleDAO implements GestionaleDAO {
    /**
     * The Db.
     */
    DbConn db = new DbConn();
    /**
     * The C.
     */
    Connection c = null;

    /**
     * Instantiates a new Implementazione gestionale dao.
     */
    public ImplementazioneGestionaleDAO(){
        try{
            c = db.conn_db("postgres", "postgres", "Blacks_27");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //l_ prima di ogni variabile fa riferimento al fatto che sia una lista, esempio: l_CF = lista codici fiscali

    @Override
    public void getImpiegati(ArrayList<String> l_CF, ArrayList<String> l_Nomi, ArrayList<String> l_Cognomi, ArrayList<Date> l_DateNascita, ArrayList<Date> l_DateAssunzioni, ArrayList<String> l_Contratti, ArrayList<Boolean> l_Merito, ArrayList<Float> l_Salari, ArrayList<String> l_Categorie, ArrayList<Integer> l_Eta) {
        try {
            String query = "SELECT * FROM impiegato";
            PreparedStatement stm = c.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                l_CF.add(rs.getString("cf"));
                l_Nomi.add(rs.getString("nome"));
                l_Cognomi.add(rs.getString("cognome"));
                l_DateNascita.add(rs.getDate("datanascita"));
                l_DateAssunzioni.add(rs.getDate("dataassunzione"));
                l_Contratti.add(rs.getString("codicecon"));
                l_Merito.add(rs.getBoolean("merito"));
                l_Categorie.add(rs.getString("categoria"));
                l_Salari.add(rs.getFloat("salario"));
                l_Eta.add(rs.getInt("eta"));
            }

            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getPromozioni(ArrayList<String> l_CF, ArrayList<Date> l_DatePassaggio, ArrayList<String> l_Contratti, ArrayList<String> l_VecchieCategorie, ArrayList<String> l_NuoveCategorie) {
        try{
            String query = "SELECT * FROM promozione";
            PreparedStatement stm = c.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                l_CF.add(rs.getString("cf"));
                l_DatePassaggio.add(rs.getDate("datapassaggio"));
                l_Contratti.add(rs.getString("codicecon"));
                l_VecchieCategorie.add(rs.getString("vecchiacategoria"));
                l_NuoveCategorie.add(rs.getString("nuovacategoria"));
            }

            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLaboratori(ArrayList<String> l_Nomi, ArrayList<String> l_RespSci, ArrayList<String> l_Topic, ArrayList<Integer> l_NumeroAfferenti) {
        try{
            String query = "SELECT * FROM laboratorio";
            PreparedStatement stm = c.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                l_Nomi.add(rs.getString("nome"));
                l_RespSci.add(rs.getString("respscie"));
                l_Topic.add(rs.getString("topic"));
                l_NumeroAfferenti.add(rs.getInt("n_afferenti"));
            }

            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getProgetti(ArrayList<String> l_CUP, ArrayList<String> l_RefSci, ArrayList<String> l_Resp, ArrayList<String> l_Nomi, ArrayList<Float> l_Budget) {
        try{
            String query = "SELECT * FROM progetto";
            PreparedStatement stm = c.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                l_CUP.add(rs.getString("cup"));
                l_RefSci.add(rs.getString("refscie"));
                l_Resp.add(rs.getString("respscie"));
                l_Nomi.add(rs.getString("nome"));
                l_Budget.add(rs.getFloat("budget"));
            }

            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getAfferenti(String nomeLab, ArrayList<String> l_CF) {
        try{
            String query = "SELECT * FROM utilizza WHERE nomelab = ?";
            PreparedStatement stm = c.prepareStatement(query);
            stm.setString(1,nomeLab);
            ResultSet rs = stm.executeQuery();

            while(rs.next())
                l_CF.add(rs.getString("cf"));

            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
