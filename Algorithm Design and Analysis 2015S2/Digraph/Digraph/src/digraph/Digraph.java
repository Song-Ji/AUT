package digraph;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;



/**
 * This class implements the directed graph ADT and a simple GUI for visualising a directed graph
 * The data structure used for implementing a digraph is the adjacency list.
 * The nodes are labeled by Integers
 *
 * The GUI should allow the user to add/remove nodes/edges, as well as performing simple updates and queries on the graph
 * Control to the data structure is done by both the mouse and through a textfield at the bottom of the window
 *
 *
 * @Author: Jiamou Liu
 * @Student Name: Song
 */
public class Digraph extends JPanel implements MouseMotionListener, MouseListener, ActionListener {

	private int barb; //size of an arrow edge
	private double phi; //angle of an arrow edge
	private Integer moveNode; //the node the user is moving on the GUI
	private Integer selectedNode; // the node selected

    public final static int CIRCLEDIAMETER = 40; //Diameter of the nodes


	// This is the adjacency list representation of the digraph
	// The nodes are denoted here as Integers
	// Each node is associated with a list of Integers, which indicates its out-neighbours
	private HashMap<Integer, List<Integer>> data;

	//
	private HashMap<Integer, Node> nodeList;

	// The collection of node in the graph
	// This set is the key set of data
	private Set<Integer> nodeSet;


	// The textfield used for user to specify commands
	private JTextField tf;


	// The Constructor
	public Digraph(){


		data = new HashMap<Integer, List<Integer>>();
		nodeList = new HashMap<Integer, Node>();
		nodeSet=data.keySet();

        JPanel panel = new JPanel();
        barb = 20;                   // barb length
        phi = Math.PI / 12;             // 30 degrees barb angle
        setBackground(Color.white);
        addMouseMotionListener(this);
        addMouseListener(this);
        tf = new JTextField();
        tf.addActionListener(this);
        setLayout(new BorderLayout());
		add(panel, BorderLayout.NORTH);
		add(tf, BorderLayout.SOUTH);
        moveNode = -1; 				// Initial values of moveNode is -1
		selectedNode=-1;			// Initial values of moveNode is -1

	}



	/**
	  * The method loads a digraph stored in the file fileName in adjacency list representation
	  * The top line of the file contains the number n of nodes in the graph
	  * The nodes in the graph are then given the indices 0,...,n-1
	  *
	  */
	public void load(String fileName){
		try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int numNodes = Integer.parseInt(br.readLine());
            int pos = 0;
            String output;
            for(int i=0;i<numNodes;i++){
			   add(i);
		   }

		    for(int i=0; i<numNodes;i++){
               output = br.readLine();
               if(output!=null){
                   StringTokenizer token = new StringTokenizer(output);
                   while (token.hasMoreTokens()) {
                       addEdge(i,Integer.parseInt(token.nextToken()));
                   }
               }
            }
            br.close();

        }  catch(FileNotFoundException e){
           	System.out.println("File can't be found.");
        }catch(Exception e){
           e.printStackTrace();
        }

	}

	/**
	  * The method adds a node to the digraph, labeled by the int value node
	  *
	  */
	public void add(int node){
		// If the label node is already in the digraph, do nothing and return
		if(data.containsKey((Integer)node)) return;
		// Create a new linked list
		List<Integer> list = new LinkedList<Integer>();
		// Add a new entry to the adjacency list
		data.put((Integer)node, list);

		// Create a new node in the GUI
		// Set a random initial position
		// Link the new node with the corresponding node in the GUI
		Node nodeVisual = new Node(node);
        nodeVisual.xpos = 50 +(int) (Math.random() * 320);
        nodeVisual.ypos = 50 +(int) (Math.random() * 320);
		nodeList.put((Integer)node, nodeVisual);

	}


	/**
	  * The method adds an edge to the digraph
	  * The source of the edge is labeled node1
	  * The target of the edge is labeled node2
	  *
	  */
	public void addEdge( int node1, int node2){
		if (node1==node2) return;
		if (!data.containsKey((Integer)node1) || !data.containsKey((Integer)node2))
			return;
		List<Integer> list = data.get((Integer)node1);
		if(!list.contains((Integer)node2))
			list.add((Integer)node2);
	}

	/**
	  * The method removes an edge from the digraph
	  * The source of the edge is labeled node1
	  * The target of the edge is labeled node2
	  *
	  */
	public void removeEdge(int node1, int node2){
		if (!data.containsKey((Integer)node1) || !data.containsKey((Integer)node2))
			return;
		List<Integer> list = data.get((Integer)node1);
		if(list.contains((Integer)node2))
			list.remove((Integer)node2);
	}


	/**
	  * The method removes a node from the digraph
	  * You need to complete this method
	  * It should do nothing if the node is not contained in the digraph
	  */
	public void remove(int node){
            if(!data.containsKey((Integer)node)) return;
            List<Integer> list;
            for(Integer i:nodeSet)
            {
                list=data.get((Integer)i);
                if(list.contains((Integer)node))
                    list.remove((Integer)node);
            }
            data.remove((Integer)node);
            nodeList.remove((Integer)node);
	}




	/**
	  * This method computes and returns the indegree of a given node in the digraph
	  *
	  * You need to complete this method
	  *
	  */
	public int indegree(int node){
            if (!data.containsKey((Integer)node)) return -1;
            int counter=0;
            List<Integer> list;
            for (Integer i: nodeSet){
			list = data.get(i);
			if(list.contains(node))
                                counter++;	
            }	
           return counter;
	}
            

	/**
	  * This method computes and returns the outdegree of the given node in the digraph
	  *
	  */
	public int outdegree(int node){
		if (!data.containsKey((Integer)node)) return -1;
		return data.get((Integer)node).size();
	}


	/**
	  * This method computes and returns the size (number of edges) of the graph
	  *
	  */
	public int graphSize(){
		List<Integer> list;
		int s=0;
		for (Integer j: nodeSet){
			list = data.get((Integer)j);
			s=s+list.size();
		}
		return s;
	}

	/**
	  * This method computes and returns the order (number of nodes) of the graph
	  *
	  */
	public int graphOrder(){
		return data.size();
	}


	/**
	  * This method returns true if and only if the given node i
	  * is a universal source in the graph
	  *
	  */
	public boolean isUniversalSource(int node){
		if (!data.containsKey((Integer)node)) return false;
		return (indegree(node)==0 && outdegree(node)==(graphOrder()-1));
	}

	/**
	  * This method returns true if and only if the given node i
	  * is a universal sink in the graph
	  *
	  */
	public boolean isUniversalSink(int node){
		if (!data.containsKey((Integer)node)) return false;
		return (outdegree(node)==0 && indegree(node)==(graphOrder()-1));
	}


	/**
	  * This method prints out the adjacency list of the graph
	  *
	  */
	public void printList(){
		int n=graphOrder();
		List<Integer> list;
                System.out.println();
                System.out.println("adjacent list");
		for (int i=0;i<n;i++){
				System.out.print("---");
		}
		System.out.print('\n');

		for (Integer i: nodeSet){
			list = data.get(i);
			System.out.print(""+i+": ");
			for (Integer j: list){
				System.out.print(" "+j);
			}
			System.out.print('\n');
		}
		for (int i=0;i<n;i++){
				System.out.print("---");
		}
		System.out.print('\n');

	}

	/**
	  * This method prints out the adjacency matrix of the graph
	  * You need to complete this method
	  *
	  * The method computes the following data structure:
	  * 1. a HashMap labels which associates each number between 0 and n-1 a unique node label
	  * 2. a boolean nxn matrix adjMatrix storing the adjacency matrix where:
	  *		 the ith row/column  corresponds to the node with label labels.get(i)
	  *
	  * The method then prints out the adjacency matrix
	  * To the left and on top of the matrix, the method also prints out
	  * the node label which corresponds to each row and column
	  */
	public void printMatrix(){
		// n is the order of the graph
		int n=graphOrder();

		// the HashMap associates an index in [0..n-1] with a node label
		HashMap<Integer,Integer> labels = new HashMap<Integer,Integer>();

		// the adjacency matrix of the digraph, where the node indices in
		// the matrix are indicated by the labels HashMap
		boolean[][] adjMatrix = new boolean[n][n];
                System.out.println("adjacent matrix");

		List<Integer> list;
                for (int i=0;i<n;i++){
				System.out.print("---");
		}
		System.out.println();
                
                int index=0;
                System.out.print(" ");

		for (Integer i: nodeSet){
			labels.put((Integer) index, (Integer)i);
			index++;
                        System.out.print(" "+labels.get((Integer)i));
                        
                        
		}
                System.out.println();
		for (int i=0;i<n;i++){
                    labels.put((Integer) index, (Integer)i);
                            System.out.print(labels.get((Integer)i));
			list = data.get(labels.get((Integer)i));
			for (int j=0; j<n; j++){
				adjMatrix[i][j]=(list.contains(labels.get((Integer)j)));
                                 if(adjMatrix[i][j]==true)
                                    System.out.print(" "+1);
                                 
                                else
                                    System.out.print(" "+0);
			}
                        System.out.println();
		}
                
                
                for (int i=0;i<n;i++){
				System.out.print("---");
		}
		System.out.println();

	}


	/**
	  * Clear the digraph
	  *
	  */
	public void clear(){
		data.clear();
		nodeList.clear();
	}


	/**
	  * Compute the transpose of the current digraph
	  * which is defined as the graph with the same nodes
	  * while the direction of all edges are reversed
	  */
	public void transpose(){
            
            int n=graphOrder();
            HashMap<Integer,Integer> labels = new HashMap<Integer,Integer>();
            boolean[][] adjMatrix = new boolean[n][n];
            List<Integer> list;
            int index=0;
            int[][] originalMatrix=new int[n][n];
            
            int[][] reversedMatrix=new int[n][n];
            
            for (Integer i: nodeSet){
                labels.put((Integer) index, (Integer)i);
                index++;
            }
            for (int i=0;i<n;i++){
                list = data.get(labels.get((Integer)i));
                for (int j=0; j<n; j++){
                    adjMatrix[i][j]=(list.contains(labels.get((Integer)j)));
                    if(adjMatrix[i][j]==true)
                        originalMatrix[i][j]=1;
                    else
                        originalMatrix[i][j]=0;
                    removeEdge(i,j);
                }
            }
            for (int i=0;i<n;i++){
                for(int j=0;j<n;j++)
                {
                   reversedMatrix[i][j]= originalMatrix[j][i];
                   if(reversedMatrix[i][j]==1)
                   {
                       addEdge(i,j);
                      
                   }
                   
                }     
            }

	}

	/**
	  * Perform DFS traversal on the current digraph
	  * starting from the smallest node
	  * Print out a string representation of the DFS forest
	  */

        
	public void dfs(){
             //递归
            HashMap<Integer, Boolean> visited=new HashMap<Integer, Boolean>();
		for (Integer i: nodeSet){

                    visit(data,visited,i);
		}
                System.out.println();
	}
        private  void visit(HashMap<Integer , List<Integer>> graph,HashMap<Integer, Boolean> visited,int node)
        { 
           if(!visited.containsKey(node))
           {
               visited.put(node,true);
               System.out.print("("+node);
               for (Integer i: data.get(node)){
                   
                      if(!visited.containsKey(i))
                      {
                          visit(graph,visited,i);//递归访问其邻近节点
                      }

                   }
                   System.out.print(node+")");
            } 
        }

	/**
	  * Decide if the current digraph contains a directed cycle
	  * If there is a cycle, print a message saying that the graph is not linearisable
	  * If there is no cycle, print out a linearisation
	  * You need to use the DFS algorithm to compute the linearisation
	  */
	boolean[] visited;
        // 环路检测
	boolean[] onStack;
        Stack<Integer> S;
        Stack<Integer> cycle;
        int[] edge;
        
        public void linearise(){
            int n=graphOrder();
            this.S=new Stack<Integer>();
            this.visited=new boolean[n];
            this.onStack = new boolean[n];
            this.edge = new int[n];
            
            if(cyclic())
            {
                System.out.println("the graph is not linearisable, it contains  cycle");
                return;
            }
        
            Stack<Integer> stack=new Stack();
            stack.clear();
		for(int i=0;i<n;i++){
                    if(!visited[i])
                {
                   
                    dfsLinearise(data,i);
                }
                }
            while(!S.empty())
                System.out.print(S.pop());
            System.out.println();
	}
        
        private void dfsLinearise(HashMap<Integer , List<Integer>> graph,int node)
        {
            visited[node]=true;
            //顶点记录到调用栈中
            onStack[node] = true;
            
            for(Integer i:graph.get(node))
            {
                if(cyclic())
                {
                    return;
                }
                if(!visited[i])
                {
                    edge[i]=node;
                    dfsLinearise(graph,i);
                }
                else if(onStack[i])
                {
                     //存在环路
                    cycle = new Stack<Integer>();
                    cycle.push(i);
                    for(int start = node; start != i; start = edge[start])
                    {
                        cycle.push(node);
                    }
                    cycle.push(i);
                }
            }
            S.push(node);
            onStack[node]=false;
        }

        public boolean cyclic()
        {
            return (null != cycle);
        }
            
  


	public boolean isEmpty(){
		return nodeSet.isEmpty();
	}



	/**
	  * Converts the current digraph to its underlying graph
	  * which is defined as the undirected graph obtained by "removing the direction" from all directed edges
	  */
	public void underlying(){

		int n=graphOrder();
		boolean[][] adjMatrix = new boolean[n][n];
		List<Integer> list;
		int row=0;
		int column=0;
		HashMap<Integer,Integer> labels = new HashMap<Integer,Integer>();
		int index=0;

		for (Integer i: nodeSet){
			labels.put((Integer) index, (Integer)i);
			index++;
		}
		for (int i=0;i<n;i++){
			list = data.get(labels.get((Integer)i));
			for (int j=0; j<n; j++){
				adjMatrix[i][j]=(list.contains(labels.get((Integer)j)));
                               
			}
		}

		for (int i=0; i<n;i++){
			list = data.get(labels.get(i));
			list.clear();
			for(int j=0; j<n; j++){
				if(adjMatrix[j][i] || adjMatrix[i][j])
					addEdge(i,j);
			}
		}
	}

	public void contract(int node1, int node2){
		if (!data.containsKey(node1) || !data.containsKey(node2) || node1==node2) return;

		int small,big;
		if(node1<node2) {
			small = node1;
			big=node2;
		}
		else{
			small=node2;
			big=node1;
		}

		List<Integer> list1 = data.get((Integer)small);
		List<Integer> list2 = data.get((Integer)big);

		for (Integer i: list2){
			if (i!=small && !list1.contains((Integer)i)){
				list1.add((Integer)i);
			}
		}
		List<Integer> list;

		if (list1.contains(big))
			list1.remove((Integer)big);

		for (Integer i: nodeSet){
			if(i!=big && i!=small){
				list = data.get((Integer)i);
				if (list.contains(big)){
					list.remove((Integer)big);
					if(!list.contains(small))
						list.add((Integer)small);
				}
			}
		}


		data.remove((Integer)big);
		nodeList.remove((Integer)big);
	}

	/**
	  * This method specifies how the digraph may be controled by the user
	  * by inputing commands in the TextField
	  *
	  */
	public void actionPerformed(ActionEvent evt) {
	    String command = tf.getText();

		StringTokenizer st = new StringTokenizer(command);

		String token,opt;
		Integer node1, node2;
		if(st.hasMoreTokens()) {
			token = st.nextToken();
			token=token.toLowerCase();
			switch(token){
				// if the input command is "load file"
				case "load":
					try{
						clear();
						opt=st.nextToken();
						load(opt);
					}catch(Exception e){
						System.out.println("Invalid command");
					}
					break;
				// if the input command is "add node a" or "add edge a b"
				case "add":
							try{
								opt=st.nextToken();
								opt=opt.toLowerCase();
								if(opt.equals("edge")){
									node1 = Integer.parseInt(st.nextToken());
									node2 = Integer.parseInt(st.nextToken());
									if(st.hasMoreTokens()) break;
									addEdge(node1,node2);
								}
								else if(opt.equals("node")){
									node1 = Integer.parseInt(st.nextToken());
									if(st.hasMoreTokens()) break;
									add(node1);
								}
							}catch(Exception e){
								System.out.println("Invalid command");
							}
							break;
				// if the input command is "remove node a" or "remove edge a b"
				case "remove":
							try{
								opt=st.nextToken();
								opt=opt.toLowerCase();
								if(opt.equals("edge")){
									node1 = Integer.parseInt(st.nextToken());
									node2 = Integer.parseInt(st.nextToken());
									if(st.hasMoreTokens()) break;
									removeEdge(node1,node2);
								}
								else if(opt.equals("node")){
									node1 = Integer.parseInt(st.nextToken());
									if(st.hasMoreTokens()) break;
									remove(node1);
								}
							}catch(Exception e){System.out.println("Invalid command");}
							break;
				// if the input command is "print degres", "print size", "print order", "print list" or "print matrix"
				case "print":
							try{
								opt=st.nextToken();
								opt=opt.toLowerCase();
								if(st.hasMoreTokens()){
									System.out.println("Invalid command");
									break;
								}
								switch(opt){
									case "degrees":
										// print the indegree and outdegree of the nodes
										if (opt.equals("degrees")){
											for (Integer i: nodeSet)
												System.out.println("Node "+i+" indegree: "+ indegree(i) + " outdegree: "+outdegree(i));
										}
										break;
									case "size":
										// print the size of the graph
										System.out.println("Size of the digraph: "+graphSize());
										break;
									case "order":
										// print the order of the graph
										System.out.println("Order of the digraph: "+graphOrder());
										break;
									case "matrix":
										// print the adjacency matrix of the graph
										printMatrix();
										break;
									case "list":
										// print the adjacency list of the graph
										printList();
										break;
									default:
										System.out.println("Invalid command");
										break;
								}
							}catch(Exception e){System.out.println("Invalid command");}
							break;
				// If the input command is "transpose"
				case "transpose":
							if (!st.hasMoreTokens())
								transpose(); // compute the tranpose graph
							else
								System.out.println("Invalid command");
							break;
				// If the input command is "underlying"
				case "underlying":
							try{
								if(st.nextToken().toLowerCase().equals("graph") && !st.hasMoreTokens())
									underlying(); // compute the underlying graph
							} catch(Exception e){System.out.println("Invalid command");}
							break;
				// If the input command is "find universal source" or "find universal sink"
				case "find":
							try{
								if (!st.nextToken().equals("universal")){
									System.out.println("Invalid command");
									break;
								}
								opt=st.nextToken();
								opt=opt.toLowerCase();
								boolean found=false;
								if(st.hasMoreTokens()) break;
								switch(opt){
									case "source":
										for (Integer i: nodeSet){
											if (isUniversalSource(i)){
												selectedNode = i;
												System.out.println("Universal Source found: "+i);
												found=true;
												break;
											}
										}
										if (!found)
											System.out.println("There is no universal source in the graph");
										break;
									case "sink":
										for (Integer i: nodeSet){
											if (isUniversalSink(i)){
												selectedNode = i;
												System.out.println("Universal Sink found: "+i);
												found=true;
												break;
											}
										}
										if (!found)
											System.out.println("There is no universal sink in the graph");
										break;
									default:
										System.out.println("Invalid command");
										break;
								}
							} catch(Exception e){System.out.println("Invalid command");}
							break;
				//If the input command is "contract a b"
				case "contract":
							try{
									node1 = Integer.parseInt(st.nextToken());
									node2 = Integer.parseInt(st.nextToken());
									if(st.hasMoreTokens()) break;
									contract(node1,node2); // contract the edge from a to b
							} catch (Exception e){System.out.println("Invalid command");}
							break;
				//If the input command is "clear"
				case "clear":
							if (!st.hasMoreTokens())
								clear();
							else
								System.out.println("Invalid command");
							break;
				//If the input command is "dfs"
				case "dfs":
							if (!st.hasMoreTokens())
								dfs();
							else
								System.out.println("Invalid command");
							break;
				case "linearise":
							if (!st.hasMoreTokens())
								linearise();
							else
								System.out.println("Invalid command");
							break;
				//If the input command is none of the above
				default:
						System.out.println("Invalid command");
						break;
			}

     	}
     	// repaint the graph to show the change
     	repaint();

	}



	/**
	  * Paint the digraph to the panel
	  *
	  */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //clear the previous screen
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 1500, 1500);
        g2d.setColor(Color.BLACK);

		List<Integer> outNeighbours;

		for (Integer i : nodeSet){
			if(selectedNode==i)
				nodeList.get(i).draw(g, Color.RED);
			else
				nodeList.get(i).draw(g, Color.BLACK);
			outNeighbours = data.get(i);
			for(int j=0; j<outNeighbours.size(); j++){
				drawEdge(i,outNeighbours.get(j),g, Color.BLACK, Color.BLUE);
			}
		}

    }


   /**
    * Draw a directed edge between 2 nodes with the specific color for the line and the arrow.
    */
    public void drawEdge(Integer node1, Integer node2, Graphics g, Color colorLine, Color colorArrow) {

        Graphics2D g2 = (Graphics2D) g;

        int startX = nodeList.get(node1).getEdgeX(nodeList.get(node2));
        int startY = nodeList.get(node1).getEdgeY(nodeList.get(node2));

        int destX = nodeList.get(node2).getEdgeX(nodeList.get(node1));
        int destY = nodeList.get(node2).getEdgeY(nodeList.get(node1));

        g2.setStroke(new BasicStroke(2));
        g2.setColor(colorLine);

        g2.drawLine(startX, startY, destX, destY);

        double theta, x, y;
        g2.setPaint(colorArrow);
        theta = Math.atan2(destY - startY, destX - startX);
        drawArrow(g2, theta, destX, destY);

    }

    //draws the arrows on the edges
    private void drawArrow(Graphics2D g2, double theta, double x0, double y0) {
        g2.setStroke(new BasicStroke(3));
        double x = x0 - barb * Math.cos(theta + phi);
        double y = y0 - barb * Math.sin(theta + phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
        x = x0 - barb * Math.cos(theta - phi);
        y = y0 - barb * Math.sin(theta - phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
    }




    // Mouse Actions:
    // Moving a node: The user may drag and drop a node to any position
    // Add a node: The user may add a node by clicking a white area of the frame
    //				The newly added node will be automatically selected
    // Selecting a node: The user may select a node by click on a node
    // Add an edge: Once a node is selected, the user may add an outgoing edge
    //				to the selected node by clicking another node
    @Override
    public void mouseDragged(MouseEvent e) {
        if (moveNode >= 0) {
            Node node = nodeList.get(moveNode);
            node.xpos = e.getPoint().x;
            node.ypos = e.getPoint().y;
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
		Node node;
		boolean onNode=false;
        Integer clicked=-1;
        for (Integer i: nodeSet) {
			node=nodeList.get(i);
            //Calculate the distance to the center of a node
            double distance=Math.sqrt(Math.pow((e.getX()-node.xpos), 2)+Math.pow((e.getY()-node.ypos), 2));
            if (distance<=(1.0*CIRCLEDIAMETER/2)) {
                onNode=true;
                clicked = i;
            }
        }
        if (onNode){
			if (selectedNode==-1)
				selectedNode=clicked;
			else{
				if (clicked.equals(selectedNode)){
					selectedNode=-1;
				}
				else{
					addEdge(selectedNode,clicked);
					selectedNode=-1;
				}
			}
		}
        if (!onNode) {
        	int newNode =0;
        	while(nodeSet.contains((Integer)newNode))
        		newNode++;
        	add((Integer)newNode);
        	selectedNode=newNode;
		}
      	repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
		Node node;
        for (Integer i: nodeSet) {
            node=nodeList.get(i);
            //Calculate the distance to the center of a node
            double distance=Math.sqrt(Math.pow((e.getX()-node.xpos), 2)+Math.pow((e.getY()-node.ypos), 2));
            if (distance<=(1.0*CIRCLEDIAMETER/2)) {
                moveNode = i;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        moveNode = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }



	// An inner class storing information regarding the visualisation of a node
    private class Node {

        public int xpos;
        public int ypos;
        public int nodeNum;
        public int inEdges, arraySpot;

		int dirX, dirY;

        public Node(int num) {
            xpos = 0;
            ypos = 0;
            nodeNum = num;
            inEdges = 0;
            arraySpot = 0;

			double rand = Math.random();

			if(rand<0.25){
				dirX=1;
				dirY=1;
			} else if(rand<0.5){
				dirX=-1;
				dirY=1;
			} else if(rand<0.75){
				dirX=1;
				dirY=-1;
			} else{
				dirX=-1;
				dirY=-1;
			}

        }

		// returns the label of the node
        public int label(){
			return nodeNum;
		}

		// compute the x coordinate of the source of the edge from this to the specified node
        public int getEdgeX(Node node) {
            double direction = 1.0;
            if (node.xpos < xpos) {
                direction = -1.0;
            }
            double x2subx1sqr = Math.pow((node.xpos - xpos), 2);
            double y2suby1sqr = Math.pow((node.ypos - ypos), 2);
            double rsqr = Math.pow(CIRCLEDIAMETER*1.0/2, 2);
            double x = Math.sqrt((x2subx1sqr * rsqr / (x2subx1sqr + y2suby1sqr))) * direction + xpos;
            return (int) Math.round(x);
        }

		// compute the y coordinate of the source of the edge from this to the specified node
        public int getEdgeY(Node node) {
            double direction = 1.0;
            if (node.ypos < ypos) {
                direction = -1.0;
            }
            double x2subx1sqr = Math.pow((node.xpos - xpos), 2);
            double y2suby1sqr = Math.pow((node.ypos - ypos), 2);
            double rsqr = Math.pow(CIRCLEDIAMETER*1.0/2, 2);//Square root of radius
            double y = Math.sqrt((y2suby1sqr * rsqr / (x2subx1sqr + y2suby1sqr))) * direction + ypos;
            return (int) Math.round(y);
        }

		// draw the node
        public void draw(Graphics g, Color color) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(color);
            g2d.drawOval(xpos-(CIRCLEDIAMETER / 2), ypos-(CIRCLEDIAMETER / 2), CIRCLEDIAMETER, CIRCLEDIAMETER);
            g2d.setColor(Color.BLUE);
            g2d.drawString("" + nodeNum, xpos-3, ypos+4 );
            g2d.setColor(Color.BLACK);
        }

        public void move(){

			xpos = xpos -2*dirX;
			ypos = ypos -2*dirY;

			if (xpos<50 || xpos>360) dirX=(-1)*dirX;
			if (ypos<50 || ypos>360) dirY=(-1)*dirY;


		}
    }



	// An animated demo program that performs several graph updates and queries for testing the data structure
	public void demo(){

		for (Integer i: nodeSet)
			System.out.println("Node "+i+" indegree: "+ indegree(i) + " outdegree: "+outdegree(i));
		System.out.println("Size of the digraph: "+graphSize());
		System.out.println("Order of the digraph: "+graphOrder());

		boolean found=false;

		printMatrix();
		printList();

		try{
			Thread.sleep(2000);
			for (Integer i: nodeSet){
				if (isUniversalSource(i)){
					selectedNode = i;
					System.out.println("Universal Source found: "+i);
					found=true;
					break;
				}
			}
			repaint();

		    Thread.sleep(1500);

			found=false;
			for (Integer i: nodeSet){
				if (isUniversalSink(i)){
					selectedNode = i;
					System.out.println("Universal Sink found: "+i);
					found=true;
					break;
				}
			}
			repaint();

		    Thread.sleep(1500);
			selectedNode=-1;


			System.out.println("Transpose");
			transpose();
			repaint();
			printMatrix();

		    Thread.sleep(1500);

			System.out.println("Underlying Graph");
			underlying();
			repaint();
			printMatrix();

		    Thread.sleep(1500);

			clear();
			System.out.println("Create a directed star");
			add(0);
			for (int i=1;i<11;i++){
				add(i);
				addEdge(0,i);
			}
			repaint();

			for (int i=0;i<500;i++){
			    Thread.sleep(30);
			    for(Integer node: nodeSet){
					nodeList.get(node).move();
				}
				if(i>50) remove((i+1)/50);
				repaint();
			}

			Thread.sleep(500);

			clear();

			System.out.println("Create a directed cycle");

			for (int i=0;i<10;i++){
				add(i);
				if (i>0)
					addEdge(i,i-1);
			}
			addEdge(0,9);

			repaint();

			for (int i=0;i<500;i++){
			    Thread.sleep(30);
			    for(Integer node: nodeSet){
					nodeList.get(node).move();
				}
				if(i>50) contract((i+1)%500/50,0);
				repaint();
			}

			Thread.sleep(500);

			System.out.println("Create a complete graph");
			for (int i=0;i<10;i++){
				add(i);
			}
			for (Integer i: nodeSet){
				for(Integer j: nodeSet){
					if (i!=j){
						addEdge(i,j);
						addEdge(j,i);
					}
				}
			}
			repaint();


			for (int i=0;i<500;i++){
				Thread.sleep(30);
				for(Integer node: nodeSet){
					nodeList.get(node).move();
				}
				if(i>50) remove((i+1)%500/50);
					repaint();
			}

		}catch(Exception e)	{System.out.println("Exception caught");}



	}


	// Perform a test of different functions of the program
	public void test(){
		for(int i=0;i<5;i++)
			add(i);

		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++)
				if(Math.random()<0.2) addEdge(i,j);

		for (Integer i: nodeSet)
			System.out.println("Node "+i+" indegree: "+ indegree(i) + " outdegree: "+outdegree(i));

		System.out.println("Size of the digraph: "+graphSize());
		System.out.println("Order of the digraph: "+graphOrder());

		boolean found=false;

		printMatrix();
		printList();

		dfs();
		if(cyclic())
			System.out.println("The digraph contains a cycle");
		else
			System.out.println("The digraph contains no cycle");


	}


	// The main method builds a digraph and add three initial nodes to the digraph labeled 0,1,2
	// and adds some edges among these nodes
	//
    public static void main(String[] args) {

		//************************
		// You may change this method to test your programs
		//************************
        Digraph g = new Digraph();
        

        JFrame frame = new JFrame("Directed Graph Implementation");
        frame.setSize(450, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
        frame.getContentPane().add(g);
        frame.setVisible(true);

		g.test();
                //g.demo();


    }



}
