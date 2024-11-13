package ImplementazionePGresDAO;

import ConnectDB.DbConn;
import DAO.LaboratorioDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The type Implementazione laboratorio dao.
 */
public class ImplementazioneLaboratorioDAO implements LaboratorioDAO {
    //private DbConn db;
    private Connection c;

    /**
     * Instantiates a new Implementazione laboratorio dao.
     */
    public ImplementazioneLaboratorioDAO() {
        DbConn db = new DbConn();
        try {
            c = db.conn_db("postgres", "postgres", "Blacks_27");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inserisciLaboratorio(String nome, String respSci, String topic)
            throws SQLException {
        String query = "CALL inseriscilaboratorio(?,?,?); ";
        PreparedStatement stm = c.prepareStatement(query);
        stm.setString(1, nome);
        stm.setString(2, respSci);
        stm.setString(3, topic);

        stm.executeUpdate();
    }

    @Override
    public void rimuoviLaboratorio(String nome) throws SQLException{
        String query = "CALL rimuovi_laboratorio(?);";
        PreparedStatement stm = c.prepareStatement(query);
        stm.setString(1,nome);

        stm.executeUpdate();
    }

    @Override
    public void aggiungiAfferente(String nomeLab, String cf) throws SQLException{
        String queryUti = "CALL inserisciutilizza(?,?)";
        PreparedStatement stm = c.prepareStatement(queryUti);
        stm.setString(1, cf);
        stm.setString(2, nomeLab);
        stm.executeUpdate();
    }

    @Override
    public void afferenzeLab(String nomelab, ArrayList<String> l_CF){
        try{
            String query = "SELECT cf FROM utilizza WHERE nomelab = ?";
            PreparedStatement stm = c.prepareStatement(query);
            stm.setString(1,nomelab);

            ResultSet rs = stm.executeQuery();

            while(rs.next())
                l_CF.add(rs.getString(1));

        }catch (SQLException e){
            System.out.println("Errore a ottenere informazioni sulle afferenze!");
            e.printStackTrace();
        }
    }

    @Override
    public void getRespSci(String nomelab, ArrayList<String> resp){
        try{
            String query = """
                    SELECT i.cf, i.nome, i.cognome
                    FROM laboratorio AS u
                    JOIN Impiegato AS i ON u.respscie = i.cf
                    WHERE u.nome = ?""";
            PreparedStatement stm = c.prepareStatement(query);
            stm.setString(1, nomelab);

            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                resp.add(rs.getString("cf"));
                resp.add(rs.getString("nome"));
                resp.add(rs.getString("cognome"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getProgLavora(String nomelab, ArrayList<String> CUP){
        try{
            String query = "SELECT cup FROM lavora WHERE lab1 = ? OR lab2 = ? OR lab3 = ?";
            PreparedStatement stm = c.prepareStatement(query);
            stm.setString(1,nomelab);
            stm.setString(2,nomelab);
            stm.setString(3,nomelab);

            ResultSet rs = stm.executeQuery();

            while(rs.next())
                CUP.add(rs.getString(1));

        }catch (SQLException e){
            System.out.println("Errore a ottenere informazioni sulle afferenze!");
            e.printStackTrace();
        }
    }
}