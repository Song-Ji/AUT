

// This class implements four algorithms for computing the Fibonacci number as taught in the class
// The first algorithm fib1 is a recursive algorithm directly applying the definition of the fibonacci numbers
// The second algorithm fib2 is an iterative algorithm using an array to store all fibonacci numbers
// The third algorithm uses a linear number of matrix multiplications
// The fourth algorithm uses a logarithmic number of matrix multiplications
// Authors:
// IDs:

public class Fibonacci{


	// Fill in your code for fib1
	public static long fib1(int n){
            if(n==0)
            return 0;
            if(n==1)
            return 1;
            return fib1(n-1)+fib1(n-2);

	}

	// Fill in your code for fib2
	public static long fib2(int n){
            if(n==0)
                return 0;
            int[] f=new int[n+1];
            int i=2;
            f[0]=0;
            f[1]=1;
            for(i=2;i<=n;i++) 
            {
                f[i]=f[i-1]+f[i-2];
            }
            return f[n];

	}

	public static int[][] matrix(int[][] a, int[][] b){
		int[][] c = new int[2][2];
		c[0][0]=a[0][0]*b[0][0]+a[0][1]*b[1][0];
		c[0][1]=a[0][0]*b[0][1]+a[0][1]*b[1][1];
		c[1][0]=a[1][0]*b[0][0]+a[1][1]*b[1][0];
		c[1][1]=a[1][0]*b[0][1]+a[1][1]*b[1][1];
		return c;
	}

	// Fill in your code for fib3
	public static long fib3(int n){
            if(n==0)
            return 0;
        if(n==1)
            return 1;
        int[][]A={{0,1},{1,1}};
        int[][]B={{1,0},{0,1}};
       
        for(int i=1;i<=n;i++)
        {
            B=matrix(B,A);
            
        }
        return B[0][1];

	}

	// Fill in your code for fib4
	public static long fib4(int n){
             if(n==0)
            return 0;
        if(n==1)
            return 1;
        int[][]A={{0,1},{1,1}};
        int[][]B={{1,0},{0,1}};
        
        while(n>0){
            if (n%2==1)
                B=matrix(B,A);
            A=matrix(A,A);
            n=n/2;
        }
        return B[0][1];

	}

	// This method performs an experiment on fib1 given three parameters num, gap and repeat
	// - num is the number of fibonacci numbers to compute
	// - gap is the gap between consecutive input values
	// - repeat is the number of times the computation is repeated to get a more accurate indication on the growth rate of the algorithm
	// The method then generates a plot based on the running time of the algorithm and the input values


	public static void experiment1(int num, int gap, int repeat){

			long[] x = new long[num];
			long[] y = new long[num];

			long start = 0;
			long end = 0;

			for (int i=1;i<num+1;i++){
				x[i-1]=gap*i;
				// create an Islanders istance with gap*i number of islanders
				start = System.currentTimeMillis();

				// repeat the experiment a number of times
				for(int j=0;j<repeat;j++){
					fib1(gap*i);
				}

				end = System.currentTimeMillis();
				y[i-1]=(end-start); //taking the average number of questions

			}
			// ploting a curve using the GraphingData class
			GraphingData gd = new GraphingData("Algorithm 1: Plot of running time v.s. n",x,y);
	}


	// This method performs an experiment on fib2 given three parameters num, gap and repeat
	// - num is the number of fibonacci numbers to compute
	// - gap is the gap between consecutive input values
	// - repeat is the number of times the computation is repeated to get a more accurate indication on the growth rate of the algorithm
	// The method then generates a plot based on the running time of the algorithm and the input values

	public static void experiment2(int num, int gap, int repeat){

			long[] x = new long[num];
			long[] y = new long[num];

			long start = 0;
			long end = 0;

			for (int i=1;i<num+1;i++){
				x[i-1]=gap*i;
				// create an Islanders istance with gap*i number of islanders
				start = System.currentTimeMillis();

				// repeat the experiment a number of times
				for(int j=0;j<repeat;j++){
					fib2(gap*i);
				}

				end = System.currentTimeMillis();
				y[i-1]=(end-start); //taking the average number of questions

			}
			// ploting a curve using the GraphingData class
			GraphingData gd = new GraphingData("Algorithm 2: Plot of running time v.s. n",x,y);
	}

	// This method performs an experiment on fib3 given three parameters num, gap and repeat
	// - num is the number of fibonacci numbers to compute
	// - gap is the gap between consecutive input values
	// - repeat is the number of times the computation is repeated to get a more accurate indication on the growth rate of the algorithm
	// The method then generates a plot based on the running time of the algorithm and the input values

	public static void experiment3(int num, int gap, int repeat){

			long[] x = new long[num];
			long[] y = new long[num];

			long start = 0;
			long end = 0;

			for (int i=1;i<num+1;i++){
				x[i-1]=gap*i;
				// create an Islanders istance with gap*i number of islanders
				start = System.currentTimeMillis();

				// repeat the experiment a number of times
				for(int j=0;j<repeat;j++){
					fib3(gap*i);
				}

				end = System.currentTimeMillis();
				y[i-1]=(end-start); //taking the average number of questions

			}
			// ploting a curve using the GraphingData class
			GraphingData gd = new GraphingData("Algorithm 3: Plot of running time v.s. n",x,y);
	}

	// This method performs an experiment on fib4 given three parameters num, gap and repeat
	// - num is the number of fibonacci numbers to compute
	// - gap is the gap between consecutive input values
	// - repeat is the number of times the computation is repeated to get a more accurate indication on the growth rate of the algorithm
	// The method then generates a plot based on the running time of the algorithm and the input values
	public static void experiment4(int num, int gap, int repeat){

			long[] x = new long[num];
			long[] y = new long[num];

			long start = 0;
			long end = 0;

			for (int i=1;i<num+1;i++){
				x[i-1]=gap*i;
				// create an Islanders istance with gap*i number of islanders
				start = System.currentTimeMillis();

				// repeat the experiment a number of times
				for(int j=0;j<repeat;j++){
					fib4(gap*i);
				}

				end = System.currentTimeMillis();
				y[i-1]=(end-start); //taking the average number of questions

			}
			// ploting a curve using the GraphingData class
			GraphingData gd = new GraphingData("Algorithm 4: Plot of running time v.s. n",x,y);
	}


	// The main method performs experiments on all four algorithms
	public static void main(String[] args){

		experiment1(40, 1, 10);
		experiment2(40, 10, 100000);
		experiment3(40, 10, 100000);
		experiment4(40, 10, 100000);

		/*
		for(int i=10;i<35;i++){
			before = System.nanoTime();
			for(int j=0;j<100;j++)
				fib1(i);//System.out.println(fib1(i));
			after = System.nanoTime();
			System.out.println("fib1("+i+")="+(after-before));
		}
		for(int i=50;i<100;i=i+5){
			before = System.nanoTime();
			for(int j=0;j<100;j++)
				fib2(i);//System.out.println(fib2(i));
			after = System.nanoTime();
			System.out.println("fib2("+i+")="+((after-before)/100));
		}
		for(int i=50;i<100;i=i+5){
			before = System.nanoTime();
			for(int j=0;j<100;j++)
				fib3(i);//System.out.println(fib3(i));
			after = System.nanoTime();
			System.out.println("fib3("+i+")="+((after-before)/100));
		}
		for(int i=50;i<100;i=i+5){
			before = System.nanoTime();
			for(int j=0;j<100;j++)
				fib4(i);//System.out.println(fib4(i));
			after = System.nanoTime();
			System.out.println("fib4("+i+")="+((after-before)/100));
		}*/

	}

}