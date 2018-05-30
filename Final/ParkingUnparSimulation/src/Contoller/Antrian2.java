package Contoller;

import Interface.OpenFile;
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
public class Antrian2 extends Machine {

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
    private OpenFile of;

    /**
     * Machine Constructor
     */
    public Antrian2() {

        //MACHINE 1
        this.of = new OpenFile();
        of.bacaInput();

        this.rangeData = of.getArival().length;
        int range = of.getArival().length;

        this.uang = new int[range];
        this.orang = new int[range];
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
     */
    @Override
    public void calculateService(int m) {
        if (of.mode().equalsIgnoreCase("masuk")) {
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
            if (this.pointerService != uang.length - 1) {
                this.pointerService++;
            }
        }
       else {
            if (m == 1) {

                if (this.uang[this.pointerService] == 0) {
                    this.service[this.index] = 20;

                } else {
                    this.service[this.index] = 5;
                }

            } else {

                if (this.uang[this.pointerService] == 0) {
                    this.service_2[this.index_2] = 20;
                } else {
                    this.service_2[this.index_2] = 5;
                }

            }
            if (this.pointerService != uang.length - 1) {
                this.pointerService++;
            }
        }
    }

    /**
     * Generate arrival.
     * @return 
     */
    public int calculateArrival() {
        if (this.index == 0) {
            this.arrival[0] = this.orang[0];
            return 1;
        } else if (this.index_2 == 0) {
            this.arrival_2[0] = this.orang[1];
            return 2;
        } else if (this.completion[index - 1] <= this.completion_2[this.index_2 - 1]) {
            this.arrival[this.index] = this.orang[this.pointer];
            this.pointer++;
            return 1;
        } else if (this.completion[index - 1] >= this.completion_2[this.index_2 - 1]) {
            this.arrival_2[this.index_2] = this.orang[this.pointer];
            this.pointer++;
            return 2;
        }
        
        return -1; //not going anywhere
    }

    private void createDummy() {
        for (int i = 0; i < rangeData; i++) {
            this.uang[i] = Integer.parseInt(of.getUang()[i]);
            
        }
        for (int i = 0; i < rangeData; i++) {
            this.orang[i] = Integer.parseInt(of.getArival()[i]);
        }

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

    private String finalCount() {
        double avService = 0.0;
        for (int i = 0; i < this.rangeData; i++) {
            avService += this.service[i];
        }
        for (int i = 0; i < this.rangeData; i++) {
            avService += this.service_2[i];
        }
        avService = avService / this.rangeData;

        double avDelay = 0.0;
        for (int i = 0; i < this.rangeData; i++) {
            avDelay += this.delay[i];
        }
        for (int i = 0; i < this.rangeData; i++) {
            avDelay += this.delay_2[i];
        }
        avDelay = avDelay / this.rangeData;

        double avWait = 0.0;
        for (int i = 0; i < this.rangeData; i++) {
            avWait += this.waiting[i];
        }
        for (int i = 0; i < this.rangeData; i++) {
            avWait += this.waiting_2[i];
        }
        avWait = avWait / this.rangeData;
        System.out.println("rangeDat "+this.rangeData);

        String res = new String();
        res += "\n";
        res += "Average Service = ";
        res += String.format("%.2f", avService) + " sec \n";

        res += "Average Delay = ";
        res += String.format("%.2f", avDelay) + " sec \n";

        res += "Average Wait = ";
        res += String.format("%.2f", avWait) + " sec \n";

        res += "Complete in " + this.getMaxCompletion() + " sec";

        return res;
    }

    private int getMaxCompletion() {
        int max1 = 0;
        int max2 = 0;

        for (int i = 0; i < this.completion.length; i++) {
            if (max1 < this.completion[i]) {
                max1 = this.completion[i];
            }
        }

        for (int i = 0; i < this.completion_2.length; i++) {
            if (max2 < this.completion_2[i]) {
                max2 = this.completion_2[i];
            }
        }

        if (max1 > max2) {
            return max1;
        } else {
            return max2;
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

            this.result += this.finalCount();
        }
    }

    public void printOut() {
        System.out.println(this.result);
    }

}
