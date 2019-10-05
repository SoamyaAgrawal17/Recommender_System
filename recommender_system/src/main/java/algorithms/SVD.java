package algorithms;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Soamya Agrawal
 */
public class SVD {

    /**
     * @param rows    the number of rows
     * @param columns the number of columns
     * @throws java.io.IOException
     */

    public double getSVDError(int rows, int columns) throws IOException {

        double[][] a = new double[rows][columns];

        BufferedReader br = new BufferedReader(new FileReader("/Users/soamya.agrawal/Desktop/BITS_Assignment/Information_Retrieval/Recommender_System/recommender_system/src/main/resources/movies.csv"));
        HashMap<Integer, Integer> hmap = new HashMap<>();
        int i;
        br.readLine();

        //read movie file , store values in the array.
        for (i = 0; i < columns; i++) {
            String line = br.readLine();
            String[] line1 = line.split(",");
            hmap.put(Integer.parseInt(line1[0]), i);
        }

        br.close();

        // read user,movie and rating from file 
        BufferedReader br1 = new BufferedReader(new FileReader("/Users/soamya.agrawal/Desktop/BITS_Assignment/Information_Retrieval/Recommender_System/recommender_system/src/main/resources/ratings20.csv"));
        br1.readLine();
        int l;
        int m;
        int j;
        double r;
        int numMovies = 0;
        int[] rowsToNumMovies = new int[]{0, 0, 5, 81, 101, 172, 198, 205, 274, 300, 315, 333, 343, 358, 375, 377, 646, 651, 733, 784, 1120, 1170};
        numMovies = rowsToNumMovies[rows];
        for (i = 0; i < numMovies; i++) {
            String line = br1.readLine();
            String[] line2 = line.split(",");
            l = Integer.parseInt(line2[0]);

            m = hmap.get(Integer.parseInt(line2[1]));
            r = Double.parseDouble(line2[2]);
            a[l][m] = r;

        }

        br1.close();

        //matrix a Obtained from the given data
        /*log.info();
        log.info("**********************************************************");
        log.info("A Matrix");
        log.info("**********************************************************");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                log.info(a[i][j] + " ");
            }
            log.info();
        }
*/
        //let's make U matrix
        //transpose of A matrix
        double[][] at = new double[columns][rows];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                at[j][i] = a[i][j];
            }
        }

        /*
        // printing a transpose matrix
        for (i = 0; i < columns; i++) {
            for (j = 0; j < rows; j++) {
                log.info(at[i][j] + " ");
            }
            log.info();
        }*/

        //multiply a (rowsxcolumns) with atranspose (columnsxrows) to Matrix U
        double[][] u = new double[rows][rows];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                for (int k = 0; k < columns; k++) {
                    u[i][j] += a[i][k] * at[k][j];
                }
            }
        }

       /* for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                log.info(u[i][j] + " ");
            }
            log.info();
        }*/

        Matrix mat = new Matrix(u);
        EigenvalueDecomposition evd = new EigenvalueDecomposition(mat);
        Matrix sigma = evd.getD();
        double[][] sigmaa = sigma.getArray();

        Matrix m1 = evd.getV();
        double[][] b = m1.getArray();

//        log.info("**********************************************************");
//        log.info("**********************************************************");
//        log.info("Sigma matrix");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                if (sigmaa[i][j] < 0) {
                    sigmaa[i][j] = 0;

                }
                //log.info(sigmaa[i][j] + " ");
            }

//            log.info();
        }

        //multiply at (columnsxrows) with a (rowsxcolumns) to obtain U transpose matrix
        double[][] uT = new double[columns][columns];
        for (i = 0; i < columns; i++) {
            for (j = 0; j < columns; j++) {
                for (int k = 0; k < rows; k++) {
                    uT[i][j] += at[i][k] * a[k][j];
                }
            }
        }

        Matrix mNew = new Matrix(uT);
        EigenvalueDecomposition evd1 = new EigenvalueDecomposition(mNew);

        Matrix m11 = evd1.getV();
        double[][] b1 = m11.getArray();

        double[][] b1t = new double[columns][columns];
        for (i = 0; i < columns; i++) {
            for (j = 0; j < columns; j++) {
                b1t[j][i] = b1[i][j];
            }
        }

        double temp;
        for (i = 0; i < columns; i++) {
            for (j = 0; j < columns / 2; j++) {
                temp = b1[i][j];
                b1[i][j] = b1[i][columns - j - 1];
                b1[i][columns - j - 1] = temp;
            }
        }

        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows / 2; j++) {
                temp = b[i][j];
                b[i][j] = b[i][rows - j - 1];
                b[i][rows - j - 1] = temp;
            }
        }
        
        /*log.info("**********************************************************");
        log.info("**********************************************************");
        log.info("U matrix");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                log.info(b[i][j] + "    ");
            }
            log.info();
        }*/

        //for sigma matrix Arranging the terms in decreasing order
        for (i = 0; i < rows / 2; i++) {
            temp = sigmaa[i][i];
            sigmaa[i][i] = sigmaa[rows - i - 1][rows - i - 1];
            sigmaa[rows - i - 1][rows - i - 1] = temp;
        }

        double[][] sigmaFinal = new double[rows][columns];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                sigmaa[i][j] = Math.sqrt(sigmaa[i][j]);
                sigmaFinal[i][j] = sigmaa[i][j];
            }
        }

     /*   log.info();
        log.info("**********************************************************");
        log.info("Sigma Final matrix");
        log.info("**********************************************************");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                log.info(sigmaFinal[i][j] + "     ");
            }
            log.info();
        }
*/
        //for v transpose matrix arranging the terms in Decreasing order
        for (i = 0; i < columns / 2; i++) {
            for (j = 0; j < columns; j++) {
                temp = b1t[i][j];
                b1t[i][j] = b1t[columns - i - 1][j];
                b1t[columns - i - 1][j] = temp;
            }
        }

        /*log.info("V Transpose matrix");
        for (i = 0; i < columns; i++) {
            for (j = 0; j < columns; j++) {
                log.info(b1t[i][j] + "    ");
            }
            log.info();
        }*/

        double[][] temp1 = new double[rows][columns];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                for (int k = 0; k < columns; k++) {
                    temp1[i][j] += (a[i][k] * b1[k][j]);
                }
            }
        }
/*log.info();
        log.info("**********************************************************");
        log.info("Calculated A*V matrix");
        log.info("**********************************************************");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                log.info(temp1[i][j] + "    ");
            }
            log.info();
        }*/

        double[][] finalU = new double[rows][rows];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                if (sigmaFinal[j][j] != 0) {
                    finalU[i][j] = temp1[i][j] / sigmaFinal[j][j];
                }
            }
        }

        double[][] temp2 = new double[rows][columns];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                for (int k = 0; k < rows; k++) {
                    temp2[i][j] += sigmaa[i][k] * temp1[k][j];
                }
            }
        }

        /**Calulating U matrix for known V matrix so that we get unique eigen values**/
/*log.info();
        log.info("**********************************************************");
        log.info("Calculated U matrix");
        log.info("**********************************************************");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                log.info(finalU[i][j] + "    ");
            }
            log.info();
        }*/

        double error = 0;
        int k = 0;

        // Calculating U * Sigma Matrix
        double[][] usigcal = new double[rows][columns];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                for (k = 0; k < rows; k++) {
                    usigcal[i][j] += finalU[i][k] * sigmaFinal[k][j];
                }
            }
        }

        // Calcuating U * Sigma * V Transpose matrix
        double[][] amatcal = new double[rows][columns];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                for (k = 0; k < columns; k++) {
                    amatcal[i][j] += usigcal[i][k] * b1t[k][j];
                }
            }
        }

        //error calculation
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                error += Math.pow((a[i][j] - amatcal[i][j]), 2);
            }
        }

      /*  log.info();
        log.info("**********************************************************");
        log.info("Calculated finally the A matrix");
        log.info("**********************************************************");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                log.info(amatcal[i][j] + "    ");
            }
            log.info();
        }*/
        error = Math.sqrt(error);
       /* log.info();
        log.info("**********************************************************");
        log.info("Error is  " + error);
        log.info("**********************************************************");*/
        hmap.clear();
        return error;
    }

}
