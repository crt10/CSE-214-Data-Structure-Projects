package hw4;

/**
 * The Simulator class contains the main method that runs simulations of networks
 * based off of the users inputed parameters.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Simulator {
	
	public static final int MAX_PACKETS = 3;
	public static int maxBufferSize;
	
	public static void main(String[] args) {
		Router dispatcher = new Router();
		Collection<Router> routers = new ArrayList<>();
		double arrivalProb;
		int numIntRouters;
		int minPacketSize;
		int maxPacketSize;
		int bandwidth;
		int duration;
		Scanner kb = new Scanner(System.in);
		String input = "y";
		System.out.println("Starting simulator...\n");
		while (input.equalsIgnoreCase("y")) {
			routers.clear();
			try {
				System.out.println("Enter the number of Intermediate routers: ");
				numIntRouters = kb.nextInt();
				for (int i = 0; i < numIntRouters; i++) {
					routers.add(new Router());
				}
				System.out.println("\nEnter the arrival probability of a packet: ");
				arrivalProb = kb.nextDouble();
				System.out.println("\nEnter the maximum buffer size of a router: ");
				maxBufferSize = kb.nextInt();
				System.out.println("\nEnter the minimum size of a packet: ");
				minPacketSize = kb.nextInt();
				System.out.println("\nEnter the maximum size of a packet: ");
				maxPacketSize = kb.nextInt();
				System.out.println("\nEnter the bandwidth size: ");
				bandwidth = kb.nextInt();
				System.out.println("\nEnter the simulation duration: ");
				duration = kb.nextInt();
				kb.nextLine();
				simulate(dispatcher, routers, arrivalProb, minPacketSize, maxPacketSize, bandwidth, duration);
				System.out.println("\nDo you want to try another simulation? (y/n): ");
				input = kb.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("\nPlease enter in appropriate values.\n");
				kb.nextLine();
			}
		}
		System.out.println("\nProgram terminating successfully");
		kb.close();
	}
	
	/**
	 * Runs a simulation of a network using the supplied parameters.
	 * 
	 * @param dispatcher
	 *     The level 1 router.
	 * @param routers
	 *     The collection of level 2, intermediate routers.
	 * @param arrivalProb
	 *     The probability of a new packet arriving at the Dispatcher.
	 * @param minPacketSize
	 *     The minimum size of a Packet.
	 * @param maxPacketSize
	 *     The maximum size of a Packet.
	 * @param bandwidth
	 *     The maximum number of Packets the Destination router can accept at a given simulation unit.
	 * @param duration
	 *     The number of simulation units.
	 *     
	 * @return
	 *     Return the average time each packet spends within the network.
	 */
	public static double simulate (Router dispatcher, Collection<Router> routers, double arrivalProb, int minPacketSize, int maxPacketSize, int bandwidth, int duration) {
		int totalServiceTime = 0;
		int totalPacketsArrived = 0;
		int packetsDropped = 0;
		int index = 1;
		for (int time = 1; time <= duration; time++) {
			System.out.println("\nTime: " + time);
			for (int o = 1; o <= MAX_PACKETS; o++) { //Decide whether packets have arrived at the Dispatcher. A maximum of 3 can arrive at a given time.
				if (Math.random() < arrivalProb) {
					int size = randInt(minPacketSize, maxPacketSize);
					Packet arrivedPacket = new Packet(size, time);
					dispatcher.enqueue(arrivedPacket);
					System.out.println("Packet " + arrivedPacket.getId()
					  + " arrives at the dispatcher with size " + size +".");
				}
			}
			while (!dispatcher.isEmpty()) { //If the Dispatcher contains unsent packets, send them off to one of the Intermediate routers.
				try {
					for (Router r: routers) {
						if (!dispatcher.isEmpty() && Router.sendPacketTo(routers) == index) {
							System.out.println("Packet " + dispatcher.peek().getId()
							  + " sent to Router " + index + ".");
							r.enqueue(dispatcher.dequeue());
						}
						index++;
					}
					index = 1;
				} catch (CongestedNetworkException e) {
					packetsDropped++;
					System.out.println(e.getMessage() + "Packet "
					  + dispatcher.dequeue().getId() + " is dropped.");
				}
			}
			int sentPackets = 0;
			for (Router r: routers) { //Decrement all packets counters in the beginning of the queue at each Intermediate router.
				Packet frontPacket = r.peek();
				if (frontPacket != null) {
					if (frontPacket.getTimeArrive() != time && !(sentPackets == bandwidth && frontPacket.getTimeArrive() == 1)) {
						frontPacket.setTimeToDest(frontPacket.getTimeToDest()-1);
					}
					if (frontPacket.getTimeToDest() == 0 && sentPackets < bandwidth) { //If any packets are ready to be forwarded to the Destination router, do so.
						sentPackets++;
						totalServiceTime += (time-frontPacket.getTimeArrive());
						totalPacketsArrived++;
						System.out.println("Packet " + frontPacket.getId()
						  + " has successfully reached its destination: +"
						  + (time-r.dequeue().getTimeArrive()));
					}
				}
			}
			index = 1;
			for (Router r: routers) {
				System.out.println("R" + index + ":" + r.toString());
				index++;
			}
		}
		Packet.setPacketCount(0);
		double averageServiceTime = (double)totalServiceTime/totalPacketsArrived;
		System.out.println("\nSimulation ending...");
		System.out.println("Total service time: " + totalServiceTime);
		System.out.println("Total packets served: " + totalPacketsArrived);
		System.out.println(String.format("Average service time per packet: %.2f", averageServiceTime));
		System.out.println("Total packets dropped: " + packetsDropped);
		return averageServiceTime;
	}
	
	/**
	 * Returns a randomly generated integer between the minValue and maxValue, inclusively.
	 * 
	 * @param minVal
	 *     The minimum value the number can be.  
	 * @param maxVal
	 *     The maximum value the number can be.
	 *     
	 * @return
	 *     Returns the randomly generated integer.
	 */
	private static int randInt(int minVal, int maxVal) {
		return (int)(Math.random()*(maxVal-minVal+1) + minVal);
	}
}
