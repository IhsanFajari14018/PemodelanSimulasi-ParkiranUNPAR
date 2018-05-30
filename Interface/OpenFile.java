/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author farhan
 */
public class OpenFile {

    String[] tempArrival;
    String[] tempUang;
    int[] arrival, uang;
    String jumlahMesin;
    String mode;
    JFileChooser fileChooser = new JFileChooser();
    StringBuilder sb = new StringBuilder();
    String filePathName;

    public void PickMe() throws Exception {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            java.io.File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            filePathName = filePath;

            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                sb.append(input.nextLine());
                sb.append("\n");
            }
            input.close();
        } else {
            sb.append("Tidak Ada File");
        }
    }

    public String bacaInput() {
        //File file = new File("C:\\Users\\farha\\Documents\\NetBeansProjects\\ParkiranUnparFunction\\coba.txt");
        File file = new File(filePathName);
        String row = "";

        //File file = new File("D:\\Data\\Rendra\\Kuliah\\Jaringan Syaraf Tiruan\\09. Tugas Besar\\Program\\JSTSom\\fileIkan.txt");
        //DefaultTableModel model = null;
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            // model = (DefaultTableModel) dataTabel.getModel();
            Object[] lines = br.lines().toArray();

            for (int i = 0; i < lines.length; i++) {
                //row = lines[i].toString() + "\n";
                row = lines[i].toString();
                if (i == 0) {
                    String[] hasil = row.split("=");
                    jumlahMesin = hasil[1];
                } else if (i >= 1) {
                    if(i == 1) {
                        this.tempArrival = row.split("=");
                        mode = this.tempArrival[1];
                    }
                    else {
                        String[] temp = row.split("=");
                        if(temp[0].equals("a")) {
                            tempArrival = temp[1].split(",");
                            arrival = new int[tempArrival.length];
                            for(int j = 0;j<arrival.length;j++) {
                                arrival[j] = Integer.parseInt(tempArrival[j]);
                            }
                        }
                        else {
                            tempUang = temp[1].split(",");
                            uang = new int[tempUang.length];
                            for(int j = 0;j<uang.length;j++) {
                                uang[j] = Integer.parseInt(tempUang[j]);
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            //Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
}