package algorithms;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;

@Slf4j
public class CompareSVDAndCUR implements Serializable {
    public static void main(String[] args) {
        SVD svd = new SVD();
        CUR cur = new CUR();
        int i;
        for (i = 2; i <= 21; i++) {


            try {
                long startTime = System.currentTimeMillis();
                double svdError = svd.getSVDError(i, 1000);

                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                log.info("SVD time is " + totalTime);
                log.info("SVDError " + i + "rows and " + "1000 columns " + svdError);

                long startTime1 = System.currentTimeMillis();
                double curError = cur.getCURError(i);
                long endTime1 = System.currentTimeMillis();
                long totalTime1 = endTime1 - startTime1;
                log.info("CUR time is " + totalTime1);
                log.info("CURError " + i + "rows and " + i + "columns " + curError);


            } catch (IOException e) {
                log.error(e.getMessage());
            }

        }
    }


}
