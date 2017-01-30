// This class implements a binary heap data structure using the array-based representation of a complete binary tree
// You need to complete each method
// You may need to add a few more methods
// Author:
// Student ID:

public class ArrayHeap<E extends Comparable<E>>{
	E[] data; // The array representing the complete tree

	// Complete the constructor
 	public ArrayHeap(){

	}

	// Complete the add method
	// The method first check if v is already contained in the heap. If it is contained in the heap, do not add it.
	// Otherwise, add the element to the appropriate position
	public void add(E v){

	}

	// Complete the remove method
	// The method returns and removes the smallest element
	public E remove(){

	}

	// Complete the getMin method
	// The method returns the smallest element
	public E getMin(){

	}

	// Complete the toString method
	// The return string must represent the content of the data array
	public String toString(){

	}

	// Complete the size method
	// return the current number of elements in the heap
	public int size(){

	}

	// Complete the isEmpty method
	// return true if and only if the heap is empty

	public boolean isEmpty(){

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

			while (s.!isEmpty())
				System.out.print(heap.remove()+" ");

		}
		catch(Exception e){e.printStackTrace();}


	}


}