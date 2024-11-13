package ImplementazionePGresDAO;

import ConnectDB.DbConn;
import DAO.ImpiegatoDAO;

import java.sql.*;
import java.util.ArrayList;

/**
 * The type Implementazione impiegato dao.
 */
public class ImplementazioneImpiegatoDAO implements ImpiegatoDAO {
    //private DbConn db;
    private Connection c;

    /**
     * Instantiates a new Implementazione impiegato dao.
     */
    public ImplementazioneImpiegatoDAO(){
        DbConn db = new DbConn();
        try{
            c = db.conn_db("postgres", "postgres", "Blacks_27");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void inserisciImpiegato(String cf, String nome, String cognome, Date dataNascita, Date dataAssunzione,
                                   String codiceCon, boolean merito, float salario, String categoria, int eta)
            throws SQLException {
        String queryImp = "CALL inserisciimpiegato(?,?,?,?,?,?,?,?,?::numeric);";

        CallableStatement stm = c.prepareCall(queryImp);
        stm.setString(1, cf);
        stm.setString(2, nome);
        stm.setString(3, cognome);
        stm.setDate(4, dataNascita);
        stm.setBoolean(5, merito);
        stm.setString(6, codiceCon);
        stm.setDate(7, dataAssunzione);
        stm.setString(8, categoria);
        stm.setDouble(9, salario);

        stm.executeUpdate();
    }

    @Override
    public void rimuoviImpiegato(String cf) throws SQLException{
        String query = "CALL rimuovi_impiegato(?); ";
        PreparedStatement stm = c.prepareStatement(query);
        stm.setString(1, cf);
        stm.executeUpdate();
    }

    @Override
    public void promuoviImpiegato(String cf, boolean merito) throws SQLException{
        String query = "CALL inseriscipromozioni(?,?)";
        CallableStatement stm = c.prepareCall(query);
        stm.setString(1, cf);
        stm.setBoolean(2, merito);
        stm.executeUpdate();
    }

    @Override
    public void getAfferenzeImp(String cf, ArrayList<String> laboratorio){
        try {
            String query = "SELECT nomelab FROM utilizza WHERE cf = ?";
            PreparedStatement stm = c.prepareStatement(query);
            stm.setString(1, cf);
            ResultSet rs = stm.executeQuery();

            while (rs.next())
                laboratorio.add(rs.getString(1));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getProgettiLab(String cf,ArrayList<String> progetto){
        try {
            String query = "SELECT * FROM lavora_view(?)";
            PreparedStatement stm = c.prepareStatement(query);

            stm.setString(1,cf);

            ResultSet rs = stm.executeQuery();

            if(rs.next())
                progetto.add(rs.getString(1));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void getPromozioniImp(String cf, ArrayList<String> l_Promozioni, ArrayList<Date> date){
        try{
            String query = "SELECT * FROM promozioni WHERE cf = ?";
            PreparedStatement stm = c.prepareStatement(query);
            stm.setString(1, cf);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                for(int i=2; i<10; i++){
                    if(i%2 == 0) {
                        String promozione = rs.getString(i);
                        if (promozione != null)
                            l_Promozioni.add(promozione);
                    }else {
                        Date data = rs.getDate(i);
                        if (data != null)
                            date.add(data);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}