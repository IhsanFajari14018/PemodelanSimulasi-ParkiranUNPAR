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
 *
 * @author Modsim Team
 * @version 28 Mei 2018
 *
 */
public class AntriMasuk_2Machine extends Machine {

    //private int uang;
    private int time; // dalam menit
    //private boolean dompet; // kondisi kalo dompet ada di tas atau tidak

    /**
     * Machine 1 Variable untuk para pengendara. Disimpan dalam array
     * merepresentasikan setiap indexnya adalah nomor pelanggan.
     */
    private int[] uang;
    private int[] service;
    private int[] arrival;
    private int[] delay;
    private int[] waiting;
    private int[] completion;
    private int index;

    /**
     * Machine 2 Variable untuk para pengendara. Disimpan dalam array
     * merepresentasikan setiap indexnya adalah nomor pelanggan.
     */
    private int[] uang_2;
    private int[] service_2;
    private int[] arrival_2;
    private int[] delay_2;
    private int[] waiting_2;
    private int[] completion_2;
    private int index_2;

    /**
     * Internal variable. For logic purpose.
     */
    private int rangeData;
    private String result;
    private int currentArrival;
    private int pointer;

    /**
     * Machine Constructor
     */
    public AntriMasuk_2Machine(int nData) {
        this.rangeData = nData;
        int range = 5;

        //MACHINE 1
        this.uang = new int[range];
        this.service = new int[range];
        this.arrival = new int[range];
        this.delay = new int[range];
        this.waiting = new int[range];
        this.completion = new int[range];

        //MACHINE 2
        this.uang_2 = new int[range];
        this.service_2 = new int[range];
        this.arrival_2 = new int[range];
        this.delay_2 = new int[range];
        this.waiting_2 = new int[range];
        this.completion_2 = new int[range];

        this.time = 1; // asumsi

        this.generateUang();
    }

    /**
     * Money generator. Do they paid with 2000 or higher value of money.
     */
    private void generateUang() {
        Random r = new Random();

        //WITH RANDOM
        //MACHINE 1
        // for (int i = 0; i < this.rangeData; i++) {
        //     int temp = r.nextInt(2);
        //     this.uang[i] = temp != 0;
        // }
        // //MACHINE 2
        // for (int i = 0; i < this.rangeData; i++) {
        //     int temp = r.nextInt(2);
        //     this.uang_2[i] = temp != 0;
        // }


        //NOT RANDOM
        this.uang = new int[]{0,0,1,1,1};
        this.uang_2 = new int[]{0,0,1,1,1};

    }

    /**
     * To simulate the parking system. Using 2 Machine.
     */
    @Override
    public void proses() {
        for (int i = 0; i < this.rangeData; i++) {
            // CALCULATE SERVICE

            this.calculateService();

            // CALCULATE ARRIVAL
            this.calculateArrival();

            // CALCULATE DELAY
            //karena yang datang pertama itu tidak ada delay
            this.calculateDelay();

            // CACULATE WAIT
            this.calculateWait();

            // CALCULATE COMPLETION
            this.calculateCompletion();
        }

        this.variableToString();
        this.printOut();
    }

    /**
     * To calcuate completion.
     */
    public void calculateCompletion() {
        int i = this.index;
        int i2 = this.index_2;
        System.out.println("isii "+i+" "+i2);


        this.completion[i] = this.arrival[i] + this.service[i] + this.delay[i];
        //System.out.print("c1"+this.completion[i]+" ");

        this.completion_2[i2] = this.arrival_2[i2] + this.service_2[i2] + this.delay_2[i2];
        //System.out.println("c2"+this.completion[i2]);
    }

    /**
     * To calcuate wait.
     */
    public void calculateWait() {
        int i = this.index;
        int i2 = this.index_2;

        this.waiting[i] = this.delay[i] + this.service[i];
        this.waiting_2[i2] = this.delay_2[i2] + this.service_2[i2];
    }

    /**
     * To calcuate delay.
     */
    public void calculateDelay() {

        int i = this.index;
        int i2 = this.index_2;

        if (i == 0) {
            this.delay[i] = 0;
        } else if (i2 == 0) {
            this.delay_2[i2] = 0;
        } else {
            this.delay[i] = this.completion[i - 1] - this.arrival[i];
            this.delay_2[i2] = this.completion_2[i2 - 1] - this.arrival_2[i2];
        }
    }

    /**
     * Generate service.
     */
    @Override
    public void calculateService() {
        int i = this.index;
        int i2 = this.index_2;

        // CALCULATE SERVICE in machine 1
        // kondisi tidak pakai uang pas
        if (this.uang[i] == 0) {
            this.service[i] = 5;
        } else {
            this.service[i] = 2;
        }

        // CALCULATE SERVICE in machine 2
        // kondisi tidak pakai uang pas
        if (this.uang_2[i2] == 0) {
            this.service_2[i2] = 5;
        } else {
            this.service_2[i2] = 2;
        }
    }

    /**
     * Generate arrival.
     */
    public void calculateArrival() {
        Random r = new Random();
        this.currentArrival = 0;
        int i = this.index;
        int i2 = this.index_2;

        //GENERATE ARRIVAL TIME
        //int randArrival = r.nextInt(4) + 1; //Datanya random dulu buat sementara
        int[] randArrival = this.arrivalDummy();
        //System.out.println(randArrival+"INPUT");

        if(i==0){ //Awalan
            this.arrival[i] = randArrival[this.pointer];
            //System.out.println(randArrival+" A-1");
            this.updateIndex(1);
        }else if(i2==0){ //Awalan
            this.arrival_2[i2] = randArrival[this.pointer];
            //System.out.println(randArrival+" A-2");
            this.updateIndex(2);
        }else if(this.completion[i-1] < this.completion_2[i2-1]){
            this.arrival[i] = randArrival[this.pointer];
            this.updateIndex(1);
        }else if(this.completion[i-1] > this.completion_2[i2-1]){
            this.arrival_2[i2] = randArrival[this.pointer];
            this.updateIndex(2);
        }

        this.pointer++;


//        else if(this.completion[i-1] < this.completion_2[i2-1]){
//            System.out.println("asdasdas");
//            this.arrival[i] = randArrival;
//            this.updateIndex(1);
//        }else if(this.completion[i-1] > this.completion_2[i2-1]){
//            System.out.println("KADIEU?");
//            this.arrival_2[i2] = randArrival;
//            this.updateIndex(2);
//        }

//        if (this.arrival[i] == 0) { //Kalau kosong //First attempt
//            //this.arrival[i] = this.calculateCurrentArrival(1) + randArrival;
//            this.arrival[i] = randArrival;
//            this.updateIndex(1);
//        } else if (this.arrival_2[i] == 0) {
//            this.arrival_2[i] = this.calculateCurrentArrival(2) + randArrival;
//            this.updateIndex(2);
//        } else if (this.completion[i - 1] < this.completion_2[i2 - 1]) { // Jika m1 selesai sblm m2
//            this.arrival[i] = this.calculateCurrentArrival(1) + randArrival;
//            this.updateIndex(1);
//        } else if (this.completion_2[i2 - 1] < this.completion[i - 1]) { //jika m2 slsai sblm m1
//            this.arrival_2[i2] = this.calculateCurrentArrival(2) + randArrival;
//            this.updateIndex(2);
//        }

        //updating the arrival
        //his.currentArrival = this.currentArrival + randArrival;
    }

    private int[] arrivalDummy(){
        int[] res = {1,4,5,6,9};
        return res;
    }

    /**
     * NOT YET USED
     *
     * Get the currently maximum value of arrival.
     *
     * @param machine
     * @return
     */
    private int calculateCurrentArrival(int machine) {

        if (machine == 1) {
            //calculate current max value of arrival in this MACHINE
            int res = 0;
            for (int i = 0; i < this.rangeData; i++) {
                res+= this.arrival[i];
            }
            System.out.println(res+" //");
            return res;
        } else if (machine == 2) {
//            int res = 0;
//            res += this.arrival_2[i];
//            //System.out.print(res+"res2 ");
//            System.out.println();
//            return res;
        }

        return -1; //wrong input
    }

    /**
     * Updating the index.
     */
    private void updateIndex(int machine) {
        if ((machine == 1) && (this.index != this.rangeData-1)) {
            this.index++;
        } else if ((machine == 2) && (this.index_2 != this.rangeData-1)) {
            this.index_2++;
        }
    }

    public void variableToString() {
        this.result = new String();
        String temp = new String();

        // MACHINE 1
        // ARRIVAL
        this.result = "a: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.arrival[i] + ",";
        }
        temp = temp.substring(0, temp.length() - 1);
        this.result += temp + "\n";
        //System.out.println(this.result+"A");
        temp = new String();

        //SERVICE
        this.result += "s: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.service[i] + ",";
        }
        temp = temp.substring(0, temp.length() - 1);
        this.result += temp + "\n";
        //System.out.println(temp+"S");
        temp = new String();

        //DELAY
        this.result += "d: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.delay[i] + ",";
        }
        temp = temp.substring(0, temp.length() - 1);
        this.result += temp + "\n";
        temp = new String();

        //WAIT
        this.result += "w: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.waiting[i] + ",";
        }
        temp = temp.substring(0, temp.length() - 1);
        this.result += temp + "\n";
        temp = new String();

        //COMPLETION
        this.result += "c: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.completion[i] + ",";
        }
        temp = temp.substring(0, temp.length() - 1);
        this.result += temp + "\n";
        temp ="";

        this.result += "\n";

        // MACHINE 2
        // ARRIVAL
        this.result += "a: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.arrival_2[i] + ",";
        }
        temp = temp.substring(0, temp.length() - 1);
        this.result +=temp+"\n";
        //System.out.println(this.result+"A");
        temp = new String();

        //SERVICE
        this.result += "s: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.service_2[i] + ",";
        }
        temp = temp.substring(0, temp.length() - 1);
        this.result += temp + "\n";
        //System.out.println(temp+"S");
        temp = new String();

        //DELAY
        this.result += "d: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.delay_2[i] + ",";
        }
        temp = temp.substring(0, temp.length() - 1);
        this.result += temp + "\n";
        temp = new String();

        //WAIT
        this.result += "w: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.waiting_2[i] + ",";
        }
        temp = temp.substring(0, temp.length() - 1);
        this.result += temp + "\n";
        temp = new String();

        //COMPLETION
        this.result += "c: ";
        for (int i = 0; i < this.rangeData; i++) {
            temp += this.completion_2[i] + ",";
        }
        temp = temp.substring(0, temp.length() - 1);
        this.result += temp + "\n";

    }

    public void printOut() {
        System.out.println(this.result);
    }

}
