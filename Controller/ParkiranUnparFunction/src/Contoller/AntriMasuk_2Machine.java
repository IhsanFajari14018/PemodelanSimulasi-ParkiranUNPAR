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
    private int pointerService;
    private int[] orang;

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
        this.service_2 = new int[range];
        this.arrival_2 = new int[range];
        this.delay_2 = new int[range];
        this.waiting_2 = new int[range];
        this.completion_2 = new int[range];

        this.time = 1; // asumsi
        this.pointer = 2;
        this.pointerService = 2;

        this.createDummy();
    }

    /**
     * To simulate the parking system. Using 2 Machine.
     */
    @Override
    public void proses() {
        for (int i = 0; i < this.rangeData; i++) {
            // CALCULATE ARRIVAL
            int m = this.calculateArrival();

            // CALCULATE SERVICE
            this.calculateService(m);

            // CALCULATE DELAY
            //karena yang datang pertama itu tidak ada delay
            this.calculateDelay();

            // CACULATE WAIT
            this.calculateWait();

            // CALCULATE COMPLETION
            this.calculateCompletion();

            if (m == 1) {
                this.updateIndex(1);
            } else {
                this.updateIndex(2);
            }
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
            int temp1 = this.completion[i - 1] - this.arrival[i];
            if (temp1 < 0) {
                temp1 = 0;
            }
            this.delay[i] = temp1;

            int temp2 = this.completion_2[i2 - 1] - this.arrival_2[i2];
            if (temp2 < 0) {
                temp2 = 0;
            }
            this.delay_2[i2] = temp2;
        }
    }

    /**
     * Generate service.
     * @param m
     */
    
    @Override
    public void calculateService(int m) {

        if (m == 1) {
            if (this.uang[this.pointerService] == 0) {
                this.service[this.index] = 5;
            } else {
                this.service[this.index] = 2;
            }
        } else {

            if (this.uang[this.pointerService] == 0) {
                this.service_2[this.index_2] = 5;
            } else {
                this.service_2[this.index_2] = 2;
            }
        }
    }

    /**
     * Generate arrival.
     */
    public int calculateArrival() {
        if (this.index == 0) {
            this.arrival[0] = this.orang[0];
            return 1;
        } else if (this.index_2 == 0) {
            this.arrival_2[0] = this.orang[1];
            return 2;
        } else if (this.completion[index - 1] < this.completion_2[this.index_2 - 1]) {
            this.arrival[this.index] = this.orang[this.pointer];
            this.pointer++;
            return 1;
        } else if (this.completion[index - 1] > this.completion_2[this.index_2 - 1]) {
            this.arrival_2[this.index_2] = this.orang[this.pointer];
            this.pointer++;
            return 2;
        }
        return -1;
    }

    private void createDummy() {
        this.uang = new int[]{0, 0, 1, 1, 1};
        this.orang = new int[]{1, 4, 5, 6, 9};
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
        return 0;
    }

    /**
     * Updating the index.
     */
    private void updateIndex(int machine) {
        if ((machine == 1) && (this.index != this.rangeData - 1)) {
            this.index++;
        } else if ((machine == 2) && (this.index_2 != this.rangeData - 1)) {
            this.index_2++;
        }
    }

    public void variableToString() {
        this.result = new String();
        String temp = new String();
        boolean print = true;
        // MACHINE 1
        // ARRIVAL
        this.result = "a: ";
        if (true) {
            for (int i = 0; i < this.rangeData; i++) {
                if (this.arrival[i] == 0) {
                    break;
                }
                temp += this.arrival[i] + ",";

            }
            temp = temp.substring(0, temp.length() - 1);
            this.result += temp + "\n";
            //System.out.println(this.result+"A");
            temp = new String();

            //SERVICE
            this.result += "s: ";
            for (int i = 0; i < this.rangeData; i++) {
                if (this.arrival[i] == 0) {
                    break;
                }
                temp += this.service[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);
            this.result += temp + "\n";
            //System.out.println(temp+"S");
            temp = new String();

            //DELAY
            this.result += "d: ";
            for (int i = 0; i < this.rangeData; i++) {
                if (this.arrival[i] == 0) {
                    break;
                }
                temp += this.delay[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);
            this.result += temp + "\n";
            temp = new String();

            //WAIT
            this.result += "w: ";
            for (int i = 0; i < this.rangeData; i++) {
                if (this.arrival[i] == 0) {
                    break;
                }
                temp += this.waiting[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);
            this.result += temp + "\n";
            temp = new String();

            //COMPLETION
            this.result += "c: ";
            for (int i = 0; i < this.rangeData; i++) {
                if (this.arrival[i] == 0) {
                    break;
                }
                temp += this.completion[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);
            this.result += temp + "\n";
            temp = "";
        }
        this.result += "\n";

        // MACHINE 2
        // ARRIVAL
        this.result += "a: ";
        if (true) {
            for (int i = 0; i < this.rangeData; i++) {
                if (this.arrival_2[i] == 0) {
                    break;
                }
                temp += this.arrival_2[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);
            this.result += temp + "\n";
            //System.out.println(this.result+"A");
            temp = new String();

            //SERVICE
            this.result += "s: ";
            for (int i = 0; i < this.rangeData; i++) {
                if (this.arrival_2[i] == 0) {
                    break;
                }
                temp += this.service_2[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);
            this.result += temp + "\n";
            //System.out.println(temp+"S");
            temp = new String();

            //DELAY
            this.result += "d: ";
            for (int i = 0; i < this.rangeData; i++) {
                if (this.arrival_2[i] == 0) {
                    break;
                }
                temp += this.delay_2[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);
            this.result += temp + "\n";
            temp = new String();

            //WAIT
            this.result += "w: ";
            for (int i = 0; i < this.rangeData; i++) {
                if (this.arrival_2[i] == 0) {
                    break;
                }
                temp += this.waiting_2[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);
            this.result += temp + "\n";
            temp = new String();

            //COMPLETION
            this.result += "c: ";
            for (int i = 0; i < this.rangeData; i++) {
                if (this.arrival_2[i] == 0) {
                    break;
                }
                temp += this.completion_2[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);
            this.result += temp + "\n";
        }
    }

    public void printOut() {
        System.out.println(this.result);
    }
}