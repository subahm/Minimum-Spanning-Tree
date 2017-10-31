/*
  Name: Subah Mehrotra
  Course: CSC 226
*/

import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.util.PriorityQueue;

public class MST {


  private static class WeightedQuickUnion{
    private int[] parent;
    private int[] size;
    private int count;

    public WeightedQuickUnion(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    public boolean connected(int p, int q) {
        if(find(p) == find(q)){
          return true;
        }
        else{
          return false;
        }
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ){
          return;
        }
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] = size[rootQ] + size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] = size[rootP] + size[rootQ];
        }
        count--;
    }
  }

  private static class Edge {
		int v1;
		int v2;
		int weight;

		public Edge(int v1, int v2, int weight) {
			this.v1 = v1;
			this.v2 = v2;
			this.weight = weight;
		}
	}
    /* mst(G)
       Given an adjacency matrix for graph G, return the total weight
       of all edges in a minimum spanning tree.

       If G[i][j] == 0, there is no edge between vertex i and vertex j
       If G[i][j] > 0, there is an edge between vertices i and j, and the
       value of G[i][j] gives the weight of the edge.
       No entries of G will be negative.
    */

  // Helper method to heapify
  private static PriorityQueue<Edge> heapify(int[][] G) {
  	PriorityQueue<Edge> pq2 = new PriorityQueue<Edge>(G.length^2, (x, y) -> x.weight - y.weight);
  	for (int i = 0; i < G.length; i++) {
  		for (int j = 0; j < G.length; j++) {
  			if (G[i][j] > 0) {
             pq2.add(newEdge(i,j,G[i][j]));
  			}
  		}
  	}
  	return pq2;
  }

  // Add a new edge
  private static Edge newEdge(int i, int j, int w){
    Edge e = new Edge(i, j, w);
    return e;
  }


  static int mst(int[][] G) {
	   int numVerts = G.length;

	/* Find a minimum spanning tree using Kruskal's algorithm */
	/* (You may add extra functions if necessary) */

	/* ... Your code here ... */
  WeightedQuickUnion unionFind = new WeightedQuickUnion(numVerts);
  PriorityQueue<Edge> pq = heapify(G);

	/* Add the weight of each edge in the minimum spanning tree
	   to totalWeight, which will store the total weight of the tree.
	*/
	int totalWeight = 0;
	/* ... Your code here ... */
  while (unionFind.count() > 1) {
	   Edge front = pq.remove();
	   if (!unionFind.connected(front.v1, front.v2)) {
		     unionFind.union(front.v1, front.v2);
			   totalWeight = totalWeight + front.weight;
		  }
		}

	  return totalWeight;
  }


    public static void main(String[] args) {
	/* Code to test your implementation */
	/* You may modify this, but nothing in this function will be marked */

	int graphNum = 0;
	Scanner s;

	if (args.length > 0) {
	    //If a file argument was provided on the command line, read from the file
	    try {
		s = new Scanner(new File(args[0]));
	    }
	    catch(java.io.FileNotFoundException e) {
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n",args[0]);
	}
	else {
	    //Otherwise, read from standard input
	    s = new Scanner(System.in);
	    System.out.printf("Reading input values from stdin.\n");
	}

	//Read graphs until EOF is encountered (or an error occurs)
	while(true) {
	    graphNum++;
	    if(!s.hasNextInt()) {
		break;
	    }
	    System.out.printf("Reading graph %d\n",graphNum);
	    int n = s.nextInt();
	    int[][] G = new int[n][n];
	    int valuesRead = 0;
	    for (int i = 0; i < n && s.hasNextInt(); i++) {
		G[i] = new int[n];
		for (int j = 0; j < n && s.hasNextInt(); j++) {
		    G[i][j] = s.nextInt();
		    valuesRead++;
		}
	    }
	    if (valuesRead < n * n) {
		System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
		break;
	    }
	    if (!isConnected(G)) {
		System.out.printf("Graph %d is not connected (no spanning trees exist...)\n",graphNum);
		continue;
	    }
	    int totalWeight = mst(G);
	    System.out.printf("Graph %d: Total weight of MST is %d\n",graphNum,totalWeight);

	}
    }

    /* isConnectedDFS(G, covered, v)
       Used by the isConnected function below.
       You may modify this, but nothing in this function will be marked.
    */
    static void isConnectedDFS(int[][] G, boolean[] covered, int v) {
	covered[v] = true;
	for (int i = 0; i < G.length; i++) {
	    if (G[v][i] > 0 && !covered[i]) {
		isConnectedDFS(G,covered,i);
	    }
	}
    }

    /* isConnected(G)
       Test whether G is connected.
       You may modify this, but nothing in this function will be marked.
    */
    static boolean isConnected(int[][] G) {
	boolean[] covered = new boolean[G.length];
	for (int i = 0; i < covered.length; i++) {
	    covered[i] = false;
	}
	isConnectedDFS(G,covered,0);
	for (int i = 0; i < covered.length; i++) {
	    if (!covered[i]) {
		return false;
	    }
	}
	return true;
    }

}
