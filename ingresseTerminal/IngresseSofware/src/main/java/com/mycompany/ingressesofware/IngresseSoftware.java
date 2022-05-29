package com.mycompany.ingressesofware;

import com.mycompany.ingresse.coleta.dados.Componentes;
import com.mycompany.ingresse.coleta.dados.Conexao;

import java.text.ParseException;
import java.util.Scanner;

public class IngresseSoftware {
    private Usuario sessao;

    public IngresseSoftware(Usuario sessao) {
        this.sessao = sessao;
    }

    public static void main(String[] args) throws ParseException {
        Componentes comps = new Componentes();
        Conexao connect = new Conexao();
        SlackIntegrationTest slackAlert = new SlackIntegrationTest();
        SlackRelatorio slackRelatorio = new SlackRelatorio();
        slackAlert.sendMessageToSlack("Iniciando...");
        slackRelatorio.sendRelatorio("Iniciando...");
        TelaLoginVM telaLogin = new TelaLoginVM();

        telaLogin.validarEntradaLogin();
        
    }
}
