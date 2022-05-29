/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ingressesofware;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 *
 * @author mfazolim
 */
public class AlertaMonitoramento{

    Timer timer = new Timer();
    private Long segundo = 1000L;
    private Long minuto = segundo * 60;
    private Long hora = minuto * 60;

    public void textoAlertaMonitoramento1(Integer cpu){
        System.out.println(String.format("O processamento (Cpu) atingiu %d%%", cpu));
    }
    public void textoAlertaMonitoramento2(Double temperatura){
        System.out.println(String.format("Temperatura atingiu %.2f °C", temperatura));
    }
    public void textoAlertaMonitoramento3(Integer ram){
        System.out.println(String.format("Memória ram atingiu %d%%", ram));
    }
    private void textoAlertaMonitoramento4(){
        System.out.println("Sem retorno...");
    }
    public void textoAlertaMonitoramento5(Integer armaz){
        System.out.println(String.format("Armazenamento atingiu %d%%", armaz));
    }


}
