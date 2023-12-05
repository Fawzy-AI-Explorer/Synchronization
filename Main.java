import static java.lang.Thread.sleep;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/*creating the router and devices, taking user input for the number of Wi-Fi connections and devices,
 and then starting the device threads.*/
class Network {


    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        int numberOfConnections;                      //the number of Wi-Fi connections
        int numberOfDecives;                           //the number of devices to be connected
        ArrayList<Thread> devices = new ArrayList<>(); //store instances of the Device class
        Scanner input = new Scanner(System.in);

        System.out.println("What is number of WI-FI Connections?");
        numberOfConnections = input.nextInt();
        Router router = new Router(numberOfConnections);

        System.out.println("What is number of devices Clients want to connect?");
        numberOfDecives = input.nextInt();

        for (int i = 0; i < numberOfDecives; i++) {
            String name, type;
            name = in.next();
            type = in.next();
            devices.add(new Thread(new Device(name, type, router)));
        }


        for (int i = 0; i < numberOfDecives; i++) {
            Math math;
            Thread.sleep((long) (Math.random() * 100));
            // devices.get(i).run();
            devices.get(i).start(); //starting the Device thread and allowing it to run concurrently with other threads in  program.enabling it to execute its run() method
        }
    }

}