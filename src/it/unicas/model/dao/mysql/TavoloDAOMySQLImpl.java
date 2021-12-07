package it.unicas.model.dao.mysql;

import it.unicas.model.Tavolo;
import it.unicas.model.dao.DAO;
import it.unicas.model.dao.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TavoloDAOMySQLImpl implements DAO<Tavolo>{

    private TavoloDAOMySQLImpl(){}
    private static DAO dao =null;
    private static Logger logger = null;



    public static DAO getInstance(){
        if (dao == null){
            dao = new TavoloDAOMySQLImpl();
            logger = Logger.getLogger(TavoloDAOMySQLImpl.class.getName());
        }
        return dao;
    }

    public static void main(String args[]) throws DAOException, SQLException {

        TavoloDAOMySQLImpl c = new TavoloDAOMySQLImpl();

        List<Tavolo> list = c.select(null);
        //test di stampa
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }


}

    public List<Tavolo> select(Tavolo t) throws SQLException {


            ArrayList<Tavolo> lista = new ArrayList<>();
            Statement st = DAOMySQLSettings.getStatement();
            String sql = "SELECT * FROM tavolo where locazione_tavolo = '"+ t.getLocazione_tavolo()+"' and occupato = '"
                    +t.isOccupato()+"'";
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                lista.add(new Tavolo(rs.getInt("numero_tavolo"),
                        rs.getInt("occupato"),
                        rs.getString("locazione_tavolo")));
            }
            System.out.println(sql);
            DAOMySQLSettings.closeStatement(st);


            return lista;


    }

    @Override
    public void update(Tavolo a) throws DAOException {

    }

    @Override
    public void insert(Tavolo a) throws DAOException, SQLException {
        try {
            Statement st = DAOMySQLSettings.getStatement();
            String sql = "INSERT INTO tavolo(numero_tavolo, occupato, locazione_tavolo) VALUES ('"
                    + a.getNumero_tavolo() + "','" + a.isOccupato() + "','"
                    + a.getLocazione_tavolo() + "')";
            st.executeUpdate(sql);

            System.out.println(sql);
            DAOMySQLSettings.closeStatement(st);

            logger.info("SQL" + sql);

            }catch (SQLException e){
            e.printStackTrace();
        }


    }

    @Override
    public void delete(Tavolo a) throws DAOException, SQLException {

        Statement st = DAOMySQLSettings.getStatement();
        String sql = "DELETE FROM tavolo WHERE locazione_tavolo = '"+ a.getLocazione_tavolo()+"' and occupato = '"
                +a.isOccupato()+"'and numero_tavolo ='"+a.getNumero_tavolo()+"'";
        int n = st.executeUpdate(sql);

        System.out.println(sql);
        DAOMySQLSettings.closeStatement(st);


    }

    @Override
    public List<Tavolo> join(Tavolo a) throws DAOException, SQLException {
        return null;
    }


}
