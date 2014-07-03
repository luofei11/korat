package korat.examples.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import korat.finitization.IArraySet;
import korat.finitization.IFinitization;
import korat.finitization.IIntSet;
import korat.finitization.IObjSet;
import korat.finitization.impl.FinitizationFactory;

public class Graph {
    private Vertex root;
    public class Vertex {
    	private Vertex[] outgoingEdges;
    }
    private int size;
    public class ListNode{
    	public Vertex v;
    	public int index;
    	public ListNode next;
    }
    public ListNode head;
    public Set<Vertex> visited;
    public Set<Vertex> current;
    public boolean repOK() {
    	head = null;
        visited = new HashSet<Vertex>();
        current = new HashSet<Vertex>();
        if(root == null) return size == 0;
        if(Acyclic(root))
        {
            return size == visited.size();
        }
        return false;
    }
    
   

    private boolean Acyclic(Vertex a){
        for (int i = 0; i < a.outgoingEdges.length; i++)
        {
            Vertex child = a.outgoingEdges[i];
            // Avoid self-loop of vertex
            if(child == a) return false;
        	// Avoid the same children
            for (int j = 0; j < i; j++)
                if (child == a.outgoingEdges[j]) return false;
        }
        // Create the List for DFS and backtracking
        ListNode head1 = new ListNode();
    	head1.v = a;
    	head1.index = 0;
    	head1.next = head;
    	head = head1;
    	if(!current.add(a))
    	{
    		return false;
    	}
    	if(visited.add(a))
    	{
    		for( ; head1.index < head1.v.outgoingEdges.length ; head1.index++)
    		{
    			if(!Acyclic(a.outgoingEdges[head1.index]))
    			{
    				return false;
    			}
    		}
    	}
    	current.remove(a);
    	head = head.next;
    	return true;
    }
     
    
    public static IFinitization finGraph(int nodeNum) {
        IFinitization f = FinitizationFactory.create(Graph.class);
        IObjSet nodes = f.createObjSet(Vertex.class, nodeNum, false);
        IIntSet arrLen = f.createIntSet(0, nodeNum - 1);
        IArraySet outgoingEdgesArray = f.createArraySet(Vertex[].class, arrLen, nodes, nodeNum);
        f.set("root", nodes);
        f.set("size", f.createIntSet(nodeNum));
        f.set("Vertex.outgoingEdges", outgoingEdgesArray);
        return f;
    }
}