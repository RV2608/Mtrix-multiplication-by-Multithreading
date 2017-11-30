import java.util.concurrent.*;
import java.util.*;
class Mat{
	public static void printMatrix(int mat[][],int row,int col)
    {
        int i,j;
        for(i=0;i<row;i++)
        {
            for(j=0;j<col;j++)
                System.out.print(mat[i][j]+"\t");
            System.out.println();
        }
    }
    public static void main(String args[]){
        int i,j,rA,cA,rB,cB,k;
        Scanner Input = new Scanner(System.in);
        Random obj = new Random();
        System.out.print("Row for Matrix A: ");
        rA=Input.nextInt();
        System.out.print("Column for Matrix A: ");
        cA=Input.nextInt();
        System.out.print("Row for Matrix B: ");
        rB=Input.nextInt();
        System.out.print("Column for Matrix B: ");
        cB=Input.nextInt();
        //Condition Neccesary For Matrix Multiplication.
        if(cA!=rB)
        {
               System.out.println("Product Of Matrices is Not Possible");
               System.exit(-1);
        }
        int A[][] = new int[rA][cA] ;		//For Storing First Matrix.
        int B[][] = new int[rB][cB] ;		//For Storing Second Matrix.
        int C[][] = new int[rA][cB] ;
        int D[][] = new int[rA][cB] ;
        //Initialising Matrix A
        for(i=0;i<rA;i++)
        	for(j=0;j<cA;j++)
        		A[i][j]=obj.nextInt(8);
        //Initialising Matrix B
        for(i=0;i<rB;i++)
           for(j=0;j<cB;j++)
            	B[i][j]=obj.nextInt(8);
        double begin = System.currentTimeMillis();
    // Calculating Execution Time for matrix multiplication without multithreading.   
        for(i=0;i<rA;i++)
          	for(j=0;j<cB;j++)
             	for(k=0;k<rB;k++)
                  	C[i][j]+=A[i][k]*B[k][j];
        double end = System.currentTimeMillis();
        double duration = (end - begin)/1000 ;
        System.out.println(" \nExecution time of Simple Matrix Multiplication is : "+ duration +" seconds");
    // Calculating Execution Time for matrix multiplication with multithreading.
        begin = System.currentTimeMillis();
        try{
        ExecutorService Exec = Executors.newFixedThreadPool(10);
        begin = System.currentTimeMillis();
        for(i=0;i<rA;i++)
            for(j=0;j<cB;j++)
            {
                Product P = new Product(A,B,D,i,j,cA);
                Exec.execute(P);
            }
        Exec.shutdown();
         while (!Exec.isTerminated()) {
        }
        Exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        end = System.currentTimeMillis();
        duration = (end - begin)/1000;
        System.out.println(" \nExecution time of matrix Multiplication using Multithreadings : "+ duration +" seconds");
        }catch(Exception e){}
        /*System.out.println("Matrix A");
        	printMatrix(A,rA,cA);
        System.out.println("Matrix B");
        	printMatrix(B,rB,cB);
        System.out.println("Multiplied Matrix By Simple Method");
        	printMatrix(C,rA,cB);
        System.out.println("\nMatrix Multiplied By Mulithreadings:\n");
        	printMatrix(D,rA,cB);*/
	}
}
class Product implements Runnable {
      private int matA[][] , matB[][] , matD[][] ;
      private int row , col , len ,i ,j;
      public Product(int A[][] ,int B[][] ,int D[][],int row, int col,int len)
      {
         matA=A;
         matB=B;
         matD=D;
         this.row=row;
         this.col=col;
         this.len=len;
      }

     public void run()
     {
         for( i=0;i<len;i++)
               matD[row][col] += matA[row][i]*matB[i][col];
     }
}