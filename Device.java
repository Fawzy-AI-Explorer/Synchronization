import java.io.FileWriter;
import java.io.PrintWriter;

class Device extends Thread {
    public String name;      //name of the device
    public String type;      //type of the device
    public int connectionID; //connection ID of the device
    public  Router router;   //An instance of the Router class to which the device is connected

    public Device(String name, String type, Router router) {
        this.name = name;
        this.type = type;
        this.router = router;
        connectionID = 1;
    }
    /*
    Finally, it calls router.semaphore.signal() to release the semaphore, allowing other waiting threads to proceed..*/
    @Override //(Override from Thread class)
    public void run() {
        try {
            router.arrived(this);       //to indicate that the device has arrived at the router
            router.semaphore.wait(this);//to wait for the semaphore, ensuring that the device doesn't exceed the maximum allowed connections.
            connectionID = router.connect(this); // establish a connection , updates the connectionID
            activity();                    //(2 seconds)
            router.disconnect(this); //disconnect the device from the router
            router.semaphore.signal();     //release the semaphore, allowing other waiting threads to proceed

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void activity() throws InterruptedException {
        String output= "Connection " + connectionID + ": " + name + " login ";
        writeOutputToFile(output);
        output= "Connection " + connectionID + ": " + name + " Performs online activity";
        writeOutputToFile(output);
        Math math;
        Thread.sleep((long)(Math.random() * 100));
    }

    private void writeOutputToFile(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true))) {
            writer.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

