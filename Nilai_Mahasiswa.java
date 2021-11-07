/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO
 */
import java.util.*;
import java.lang.reflect.Array;

public class Nilai_Mahasiswa {
  public static void main(String[] args) {
    int index = 0;
    int Total_mahasiswa;
    Scanner input = new Scanner(System.in);
    String NIM;
    String Nama;
    int Nilai;
    ArrayList<Mahasiswa> Data_Mahasiswa = new ArrayList<Mahasiswa>();
    System.out.println("========================Tugas PBO Sesi 7=======================");
    System.out.println("====================PROGRAM NILAI MAHASISWA====================");
    System.out.println("");
    System.out.print("Masukkan jumlah mahasiswa : ");
    Total_mahasiswa = Integer.parseInt(input.nextLine());
    System.out.println("---------------------------------------------------------------");
    while (index != Total_mahasiswa) {
      System.out.println("Mahasiswa ke - " + (index + 1));
      System.out.print("Masukkan NIM   : ");
      NIM = input.nextLine();
      System.out.print("Masukkan Nama  : ");
      Nama = input.nextLine();
      System.out.print("Masukkan Nilai : ");
      Nilai = Integer.parseInt(input.nextLine());
      System.out.println("---------------------------------------------------------------");
      Data_Mahasiswa.add(new Mahasiswa(NIM, Nama, Nilai));
      index += 1;
    }
    Ujian ulangan = new Ujian();
    ulangan.kelola_mahasiswa(Data_Mahasiswa); 
    ulangan.get_ujian_akhir();
  }
}

class Ujian {
  private int lulus = 0, gagal = 0;
  private float nilai_rata_rata = 0;
  private ArrayList<Mahasiswa> mahasiswa;
  private ArrayList<Mahasiswa> mahasiswa_lulus = new ArrayList<Mahasiswa>();
  private ArrayList<Mahasiswa> mahasiswa_gagal = new ArrayList<Mahasiswa>();

  public void kelola_mahasiswa(ArrayList<Mahasiswa> mahasiswa) {
    this.mahasiswa = mahasiswa;
  }

  public Mahasiswa[] sortir_mahasiswa(Mahasiswa[] mahasiswa) {
    for (int i = 0; i < mahasiswa.length; i++) {
      Mahasiswa temp = mahasiswa[i];
      int posisi = i;
      while( 
        posisi >= 1 && 
        mahasiswa[posisi - 1].get_Nilai().charAt(0) > temp.get_Nilai().charAt(0) 
      ) {
        mahasiswa[posisi] = mahasiswa[posisi - 1];
        posisi--;
      }
      mahasiswa[posisi] = temp;
    }
    return mahasiswa;
  }

  public Mahasiswa[][] kategori_nilai_mahasiswa(Mahasiswa[] array_mahasiswa) {
    int index_nilai = 0; 
    int index_mahasiswa = 0;
    Mahasiswa[] sortir_array_mahasiswa = this.sortir_mahasiswa(array_mahasiswa);
    Mahasiswa[][] grup_nilai_mahasiswa = new Mahasiswa[5][this.mahasiswa.size()];
    String alur = sortir_array_mahasiswa[0].get_Nilai();
    for (Mahasiswa mahasiswa: sortir_array_mahasiswa) {
      if (!alur.equals(mahasiswa.get_Nilai())) {
        alur = mahasiswa.get_Nilai();
        index_nilai++;
        index_mahasiswa = 0;
      }
      if (alur.equals(mahasiswa.get_Nilai())) {
        grup_nilai_mahasiswa[index_nilai][index_mahasiswa] = mahasiswa;
        index_mahasiswa += 1;
      } 
    }
    return grup_nilai_mahasiswa;
  }

  public void getResume_mahasiswa() {
    ArrayList<Mahasiswa> gabungan_mahasiswa = new ArrayList<Mahasiswa>();
    gabungan_mahasiswa.addAll(this.mahasiswa_lulus);
    gabungan_mahasiswa.addAll(this.mahasiswa_gagal);
    Mahasiswa[] array_mahasiswa = gabungan_mahasiswa.toArray(
      new Mahasiswa[this.mahasiswa.size()]
    ); 
    Mahasiswa[][] kategori_nilai_mahasiswa = this.kategori_nilai_mahasiswa(
      array_mahasiswa
    );
    for (Mahasiswa[] nilai_mahasiswa: kategori_nilai_mahasiswa) {
      if (nilai_mahasiswa[0] != null) {
        ArrayList<Mahasiswa> mahasiswa = new ArrayList<Mahasiswa>(
          Arrays.asList(nilai_mahasiswa)
        );
        System.out.println(
          "Mahasiswa dengan Nilai " + nilai_mahasiswa[0].get_Nilai() 
          + " yaitu     : " + this.bagan_mahasiswa(mahasiswa)
        ); 
      }
    }
  }

  public String bagan_mahasiswa(ArrayList<Mahasiswa> mahasiswa) {
    String nilai_akhir = "";
    for (int i = 0; i < mahasiswa.size(); i++) {
      if (mahasiswa.get(i) != null) {
        if (i == 0) {
          nilai_akhir += mahasiswa.get(i);
        } else {
          nilai_akhir += ", " + mahasiswa.get(i);
        }
      }
    }
    return nilai_akhir;
  }

  public void get_ujian_akhir() {
    for (Mahasiswa mahasiswa: this.mahasiswa) {
      System.out.println("Data Mahasiswa");
      System.out.println("NIM   : " + mahasiswa.get_NIM());
      System.out.println("Nama  : " + mahasiswa.get_Nama());
      System.out.println("Nilai : " + mahasiswa.getNilai());
      System.out.println("Grade : " + mahasiswa.get_Nilai());
      System.out.println("===============================================================");
      if (
        mahasiswa.get_Nilai().equals("A") || 
        mahasiswa.get_Nilai().equals("B") ||
        mahasiswa.get_Nilai().equals("C") 
      ) {
        this.mahasiswa_lulus.add(mahasiswa);
      } else {
        this.mahasiswa_gagal.add(mahasiswa);
      }
      this.nilai_rata_rata += mahasiswa.getNilai();
    }
    this.nilai_rata_rata = this.nilai_rata_rata / this.mahasiswa.size();
    System.out.println("Jumlah Mahasiswa                   : " + this.mahasiswa.size());
    System.out.println(
      "Jumlah Mahasiswa yang lulus        : " + this.mahasiswa_lulus.size() +
      " yaitu " + this.bagan_mahasiswa(this.mahasiswa_lulus)
    );
    System.out.println(
      "Jumlah Mahasiswa yang tidak lulus  : "  + this.mahasiswa_gagal.size() +
      " yaitu " + this.bagan_mahasiswa(this.mahasiswa_gagal)
    );
    this.getResume_mahasiswa();
    System.out.println("Rata-rata nilai mahasiswa adalah   : " + this.nilai_rata_rata);
    System.out.println("");
    System.out.println("=====================Muhammad Rifqi Darmawan===================");
    System.out.println("==========================Reguler TI20A========================");
    System.out.println("");
  }
}

class Nilai {
  protected int nilai;

  Nilai(int nilai) {
    this.nilai = nilai;
  }

  public int get_nilai() {
    return this.nilai;
  }

  public String get_Nilai() {
    if (this.nilai >= 80 && this.nilai <= 100) return "A";
    if (this.nilai >= 70 && this.nilai <= 79) return "B";
    if (this.nilai >= 60 && this.nilai <= 69) return "C";
    if (this.nilai >= 50 && this.nilai <= 59) return "D";
    if (this.nilai < 50) return "E";
    else return "Inputan Salah";
  }
}

class Mahasiswa extends Nilai {
  private String NIM;
  private String Nama;

  Mahasiswa(String NIM, String Nama, int Nilai) {
    super(Nilai);
    this.NIM = NIM;
    this.Nama = Nama;
  }

  public String get_NIM() {
    return this.NIM;
  }

  public String get_Nama() {
    return this.Nama;
  }

  public int getNilai() {
    return this.nilai;
  }

  public String toString() {
    return this.Nama;
  }
}