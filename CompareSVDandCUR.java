package IRRecommenderSystem;

import java.io.IOException;
import java.io.Serializable;

public class CompareSVDandCUR extends Object implements Serializable {
	public static void main(String[]args)
	{
		SVD svd =new SVD();
		CUR cur =new CUR();
		int i;
		for(i=2;i<=21;i++)
		{
			
				
				try {
					long startTime = System.currentTimeMillis();
					double SVDerror=svd.getSVDError(i, 1000);
					
					long endTime   = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					System.out.println("SVD time is "+totalTime);
					System.out.println("SVDError "+ i +"rows and " +"1000 columns "+SVDerror);
					
					long startTime1 = System.currentTimeMillis();
						double CURerror=cur.getCURError(i);
						long endTime1   = System.currentTimeMillis();
						long totalTime1 = endTime1 - startTime1;
						System.out.println("CUR time is "+totalTime1);
						System.out.println("CURError "+ i +"rows and " +i + "columns "+CURerror);
						
					
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	

}
