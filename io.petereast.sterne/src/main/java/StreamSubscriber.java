import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

public class StreamSubscriber extends Thread {
    private String stream_id;
    private BlockingQueue<byte[]> backing_store;
    private OutputStream outgoing_data;
    StreamSubscriber(String stream_id, BlockingQueue<byte[]> backing_store, OutputStream outgoing_data) {

        this.stream_id = stream_id;
        this.backing_store = backing_store;
        this.outgoing_data = outgoing_data;

    }

    public void run() {
        // Perform the logic
        try {
            // Read from the queue and write the result
            while (this.backing_store != null) {
                // naieve implementation
                this.outgoing_data.write(this.backing_store.take());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
