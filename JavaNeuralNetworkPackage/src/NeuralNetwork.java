import java.util.Random;

public class NeuralNetwork {
	
	Layer[] layers;
	int numberOfNodes;
	
	//Initialises the neural network
	public NeuralNetwork(){
		layers = new Layer[0];
	}
	
	//Adds a layer of nodes with the number of normal nodes as the first input the activation function as the second input and the number of 
	//bias nodes as the last input (maximum of 1)
	public  void addLayer(int numberOfNodes, String activationFunction, int biasNode) {
		
		assert activationFunction=="sigmoid" || activationFunction=="relu" || activationFunction=="tanh": "Invalid activation function";
		assert biasNode==0 || biasNode==1: "Invalid bias node input";
		
		if(layers.length!=0) {
			Layer newLayer = new Layer(numberOfNodes, activationFunction, biasNode);
			layers[layers.length-1].connect(newLayer); 
			Layer[] newLayers = new Layer[layers.length + 1];
			for(int x=0; x<layers.length; x++) {
				newLayers[x] = layers[x];
			}
			newLayers[layers.length] = newLayer;
			layers = newLayers;
		}
		else {
			layers = new Layer[1];
			layers[0] = new Layer(numberOfNodes, activationFunction, biasNode);
		}
		
		this.numberOfNodes = this.numberOfNodes + numberOfNodes;

	}
	
	//Returns a vector of all weights in the network
	public double[] getWeights() {
		int totalWeights = 0;
		
		for(int x=0; x<layers.length-1; x++) {
			totalWeights = totalWeights + layers[x].nodes.length*layers[x+1].numberOfNodes;
		}
		double[] weights = new double[totalWeights];
		int counter = 0;
		for(int x=0; x<layers.length-1; x++) {
			double[] weightHolder = layers[x].getWeights(layers[x+1].numberOfNodes);
			for(int y=0; y<weightHolder.length; y++) {
				weights[counter] = weightHolder[y];
				counter++;
			}
		}
		return weights;
	}
	
	//Sets the weights in a network with the input being the new weights
	public void setWeights(double[] weights) {
		
		int counter = 0;
		for(int y=0; y<layers.length; y++) {
			for(int x=0; x<layers[y].nodes.length; x++) {
				Node node = layers[y].nodes[x];
				LinkedListNode next = node.connections.head;
				while(next!=null) {
					next.weight = weights[counter];
					counter++;
					next=next.next;
				}
			}
		
		}
		
	}

	//Returns the output of the network when an input vector is inputed to the first layer in the network
	public double[] getOutput(double[] input) {
		Node[] inputLayerNodes = layers[0].nodes;
		clean();
		
		for(int x=0; x<layers[0].numberOfNodes; x++) {
			inputLayerNodes[x].input = input[x];
		}
		for(int x=0; x<layers.length-1; x++) {
			layers[x].fire();
		}
		int outLength = layers[layers.length-1].nodes.length;
		double[] out = new double[outLength];
		if(layers[layers.length-1].nodes[0].activationFunction=="sigmoid") {
			for(int x=0; x<outLength; x++) {
				out[x] = sigmoid(layers[layers.length-1].nodes[x].input);
			}
		}
		else if(layers[layers.length-1].nodes[0].activationFunction=="relu") {
			for(int x=0; x<outLength; x++) {
				out[x] = relu(layers[layers.length-1].nodes[x].input);
			}
		}
		else if(layers[layers.length-1].nodes[0].activationFunction=="tanh") {
			for(int x=0; x<outLength; x++) {
				out[x] = tanh(layers[layers.length-1].nodes[x].input);
			}
		}

		return out;
	}
	
	//Sets all inputs to 0 for all nodes in the network
	public void clean() {
		for(int x=0; x<layers.length; x++) {
			layers[x].clean();
		}
	}
	
	//Mutates the weights in the network with the chance of mutation of a weights as chanceWeight
	public void mutate(double chanceWeight) {
		double[] weights = getWeights();
		for(int x=0; x<weights.length; x++) {
			Random r = new Random();
			double number =  r.nextDouble();
			if(number<chanceWeight) {
				weights[x] = weights[x] + (r.nextDouble()-0.5)*2*5;
			}
		}
		setWeights(weights);
	}
	
	//Returns a new weight vector which is the result of breeding this network and the inputed network NN
	public double[] mateWeights(NeuralNetwork NN) {
		int NOfLayers = layers.length-1;
		double[] newWeights = new double[getWeights().length];
		boolean first = true;
		int counter = 0;
		for(int x=0; x<NOfLayers; x++) {
			if(first==true) {
				double[] weightHolder = layers[x].getWeights(layers[x+1].numberOfNodes);
				for(int y=0; y<weightHolder.length; y++) {
					newWeights[counter] = weightHolder[y];
					counter++;
				}
				first = false;
				System.out.print("first\n");
			}
			else {
				double[] weightHolder = NN.layers[x].getWeights(layers[x+1].numberOfNodes);
				for(int y=0; y<weightHolder.length; y++) {
					newWeights[counter] = weightHolder[y];
					counter++;
				}
				first = true;
				System.out.print("second\n");
			}
		}
		return newWeights;	
	}
	
	//Does sigmoid function
	public double sigmoid(double x) {
		return (1/( 1 + Math.pow(Math.E,(-1*x))));
	}
	
	//Does relu function
	public double relu(double x) {
		if(x>0) {
			return x;
		}
		else {
			return 0;
		}
	}
	
	//Does tanh function
	public double tanh(double x) {
		return Math.tanh(x);
	}
}
