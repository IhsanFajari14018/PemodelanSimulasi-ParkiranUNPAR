package Contoller;

import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * Kelas yang mensimulasikan perhitungan waktu pada saat masuk parkiran motor
 * PPAG UNPAR.
 * @author Modsim Team
 * @version 28 Mei 2018
 *
 */
public class AntriMasuk_1Machine extends Machine {

    //private int uang;
    private int time; // dalam menit
    //private boolean dompet; // kondisi kalo dompet ada di tas atau tidak

    /**
     * Variable untuk para pengendara. Disimpan dalam array merepresentasikan
     * setiap indexnya adalah nomor pelanggan.
     */
    private boolean[] uang;
    private int[] service;
    private int[] arrival;
    private int[] delay;
    private int[] waiting;
    private int[] completion;

    private int rangeData;
    private int index;
    private String result;
    private int currentArrival;

    public AntriMasuk_1Machine(int nData) {
        this.rangeData = nData;
        int range = 10;

        this.uang = new boolean[range];
        this.service = new int[range];
        this.arrival = new int[range];
        this.delay = new int[range];
        this.waiting = new int[range];
        this.completion = new int[range];

        this.time = 1; // asumsi
        
        this.generateUang();
    }
    
    private void generateUang(){
        Random r = new Random();        
        for (int i = 0; i < this.rangeData; i++) {
            int temp = r.nextInt(2);
            this.uang[i] = temp != 0;
        }
    }

    @Override
    /**
     * To simulate the parking system.
     */
    public void proses() {

        for (int i = 0; i < this.rangeData; i++) {

            // CALCULATE ARRIVAL
            this.calculateArrival();

            // CALCULATE SERVICE
            this.calculateService();
            
            // UPDATE INDEX
            this.updateIndex();

            // CALCULATE DELAY       
            //karena yang datang pertama itu tidak ada delay
            if (i == 0) {
                this.delay[i] = 0;
            } else {
                this.delay[i] = this.completion[i - 1] - this.arrival[i];
            }
            
            // CACULATE WAIT
            this.waiting[i] = this.delay[i] + this.service[i];

            // CALCULATE COMPLETION
            this.completion[i] = this.arrival[i] + this.service[i] + this.delay[i];
        }
        
        this.variableToString();
        
        this.printOut();
    }

    /**
     *
     */
    @Override
    public void calculateService() {
        int i = this.index;
        // CALCULATE SERVICE
        // kondisi tidak pakai uang pas
        if (this.uang[i] == false) {
            this.service[i] = this.time + 5;
        } else {
            this.service[i] = this.time + 2;
        }
    }

    /**
     * Generate sample case for arrival.
     */
    public void calculateArrival() {
        Random r = new Random();
        this.currentArrival = 0;
        int i = this.index;

        //GENERATE ARRIVAL
        int temp = 0;

        //arrival
        int randArrival = r.nextInt(4) + 1;
        this.arrival[i] = this.currentArrival + randArrival;
        //System.out.print(this.arrival[i]+"//");

        //updating the arrival
        this.currentArrival = this.currentArrival + randArrival;
    }
    
    /**
     * Updating the index.
     */
    private void updateIndex(){
        this.index++;
    }

    public void variableToString() {
        this.result = new String();
        String temp = new String();
        //System.out.println("");
        
        //ARRIVAL 
        this.result ="a: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.arrival[i]+",";
        }
        temp = temp.substring(0, temp.length()-1);
        this.result += temp+"\n";
        //System.out.println(this.result+"A");
        temp = new String();
        
        //SERVICE
        this.result +="s: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.service[i]+",";
        }
        temp = temp.substring(0, temp.length()-1);
        this.result += temp+"\n";
        //System.out.println(temp+"S");
        temp = new String();
        
        //DELAY
        this.result +="d: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.delay[i]+",";
        }
        temp = temp.substring(0, temp.length()-1);
        this.result += temp+"\n";
        temp = new String();
        
        //WAIT
        this.result +="w: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.waiting[i]+",";
        }
        temp = temp.substring(0, temp.length()-1);
        this.result += temp+"\n";
        temp = new String();
        
        //COMPLETION
        this.result +="c: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.completion[i]+",";
        }
        temp = temp.substring(0, temp.length()-1);
        this.result += temp+"\n";
        
    }

    public void printOut() {
        System.out.println(this.result);    
    }

}