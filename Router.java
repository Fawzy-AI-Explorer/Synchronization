import java.io.FileWriter;
import java.io.PrintWriter;

//This class contains a list of connections and methods to occupy a connection and release a connection.
class Router {
    public boolean[] connected;           // An array of booleans representing the connection status of devices.
    public int maxDevices ;               // maximum number of devices that can be connected to the router.
    public  int currentConnectedDevices;  // the current number of devices connected to the router.
    public Semaphore semaphore;           // class used for controlling access to the router's resources.

    Router(int maxDevices) {
        this.maxDevices = maxDevices;         //initializes the maxDevices variable
        semaphore = new Semaphore(maxDevices);//creates a Semaphore object with the specified maxDevices value
        connected = new boolean[maxDevices];  //initializes the connected array with maxDevices number of Devices
    }

    /**
     * The synchronized keyword is used in the method signature to
     * make the entire method synchronized.
     * This means only one thread can execute this method at a time for a given instance of the Router class.*
     * inside the for loop (where devices are being connected) is executed by only one thread at any given moment
     **/
    public synchronized int connect(Device device) throws InterruptedException {
        //This method is used to connect a device to the router.
        //InterruptedException --> if the thread is interrupted while waiting to acquire the semaphore.
        // sleep(100) --> does not affect the synchronization itself ,synchronized only controls access to the synchronized methods
        for (int i = 0; i < maxDevices; i++) {
            if(!connected[i]){
                currentConnectedDevices++;
                device.connectionID = i + 1;
                connected[i] = true;
                Math math;
                Thread.sleep((long)(Math.random() * 100));
                break;
            }
        }
        return device.connectionID;
    }

    public synchronized void disconnect(Device device){
        currentConnectedDevices--;
        connected[device.connectionID-1] = false;
        notify();
        String output="Connection " + device.connectionID + ": " + device.name + " Logged out";
        writeOutputToFile(output);
    }

    private void writeOutputToFile(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true))) {
            writer.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void arrived(Device device){
        //this method to indicate that the device has arrived at the router.
        //   System.out.println( device.name +" (" + device.type + ")" +" arrived");
    }
}