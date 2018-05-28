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

    public AntriMasuk(int nData) {
        this.rangeData = nData;
        int range = 10;

        this.uang = new boolean[range];
        this.service = new int[range];
        this.arrival = new int[range];
        this.delay = new int[range];
        this.waiting = new int[range];
        this.completion = new int[range];

        this.time = 1; // asumsi
        
        //Generate
        this.sampleCase();
        this.calculateService();        
    }

    @Override
    /**
     * To simulate the parking system.
     */
    public void proses() {
        for (int i = 0; i < this.rangeData; i++) {
            // CACULATE WAIT
            this.waiting[i] = this.delay[i] + this.service[i];
            
            // CALCULATE DELAY       
            //karena yang datang pertama itu tidak ada delay
            if(i==0){
                this.delay[i] = 0;
            }else{
                this.delay[i] = this.completion[i-1] - this.arrival[i];
            }
            
            // CALCULATE COMPLETION
            this.completion[i] = this.arrival[i] + this.service[i] + this.delay[i];
        }        
    }

    /**
     * 
     */
    @Override
    public void calculateService(){
         // CALCULATE SERVICE
         for (int i = 0; i < this.rangeData; i++) {
            // kondisi tidak pakai uang pas
            if (this.uang[i]==false) {
                this.service[i] = this.time + 5;
            }else{
                this.service[i] = this.time + 2;
            }
        }
    }
    
    
    /**
     * Generate sample case for arrival.
     */
    public void sampleCase() {
        Random r = new Random();
        int currentArrival = 0;
        
        //GENERATE ARRIVAL
        for (int i = 0; i < this.rangeData; i++) {
            int temp = 0;

            //arrival
            int randArrival = r.nextInt(4) + 1;
            this.arrival[i] = currentArrival + randArrival;
            
            //updating the arrival
            currentArrival = currentArrival + randArrival;
        }
    }
    
    
    public void variableToString(){
        
    }

}
