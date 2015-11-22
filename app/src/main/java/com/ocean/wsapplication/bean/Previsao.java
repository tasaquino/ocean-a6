package com.ocean.wsapplication.bean;

/**
 * Created by thaisandrade on 22/11/15.
 */
public class Previsao {
    private int temperatura;
    private String condicao;
    private String icone;

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}