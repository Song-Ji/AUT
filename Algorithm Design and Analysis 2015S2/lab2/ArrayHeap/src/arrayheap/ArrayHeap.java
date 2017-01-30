package arrayheap;
// This class implements a binary heap data structure using the array-based representation of a complete binary tree
// You need to complete each method

import java.io.BufferedReader;
import java.io.FileReader;

// You may need to add a few more methods
// Author:Wendy
// Student ID:14871088

public class ArrayHeap<E extends Comparable<E>>{
	E[] data; // The array representing the complete tree
        private static final int INITIAL_CAPACITY = 100;// 初始容量
        private int size; // 当前堆大小
	// Complete the constructor
 	public ArrayHeap(){
            size=0;
            data = (E[]) (new Comparable[INITIAL_CAPACITY]);

	}
        //compute location of parent given the location i
        protected static int parent(int i){return (i-1)/2;}
        //compute location of left child given the location i
        protected static int left(int i){return 2*i+1;}
        //... right child
        protected static int right(int i){return 2*i+2;}
        public E getFirst()
        {
            return data[0];
        }
        protected void percolateUp(int leaf)
        {
          int parent=parent(leaf);
          E value=data[leaf];
          while(leaf>0&&(value.compareTo(data[parent])<0))
          {
              data[leaf]=data[parent];
              leaf=parent;
              parent=parent(leaf);              
          }
          data[leaf]=value;
        }

	// Complete the add method
	// The method first check if v is already contained in the heap. If it is contained in the heap, do not add it.
	// Otherwise, add the element to the appropriate position
	public void add(E v){
            
            ensureCapacity();
            data[size]=v;
            percolateUp(size);
            size++;
	}
        public boolean ensureCapacity()
        {
            return size==data.length-1;//has free position
        }
        protected void pushDown(int root)
        {
            int left=left(root);
            int right=right(root);
            if(left(root)>=size)return;
            E value=data[root];
            int toSwap=root;
            if(right>=size) 
                toSwap=left;
            else if(data[left].compareTo(data[right])<0)
                toSwap=left;
            else
                toSwap=right;
            if(value.compareTo(data[toSwap])<=0)
                return;
            data[root]=data[toSwap];
            data[toSwap]=value;
            pushDown(toSwap);    
        }

	// Complete the remove method
	// The method returns and removes the smallest element
	public E remove(){
            E minVal=getFirst();
            data[0]=data[size-1];
            size--;
            if(size>1)
                pushDown(0);
            return minVal;

	}

	// Complete the getMin method
	// The method returns the smallest element
	public E getMin(){
            if(isEmpty())
                return null;
            return data[1];
            

	}

	// Complete the toString method
	// The return string must represent the content of the data array
	public String toString(){
            final StringBuffer stringbuffer=new StringBuffer();
            stringbuffer.append("[ ");
            for(int i=1;i<size+1;i++)
            {
                if(i!=1)
                    stringbuffer.append(",");
                stringbuffer.append(data[i]);
            }
            stringbuffer.append(" ]");
            
            return stringbuffer.toString();

	}

	// Complete the size method
	// return the current number of elements in the heap
	public int size(){
            return size;

	}

	// Complete the isEmpty method
	// return true if and only if the heap is empty

	public boolean isEmpty(){
            return size==0;
	}


	// The main method creates a heap containing Strings and add a list of names to the data structure
	// Then remove the names one by one, so they are printed in sorted order
	public static void main(String[] args){

		ArrayHeap<String> heap = new ArrayHeap<String>();

		try{
			BufferedReader fileRead = new BufferedReader(new FileReader("add.txt"));
			String s;
			while ((s=fileRead.readLine())!= null)
				heap.add(s);

			while (!heap.isEmpty())
                        {
                            //System.out.print(" a");
				System.out.println(heap.remove()+" ");
                        }

		}
		catch(Exception e){e.printStackTrace();}


	}


}