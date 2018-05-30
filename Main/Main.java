/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Contoller.AntriKeluar;
import Contoller.AntriMasuk;
import Contoller.Machine;

/**
 *
 * @author SAN
 */
public class Main {
    public static void main(String[] args) {
        Machine amt1 = new AntriMasuk(10);
        Machine amt2 = new AntriKeluar(10);
    
        amt1.proses();
        amt2.proses();
        
        System.out.println("Simulasi antrian masuk parkiran motor PPAG UNPAR");
        System.out.println(amt1.printOut());
        System.out.println("---------------------------------------------------");
        System.out.println("Simulasi antrian keluar parkiran motor PPAG UNPAR");
        System.out.println(amt2.printOut());
    }
}