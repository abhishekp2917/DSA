class MyQueue {

	// variable to track the front and rear part of the queue in array
    int front, rear;
	// initializing array of size 100005 which is greater than the maximum number of push calls
	// due to which all the push element could be stored in single array
	int arr[] = new int[100005];

    MyQueue()
	{
		front=0;
		rear=0;
	}
	
	//Function to push an element x in a queue.
	void push(int x)
	{
		// for every push call, add that element in the array at rear index
		// thereafter increment the rear index by 1
 	    arr[rear++] = x;
	} 

    //Function to pop an element from queue and return that element.
	int pop()
	{
		// if both front and rear point to same index, that means the queue (array) is empty so return -1
		if(front==rear) return -1;
		// else return element of front index and then increment the front index by 1 which will 
		// imitate as if that element has been removed
		return arr[front++];
	} 
}


