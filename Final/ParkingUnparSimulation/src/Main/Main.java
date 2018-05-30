/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Contoller.*;

import Interface.OpenFile;
import java.io.*;
import java.util.*;

/**
 *
 * @author SAN
 */
public class Main {

    public static void main(String[] args) {
//        OpenFile of = new OpenFile();
//               of.bacaInput();
        OpenFile of = new OpenFile();
        of.bacaInput();

        String mode = of.jumlahMesin();

        if (mode.equalsIgnoreCase("1")) {
            Machine amt = new Antrian1();
            amt.proses();
        } else {
            Machine amt = new Antrian2();
            amt.proses();
        }

    }
}
