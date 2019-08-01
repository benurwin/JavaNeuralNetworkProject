public class Layer {
	
	int numberOfNodes;
	Node[] nodes;
	
	public Layer(int numberOfNodes, String activationFunction, int biasNode) {
		this.numberOfNodes = numberOfNodes;
		nodes = new Node[numberOfNodes+biasNode];
		for(int x=0; x<numberOfNodes; x++) {
			nodes[x] = new Node(activationFunction,false);
		}
		if(biasNode==1) {
			nodes[numberOfNodes] = new Node(activationFunction,true);
		}
	}
	
	public void connect(Layer layer) {
		
		for(int x=0; x<layer.numberOfNodes; x++) {
			for(int y=0; y<nodes.length; y++) {
				nodes[y].connect(layer.nodes[x]);
			}
		}
		
	}
	
	public double[] getWeights(int nextNumber) {
		double[] weightHolder = new double[nodes.length*nextNumber];
		int counter = 0;
		for(int x=0; x<nodes.length; x++) {
			LinkedListNode next = nodes[x].connections.head;
			while(next!=null) {
				weightHolder[counter] = next.weight;
				counter++;
				next=next.next;
			}
		}
		return weightHolder;
	}
	
	public void fire() {
		if(nodes[0].activationFunction=="relu") {
			for(int x=0; x<numberOfNodes; x++) {
				if(nodes[x].input>0) {
					nodes[x].fire();
				}
			}
			if(numberOfNodes>nodes.length) {
				nodes[nodes.length-1].fire();
			}
		}
		else {
			for(int x=0; x<nodes.length; x++) {
				nodes[x].fire();
			}
		}
	}
	
	public void clean() {
		for(int x=0; x<nodes.length; x++) {
			nodes[x].input = 0;
		}
	}
	
}
