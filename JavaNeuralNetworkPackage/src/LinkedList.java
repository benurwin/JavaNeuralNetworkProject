public class LinkedList {
	
	LinkedListNode head;
	
	public LinkedList(){
		
	}
	
	public void add(Node node, double weight) {
		LinkedListNode newNode = new LinkedListNode(node ,weight);
		newNode.next = head;
		head = newNode;
	}
	
}
