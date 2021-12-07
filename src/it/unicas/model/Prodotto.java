package it.unicas.model;



import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class Prodotto {

    private IntegerProperty id_prodotto;
    private StringProperty nome_prodotto;
    private StringProperty tipo_prodotto;
    private IntegerProperty alcolico;
    private FloatProperty prezzo_prodotto;
    private IntegerProperty quantita_prodotto;



    public Prodotto(){};

    public Prodotto(Integer id_prodotto,String nome_prodotto, String tipo_prodotto,Integer alcolico, Float prezzo_prodotto, Integer quantita_prodotto){

        this.nome_prodotto = new SimpleStringProperty(nome_prodotto);
        this.tipo_prodotto = new SimpleStringProperty(tipo_prodotto);
        this.alcolico = new SimpleIntegerProperty(alcolico);
        this.prezzo_prodotto = new SimpleFloatProperty(prezzo_prodotto);
        this.quantita_prodotto = new SimpleIntegerProperty(1);
        if (id_prodotto != null){
            this.id_prodotto = new SimpleIntegerProperty(id_prodotto);
        } else {
            this.id_prodotto = null;
        }
    }

    public Integer getId_prodotto() {
        if (id_prodotto == null) {
            this.id_prodotto =new SimpleIntegerProperty();
        }
        return id_prodotto.get();
    }

    public IntegerProperty id_prodottoProperty() {
        return id_prodotto;
    }

    public void setId_prodotto(Integer id_prodotto) {
        if(this.id_prodotto == null) {
            this.id_prodotto = new SimpleIntegerProperty();
        }
        this.id_prodotto.set(id_prodotto);
    }

    public String getNome_prodotto() {
        return nome_prodotto.get();
    }

    public StringProperty nome_prodottoProperty() {
        return nome_prodotto;
    }

    public void setNome_prodotto(String nome_prodotto) {
        if(this.nome_prodotto == null){
            this.nome_prodotto = new SimpleStringProperty();
        }
        this.nome_prodotto.set(nome_prodotto);
    }

    public String getTipo_prodotto() {
        return tipo_prodotto.get();
    }

    public StringProperty tipo_prodottoProperty() {
        return tipo_prodotto;
    }

    public void setTipo_prodotto(String tipo_prodotto) {
        if(this.tipo_prodotto == null) {
            this.tipo_prodotto = new SimpleStringProperty();
        }
        this.tipo_prodotto.set(tipo_prodotto);
    }

    public Integer isAlcolico() {
        return alcolico.get();
    }

    public IntegerProperty alcolicoProperty() {
        return alcolico;
    }

    public void setAlcolico(Integer alcolico) {
        if(this.alcolico == null) {
            this.alcolico = new SimpleIntegerProperty();
        }
        this.alcolico.set(alcolico);
    }

    public Float getPrezzo_prodotto() {
        return prezzo_prodotto.get();
    }

    public FloatProperty prezzo_prodottoProperty() {
        return prezzo_prodotto;
    }

    public void setPrezzo_prodotto(Float prezzo_prodotto) {
        if(this.prezzo_prodotto == null) {
            this.prezzo_prodotto = new SimpleFloatProperty();
        }
        this.prezzo_prodotto.set(prezzo_prodotto);
    }

    public int getQuantita_prodotto() {
        if(quantita_prodotto == null)
            quantita_prodotto = new SimpleIntegerProperty();
        return quantita_prodotto.get();
    }

    public IntegerProperty quantita_prodottoProperty() {
        return quantita_prodotto;
    }

    public void setQuantita_prodotto(Integer quantita_prodotto) {
        if (this.quantita_prodotto == null){
            this.quantita_prodotto = new SimpleIntegerProperty();
        }
        this.quantita_prodotto.set(quantita_prodotto);
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "id_prodotto=" + id_prodotto +
                ", nome_prodotto=" + nome_prodotto +
                ", tipo_prodotto=" + tipo_prodotto +
                ", alcolico=" + alcolico +
                ", prezzo_prodotto=" + prezzo_prodotto +
                ", quantita_prodotto=" + quantita_prodotto +
                '}';
    }

    public static void main(String[] args) {
        Prodotto prodotto = new Prodotto();
        List<Prodotto> prodotti = new ArrayList<>();

        System.out.println("Prodotto aggiunto");


    }



}
