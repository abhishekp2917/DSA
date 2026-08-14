// Structure of Node Class

import java.util.ArrayList;

class Node {
    int data;
    Node next;

    public Node(int data){
        this.data = data;
    }
}

class Solution
{
    public static Node quickSort(Node head)
    {
        // A single node is already sorted, so no partitioning is required.
        if(head.next==null) return head;

        // Use the first node as the pivot and partition the list into:
        // values smaller than pivot, values equal to pivot, and values greater than pivot.
        ArrayList<Node> nodes = splitNodesAroundThreshold(head, head.data);

        // Recursively sort only the smaller and greater partitions.
        // The equal partition is already in its correct relative position.
        if(nodes.get(0)!=null) nodes.set(0, quickSort(nodes.get(0)));
        if(nodes.get(2)!=null) nodes.set(2, quickSort(nodes.get(2)));

        // Join the three partitions in sorted order:
        // smaller -> equal -> greater.
        Node tempHead = new Node(-1), ptr = tempHead;

        if(nodes.get(0)!=null) {
            ptr.next = nodes.get(0);
            ptr = getLastNode(nodes.get(0));
        } 

        if(nodes.get(1)!=null) {
            ptr.next = nodes.get(1);
            ptr = getLastNode(nodes.get(1));
        }

        if(nodes.get(2)!=null) {
            ptr.next = nodes.get(2);
            ptr = getLastNode(nodes.get(2));
        }

        // Return the actual head, skipping the dummy node.
        return tempHead.next;
    }

    public static ArrayList<Node> splitNodesAroundThreshold(Node head, int threshold) {

        // Dummy node makes removing nodes from the original list easier,
        // including when the node being removed is the original head.
        Node tempHead = new Node(-1);
        tempHead.next = head;
        Node prev = tempHead, curr = prev.next;

        // Maintain three separate lists for values smaller than,
        // equal to, and greater than the pivot.
        Node belowThresholdHead = new Node(-1), ptr1 = belowThresholdHead;
        Node equalToThresholdHead = new Node(-1), ptr2 = equalToThresholdHead;
        Node aboveThresholdHead = new Node(-1), ptr3 = aboveThresholdHead;

        while(curr!=null) {

            if(curr.data<threshold) {

                // Move curr into the smaller-than-pivot list.
                ptr1.next = curr;
                ptr1 = ptr1.next;

                // Remove curr from the original list.
                prev.next = curr.next;
                curr = curr.next;

                // Detach the moved node from its old list.
                ptr1.next = null;
            }

            else if(curr.data>threshold) {

                // Move curr into the greater-than-pivot list.
                ptr3.next = curr;
                ptr3 = ptr3.next;

                // Remove curr from the original list.
                prev.next = curr.next;
                curr = curr.next;

                // Detach the moved node from its old list.
                ptr3.next = null;
            }

            else {

                // Move curr into the equal-to-pivot list.
                ptr2.next = curr;
                ptr2 = ptr2.next;

                // Remove curr from the original list.
                prev.next = curr.next;
                curr = curr.next;

                // Detach the moved node from its old list.
                ptr2.next = null;
            }
        }

        // Return the three partitions so that the smaller and greater
        // partitions can be recursively sorted.
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(belowThresholdHead.next);
        nodes.add(equalToThresholdHead.next);
        nodes.add(aboveThresholdHead.next);
        return nodes;
    }

    public static Node getLastNode(Node head) {

        // Find the tail so the next partition can be attached to it.
        while(head.next!=null) head = head.next;

        return head;
    }
}