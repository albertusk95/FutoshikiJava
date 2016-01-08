package bin;

import java.util.Scanner;
import java.io.IOException;

/** File ini mengatur halaman utama dari aplikasi **/

public class HalamanUtama {
	
	public static void main(String[] args) throws Exception {
		
		//Deklarasi variabel untuk input data (scanner)
		Scanner input_scan = new Scanner(System.in);
		
		/** KAMUS LOKAL **/
		int a, b;
		int jenis_aksi;
		int[][] outputArr, solvedSudoku;
		String nama_file;
		
		/** ALGORITMA **/
		//Interface awal
		System.out.println("         Welcome!         ");
		System.out.println("Futoshiki Solver Sederhana");
		System.out.println("==========================");
		
		//Input user
		System.out.println("Yang ingin Anda lakukan? [0/1]");
		System.out.println("0. Start");
		System.out.println("1. Exit");
		System.out.println("------------------------------");
		System.out.print("> ");
		
		jenis_aksi = input_scan.nextInt();
		System.out.println();
		
		if (jenis_aksi == 0) {
			//Membaca file input
			nama_file = "/home/nim_13514100/Documents/FutoshikiJava/FileInput";
			ReadFileEksternal RFE = new ReadFileEksternal(nama_file);
			
			//Merepresentasikan input ke dalam array 2 dimensi
			outputArr = RFE.buatDuaDimensi();
			
			//Menampilkan representasi input dalam array 2 dimensi
			for (a=0; a < RFE.banyakBaris(); a++) {
				for (b=0; b < outputArr[0].length; b++) {
					System.out.print(outputArr[a][b]);
					if (outputArr[a][b] < 0) {
						System.out.print(" "+" ");
					} else {
						System.out.print(" "+" "+" ");
					}
                }
                System.out.println();
            }
            System.out.println();
            //Solving...
            FutoshikiSolver FS = new FutoshikiSolver(outputArr, 9, 9);
            //solvedSudoku = SS.solver();
			FS.solver();
		}
		
	}
	
}

