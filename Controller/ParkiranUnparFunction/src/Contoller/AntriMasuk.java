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
public class AntriMasuk extends Machine {
    /**
     * Variable untuk para pengendara.
     * Disimpan dalam array yang merepresentasikan setiap index
     * dimana indexnya adalah nomor pelanggan.
     */
    
    private int time; // asumsi dalam detik
    private boolean[] uang;
    private int[] service, arrival, delay, waiting, completion;
    private int rangeData, index, currentArrival;
    private String result;
    
    /**
     * Contructor
     * @param nData 
     */
    public AntriMasuk(int nData) {
        this.rangeData = nData;

        this.uang = new boolean[rangeData];
        this.service = new int[rangeData];
        this.arrival = new int[rangeData];
        this.delay = new int[rangeData];
        this.waiting = new int[rangeData];
        this.completion = new int[rangeData];
        this.currentArrival = this.time = 0;
        
        this.generateUang();
    }
    
    /**
     * method untuk menambahkan data 
     * @param i index
     * @param arrival waktu kedatangan
     * @param service waktu layanan
     * @param tiket ada atau tidaknya tiket (true / false)
     */
    public void addData(int i, int arrival, int service, boolean uang) {
        this.arrival[i] = arrival;
        this.service[i] = service;
        this.delay[i] = 0;
        this.waiting[i] = 0;
        this.completion[i] = 0;
        this.uang[i] = uang;
    }
    
    /**
     * Method untuk mengembalikan banyak data yang masuk
     * @return banyak data dengan tipe data inteeger
     */
    public int getRangeData() {
        return this.rangeData;
    }
    
    /**
     * Generate sample case for money.
     */
    private void generateUang(){
        Random r = new Random();        
        for (int i = 0; i < this.rangeData; i++) {
            int temp = r.nextInt(2);
            this.uang[i] = temp != 0;
        }
    }

    @Override
    /**
     * simulate parking system.
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
    }

    /**
     * Generate sample case for service.
     */
    private void calculateService() {
        int i = this.index;
        // CALCULATE SERVICE
        
        // kondisi pakai uang pas
        if (this.uang[i]) {
            this.service[i] = this.time + 2;
        }
        // kondisi tidak pakai uang pas
        else {
            this.service[i] = this.time + 5;
        }
    }

    /**
     * Generate sample case for arrival.
     */
    private void calculateArrival() {
        Random r = new Random();
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
    
    /**
     * Make Result to String
     */
    private void variableToString() {
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
    
    /**
     * Print result
     * @return result
     */
    @Override
    public String printOut() {
        variableToString();
        return this.result;
    }
}