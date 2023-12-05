import java.io.FileWriter;
import java.io.PrintWriter;

class Semaphore {
    int value;
    public Semaphore(int value) {
        this.value = value;
    }

    public synchronized void wait(Device device) throws InterruptedException {
        value--;
        if (value < 0) { //there are more threads trying to access the shared resource than allowed
            String output= device.name + " (" + device.type + ")" + " arrived and waiting";
            writeOutputToFile(output);
            wait();

        }
        else{   //the thread is allowed to proceed.
            String output= device.name +" (" + device.type + ")" +" arrived";
            writeOutputToFile(output);

            output=device.name +" (" + device.type + ")" +" occupeid";
            writeOutputToFile(output);
        }

    }
    public synchronized void signal() {
        value++;
        if (value <= 0) { //there are waiting threads
            notify();
        }
    }

    private void writeOutputToFile(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true))) {
            writer.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

