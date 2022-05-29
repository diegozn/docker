/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ingressesofware;

import com.github.britooo.looca.api.core.Looca;
import com.google.gson.Gson;
import com.mycompany.ingresse.coleta.dados.Componentes;
import com.mycompany.ingresse.coleta.dados.Conexao;
import java.awt.Color;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 *
 * @author diego.silva@VALEMOBI.CORP
 */
public class TelaPrincipalVm {
    TelaLoginVM telaLoginVm = new TelaLoginVM();
    Componentes comps = new Componentes();
    Looca looca = new Looca();
    Conexao connect = new Conexao();
    List<Totem> verificacaoTotem = connect.getJdbc().query(String.format("SELECT * FROM totem WHERE id_processador='%s' AND serial_disco='%s' AND hostname='%s'",comps.getIdProcessador(), comps.getSerialDisco(), comps.getHostname()), new BeanPropertyRowMapper<>(Totem.class));
    Boolean totemCadastrado = verificacaoTotem.isEmpty() ? false : true;
    SlackIntegrationTest slackAlert = new SlackIntegrationTest();
    SlackRelatorio slackRelatorio = new SlackRelatorio();
    Boolean seguranca;
    Usuario sessao;
    Relatorio logAtual;
    Timer timer = new Timer();
    private Long segundo = 1000L;
    private Long minuto = segundo * 60;
    private Long hora = minuto * 60;
    private Boolean autorizar = false;

    public TelaPrincipalVm(Usuario sessao) {
        this.sessao = sessao;

        if(totemCadastrado){
            System.out.println(String.format("Monitoramento do Totem Nº %d\n\n", verificacaoTotem.get(0).getIdTotem()));
            IngresseSoftware menu = new IngresseSoftware(sessao);
            iniciarMenu();
        }
       
    }
    
    Relatorio gerarNovoRelatorio(Totem vinculo, Componentes compos){
        Relatorio log = new Relatorio(vinculo.getIdTotem(), compos.getProcessamento().intValue(), compos.regraTres(compos.getMemVolUso(), compos.getRam()), compos.regraTres(compos.getDiscoUso(), compos.getDisco()), compos.getQtdProcessos(), compos.getQtdServicos(), compos.getTemp());
        return log;
    }

    public void enviarRelatorio(Relatorio relat){
      connect.getJdbc().update("INSERT INTO logs(fkTotem,pctg_processador,pctg_memoria_uso,pctg_disco_uso,qtd_processos,qtd_servicos,temp,data_hora) VALUES (?,?,?,?,?,?,?,?)", relat.getFkTotem(),relat.getPctgProcessador(),relat.getPctgMemoriaUso(),relat.getPctgDiscoUso(),relat.getQtdProcessos(),relat.getQtdServicos(),relat.getTemp(),relat.getDataHora());

    }

    public void autoMonitorar(Boolean autorizado){
        
        if(autorizado){
        timer.scheduleAtFixedRate(new TimerTask(){
                @Override public void run(){
                    AlertaMonitoramento alertaMonitora = new AlertaMonitoramento();
                    Integer porcentagem1 = comps.getProcessamento().intValue();
                    Integer porcentagem3 = comps.regraTres(comps.getMemVolUso(), comps.getRam());
                    Integer porcentagem5 = comps.regraTres(comps.getDiscoUso(), comps.getDisco());
                    
                    logAtual = gerarNovoRelatorio(verificacaoTotem.get(0),comps);
                    enviarRelatorio(logAtual);

                    System.out.println( "CPU: "+ porcentagem1 + "%");
                    System.out.println( "Memória em uso: "+ porcentagem3 + "%");
                    System.out.println( "Armazenamento usado: "+ porcentagem5 + "%");
                    if (porcentagem1 > 1) {
                        //alertaMonitora.setVisible(true);
                        slackAlert.sendMessageToSlack("Alerta: O nível de processamento (CPU) atingiu 50%");
                        //alertaMonitora.textoAlertaMonitoramento1(porcentagem1);
                        System.out.println("");

                } 
                    if (porcentagem3 > 80) {
                    //alertaMonitora.setVisible(true);
                    slackAlert.sendMessageToSlack("Alerta: Memória ram atingiu 80%");
                    //alertaMonitora.textoAlertaMonitoramento3(porcentagem3);

                } 
                if (porcentagem5 > 80) {
                    //alertaMonitora.setVisible(true);
                    slackAlert.sendMessageToSlack("Alerta: Armazenamento atingiu 80%");
                    //alertaMonitora.textoAlertaMonitoramento5(porcentagem5);
                    }
                }
            }, segundo, minuto);
        }
        
        
    }

    public void enviarRelatorio(){
      logAtual = gerarNovoRelatorio(verificacaoTotem.get(0),comps);
      connect.getJdbc().update("INSERT INTO logs(fkTotem,pctg_processador,pctg_memoria_uso,pctg_disco_uso,qtd_processos,qtd_servicos,temp,data_hora) VALUES (?,?,?,?,?,?,?,?)", logAtual.getFkTotem(),logAtual.getPctgProcessador(),logAtual.getPctgMemoriaUso(),logAtual.getPctgDiscoUso(),logAtual.getQtdProcessos(),logAtual.getQtdServicos(),logAtual.getTemp(),logAtual.getDataHora());
      slackRelatorio.sendRelatorio(String.format("%s\n"
              + "_____________________\n"
              + "Relatório do totem:\n\n "
              + "Sistema: %s\n "
              + "Processador: %s\n "
              + "Processador em uso: %s\n "
              + "Memória em uso: %s\n "
              + "Disco em uso: %s\n "
              + "Quantidade de processos: %s\n "
              + "Quantidade de serviços: %s\n " 
              + "Temperatura: %s ",
              logAtual.getDataHora().toString(),
              looca.getSistema().toString(),
              looca.getProcessador().toString(),
              logAtual.getPctgProcessador().toString(),
              logAtual.getPctgMemoriaUso().toString(),
              logAtual.getPctgDiscoUso().toString(),
              logAtual.getQtdProcessos().toString(),
              logAtual.getQtdServicos().toString(),
              logAtual.getTemp().toString()));
    }



    public void iniciarMonitoramento() {
        System.out.println("\n\n-------------------------");
        System.out.println("Totem atual");
        System.out.println("-------------------------\n");
        System.out.println(String.format("%s\n"
                        + "_____________________\n"
                        + "Relatório do totem:\n\n "
                        + "Sistema: %s\n "
                        + "Processador: %s\n "
                        + "Processador em uso: %s\n "
                        + "Memória em uso: %s\n "
                        + "Disco em uso: %s\n "
                        + "Quantidade de processos: %s\n "
                        + "Quantidade de serviços: %s\n "
                        + "Temperatura: %s ",
                logAtual.getDataHora().toString(),
                looca.getSistema().toString(),
                looca.getProcessador().toString(),
                logAtual.getPctgProcessador().toString(),
                logAtual.getPctgMemoriaUso().toString(),
                logAtual.getPctgDiscoUso().toString(),
                logAtual.getQtdProcessos().toString(),
                logAtual.getQtdServicos().toString(),
                logAtual.getTemp().toString()));

        if(totemCadastrado){

            AlertaMonitoramento alertaMonitora = new AlertaMonitoramento();
            Integer porcentagem1 = comps.getProcessamento().intValue();
            Integer porcentagem3 = comps.regraTres(comps.getMemVolUso(), comps.getRam());
            Integer porcentagem5 = comps.regraTres(comps.getDiscoUso(), comps.getDisco());

            if(autorizar == false){
                autorizar = true;
                autoMonitorar(autorizar);
            }
            System.out.println( "CPU: "+ porcentagem1 + "%");
            System.out.println( "Memória em uso: "+ porcentagem3 + "%");
            System.out.println( "Armazenamento usado: "+ porcentagem5 + "%");

            if (porcentagem1 > 1) {
                slackAlert.sendMessageToSlack("Alerta: O nível de processamento (CPU) atingiu 50%");
                alertaMonitora.textoAlertaMonitoramento1(porcentagem1);
            } if (porcentagem3 > 80) {
                slackAlert.sendMessageToSlack("Alerta: Memória ram atingiu 80%");
                alertaMonitora.textoAlertaMonitoramento3(porcentagem3);

            } if (porcentagem5 > 80) {
                slackAlert.sendMessageToSlack("Alerta: Armazenamento atingiu 80%");
                alertaMonitora.textoAlertaMonitoramento5(porcentagem5);
            }
        }
    }

    public void validarEnvioRelatorio() {
        if(totemCadastrado){
            enviarRelatorio();
        }

    }

    public void adicionarTotem() {
        if(totemCadastrado == false){
            Double discoDouble = (double) (Math.round((comps.getDisco()/1000000000)*1.0/1.0));
            Double ramDouble = (double) (Math.round((comps.getRam()/1000000000)*1.0/1.0));
            connect.getJdbc().update("INSERT INTO totem(fkFilial, ram_total, espaco_disco, processador, data_compra, id_processador, serial_disco, hostname) VALUES (?,?,?,?,?,?,?,?)", sessao.getFkFilial(),ramDouble,discoDouble,comps.getProcessador(),comps.getDataTotem(),comps.getIdProcessador(), comps.getSerialDisco(),comps.getHostname());
        }
    }
    public void iniciarMenu() {

        Boolean continuarMenu = true;
        TelaLoginVM telaLogin = new TelaLoginVM();

        while(continuarMenu) {

            System.out.println("1 - Adicionar totem.");
            System.out.println("2 - Enviar relatório.");
            System.out.println("3 - Iniciar monitoramento.");
            System.out.println("4 - Sair. ");
            System.out.println("Selecione a opção desejada: ");

            Scanner leitor = new Scanner(System.in);
            Integer escolhaUser = leitor.nextInt();
            switch(escolhaUser) {
                case 1:
                    System.out.println("----------------------");
                    System.out.println("Adicionando totem...");
                    System.out.println("----------------------");
                    adicionarTotem();
                    System.out.println("Totem adicionado");
                    System.out.println("----------------------\n");
                    break;
                case 2:
                    System.out.println("----------------------");
                    System.out.println("Enviando relatório...");
                    System.out.println("----------------------\n\n");
                    enviarRelatorio();
                    System.out.println("Relatório enviado");
                    System.out.println("----------------------\n");
                    break;
                case 3:
                    System.out.println("----------------------");
                    System.out.println("Iniciando monitoramento...");
                    System.out.println("----------------------");
                    iniciarMonitoramento();
                    System.out.println("----------------------\n");
                    break;
                case 4:
                    System.out.println("----------");
                    System.out.println("Saindo...");
                    System.out.println("----------");
                    continuarMenu = false;
                    break;

                default:
                    System.out.println("Digite uma opção válida");
                    System.out.println("-----------------------------");

            }
        }
    }
}
