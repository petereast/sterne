import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.Time;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class StreamSubscriber extends Thread {
    private String stream_id;
    private BlockingQueue<byte[]> backing_store;
    private OutputStream outgoing_data;
    private InputStream incoming_data;
    StreamSubscriber(String stream_id, BlockingQueue<byte[]> backing_store, OutputStream outgoing_data, InputStream incoming_data) {

        this.stream_id = stream_id;
        this.backing_store = backing_store;
        this.outgoing_data = outgoing_data;
        this.incoming_data = incoming_data;

    }

    public void run() {
        // Perform the logic
        try {
            // Read from the queue and write the result
            while (this.backing_store != null) {
                // naieve implementation
                // TODO: Ensure data is not lost (maybe by waiting for an ACK?)
                // TODO: If this timeout is reached, problems happen! We die because of NullPointerException
                this.outgoing_data.write(this.backing_store.take());
                // TODO: Read input stream for an ACK (.)

                // TODO: Just wait for the ack for now, but in the future parse it and validate it
                byte[] incoming_ack = new byte[8];
                this.incoming_data.read(incoming_ack);

                // Process the ack
                Charset c = Charset.forName("utf-8");
                System.out.println(c.decode(ByteBuffer.wrap(incoming_ack)));

                this.outgoing_data.flush();
                System.out.println("Debug: subsc buffer size now: " + this.backing_store.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
