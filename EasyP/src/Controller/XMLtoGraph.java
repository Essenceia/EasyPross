package Controller;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import Model.Graph;
import Model.Node;

public class XMLtoGraph {
	private Graph root;
	
	public XMLtoGraph(String file) {
		//Create a SAXBuilder to use when reading xml file
		SAXBuilder builder = new SAXBuilder();
		// Create an instance of the xml file
		File xmlFile = new File(file+".xml");

		  try {
			//Use XML Java Builder to create elements and attributes  
			Document document = (Document) builder.build(xmlFile);
			// Get the first element of the xml file
			Element rootNode = document.getRootElement();
			// get all the children elements of the xml file
			List<Element> list = rootNode.getChildren();
			//Create the first element of the graph
			this.root = new Graph(new Node(rootNode, rootNode.getAttributes()));
			//create all children elements of the graph
			this.goThroughGraph(this.root, list);
			//Display the graph
			System.out.println(this.root.toString());

		  } catch (IOException io) {
			System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		  }
	}
	
	public Graph getRoot() {
		return root;
	}

	public void setRoot(Graph root) {
		this.root = root;
	}
	/**
	 * method to add the elements to the graph
	 * @param g
	 * @param listElement
	 * @return
	 */
	public Graph goThroughGraph(Graph g, List<Element> listElement) {
		Node n;
		//For every element of the children list
		for(Element e : listElement) {
			//Get its attributes
			 List<Attribute> listAttributes = e.getAttributes();
			 /**
			  * After unifying the XML files between Simulator and Graphical User Interface
			  */
			 // Only consider the nodes that interest us (i.e. do not consider first part of XML
			if(e.getName().equals("node") || e.getName().equals("wire_in") || e.getName().equals("wire_out")) {
				 //Create a new node
				 n = new Node(e, listAttributes);
				 //Create a new graph out of it
				 Graph newG = new Graph(n);
				 // Add this graph to the root graph
				 root.addChild(newG);
				 //Get the children elements
				 List<Element> listChild = e.getChildren();
				
				 if(!listChild.isEmpty()){	 
					 // recursive call of the method
					 g = goThroughGraph(newG, listChild);
					 //add the graph to the previously created graph
					 newG.addChild(g);
				}
			 }
			 /**
			  * Before unifying the XML files
			  */
			 // if they are inputs or outputs 
			/* if(e.getName().equals("input")|| e.getName().equals("output")) {
				 //Create a new node
				 n= new Node(e, listAttributes);
				 //transform the <io> children into vector of 0s &1s
				 //n.setValue(this.transformIOtoValue(e));
				 //Create a new graph with root the node
				 Graph newG = new Graph(n);
				 // add this graph to the graph
				 root.addChild(newG);
			 }
			 else {
				 // If not create a new node
				 n = new Node(e, listAttributes);
				 //Create a new graph out of it
				 Graph newG = new Graph(n);
				 // Add this graph to the root graph
				 root.addChild(newG);
				 //Get the children elements
				 List<Element> listChild = e.getChildren();
				
				 if(!listChild.isEmpty()){	 
					 // recursive call of the method
					 g = goThroughGraph(newG, listChild);
					 //add the graph to the previously created graph
					 newG.addChild(g);
				}
			 }	*/
		}
		return g;
	}
	/* Method to transform <io>true</io> or <io>false</io> into vector of 0s & 1s */
	/*public Vector transformIOtoValue(Element e) {
		Vector vect = new Vector<Integer>();
		List<Element> listChild = e.getChildren();
		if(!listChild.isEmpty()) {
			for(Element eC : listChild) {
				 String v = eC.getText();
				if(v != null) {
					 switch (v){
					 case "true":
					 case " true ":
						 vect.add(1);
						 break;
					 case "false":
					 case " false ":
						 vect.add(0);
						 break;
					 }
				 }
				 
			}
		}
		
		return vect;
		
	}*/
}