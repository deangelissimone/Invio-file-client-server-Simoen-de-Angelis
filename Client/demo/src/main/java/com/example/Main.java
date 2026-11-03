package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("10.22.9.19", 3000);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci una stringa:");
        String testo = scanner.nextLine();
        out.println(testo);
        String linea;
        while ((linea = in.readLine()) != null) {
            System.out.println(linea);
        }

    }
}