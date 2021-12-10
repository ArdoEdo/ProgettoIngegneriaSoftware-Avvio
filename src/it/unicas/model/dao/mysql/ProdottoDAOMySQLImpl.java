package it.unicas.model.dao.mysql;

import it.unicas.model.Prodotto;
import it.unicas.model.dao.DAO;
import it.unicas.model.dao.DAOException;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProdottoDAOMySQLImpl implements DAO<Prodotto> {

    private ProdottoDAOMySQLImpl(){};
    private static DAO dao =null;
    private static Logger logger = null;

    public static DAO getInstance(){
        if (dao == null){
            dao = new ProdottoDAOMySQLImpl();
            logger = Logger.getLogger(ProdottoDAOMySQLImpl.class.getName());
        }
        return dao;
    }

    public static void main(String args[]) throws DAOException, SQLException {




    }

    @Override
    public List<Prodotto> select(Prodotto a) throws DAOException, SQLException {
        ArrayList<Prodotto> lista = new ArrayList<>();
        Statement st = DAOMySQLSettings.getStatement();
        String sql="";

        if(a.isAlcolico()==0) {
            sql = "SELECT * FROM prodotto";
        }
        else
        {
            sql="SELECT * FROM prodotto where alcolico=0";
        }

        logger.info("SQL:"+ sql);
        ResultSet rs=st.executeQuery(sql);

        while(rs.next()){
            lista.add(new Prodotto(rs.getInt("id_prodotto"),
                    rs.getString("nome_prodotto"),
                    rs.getString("tipo_prodotto"),
                    rs.getInt("alcolico"),
                    rs.getFloat("prezzo_prodotto"),
                    rs.getInt("quantita_prodotto")));

        }
        DAOMySQLSettings.closeStatement(st);

        return lista;

    }

    @Override
    public void update(Prodotto a) throws DAOException {

    }

    @Override
    public void insert(Prodotto a) throws DAOException {
        try {

            Statement st = DAOMySQLSettings.getStatement();
            String sql = "INSERT INTO prodotto(id_prodotto,nome_prodotto,tipo_prodotto,alcolico, prezzo_prodotto, quantita_prodotto)" +
                    " VALUES ('" + a.getId_prodotto() + "','" + a.getNome_prodotto() + "','"
                    + a.getTipo_prodotto() + "','" + a.isAlcolico() + "','" + a.getPrezzo_prodotto() + "','" + a.getQuantita_prodotto() +
                    "')";
            System.out.println(sql);
            st.executeUpdate(sql);
            DAOMySQLSettings.closeStatement(st);
            logger.info("SQL" + sql);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Prodotto a) throws DAOException, SQLException {

        Statement st = DAOMySQLSettings.getStatement();
        String sql = "DELETE FROM Prodotto WHERE id_prodotto = '"+ a.getId_prodotto() +"'and nome_prodotto = '"+a.getNome_prodotto() +
                     "'and tipo_prodotto = '" + a.getTipo_prodotto() + "' and alcolico = '" + a.isAlcolico() + "'and prezzo_prodotto = '" + a.getPrezzo_prodotto()+
                     "'and quantita_prodotto = '" + a.getQuantita_prodotto() + "'";

        int n = st.executeUpdate(sql);

        System.out.println(sql);
        logger.info("SQL" + sql);
        DAOMySQLSettings.closeStatement(st);



    }

    @Override
    public List<Prodotto> join(Prodotto a) throws DAOException, SQLException {
        return null;
    }


}
