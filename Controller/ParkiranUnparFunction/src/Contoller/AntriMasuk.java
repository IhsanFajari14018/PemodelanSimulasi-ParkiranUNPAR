/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * Kelas yang mensimulasikan perhitungan waktu pada saat masuk parkiran motor PPAG UNPAR
 * dengan menggunakan 1 buah mesin
 * @author Modsim Team
 * @version 28 Mei 2018
 * 
 */
public class AntriMasuk {
    private int uang;
    private double time; // dalam menit
    private boolean dompet; // kondisi kalo dompet ada di tas atau tidak
    
    public AntriMasuk(int uang) {
        this.uang = uang;
        this.time = 1; // asumsi
        this.dompet = false;;
    }
    
    public double proses() {
        // kondisi sudah menyiapkan uang tapi nominal lebih dari 2000
        if(uang > 2000 && dompet == false) {
            return time + 1.0;
        }
        // kondisi nominal uang tidak pas dan harus mengambil dulu dari dompet
        else if(uang > 2000 && !dompet) {
            return time + 3.0;
        }
        // kondisi uang nominal sudah pas tapi harus mengambil dulu dari dompet atau saku
        else if(uang == 2000 && !dompet) {
            return time + 2.0;
        }
        // kondisi nominal uang yang diberikan sudah pas
        else {
            return time;
        }
    }
}