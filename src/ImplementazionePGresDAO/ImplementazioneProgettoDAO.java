package ImplementazionePGresDAO;

import ConnectDB.DbConn;
import DAO.ProgettoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The type Implementazione progetto dao.
 */
public class ImplementazioneProgettoDAO implements ProgettoDAO {
    //private DbConn db;
    private Connection c;

    /**
     * Instantiates a new Implementazione progetto dao.
     */
    public ImplementazioneProgettoDAO() {
        DbConn db = new DbConn();
        try {
            c = db.conn_db("postgres", "postgres", "Blacks_27");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inserisciProgetto(String cup, String refSci, String resp, String nome, float budget)
            throws SQLException {
        String query = "CALL inserisciprogetto(?,?,?,?,?::NUMERIC); ";
        PreparedStatement stm = c.prepareStatement(query);
        stm.setString(1, cup);
        stm.setString(2, refSci);
        stm.setString(3, resp);
        stm.setString(4, nome);
        stm.setString(5, String.valueOf(budget));

        stm.executeUpdate();
    }

    @Override
    public void rimuoviProgetto(String cup) throws SQLException {
        String query = "CALL rimuovi_progetto(?); ";
        PreparedStatement stm = c.prepareStatement(query);
        stm.setString(1, cup);

        stm.executeUpdate();
    }

    @Override
    public void aggiungiLaboratorio(String cup, String nomeLab) throws SQLException{
        String query = "CALL inseriscilavora(?)";
        PreparedStatement stm = c.prepareStatement(query);
        stm.setString(1, cup);

        stm.executeUpdate();
    }

    @Override
    public ArrayList<String> getProgImp(String cup){
        String query1 = "SELECT nome, cognome, cf FROM presenza WHERE cup = ?";
        ArrayList<String> info = new ArrayList<>();
        try {
            PreparedStatement stm1 = c.prepareCall(query1);
            stm1.setString(1,cup);
            ResultSet rs = stm1.executeQuery();
            while (rs.next()) {
                //elementi pari -> responsabili
                //elementi dispari -> referenti
                info.add(rs.getString("nome"));
                info.add(rs.getString("cognome"));
                info.add(rs.getString("cf"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            info = null;
        }
        return info;
    }

    @Override
    public void getLabProg(String cup, ArrayList<String> l_Lab){
        try{
            String query = "SELECT lab1, lab2, lab3 FROM lavora WHERE cup = ?";
            PreparedStatement stm = c.prepareStatement(query);
            stm.setString(1, cup);

            ResultSet rs = stm.executeQuery();
            String Lab1 = null;
            String Lab2 = null;
            String Lab3 = null;
            while (rs.next()) {
                Lab1 = rs.getString(1);
                Lab2 = rs.getString(2);
                Lab3 = rs.getString(3);
            }
            l_Lab.add(Objects.requireNonNullElse(Lab1, null));
            l_Lab.add(Objects.requireNonNullElse(Lab2, null));
            l_Lab.add(Objects.requireNonNullElse(Lab3, null));

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}