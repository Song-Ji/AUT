/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sort;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author apple
 */
public class Sort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Random random=new Random();
        Scanner sc=new Scanner(System.in);
        System.out.println("Input the number of n");
        int n=sc.nextInt();
        int[] a=new int[n];
        for(int i=0;i<n;i++)
        {
            a[i]=random.nextInt(n);
        }
//        for(int e:a)
//            System.out.println(e);
        
        long start1=System.nanoTime();
           mergeSort(a);
        System.out.println("mergeSort time "+(System.nanoTime()-start1)+"ns");
        
        for(int e:a)
            System.out.println(e);
        
        
        long start2=System.nanoTime();
           quickSort(a);
        System.out.println("quickSort time "+(System.nanoTime()-start2)+"ns");
        
//        for(int e:a)
//            System.out.println(e);
    }
    public static void mergeSort(int[] data)
    {
        int[] temp=new int[data.length];
        mergeSortRecursive(data,temp,0,data.length-1);
        
    }
    private static void mergeSortRecursive(int[] data,int[] temp,int left,int right)
    {
        int middle=(left+right)/2;
       if(left<right)
        {
            mergeSortRecursive(data,temp,left,middle);
            mergeSortRecursive(data,temp,middle+1,right);
            merge(data,temp,left,middle+1,right);
        } 
    }
    private static void merge(int[] data,int[] temp,int low,int middle,int high)
    {
        int leftEnd=middle-1;
        int tmpPos=low;
        int numElements=high-low+1;//size
        while(low<=leftEnd&&middle<=high)
            if(data[low]<=data[middle])
                temp[tmpPos++]=data[low++];
            else
                temp[tmpPos++]=data[middle++];
        while(low<=leftEnd)
            temp[tmpPos++]=data[low++];
        while(middle<=high)
            temp[tmpPos++]=data[middle++];
        for(int i=0;i<numElements;i++,high--)
        {
            data[high]=temp[high];
        }
    
    }
        
    
    public static void quickSort(int[] data)
    {
       quickSortRecursive(data,0,data.length-1); 
    }
    private static void quickSortRecursive(int[] data,int left,int right)
    {
        int pivot;
        if(left>=right)
            return ;
        else
        {
        pivot=partition(data,left,right);
        quickSortRecursive(data,left,pivot-1);
        quickSortRecursive(data,pivot+1,right);
        }
                
    }
    private static int partition(int data[],int left,int right)
    {
       while(true)
       {
           while(left<right&&data[left]<data[right])
               right--;
           if(left<right)
           {
               swap(data,left++,right);
               
           }
           
           while(left<right&&data[left]<=data[right])
               left++;
           if(left<right)
           {
               swap(data,left,right--);
               
           }
           else
               return right;
       }
       
    }
    private static void swap(int[] a,int i,int j)
        {
            int tmp;
            tmp=a[i];
            a[i]=a[j];
            a[j]=tmp;
        }
    }


