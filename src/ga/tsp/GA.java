/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.tsp;

import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.read.biff.BiffException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author cups
 */
public class GA {

    int genr = 1000, jmlKota, baris, jmlChild = 0, jmlKromosom = 10;
    private Kromosom kromosom[] = new Kromosom[jmlKromosom];
    private Kromosom child[] = new Kromosom[jmlKromosom];
    private Kromosom child1, child2, best;
    private Kota[] kota;
    private String[] tempK;
    private String address = null; //alamat file
    private double totalFitnes, pc = 0.8, pm = 0.3;

    public GA(String add) {

        this.address = add;
    }

    public void setGenr(int x) {
        genr = x;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void loadData() throws IOException, BiffException {
        File inputWorkbook = new File(address);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            int kolom = sheet.getColumns();
            baris = sheet.getRows();
            kota = new Kota[baris];
            // Loop over first 10 column and lines
            for (int i = 0; i < sheet.getRows(); i++) {
                kota[i] = new Kota();
            }
            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell = sheet.getCell(j, i);
                    CellType type = cell.getType();

                    if (type == CellType.NUMBER) {
                        if (j == 0) {
                            kota[i].setId(cell.getContents());
                        } else if (j == 1) {
                            kota[i].setX(cell.getContents());
                        } else {
                            kota[i].setY(cell.getContents());
                        }
                        jmlKota++;
                    }

                }
            }
            jmlKota = (jmlKota / kolom) + 1;
            this.best = new Kromosom(jmlKota );
            this.child1 = new Kromosom(jmlKota );
            this.child2 = new Kromosom(jmlKota );
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public Kota getKota(String id) {
        Kota temp = new Kota();
        for (int i = 0; i < jmlKota - 1; i++) {
            if (kota[i].getId().equals(id)) {
                temp = kota[i];
            }
        }
        return temp;
    }

    public boolean cek(String[] arr, String input) {
        boolean bol = false;
        for (int i = 0; i < arr.length; i++) {
            if (input.equals(arr[i])) {
                bol = false;
                break;
            } else {
                bol = true;
            }
        }
        return bol;
    }

    public double change(String x) {
        if (x != null) {
            return Double.valueOf(x);
        } else {
            return 0;
        }
    }

    public void searchMax() {
        for (int i = 0; i < kromosom.length; i++) {
            if (kromosom[i].getFitnessValue() < best.getFitnessValue()) {
                //System.out.println("fit : "+kromosom[i].getFitnessValue());
                best = kromosom[i];
            }
        }
    }

    public double hitungX2(int i, Kromosom k) {
        double temp = Math.abs(change(getKota(k.getGen(i)).getX()) - change(getKota(k.getGen(i + 1)).getX()));
        return temp * temp;
    }

    public double hitungY2(int i, Kromosom k) {
        double temp = Math.abs(change(getKota(k.getGen(i)).getY()) - change(getKota(k.getGen(i + 1)).getY()));
        return temp * temp;
    }

    public void hitungJaraknF(Kromosom k) {
        double fitnes, x = 0;
        //System.out.println("tes"+k.getGen().length+" "+kota.length);
        for (int i = 0; i < kota.length - 3; i++) {
            x = x + (Math.sqrt(hitungX2(i, k) + hitungY2(i, k)));
        }
        if (x != 0) {
            k.setDistance(x);
        } else {
            k.setDistance(0);
        }
        fitnes = 1 / x+0.0000000000000001;
        k.setFitnessValue(fitnes);
        totalFitnes = totalFitnes + fitnes;
    }

    public void makeKromosom() {
        Random r = new Random();
        boolean stop = false;

        int k, j;
        System.out.println("kota :" + kota.length);
        for (int i = 0; i < jmlKromosom; i++) {
            j = 0;
            k = 0;
            tempK = new String[kota.length];
            while (j < kota.length) {
                String ran = String.valueOf(r.nextInt(kota.length));
                //System.out.println("random num : " + ran);
                if (cek(tempK, ran) == true) {
                    tempK[k] = new String();
                    tempK[k] = ran;
                    k++;
                    j++;
                }

            }
            tempK[k - 1] = tempK[0];
            kromosom[i] = new Kromosom(kota.length + 1);
            kromosom[i].addKota(tempK);
            hitungJaraknF(kromosom[i]);
            System.out.print("Kromosom " + (i + 1) + " : ");
            cetak(kromosom[i].getGen());
            System.out.println("Fitness  : " + kromosom[i].getFitnessValue());

        }
    }

    public void cetak(String[] input) {
        System.out.print("cetak :");
        for (int i = 0; i < kota.length; i++) {
            System.out.print(input[i]);
            if (i != input.length - 1) {
                System.out.print(", ");
            } else {
                System.out.println("");
            }

            //System.out.println(""+input.length);
        }

    }

    public void crossOver() {
        double rc;
        int x, y, ran1, ran2, pos = 0;
        Random r = new Random();
        rc = r.nextDouble();
        x = r.nextInt(jmlKromosom - 1);
        ran1 = r.nextInt(baris - 1);
        y = r.nextInt(jmlKromosom - 1);
        ran2 = r.nextInt(baris - 1);
        //System.out.println("rc " + rc);
        //System.out.println("random kromosom" + x + "," + y);

        System.out.println(rc);
        if (rc < pc) {
            
            String temps1[] = new String[kota.length + 1];
            String temps2[] = new String[kota.length + 1];

            String temp1, temp2;
            Kromosom k1, k2;

            while (ran1 > ran2) {
                ran1 = r.nextInt(kota.length - 1);
                ran2 = r.nextInt(kota.length - 1);
            }

            for (int i = ran1; i <= ran2; i++) {
                //System.out.println("ran 1 :" + ran1 + "ran 2 :" + ran2);
                //System.out.println("length : " + temps2.length);
                //temps1[i] = new String();
                //temps2[i] = new String();
                //c++;
                temps1[i] = kromosom[x].getGen(i);
                temps2[i] = kromosom[y].getGen(i);

            }

            /*cetak(kromosom[x].getGen());
             System.out.println("Value parent 1 : " + kromosom[x].getFitnessValue());
             cetak(kromosom[y].getGen());
             System.out.println("Value parent 2 : " + kromosom[y].getFitnessValue());*/
            //int i = 0, k = 0;
            /*while (i <= kota.length - c) {
             for (int j = ran1; j < ran2; j++) {
             if (kromosom[x].getGen(k).equals(temps2[j]) == false) {
             //System.out.println("k : "+k);
             if (k >= ran1 && k < ran2) {
             k = ran2;
             }
             temps2[k] = kromosom[x].getGen(i);
             k++;
             i++;
             break;
             }
             }
             }*/

            for (int i = 0; i < kota.length; i++) {
                if (i < ran1 || i > ran2) {
                    if (cek(temps2, kromosom[x].getGen(i)) == true) {
                        child1.setGen(pos, kromosom[x].getGen(i));
                        pos++;
                    }
                } else {
                    child1.setGen(pos, kromosom[x].getGen(i));
                    pos++;
                }
            }

            hitungJaraknF(child1);
            if (child1.getFitnessValue() > kromosom[x].getFitnessValue()) {
                //child1.addKota(mutasi(child1));
                child[jmlChild] = child1;
            } else {
                child[jmlChild] = kromosom[x];
            }
            //cetak(child[jmlChild].getGen());
            
            
            pos = 0;
            for (int i = 0; i < kota.length; i++) {
                if (i < ran1 || i > ran2) {
                    if (cek(temps1, kromosom[y].getGen(i)) == true) {
                        child2.setGen(pos, kromosom[y].getGen(i));
                        pos++;
                    }
                } else {
                    child2.setGen(pos, kromosom[y].getGen(i));
                    pos++;
                }
            }

            hitungJaraknF(child2);
            if (child2.getFitnessValue() > kromosom[y].getFitnessValue()) {
                //child2.addKota(mutasi(child2));
                child[jmlChild + 1] = child2;
            } else {
                child[jmlChild + 1] = kromosom[y];
            }
            //System.out.println("child 2");
            //cetak(child[jmlChild + 1].getGen());
        } else {
            child[jmlChild] = kromosom[x];
            child[jmlChild + 1] = kromosom[y];

        }
        jmlChild = jmlChild + 2;
        //System.out.println("done");
    }

    public String[] mutasi(Kromosom k) {
        double rm;
        int counter = 0;
        String temp;
        ArrayList listGenWM = new ArrayList();
        ArrayList listGen = new ArrayList();
        String listAfter[] = new String[kota.length];
        int tempL[] = new int[kota.length];
        Random r = new Random();

        for (int i = 0; i < kota.length - 1; i++) {
            rm = r.nextDouble();
            if (rm < pm) {
                listGenWM.add(k.getGen(i));
                tempL[counter] = i;
                counter++;

            } else {
                listGen.add(k.getGen(i));
            }
        }
        if (counter > 2) {
            Collections.sort(listGenWM);
            listGen.addAll(listGenWM);
            for (int i = 0; i < kota.length; i++) {
                listAfter[i] = listGen.toString();
            }
            return listAfter;
        } else if (counter == 2) {
            temp = k.getGen(tempL[0]);
            k.setGen(tempL[0], k.getGen(tempL[1]));
            k.setGen(tempL[1], temp);
            return k.getGen();
        } else {
            return k.getGen();
        }

    }

    public void ulangPopulasi() {
        System.out.println("xx");
        int x = 1;
        for (int i = 0; i < genr; i++) {
            //System.out.println(kromosom.length / 2);
            for (int j = 0; j < kromosom.length / 2; j++) {
                //System.out.println("iterasi " + x);
                crossOver();
                x++;
            }
            kromosom = child;
            jmlChild = 0;
            searchMax();
        }

        System.out.println("Solusi : ");
        System.out.print("Rute : "); cetak(best.getGen());
        System.out.println("Nilai fitness : " + best.getFitnessValue());
        System.out.println("Dengan total jarak yang ditempuh : " + best.getDistance());
    }

}
