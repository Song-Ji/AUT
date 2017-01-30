/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fibonacci;

import java.util.Scanner;

/**
 *
 * @author apple
 */
public class Fibonacci {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Input the value of n:");
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        
        System.out.println("The First Try ");
        Long start1=System.nanoTime();
        for(int i=0;i<n;i++){
             
            System.out.print(fib1(i)+" ");   
        }
        System.out.println("First running times："+(System.nanoTime()-start1)+"ns");
        
        
        System.out.println("The Second Try "); 
        Long start2=System.nanoTime();
        for(int i=0;i<n;i++){
            System.out.print(fib2(i)+" ");   
        }
        System.out.println("Second running times："+(System.nanoTime()-start2)+"ns");
        
        
        System.out.println("The Third Try "); 
        Long start3=System.nanoTime();
        for(int i=0;i<n;i++){
            System.out.print(fib3(i)+" ");   
        }
        System.out.println("Third running times："+(System.nanoTime()-start3)+"ns");
        
        
        
        System.out.println("The Fourth Try"); 
        Long start4=System.nanoTime();
        for(int i=0;i<n;i++){
            System.out.print(fib4(i)+" ");   
        }
        System.out.println("Fourth running times："+(System.nanoTime()-start4)+"ns");
        
        
        
    }
        public static int fib1(int n)
        {
            if(n==0) 
                return 0;
            else if(n==1) 
                return 1;
            else  
                return fib1(n-1)+fib1(n-2);
        }
        public static int fib2(int n)
        {
            if(n==0) return 0;
            int[] f=new int[n+1];
            f[0]=0;
            f[1]=1;
            for(int i=2;i<=n;i++)
            {
                f[i]=f[i-1]+f[i-2];
                
            }
            return f[n];
        }
       public static int fib3(int n)
       {
           int[][] A={{0,1},{1,1}};
           int[][] B={{1,0},{0,1}};
           if(n==0) 
                return 0;
            else if(n==1) 
                return 1;
            else 
            {
                for(int i=1;i<=n;i++)
                    B=multiply2by2(B,A);
            }
           return B[0][1];   
       }
       public static int[][] multiply2by2(int[][] A,int[][] B)
       {
           int[][] C=new int[2][2];
           C[0][0]=A[0][0]*B[0][0]+A[0][1]*B[1][0];
           C[0][1]=A[0][0]*B[0][1]+A[0][1]*B[1][1];
           C[1][0]=A[1][0]*B[0][0]+A[1][1]*B[1][0];
           C[1][1]=A[1][0]*B[0][1]+A[1][1]*B[1][1];
           
           return C;
       }
       public static int fib4(int n)
       {
           int[][] A={{0,1},{1,1}};
           int[][] B={{1,0},{0,1}};
           if(n==0) 
                return 0;
            else if(n==1) 
                return 1;
            else 
            {
               while(n>0)
               {
                   if(n%2==1)
                       B=multiply2by2(B,A);
                   A=multiply2by2(A,A);
                   n=n/2;
               }
            
            }
           return B[0][1];
       }
    
}
