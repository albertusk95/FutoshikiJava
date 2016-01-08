package bin;

import java.util.Scanner;
import java.io.IOException;

/* -1 untuk >
 * -2 untuk < 
 * -3 untuk v
 * -4 untuk ^
 * -5 untuk no sign
 * */
public class FutoshikiSolver {
	
	private int[][] matrixFutoshiki;
	private int[][] matrixTemp;
	public int rowEff, colEff;
	public int a, b, c, d;
	public int counter_exactSolving;
	public int nbElmt;
	int[][][] hasValue = new int[6][10][10];
	int[] info_row = new int[82];
	int[] info_col = new int[82];
	int[][] info_value = new int[6][82];
	int[][] stat_lessthanUp = new int[10][10];
	int[][] stat_morethanUp = new int[10][10];
	int[][] stat_lessthanDown = new int[10][10];
	int[][] stat_morethanDown = new int[10][10];
	int[][] stat_lessthanLeft = new int[10][10];
	int[][] stat_morethanLeft = new int[10][10];
	int[][] stat_lessthanRight = new int[10][10];
	int[][] stat_morethanRight = new int[10][10];
	
	/** Konstruktor **/
	public FutoshikiSolver(int[][] matrixOutput, int row, int col) {
		matrixFutoshiki = matrixOutput;
		matrixTemp = matrixOutput;
		rowEff = row;
		colEff = col;
		
		//Inisialisasi setiap kotak dengan angka 1-5
		for (a=0; a < rowEff; a++) {
			for (b=0; b < colEff; b++) {
				for (c=1; c <= 5; c++) {
					hasValue[c][a][b] = 1;
				}
            }
        }
        
        //Inisialisasi awal status ketidaksamaan setiap sel kosong
        for (a=0; a < rowEff; a++) {
			for (b=0; b < colEff; b++) {
				stat_lessthanUp[a][b] = 0;	
				stat_morethanUp[a][b] = 0;	
				stat_lessthanDown[a][b] = 0;
				stat_morethanDown[a][b] = 0;
				stat_lessthanLeft[a][b] = 0;
				stat_morethanLeft[a][b] = 0;
				stat_lessthanRight[a][b] = 0;
				stat_morethanRight[a][b] = 0;
			}
		}
        
 /* -1 untuk >
 * -2 untuk < 
 * -3 untuk v
 * -4 untuk ^
 * -5 untuk no sign
 * */
 
        //Re-Inisialisasi status ketidaksamaan setiap sel kosong
        for (a=0; a < rowEff; a++) {
			for (b=0; b < colEff; b++) {
				if (matrixFutoshiki[a][b] == 0) {
					//re-inisialisasi jika di atas sel itu ada tanda ketidaksamaan
					if (a-1 >= 0) {
						if (matrixFutoshiki[a-1][b] == -3) {
							stat_lessthanUp[a][b] = 1;
						} else if (matrixFutoshiki[a-1][b] == -4) {
							stat_morethanUp[a][b] = 1;
						}
					} 
					if (a+1 < rowEff) {
						if (matrixFutoshiki[a+1][b] == -3) {
							stat_morethanDown[a][b] = 1;
						} else if (matrixFutoshiki[a+1][b] == -4) {
							stat_lessthanDown[a][b] = 1;
						}
					}
					if (b-1 >= 0) {
						if (matrixFutoshiki[a][b-1] == -1) {
							stat_lessthanLeft[a][b] = 1;
						} else if (matrixFutoshiki[a][b-1] == -2) {
							stat_morethanLeft[a][b] = 1;
						}
					}
					if (b+1 < colEff) {
						if (matrixFutoshiki[a][b+1] == -1) {
							stat_morethanRight[a][b] = 1;
						} else if (matrixFutoshiki[a][b+1] == -2) {
							stat_lessthanRight[a][b] = 1;
						}
					}
				}
			}
		}
	}
	
	void reInitializedFutoshiki() throws Exception {
		/* Me-reinisialisasi angka hasValue, dst dimana tidak boleh ada angka yang berulang pada
		 * satu kolom dan baris */
		 
		 int a1, b1, c1;
		 
		 for (a1=0; a1 < rowEff; a1++) {
			 for (b1=0; b1 < colEff; b1++) {
				 if (matrixFutoshiki[a1][b1] != 0 && matrixFutoshiki[a1][b1] > 0) {
					 //Re-initialized untuk sebuah baris
					 for (c1=0; c1 < colEff; c1++) {
						hasValue[matrixFutoshiki[a1][b1]][a1][c1] = 0;
					 }
					 //Re-initialized untuk sebuah kolom
					 for (c1=0; c1 < rowEff; c1++) {
						 hasValue[matrixFutoshiki[a1][b1]][c1][b1] = 0;
					 }
				 }
			 }
		 }
	}
	
	void exactSolving() throws Exception {
		int counter;
		int getrow = 0, getcol = 0;
		
		//exactSolving for row
		for (a=0; a < rowEff; a++) {
			counter = 0;
			for (b=0; b < colEff; b++) {
				if (matrixFutoshiki[a][b] == 0) {
					counter++;
					getrow = a;
					getcol = b;
				}
			}
			if (counter == 1) {
				for (c=1; c <= 5; c++) {
					if (hasValue[c][getrow][getcol] == 1) {
						matrixFutoshiki[getrow][getcol] = c;
					}
				}
				counter_exactSolving++;
				reInitializedFutoshiki();
			}
		}
		
		//exactSolving for column
		for (a=0; a < colEff; a++) {
			counter = 0;
			for (b=0; b < rowEff; b++) {
				if (matrixFutoshiki[b][a] == 0) {
					counter++;
					getrow = b;
					getcol = a;
				}
			}
			if (counter == 1) {
				for (c=1; c <= 5; c++) {
					if (hasValue[c][getrow][getcol] == 1) {
						matrixFutoshiki[getrow][getcol] = c;
					}
				}
				counter_exactSolving++;
				reInitializedFutoshiki();
			}
		}
	}
	
	//Membentuk sebuah list untuk sel kosong
	void createList() throws Exception {
		int addr_counter = 0;
		
		for (a=0; a < rowEff; a++) {
			for (b=0; b < colEff; b++) {
				if (matrixFutoshiki[a][b] == 0) {
					//representasi alamat memori dengan index angka
					addr_counter++;
					//inisialisasi isi sebuah elemen list
					for (c=1; c <= 5; c++) {
						info_value[c][addr_counter] = 0;
					}
					//re-inisialisasi isi sebuah elemen list
					for (c=1; c <= 5; c++) {
						if (hasValue[c][a][b] == 1) {
							info_value[c][addr_counter] = 1; 
						}
					}
					//menentukan info untuk row dan column sebuah elemen list
					info_row[addr_counter] = a;
					info_col[addr_counter] = b;
				}
			}
		}
		
		nbElmt = addr_counter;
	}
	
	//Menghentikan program sampai user menekan tombol Enter
	void pauseProg(){
		System.out.println("Press enter to continue...");
		Scanner keyboard = new Scanner(System.in);
		keyboard.nextLine();
	}
	
	//Solves Futoshiki puzzle dengan algoritma brute force
	void solving() throws Exception {
		int addr_counter = 1;
		int found, found_possible_value, error;
		int getrow, getcol;
		
		while (addr_counter <= nbElmt) {
			
			found_possible_value = 0;
			
			for (c=1; c <= 5; c++) {
				if (info_value[c][addr_counter] == 1) {
					found = 0;
					//cek baris, apakah ada yang sama
					for (a=0; a < colEff; a++) {
						if (matrixTemp[info_row[addr_counter]][a] == c) {
							found = 1;
						}
					}
					//cek kolom, apakah ada yang sama
					for (a=0; a < rowEff; a++) {
						if (matrixTemp[a][info_col[addr_counter]] == c) {
							found = 1;
						}
					}
					
					if (found == 0) {
						//cek dengan tanda ketidaksamaan
							getrow = info_row[addr_counter];
							getcol = info_col[addr_counter];
							error = 0;
							if (stat_lessthanUp[getrow][getcol] == 1) {
								if (matrixTemp[getrow-2][getcol] != 0) {
									if (c > matrixTemp[getrow-2][getcol]) {
										error = 1;
									}
								}
							}
							if (error == 0) {
								if (stat_lessthanDown[getrow][getcol] == 1) {
									if (matrixTemp[getrow+2][getcol] != 0) {
										if (c > matrixTemp[getrow+2][getcol]) {
											error = 1;
										}
									}
								}
								if (error == 0) {
									if (stat_morethanUp[getrow][getcol] == 1) {
										if (matrixTemp[getrow-2][getcol] != 0) {
											if (c < matrixTemp[getrow-2][getcol]) {
												error = 1;
											}
										}
									}
									if (error == 0) {
										if (stat_morethanDown[getrow][getcol] == 1) {
											if (matrixTemp[getrow+2][getcol] != 0) {
												if (c < matrixTemp[getrow+2][getcol]) {
													error = 1;
												}
											}
										}
										if (error == 0) {
											if (stat_lessthanLeft[getrow][getcol] == 1) {
												if (matrixTemp[getrow][getcol-2] != 0) {
													if (c > matrixTemp[getrow][getcol-2]) {
														error = 1;
													}
												}
											}
											if (error == 0) {
												if (stat_lessthanRight[getrow][getcol] == 1) {
													if (matrixTemp[getrow][getcol+2] != 0) {
														if (c > matrixTemp[getrow][getcol+2]) {
															error = 1;
														}
													}
												}
												if (error == 0) {
													if (stat_morethanLeft[getrow][getcol] == 1) {
														if (matrixTemp[getrow][getcol-2] != 0) {
															if (c < matrixTemp[getrow][getcol-2]) {
																error = 1;
															}
														}
													}
													if (error == 0) {
														if (stat_morethanRight[getrow][getcol] == 1) {
															if (matrixTemp[getrow][getcol+2] != 0) {
																if (c < matrixTemp[getrow][getcol+2]) {
																	error = 1;
																}
															}
														} 
														if (error == 0) {
															//inisialisasi sel kosong yg bersangkutan dengan nilai c
															matrixTemp[getrow][getcol] = c;
															info_value[c][addr_counter] = -1;
															found_possible_value = 1;
															break;
														} else {
															//do nothing - mencari nilai c yang lain
														}
													} else {
														//do nothing - mencari nilai c yang lain
													}
												} else {
													//do nothing - mencari nilai c yang lain
												}
											} else {
												//do nothing - mencari nilai c yang lain
											}
										} else {
											//do nothing - mencari nilai c yang lain
										}
									} else {
										//do nothing - mencari nilai c yang lain
									}
								} else {
									//do nothing - mencari nilai c yang lain
								}
							} else {
								//do nothing - mencari nilai c yang lain
							}
					} else {
						//do nothing - mencari nilai c yang lain
					}
				}
			}
			
			//mencetak hasil Futoshiki sementara
			/*
			System.out.println("matrixTemp:"+" "+info_row[addr_counter]+" "+info_col[addr_counter]);
			for (a=0; a < rowEff; a++) {
				for (b=0; b < colEff; b++) {
					System.out.print(matrixTemp[a][b]+" ");
				}
				System.out.println();
			}
			
			pauseProg();
			*/
			
			if (found_possible_value == 1) {
				addr_counter++;
			} else {
				for (c=1; c <= 5; c++) {
					if (info_value[c][addr_counter] == -1) {
						info_value[c][addr_counter] = 1;
					}
				}
				matrixTemp[info_row[addr_counter]][info_col[addr_counter]] = 0;
				if (addr_counter - 1 >= 1) {
					addr_counter--;
					matrixTemp[info_row[addr_counter]][info_col[addr_counter]] = 0;
				} else {
					System.out.println("No solution");
					break;
				}
			}
		}
	}
	
	void solver () throws Exception {
		
		reInitializedFutoshiki();		//me-reinisialisasi angka hasValue
		
		counter_exactSolving = 1;
		while (counter_exactSolving != 0) {
			counter_exactSolving = 0;
			exactSolving(); 			//mengisi kotak yang sudah pasti nilainya
			System.out.println("counter exact:"+" "+counter_exactSolving);
		}
		
		createList();					//membuat sebuah list untuk sel kosong
		
		matrixTemp = matrixFutoshiki;	//menyamakan matrix sementara dengan matrix asli
		
		solving();						//proses solving dengan algoritma brute force
		
		//print hasil akhir futoshiki
		System.out.println();
		for (a=0; a < rowEff; a++) {
				for (b=0; b < colEff; b++) {
					System.out.print(matrixTemp[a][b]);
					if (matrixTemp[a][b] < 0) {
						System.out.print(" "+" ");
					} else {
						System.out.print(" "+" "+" ");
					}
                }
                System.out.println();
            }
	}
	
}
