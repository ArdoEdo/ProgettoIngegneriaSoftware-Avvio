package it.unicas.model.dao.mysql;

import it.unicas.model.Ordine;
import it.unicas.model.ProdottiOrdinati;
import it.unicas.model.Prodotto;
import it.unicas.model.dao.DAO;
import it.unicas.model.dao.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProdottiOrdinatiDAOMySQLImpl implements DAO<ProdottiOrdinati> {

    private ProdottiOrdinatiDAOMySQLImpl(){};
    private static DAO dao =null;
    private static Logger logger = null;

    public static DAO getInstance(){
        if (dao == null){
            dao = new ProdottiOrdinatiDAOMySQLImpl();
            logger = Logger.getLogger(ProdottiOrdinatiDAOMySQLImpl.class.getName());
        }
        return dao;
    }




    @Override
    public List<ProdottiOrdinati> select(ProdottiOrdinati a) throws DAOException {
        ArrayList<ProdottiOrdinati> list = new ArrayList<ProdottiOrdinati>();

        try {
            Statement st = DAOMySQLSettings.getStatement();
            String sql = "SELECT prodotto.nome_prodotto, prodotto.tipo_prodotto,prodotto.alcolico,prodotto.prezzo_prodotto,\n" +
                    " ordine.quantita_prodotto_or,ordine.tavolo_locazione_tavolo,ordine.tavolo_numero_tavolo,ordine.ordine_preparato,\n" +
                    " ordine.id_ordine, ordine.prodotto_id_prodotto " +
                    " FROM prodotto inner join ordine where ordine.tavolo_numero_tavolo='" + a.getTavolo_numero_tavolo() + "'and " +
                    "ordine.tavolo_locazione_tavolo='" + a.getTavolo_locazione_tavolo() + "' and prodotto.id_prodotto=ordine.prodotto_id_prodotto";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new ProdottiOrdinati(rs.getString("nome_prodotto"),
                        rs.getString("tipo_prodotto"),
                        rs.getInt("alcolico"),
                        rs.getFloat("prezzo_prodotto"),
                        rs.getInt("id_ordine"),
                        rs.getInt("prodotto_id_prodotto"),
                        rs.getInt("quantita_prodotto_or"),
                        rs.getString("tavolo_locazione_tavolo"),
                        rs.getInt("tavolo_numero_tavolo"),
                        rs.getInt("ordine_preparato")));

            }
            DAOMySQLSettings.closeStatement(st);
        }
        catch(SQLException sq){
            throw new DAOException("Errore select(): "+sq.getMessage());
        }
        return list;
    }

    @Override
    public void update(ProdottiOrdinati a) throws DAOException {

try {
    Statement st = DAOMySQLSettings.getStatement();
    String sql = "UPDATE ordine set ordine.ordine_preparato = '1' where ordine.id_ordine ='" + a.getId_ordine() + "'";
    int rs = st.executeUpdate(sql);
    DAOMySQLSettings.closeStatement(st);
}
catch (SQLException sq){
    throw new DAOException("Errore update():" + sq.getMessage());
}

    }

    @Override
    public void insert(ProdottiOrdinati a) throws DAOException {

    }


    @Override
    public void delete(ProdottiOrdinati a) throws DAOException {
        ArrayList<ProdottiOrdinati> list = new ArrayList<ProdottiOrdinati>();
        try {
            Statement st = DAOMySQLSettings.getStatement();
            String sql = "DELETE FROM ordine where ordine.tavolo_numero_tavolo='" + a.getTavolo_numero_tavolo() + "'and ordine.tavolo_locazione_tavolo='" + a.getTavolo_locazione_tavolo() + "'";
            int rs = st.executeUpdate(sql);
            DAOMySQLSettings.closeStatement(st);
            System.out.println(sql);
        }
        catch (SQLException sq){
            throw new DAOException("Errore delete(): " + sq.getMessage());
        }
    }

    @Override
    public List<ProdottiOrdinati> join(ProdottiOrdinati a) throws DAOException {
        ArrayList<ProdottiOrdinati> list = new ArrayList<ProdottiOrdinati>();
        try {
            Statement st = DAOMySQLSettings.getStatement();
            String sql = "SELECT prodotto.nome_prodotto, prodotto.tipo_prodotto,prodotto.alcolico,prodotto.prezzo_prodotto," +
                    "ordine.quantita_prodotto_or,ordine.tavolo_locazione_tavolo,ordine.tavolo_numero_tavolo,ordine.ordine_preparato,\n" +
                    "ordine.id_ordine, ordine.prodotto_id_prodotto FROM prodotto inner join ordine on prodotto.id_prodotto = ordine.prodotto_id_prodotto" +
                    " and ordine.ordine_preparato = '0' and prodotto.tipo_prodotto ='" + a.getTipo_prodotto() + "'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new ProdottiOrdinati(rs.getString("nome_prodotto"),
                        rs.getString("tipo_prodotto"),
                        rs.getInt("alcolico"),
                        rs.getFloat("prezzo_prodotto"),
                        rs.getInt("id_ordine"),
                        rs.getInt("prodotto_id_prodotto"),
                        rs.getInt("quantita_prodotto_or"),
                        rs.getString("tavolo_locazione_tavolo"),
                        rs.getInt("tavolo_numero_tavolo"),
                        rs.getInt("ordine_preparato")));

            }
            DAOMySQLSettings.closeStatement(st);
        }
        catch(SQLException sq){
            throw new DAOException("Errore join(): "+ sq.getMessage());
        }

        return list;

    }


    public static void main(String args[]) throws DAOException, SQLException {
        ProdottiOrdinatiDAOMySQLImpl prodottiOrdinati = new ProdottiOrdinatiDAOMySQLImpl();
        ProdottiOrdinati p = new ProdottiOrdinati();
        List<ProdottiOrdinati> list = prodottiOrdinati.join(p);
        System.out.println(list);

    }
}
