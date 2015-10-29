import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra {
	
	private int [][]maticeGrafu;
	private int start;
	private ArrayList<Cesta> cesty;
	private int [] dist;
	private int nVerts;
	
	static java.util.PriorityQueue<Integer> q;
	
	
	
	public Dijkstra() {
		cesty = new ArrayList<Cesta>();		
		q = new PriorityQueue<>();
	}

	public int[] dijkstra_function(Graf g, int s) throws IOException{
		// s is the index of the starting vertex
	   	// declare variables

		int  u, v, uQ;
		
	        nVerts = g.getVelikost(); 			// get number of vertices in the graph class

	        // initialize array
	        dist = new int[nVerts];

	        for(v=0; v<nVerts; v++) 			// initializations
	        {
	        	dist[v] = 99999; 				// 99999 represents infinity

	        }// end for

	        dist[s] = 0;

	        int i  = 1;
	        
	        PriorityQueu Q = new PriorityQueu(dist);
	        
	        q.add(s);
	  
	        while(q.isEmpty() != true)
	        	// if heap is not empty
	        //while(Q.Empty() ==0)
	        {
	               u = q.remove();
	            //    uQ = Q.Delete_root();
	            //    planety.add(u);
	                v = g.nextneighbor(u);
	               
	                while(v != -1)  			// for each neighbor of u
	                {
	                	if(dist[v] > dist[u] + g.edgeLength(u,v)) {
	                		dist[v] = dist[u] + g.edgeLength(u,v);
	                	//	System.out.println(i + " Vzdalenost z " + u + " do " + v + " je " + dist[v] );
	                		i++;
	                		q.add(v);
	                         }

	                	v = g.nextneighbor(u);  	// get the next neighbor of u
	                }// end while
	            
		   
	                
	        }
	       return dist;
	   }// end bfs_function()
	
	public void vypisVzdalenosti(){
		System.out.println("");    			// display the array dist
        for(int row=0; row<nVerts; row++)
      	{
      		System.out.print(dist[row] + " ");
      	  	System.out.println("");
      	
      	}
	}
	public static int[] DijskraAlg(int[][] d, int from){
		
		Set<Integer> set = new HashSet<Integer>();
		set.add(from);
	boolean[] closed = new boolean[d.length];
		int[] distances = new int[d.length];
		for (int i = 0; i < d.length; i++) {
		if (i != from) {
		distances[i] = Integer.MAX_VALUE;
		} else {
		distances[i] = 0;
		}
			}
	int[] predecessors = new int[d.length];
		predecessors[from] = -1;
		while (!set.isEmpty()) {
				//najdi nejblizsi dosazitelny uzel
		
		int minDistance = Integer.MAX_VALUE;
		
		int node = -1;
		
		for(Integer i : set){
		
		if(distances[i] < minDistance){
		
		minDistance = distances[i];
		
		node = i;
		
		}
		
		}
		
		 
				set.remove(node);
		
		closed[node] = true;
		
		 
		
		//zkrat vzdalenosti
		
		for (int i = 0; i < d.length; i++) {
		
		//existuje tam hrana
		
		if (d[node][i] != Integer.MAX_VALUE) {
		
		if (!closed[i]) {
		
		//cesta se zkrati
		
		if (distances[node] + d[node][i] < distances[i]) {
		
		distances[i] = distances[node] + d[node][i];
		
		predecessors[i] = node;
		
		set.add(i); // prida uzel mezi kandidaty, pokud je jiz obsazen, nic se nestane
		
		}
		
		}
		
		}
		
		}
		
		}
		
		return predecessors;
		
		
		
	}

	/**
	 * @return the maticeGrafu
	 */
	public int [][] getMaticeGrafu() {
		return maticeGrafu;
	}

	/**
	 * @param maticeGrafu the maticeGrafu to set
	 */
	public void setMaticeGrafu(int [][] maticeGrafu) {
		this.maticeGrafu = maticeGrafu;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
	
 }
