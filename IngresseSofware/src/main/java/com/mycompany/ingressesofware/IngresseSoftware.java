/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.ingressesofware;

import com.github.britooo.looca.api.core.Looca;
import com.mycompany.ingresse.coleta.dados.Componentes;
import com.mycompany.ingresse.coleta.dados.Conexao;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author diego.silva@VALEMOBI.CORP
 */
public class IngresseSoftware {

    public static void main(String[] args) throws ParseException {
        Componentes comps = new Componentes();
        Conexao connect = new Conexao();  
        SlackIntegrationTest slackAlert = new SlackIntegrationTest();
        SlackRelatorio slackRelatorio = new SlackRelatorio();
        SlackToken slackToken = new SlackToken();
        Boolean repetirMenu = true; 
        slackAlert.sendMessageToSlack("Iniciando...");
        slackRelatorio.sendRelatorio("Iniciando...");
        slackToken.sendToken("Iniciando...");
        Scanner leitor = new Scanner(System.in);
        Scanner leitor2 = new Scanner(System.in);
        while(repetirMenu){
        System.out.println("Como você deseja acessar o ingresseSoftware?\n"
                + "1 - Interface gráfica\n"
                + "2 - Terminal\n"
                + "3 - Executando na vm (menos recurso)\n"
                + "Digite sua escolha: ");
        Integer changeTerminalOrSwing = leitor.nextInt();
        if (changeTerminalOrSwing == 1){
                repetirMenu = false;
                
                TelaLogin telaLogin = new TelaLogin();         
                telaLogin.setVisible(true);
        } else if(changeTerminalOrSwing == 2) {
            repetirMenu = false;
           
            System.out.println("executando monitoria terminal...");
        } else if(changeTerminalOrSwing == 3){
            repetirMenu = false;
          
            TelaLoginVM telaLoginVm = new TelaLoginVM(); 
            System.out.println("executando monitoria vm...");
            telaLoginVm.setVisible(true);
            
        } else {
            System.out.println("Digite um numero válido");
        }
      }

    }
}
