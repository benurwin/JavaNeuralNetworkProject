import java.util.Random;

public class Node {
	
	LinkedList connections = new LinkedList();
	double input;
	boolean bias;
	String activationFunction;
	
	public Node(String activationFunction,boolean biasNode) {
		bias = biasNode;
		this.activationFunction = activationFunction;
	}
	
	public void connect(Node node) {
		Random r = new Random();
		double weight = -10 + (10 + 10) * r.nextDouble();
		connections.add(node, weight);
	}
	
	public void fire() {
		if(bias) {
			LinkedListNode next = connections.head;
			while(next!=null) {
				next.node.input = next.node.input + next.weight;
				next = next.next;
			}
		}
		else {
			LinkedListNode next = connections.head;
			if(activationFunction=="relu") {
				while(next!=null) {
					next.node.input = next.node.input + next.weight*relu(input);
					next = next.next;
				}
			}else if(activationFunction=="sigmoid") {
				while(next!=null) {
					next.node.input = next.node.input + next.weight*sigmoid(input);
					next = next.next;
				}
			}else if(activationFunction=="tanh") {
				while(next!=null) {
					next.node.input = next.node.input + next.weight*tanh(input);
					next = next.next;
				}
			}

		}
	}
	
	public double sigmoid(double x) {
		return (1/( 1 + Math.pow(Math.E,(-1*x))));
	}
	
	public double relu(double x) {
		if(x>0) {
			return x;
		}
		else {
			return 0;
		}
	}
	
	public double tanh(double x) {
		return Math.tanh(x);
	}
}
