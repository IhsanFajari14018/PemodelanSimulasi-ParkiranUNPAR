/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.stream.Stream;
import javax.swing.JFileChooser;

/**
 *
 * @author farhan
 */
public class OpenFile {

    String[] arrival;
    String[] uang;
    String filePath;
    String jumlahMesin;
    String mode;
    JFileChooser fileChooser = new JFileChooser();
    StringBuilder sb = new StringBuilder();

    public void PickMe() throws Exception {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            java.io.File file = fileChooser.getSelectedFile();
            filePath = file.getAbsolutePath();

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
        File file = new File("C:\\Users\\farha\\Documents\\NetBeansProjects\\ParkiranUnparFunction\\coba.txt");
        //File file = new File(filePath);
        String row = "";

        //File file = new File("D:\\Data\\Rendra\\Kuliah\\Jaringan Syaraf Tiruan\\09. Tugas Besar\\Program\\JSTSom\\fileIkan.txt");
        //DefaultTableModel model = null;
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            // model = (DefaultTableModel) dataTabel.getModel();
            Object[] lines = br.lines().toArray();

            for (int i = 0; i < lines.length; i++) {
                row = lines[i].toString();
                if (i == 0) {
                    String[] hasil = row.split("=");
                    mode = hasil[1];
                    
                } else if (i == 1) {
                    String[] hasil = row.split("=");
                    
                    jumlahMesin  = hasil[1];
                    

                } else if (i == 2) {
                    this.arrival = row.split("=");
                    this.arrival = this.arrival[1].split(",");

                } else if (i == 3) {
                    this.uang = row.split("=");
                    this.uang = this.uang[1].split(",");
                    
                }

                // model.addRow(row);
                //String row = lines[i];
            }

        } catch (FileNotFoundException ex) {
            //Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public String[] getUang() {
        return this.uang;
    }

    public String[] getArival() {
        return this.arrival;
    }

    public String mode() {
        return mode;
    }

    public String jumlahMesin() {
        return jumlahMesin;
    }

}
