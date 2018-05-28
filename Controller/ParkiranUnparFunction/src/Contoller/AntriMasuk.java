package Contoller;

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
public class AntriMasuk extends Machine {

    //private int uang;
    private int time; // dalam menit
    //private boolean dompet; // kondisi kalo dompet ada di tas atau tidak

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

    public AntriMasuk(int nData) {
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
        for (int i = 0; i < this.rangeData; i++) {
            // kondisi tidak pakai uang pas
            if (this.uang[i] > 2000) {
                this.service[i] = this.time + 5;
            }else if(this.uang[i]==2000){
                this.service[i] = this.time + 2;
            // pas mu bayar ternyata ga punya uang, ngocek2 saku ga ada
            // nyari duit ngabisin waktu lama
            }else{
                this.service[i] =  this.time + 10;
            }                       
        }        
    }
    
    public void sampleCase(){
        for (int i = 0; i < this.rangeData; i++) {
            
        }
    }
    
}
