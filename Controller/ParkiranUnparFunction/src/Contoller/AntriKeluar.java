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
    
    /**
     * Variable untuk para pengendara. Disimpan dalam array merepresentasikan
     * setiap indexnya adalah nomor pelanggan.
     */
    private int[] arrival, service, delay, waiting, completion;
    private boolean[] tiket; // kondisi kalo tiket ada di dompet atau tidak
    
    private int banyakData;

    public AntriKeluar(int banyakData) {
        this.banyakData = banyakData; // banyaknya data
        
        this.arrival = new int[banyakData];
        this.service = new int[banyakData];
        this.delay = new int[banyakData];
        this.waiting = new int[banyakData];
        this.completion = new int[banyakData];
        this.tiket = new boolean[banyakData];

        this.time = 1; // asumsi
    }

    /**
     * method untuk menambahkan data 
     * @param i index
     * @param arrival waktu kedatangan
     * @param service waktu layanan
     * @param tiket ada atau tidaknya tiket (true / false)
     */
    public void addData(int i, int arrival, int service, boolean tiket) {
        this.arrival[i] = arrival;
        this.service[i] = service;
        this.delay[i] = 0;
        this.waiting[i] = 0;
        this.completion[i] = 0;
        this.tiket[i] = tiket;
    }
    
    /**
     * Method untuk mengembalikan banyak data yang masuk
     * @return banyak data dengan tipe data inteeger
     */
    public int getBanyakData() {
        return this.banyakData;
    }
    
    @Override
    public void proses() {
        for (int i = 0; i < banyakData; i++) {
            //Menghitung delay
            if(i == 1){
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
     * Method untuk menghitung data service berdasarkan ada atau tidak adanya tiket
     */
    @Override
    public void calculateService() {
        for (int i = 0; i < banyakData; i++) {
            if(this.tiket[i]){ // jika ada tiket
                service[i] = time + 5; // asumsi ditambah 5 detik
            } else { //  jika tidak ada tiket
                service[i] = time + 20; //  asumsi ditambah 20 detik
            }
        }
    }
}