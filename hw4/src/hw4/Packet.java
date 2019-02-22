package hw4;

/**
 * The Packet class represents a packet that will be sent through the network.
 * This object contains information such as the total number of Packets, the packets ID,
 * the packets size, the packets arrival time and its time to the destination router.
 * Getters and setters for all variables are implemented along with a ToString() method.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
public class Packet {
	
	static int packetCount = 0;
	public int id;
	public int packetSize;
	public int timeArrive;
	public int timeToDest;
	
	/**
	 * Constructs an instance of Packet with the supplied parameters.
	 * The packetCount is incremented by 1, id is set to packetCount,
	 * and timeToDest is set to the packetSize/100 by default.
	 * 
	 * Postcondition: 
	 *     This Packet has been initialized with the supplied parameters.
	 * 
	 * @param size
	 *     the size of the packet.
	 * @param time
	 *     the arrival time of the packet.
	 */
	public Packet(int size, int time) {
		packetCount++;
		setId(packetCount);
		setPacketSize(size);
		setTimeArrive(time);
		setTimeToDest(packetSize/100);
	}
	
	/**
	 * Sets the PacketCount of Packet.
	 * 
	 * @param c
	 *     The total number of packets.
	 */
	public static void setPacketCount(int c) {
		packetCount = c;
	}
	
	/**
	 * Sets the id of Packet.
	 * 
	 * @param i
	 *     The ID of the packet to set.
	 */
	public void setId(int i) {
		id = i;
	}
	
	/**
	 * Sets the packetSize of Packet.
	 * 
	 * @param s
	 *     The size of the packet to set.
	 */
	public void setPacketSize(int s) {
		packetSize = s;
	}
	
	/**
	 * Sets the timeArrive of Packet.
	 * 
	 * @param t
	 *     The arrival time of the packet to set.
	 */
	public void setTimeArrive(int t) {
		timeArrive = t;
	}
	
	/**
	 * Sets the timeToDest of Packet.
	 * 
	 * @param t
	 *     The time to the destination router of the packet to set.
	 */
	public void setTimeToDest(int t) {
		timeToDest = t;
	}
	
	/**
	 * Returns the packetCount of the Packet.
	 * 
	 * @return
	 *     Returns the total number of Packets.
	 */
	public static int getPacketCount() {
		return packetCount;
	}
	
	/**
	 * Returns the id of the Packet.
	 *  
	 * @return
	 *     Returns the id of the packet.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the packetSize of the Packet.
	 * 
	 * @return
	 *     Returns the size of the packet.
	 */
	public int getPacketSize() {
		return packetSize;
	}
	
	/**
	 * Returns the timeArrive of the Packet.
	 * 
	 * @return
	 *     Returns the arrival time of the packet.
	 */
	public int getTimeArrive() {
		return timeArrive;
	}
	
	/**
	 * Returns the timeToDest of the Packet.
	 * 
	 * @return
	 *     Returns the time to the destination router of the packet.
	 */
	public int getTimeToDest() {
		return timeToDest;
	}
	
	/**
	 * Returns a String representation of this Packet.
	 * 
	 * @return
	 *     Returns the String representation of the packet.
	 */
	public String toString() {
		return String.format("[%d, %d, %d]",id,timeArrive,timeToDest);
	}
}
