/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ingressesofware;

import com.github.britooo.looca.api.core.Looca;
import com.mycompany.ingresse.coleta.dados.Conexao;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JFrame;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class TelaLoginVM {

    private Boolean validacao = false;
    private Conexao conecta = new Conexao();
    private Timer timer = new Timer();
    private Long segundo = 1000L;
    private Long minuto = segundo * 60;
    private Long hora = minuto * 60;
    private Usuario sessao;

    public Boolean getValidacao() {
        return validacao;
    }

    public void validarEntradaLogin() {
        Boolean validacaoOpcao = false;
        System.out.println("----------------");
         System.out.println("Login");
        System.out.println("----------------");
        try {

            Scanner emailDigitado = new Scanner(System.in);
            System.out.println("Digite seu Email: ");
            String email = emailDigitado.nextLine();

            Scanner senhaDigitada = new Scanner(System.in);
            System.out.println("Digite sua senha: ");
            String senha = senhaDigitada.nextLine();
            System.out.println("-------------------");
            System.out.println("Verificando...");
            System.out.println("-------------------");
            List<Usuario> user = conecta.getJdbc().query(String.format("SELECT * FROM usuario WHERE email_usuario='%s'", email), new BeanPropertyRowMapper<>(Usuario.class));
            Boolean permissao = user.get(0).getTipo_acesso().equals("suporte") || user.get(0).getTipo_acesso().equals("gerente") ? true : false;
            if (email.equals(user.get(0).getEmail_usuario()) && senha.equals(user.get(0).getSenha()) && permissao) {
                TelaPrincipalVm telaPrincipalVm = new TelaPrincipalVm(sessao);
                telaPrincipalVm.iniciarMenu();

                validacaoOpcao = true;
            } else {
                if (validacaoOpcao) {
                    System.out.println("Email ou senha incorreto");
                    validarEntradaLogin();
                } else {

                    System.out.println("Email ou senha incorreto");
                    validarEntradaLogin();
                }
            }
        } catch (Exception e) {

            if (validacaoOpcao) {
                System.out.println("Email ou senha incorreto");
                validarEntradaLogin();
            } else {

                System.out.println("Email ou senha incorreto");
                validarEntradaLogin();
            }
        }
    }
}
