package bin;

import java.nio.charset.Charset;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.util.*;

public class ReadFileEksternal {
	
	private String lokasi;
	
	/** Konstruktor **/
	public ReadFileEksternal (String lokasi_file) {
		lokasi = lokasi_file;
	}
	
	/** Menghitung banyak baris dalam file input **/
	int banyakBaris() throws Exception {
		
		FileReader fileTujuan = new FileReader(lokasi);
		BufferedReader bfr = new BufferedReader(fileTujuan);
		
		String baris;
		int jumlahBaris = 0;
		
		while ((baris = bfr.readLine()) != null) {
			jumlahBaris++;
		}
		
		jumlahBaris--;
		
		bfr.close();
		
		return jumlahBaris;		
	}
	
	/** Membaca banyak kolom dalam file input **/
	int banyakKolom(int panjangResults, int jmlhBaris) throws Exception{
		return (panjangResults / jmlhBaris);
	}
	
	/** Mencari file input yang akan dibaca **/
	int[] bacaFileTujuan(String file) throws Exception {
		
		FileInputStream fis = new FileInputStream (file);

		int[] RV;
		
		RV = bacaNilaiDouble(fis);
		
		return RV;
	}
	
	/** Membaca isi file input **/
	int[] bacaNilaiDouble(InputStream in) throws Exception {
	
		String barisSekarang;
		
		String BARIS;
		
		BufferedInputStream s = new BufferedInputStream(in);
		BufferedReader nilaiDouble = new BufferedReader(new InputStreamReader(s));
   
		int j = 0, k;
		int bnykBaris = banyakBaris();
		
		double[] values01 = new double[9];
	
		int valRow, valCol;
		
		/*
		BARIS = nilaiDouble.readLine();
		
		StringTokenizer st = new StringTokenizer(BARIS);
		
		while (st.hasMoreElements()) {
			values01[j++] = Double.valueOf(st.nextToken()).doubleValue();
		}
		
		valRow = (int) values01[0];
		valCol = (int) values01[1];
		
		System.out.println(valRow + " " + valCol);
		
		int dimensi = valCol * valRow;
		*/
		
		int[] valuesX = new int[81];
		
		j = 0;
		k = 0;
		
		while ((barisSekarang = nilaiDouble.readLine()) != null) {  
			
			StringTokenizer st1 = new StringTokenizer(barisSekarang);
			
			while(st1.hasMoreElements()) {
				//if ( j != 0 && j != 1 ) { 
					valuesX[k++] = (int) Double.valueOf(st1.nextToken()).doubleValue();
					//System.out.println(valuesX[k]);
				//}
				j++;
			}
			
		}
		
		return valuesX;
    }
	
	/** Membuat matriks 2 dimensi dari data file input **/
	int[][] buatDuaDimensi() throws Exception{
			
			int a = 0, b = 0, i;
			int bnykBaris = banyakBaris();
			int[] results = bacaFileTujuan(lokasi);
			int nilaiKolom = banyakKolom(results.length, bnykBaris);
			//int nilaiKolom = 9;
			int[][] hasilDuaDimensi = new int[bnykBaris][nilaiKolom];
			
			for(i = 0; i < results.length; i++ ) {
				
				hasilDuaDimensi[a][b] = results[i];
				b++;
				if (b >= nilaiKolom) {
					b = 0;
					a++;
				}
			} 
			
			return hasilDuaDimensi;
		
	}
	
}

