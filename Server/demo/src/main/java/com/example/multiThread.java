package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class multiThread extends Thread {
    private final Socket socket;

    public multiThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
                Socket s = this.socket;
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            String richiesta = in.readLine();

            if (richiesta == null) {
                System.out.println("Cliente chiuso la connessione");
            }

            String comando = richiesta.trim();

            if (comando.isEmpty()) {
                out.println("ERR SYNTAX");
            }

            String[] parti = comando.split("\\s+");

            if (parti.length != 2 || !parti[0].equalsIgnoreCase("LEGGI")) {
                out.println("ERR SYNTAX");
            }

            String nomeFile = parti[1];

            if (nomeFile.contains("..")
                    || nomeFile.contains("/")
                    || nomeFile.contains("\\")) {
                out.println("ERR INVALID NAME");
            }

            File cartella = new File("demo/src/main/resources");
            File file = new File(cartella, nomeFile);

            System.out.println(file.getAbsolutePath());

            if (!file.isFile()) {
                out.println("ERR FILE NOT FOUND");
            }

            out.println("OK FILE");

            try (BufferedReader lettoreFile = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

                String riga;
                while ((riga = lettoreFile.readLine()) != null) {
                    out.println(riga);
                }
            }

            out.println("END_FILE");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
