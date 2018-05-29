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
    private int[] arrival, service, delay, waiting, completion, uang;

    private int banyakData;

    public AntriMasuk(int banyakData) {
        this.banyakData = banyakData;

        this.uang = new int[banyakData];
        this.service = new int[banyakData];
        this.arrival = new int[banyakData];
        this.delay = new int[banyakData];
        this.waiting = new int[banyakData];
        this.completion = new int[banyakData];

        this.time = 1; // asumsi
        
        //Generate
        //this.sampleCase();
        this.calculateService();        
    }

    /**
     * method untuk menambahkan data 
     * @param i index
     * @param arrival waktu kedatangan
     * @param service waktu layanan
     * @param uang membayar dengan uang pas atau tidak
     */
    public void addData(int i, int arrival, int service, int uang) {
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
    public int getBanyakData() {
        return this.banyakData;
    }
    
    @Override
    /**
     * To simulate the parking system.
     */
    public void proses() {
        for (int i = 0; i < this.banyakData; i++) {
            //Menghitung delay
            if(i == 0){
                delay[i] = 0;
            } else {
                delay[i] = completion[i-1] - arrival[i];
            }
            
            //Menghitung waiting
            waiting[i] = delay[i] + service [i];
            
            //Menghitung completion
            completion[i] = arrival [i] + delay [i] + service[i];
        }        
    }

    /**
     * Method untuk menghitung waktu service berdasarkan pembayaran,
     * apakah menggunakan uang pas atau tidak
     */
    @Override
    public void calculateService(){
         // CALCULATE SERVICE
         for (int i = 0; i < this.banyakData; i++) {
            // kondisi tidak pakai uang pas
            if (this.uang[i] == 0) {
                this.service[i] = this.time + 5; // asumsi ditambah 5 detik
            }else { // kondisi pakai uang pas
                this.service[i] = this.time + 2; //  asumsi ditambah 2 detik
            }
        }
    }
    
//    /**
//     * Generate sample case for arrival.
//     */
//    public void sampleCase() {
//        Random r = new Random();
//        int currentArrival = 0;
//        
//        //GENERATE ARRIVAL
//        for (int i = 0; i < this.banyakData; i++) {
//            int temp = 0;
//
//            //arrival
//            int randArrival = r.nextInt(4) + 1;
//            this.arrival[i] = currentArrival + randArrival;
//            
//            //updating the arrival
//            currentArrival = currentArrival + randArrival;
//        }
//    }
    
    public void variableToString(){
        
    }
}