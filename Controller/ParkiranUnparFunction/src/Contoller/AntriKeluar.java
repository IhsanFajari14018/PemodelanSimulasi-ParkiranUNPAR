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
 * PPAG UNPAR dengan menggunakan 1 buah mesin
 *
 * @author Modsim Team
 * @version 28 Mei 2018
 *
 */
public class AntriKeluar extends Machine {

    //private int uang;
    private int time; // dalam menit
    //private boolean tiket; // kondisi kalo tiket ada di dompet atau tidak
    private boolean tiket;
    /**
     * Variable untuk para pengendara. Disimpan dalam array merepresentasikan
     * setiap indexnya adalah nomor pelanggan.
     */
    private int[] uang;
    private int[] service;
    private int[] arrival;
    private int[] delay;
    private int[] waiting;
    private int[] completion;
    
    private int rangeData;

    public AntriKeluar(int nData) {
        this.rangeData = nData;
        int range = 10;
        
        this.uang = new int[range];
        this.uang = new int[range];
        this.service = new int[range];
        this.arrival = new int[range];
        this.delay = new int[range];
        this.waiting = new int[range];
        this.completion = new int[range];

        this.time = 1; // asumsi
    }

    @Override
    public void proses() {
        for (int i = 0; rangeData < 10; i++) {
            //Menghitung delay
            if(i==0){
                delay[i] = 0;
            } else {
                delay[i] = completion[i-1]-arrival[i];
            }
            
            //Menghitung waiting
            waiting[i] = delay[i] + service [i];
            
            //Menghitung completion
            completion[i] = arrival [i] + delay [i] + service[i];
        }
    }
    
    /**
     * generate arrival
     */
    public void sampleCase() {
        Random r = new Random();
        int currentArrival = 0;
        for (int i = 0; i < this.rangeData; i++) {
            int temp = 0;

            //arrival
            int randArrival = r.nextInt(4) + 1;
            this.arrival[i] = currentArrival + randArrival;
            
            //updating the arrival
            currentArrival = currentArrival + randArrival;
        }
    }
    
    public void calculateService() {
        for (int i = 0; i < rangeData; i++) {
            if(tiket==true){
                service[i] = time + 5;
            } else {
                service[i] = time + 20;
            }
        }
    }
    
}
