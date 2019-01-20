import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class StreamPublisher extends Thread {
    private String stream_id;
    private BlockingQueue<byte[]> backing_store;
    private InputStream incoming_data;
    StreamPublisher(String stream_id, BlockingQueue<byte[]> backing_store, InputStream incoming_data) {

        this.stream_id = stream_id;
        this.backing_store = backing_store;
        this.incoming_data = incoming_data;

    }

    public void run() {
        // Perform the logic
        byte[] buffer = new byte[2048];
        try {
            while ((this.incoming_data.read(buffer) > 0)) {
                // Process each part of the stream;
                // Keep taking shit from the stream until it closes.
                this.backing_store.offer(buffer, 10, TimeUnit.SECONDS);
                System.out.println("Debug: Wrote to buffer");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
