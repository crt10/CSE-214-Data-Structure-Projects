package hw4;

/**
 * The Router class represents a router in the network, which is ultimately a queue.
 * The Router extends the Java API LinkedList class and implements an enqueue and dequeue method.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
import java.util.Collection;
import java.util.LinkedList;

public class Router extends LinkedList<Packet>{
	
	/**
	 * Adds a new Packet to the end of the router buffer.
	 * 
	 * @param p
	 *     The Packet to add to the queue.
	 */
	public void enqueue(Packet p) {
		add(p);
	}
	
	/**
	 * Removes and returns the Packet at the front of the router buffer.
	 * 
	 * @return
	 *     Returns the Packet removed from the queue.
	 */
	public Packet dequeue() {
		return remove();
	}
	
	/**
	 * Finds the router with the most free buffer space (contains the least Packets),
	 * and returns the index of the router. The indexes start at 1 rather than 0.
	 * If all router buffers are full, an exception is thrown.
	 * 
	 * @param routers
	 *     The collection of routers to search through.
	 *     
	 * @return
	 *     Returns the index of the router with the smallest queue.
	 *     
	 * @throws CongestedNetworkException
	 *     Indicates that all the Router objects have no space in their queue.
	 */
	public static int sendPacketTo(Collection<Router> routers) throws CongestedNetworkException {
		int count = 1;
		int index = count;
		int min = 0;
		for (Router r: routers) {
			if (count == 1) {
				min = r.size();
			}
			if (r.size() < min) {
				min = r.size();
				index = count;
			}
			count++;
		}
		if (min == Simulator.maxBufferSize) {
			throw new CongestedNetworkException();
		}
		return index;
	}
}
