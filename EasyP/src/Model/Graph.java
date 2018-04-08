package Model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	private Node parent;
	private List<Graph> children;

	public Graph(Node n) {
		this.parent = n;
		this.children = new ArrayList<Graph>();
	}
	public void setChildren(List<Graph> c) {
		this.children = c;
	}
	public void addChild(Graph g) {
		this.children.add(g);
	}
	public List<Graph> getChildren(){
		return this.children;
	}
	public Node getNode() {
		return parent;
	}
	@Override
	public String toString() {
		String s = parent.toString();
		for(Graph g: children) {
			s+= g.getNode().toString();
		}
		return s;
	}
	/**
	 * Get the registers nodes of the graph (i.e. the "wires")
	 * @param list
	 * @return
	 */
	public <T> List<Node<T>> getRegisterNodes(List<Node<T>> list){
		if(parent.isARegister()){
			list.add(parent);
		}
		for(Graph g: children) {
			if(g.getNode().isARegister()) {
				list.add(g.getNode());
			}
		}
		return list;
	}
	/**
	 * Get the nodes that are not registers of the graph (i.e. the blocks like ProgramMemory, Program Counter, Muxs...)
	 * @param list
	 * @return
	 */
	public <T> List<Node<T>> getNonRegisterNodes(List<Node<T>> list){
		if(parent.isNotARegister()){
			list.add(parent);
		}
		for(Graph g: children) {
			if(g.getNode().isNotARegister()) {
				list.add(g.getNode());
			}
		}
		return list;
	}
}
