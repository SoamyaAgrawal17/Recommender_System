import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Soamya Agrawal
 */
public class CUR {

    public double getCURError(int c) throws IOException {
        int l;
        int m;
        int j;
        int i;
        double[][] a = new double[21][1000];
        double[][] b = new double[21][1000];
        BufferedReader br = new BufferedReader(new FileReader("movies.csv"));
        HashMap<Integer, Integer> hmap = new HashMap<>();

        br.readLine();

        //read movie file , store values in the array.
        for (i = 0; i < 1000; i++) {
            String line = br.readLine();
            String line1[] = line.split(",");
            hmap.put(Integer.parseInt(line1[0]), i);
        }

        br.close();

        // read user,movie and rating from file
        BufferedReader br1 = new BufferedReader(new FileReader("ratings20.csv"));
        br1.readLine();

        double r;
        int numMovies = 0;
        switch (c) {
            case 2:
                numMovies = 5;
                break;
            case 3:
                numMovies = 81;
                break;
            case 4:
                numMovies = 101;
                break;
            case 5:
                numMovies = 172;
                break;
            case 6:
                numMovies = 198;
                break;
            case 7:
                numMovies = 205;
                break;
            case 8:
                numMovies = 274;
                break;
            case 9:
                numMovies = 300;
                break;
            case 10:
                numMovies = 315;
                break;
            case 11:
                numMovies = 333;
                break;
            case 12:
                numMovies = 343;
                break;
            case 13:
                numMovies = 358;
                break;
            case 14:
                numMovies = 375;
                break;
            case 15:
                numMovies = 377;
                break;
            case 16:
                numMovies = 646;
                break;
            case 17:
                numMovies = 651;
                break;
            case 18:
                numMovies = 733;
                break;
            case 19:
                numMovies = 784;
                break;

            case 20:
                numMovies = 1120;
                break;
            case 21:
                numMovies = 1170;
                break;

        }
        for (i = 0; i < numMovies; i++) {
            String line = br1.readLine();
            String[] line2 = line.split(",");
            l = Integer.parseInt(line2[0]);
            m = hmap.get(Integer.parseInt(line2[1]));
            r = Double.parseDouble(line2[2]);
            a[l][m] = r;
            b[l][m] = r;
        }

        br1.close();

        //matrix A Obtained from the given data
     /*   System.out.println();
        System.out.println("**********************************************************");
        System.out.println("A Matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < 21; i++) {
            for (j = 0; j < 1000; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }*/
        //Matrix matA = new Matrix(a);

        // int c = matA.rank()/10;
        double[] colp = new double[1000];
        double sum = 0;
        //setting c matrix
        for (i = 0; i < 1000; i++) {
            for (j = 0; j < 21; j++) {
                //System.out.println(colp[i]);
                colp[i] = (b[j][i] * b[j][i]) + colp[i];
            }
            // System.out.println(colp[i]);
            sum = sum + colp[i];

        }
//        System.out.println(sum);


        for (i = 0; i < 1000; i++) {
            colp[i] = colp[i] / sum;
            // System.out.print(i + " " + colp[i] + " ");
        }

        System.out.println();
        double[] rowp = new double[21];
        //setting r matrix
        for (i = 0; i < 21; i++) {
            for (j = 0; j < 1000; j++) {
                rowp[i] = b[i][j] * b[i][j] + rowp[i];
            }
        }

        for (i = 0; i < 21; i++) {
            rowp[i] = rowp[i] / sum;
            // System.out.print(i + " " + rowp[i] + " ");
        }

        Random ran = new Random();
        ran.setSeed(123456789);
        double[][] column = new double[21][c];
        //double[][] column=new double[21][1000];
        int k = 0;
        int[] coli = new int[c];

        for (i = 0; i < c; i++) {
            int rann = ran.nextInt(1000); //column number
            if (colp[rann] != 0) {
                coli[i] = rann;
            } else {
                --i;
            }
        }

        for (i = 0; i < c; i++) {

//            System.out.println("The seed is:" + rann + " ");
            for (j = 0; j < 21; j++) {
                column[j][k] = b[j][coli[i]] / Math.sqrt(c * colp[coli[i]]);
//                System.out.print(column[j][k] + " ");
            }
//            System.out.println();
            k++;
        }
        System.out.println();
        double[][] row = new double[c][1000];
        int[] rowi = new int[c];
        k = 0;

        for (i = 0; i < c; i++) {
            int rann = ran.nextInt(20) + 1; //row number
            if (rowp[rann] != 0) {
                rowi[i] = rann;
            } else {
                --i;
            }
        }

        for (i = 0; i < c; i++) {

            //System.out.print("The seed is:" + rann + " ");
            for (j = 0; j < 1000; j++) {
                row[k][j] = b[rowi[i]][j] / Math.sqrt(c * rowp[rowi[i]]);
//                System.out.print(row[k][j] + " ");
            }
//            System.out.println();
            k++;
        }

        /*System.out.println("C matrix");
        for (i = 0; i < c; i++) {
            for(j = 0; j <c ;j++){
            System.out.print(column[j][i] + " ");
            }
            System.out.println();
        }

        System.out.println();
        for (i = 0; i < c; i++) {
            System.out.println(rowi[i] + " ");
        }
        System.out.println();*/
        //w matrix
        double[][] w = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                w[i][j] = b[rowi[i]][coli[j]];
            }
        }

    /*    System.out.println("**********************************************************");
        System.out.println("W matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                System.out.print(w[i][j] + " ");
                //w[i][j]=b[rowi[i]][coli[i]];
            }
            System.out.println();
        } */
//        double[][] w = {{0, 1000}, {1000, 0}};

//        c = 2;
        double[][] at = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                at[j][i] = w[i][j];
            }
        }

        /*
        // printing a transpose matrix
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                System.out.print(at[i][j] + " ");
            }
            System.out.println();
        }*/
        //multiply a (cxc) with atranspose (cxc) to Matrix U
        double[][] u = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                for (k = 0; k < c; k++) {
                    u[i][j] += w[i][k] * at[k][j];
                }
            }
        }

        /* for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                System.out.print(u[i][j] + " ");
            }
            System.out.println();
        }*/
        Matrix mat = new Matrix(u);
        EigenvalueDecomposition evd = new EigenvalueDecomposition(mat);
        Matrix sigma = evd.getD();
        double[][] sigmaa = sigma.getArray();

        Matrix m1 = evd.getV();
        double[][] eigenU = m1.getArray();

//        System.out.println("**********************************************************");
//        System.out.println("**********************************************************");
//        System.out.println("Sigma matrix");
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                if (sigmaa[i][j] < 0) {
                    sigmaa[i][j] = 0;
                }
                //System.out.print(sigmaa[i][j] + " ");
            }

//            System.out.println();
        }

        //multiply at (cxc) with a (cxc) to obtain U transpose matrix
        double[][] uT = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                for (k = 0; k < c; k++) {
                    uT[i][j] += at[i][k] * w[k][j];
                }
            }
        }

        Matrix mNew = new Matrix(uT);
        EigenvalueDecomposition evd1 = new EigenvalueDecomposition(mNew);

        Matrix m11 = evd1.getV();
        double[][] b1 = m11.getArray();

        double[][] b1t = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                b1t[j][i] = b1[i][j];
            }
        }

        double temp;
        for (i = 0; i < c; i++) {
            for (j = 0; j < c / 2; j++) {
                temp = b1[i][j];
                b1[i][j] = b1[i][c - j - 1];
                b1[i][c - j - 1] = temp;
            }
        }

        for (i = 0; i < c; i++) {
            for (j = 0; j < c / 2; j++) {
                temp = eigenU[i][j];
                eigenU[i][j] = eigenU[i][c - j - 1];
                eigenU[i][c - j - 1] = temp;
            }
        }

   /*     System.out.println("**********************************************************");
        System.out.println("U matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                System.out.print(eigenU[i][j] + "    ");
            }
            System.out.println();
        } */

        //for sigma matrix Arranging the terms in decreasing order
        for (i = 0; i < c / 2; i++) {
            temp = sigmaa[i][i];
            sigmaa[i][i] = sigmaa[c - i - 1][c - i - 1];
            sigmaa[c - i - 1][c - i - 1] = temp;
        }

        double[][] sigmaFinal = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                sigmaa[i][j] = Math.sqrt(sigmaa[i][j]);
                sigmaFinal[i][j] = sigmaa[i][j];
            }
        }

   /*     System.out.println();
        System.out.println("**********************************************************");
        System.out.println("Sigma Final matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                System.out.print(sigmaFinal[i][j] + "     ");
            }
            System.out.println();
        } */

        //for v transpose matrix arranging the terms in Decreasing order
        for (i = 0; i < c / 2; i++) {
            for (j = 0; j < c; j++) {
                temp = b1t[i][j];
                b1t[i][j] = b1t[c - i - 1][j];
                b1t[c - i - 1][j] = temp;
            }
        }

     /*   System.out.println("**********************************************************");
        System.out.println("V Transpose matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                System.out.print(b1t[i][j] + "    ");
            }
            System.out.println();
        }*/

        double[][] temp1 = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                for (k = 0; k < c; k++) {
                    temp1[i][j] += (w[i][k] * b1[k][j]);
                }
            }
        }
     /*   System.out.println();
        System.out.println("**********************************************************");
        System.out.println("Calculated W*V matrix calculating U");
        System.out.println("**********************************************************");
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                System.out.print(temp1[i][j] + "    ");
            }
            System.out.println();
        } */

        double[][] finalU = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                if (sigmaFinal[j][j] != 0) {
                    finalU[i][j] = temp1[i][j] / sigmaFinal[j][j];
                }
            }
        }

        double[][] temp2 = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                for (k = 0; k < c; k++) {
                    temp2[i][j] += sigmaa[i][k] * temp1[k][j];
                }
            }
        }

        /**
         * Calulating U matrix for known V matrix so that we get unique eigen
         * values*
         */
     /*   System.out.println();
        System.out.println("**********************************************************");
        System.out.println("Calculated U matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                System.out.print(finalU[i][j] + "    ");
            }
            System.out.println();
        }
*/
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
//                System.out.print(sigmaFinal[i][j] + "     ");
                if (sigmaFinal[i][j] != 0) {
                    sigmaFinal[i][j] = 1 / sigmaFinal[i][j];
                }
            }
//            System.out.println();
        }

        //Y matrix
        double[][] y = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                y[j][i] = b1[i][j];
            }
        }
        // xTmatrix
        double[][] xt = new double[c][c];
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                xt[j][i] = finalU[i][j];
            }
        }

        double[][] temp4 = new double[c][c];
        for (i = 0; i < c; i++) { // aRow
            for (j = 0; j < c; j++) { // bColumn
                for (k = 0; k < c; k++) { // aColumn
                    temp4[i][j] += y[i][k] * sigmaFinal[k][j];
                }
            }
        }
        double[][] temp5 = new double[c][c];
        for (i = 0; i < c; i++) { // aRow
            for (j = 0; j < c; j++) { // bColumn
                for (k = 0; k < c; k++) { // aColumn
                    temp5[i][j] += temp4[i][k] * sigmaFinal[k][j];
                }
            }
        }
        double [] [] temp3 = new double[c][c];
        for (i = 0; i < c; i++) { // aRow
            for (j = 0; j < c; j++) { // bColumn
                for (k = 0; k < c; k++) { // aColumn
                    temp3[i][j] += temp5[i][k] * xt[k][j];
                }
            }
        }

    /*    System.out.println("**********************************************************");
        System.out.println("CUR's U Matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < c; i++) {
            for (j = 0; j < c; j++) {
                System.out.print(temp3[i][j] + " ");
            }
            System.out.println();
        }
*/
        double error = 0;
        // Calculating C * U Matrix
        double[][] cucal = new double[21][c];
        for (i = 0; i < 21; i++) {
            for (j = 0; j < c; j++) {
                for (k = 0; k < c; k++) {
                    cucal[i][j] += column[i][k] * temp3[k][j];
                }
            }
        }

        // Calcuating C * U * R  matrix
        double[][] curcal = new double[21][1000];
        for (i = 0; i < 21; i++) {
            for (j = 0; j < 1000; j++) {
                for (k = 0; k < c; k++) {
                    curcal[i][j] += cucal[i][k] * row[k][j];
                }
            }
        }

        //error calculation
        for (i = 0; i < 21; i++) {
            for (j = 0; j < 1000; j++) {
                error += Math.pow((b[i][j] - curcal[i][j]), 2);
            }
        }

    /*    System.out.println();
        System.out.println("**********************************************************");
        System.out.println("Calculated finally the A matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < 21; i++) {
            for (j = 0; j < 1000; j++) {
                System.out.print(curcal[i][j] + "    ");
            }
            System.out.println();
        } */
        error = Math.sqrt(error);
      /*  System.out.println();
        System.out.println("**********************************************************");
        System.out.println("Error is  " + error);
        System.out.println("**********************************************************"); */
        hmap.clear();
        return error;
    }

}
