import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

public class IncomingRequestHandler {
    final protected HashMap<String, BlockingQueue<byte[]>> streams = new HashMap<>();
    // A thing to handle incoming http requests
    // Does this thing need to be a singleton?


    public IncomingRequestHandler() {
        // TODO: Make the capacity of the incoming request queue a configurable property.

       /* Create a socket listener
       - Each connection to this server has it's own thread.
       -    - This is implemented by passing the OutputStream and InputStream into some kind of handler object
       - This thread needs access to some internal representation of a stream, probably through a BlockingQueue
       - This internal stream object has a cache of the incoming data (probably a hashmap or something)
       -    - There will be two levels of hashmap: One stream -> HashMap of stream IDs and String data (or bytearrays if that's easier)
       -    - This kinda sucks, I need to think of a better way of storing this data, maybe with a Trie of bytes or something clever
              like that.
        */

       // Start a socket server
        try {
            ServerSocket server = new ServerSocket(4184);

            // HACK: Get a ref to the streams thing in scope for the anonymous class
            HashMap<String, BlockingQueue<byte[]>> local_streams = this.streams;

            while( true ) {
                Socket incoming_socket = server.accept();
                System.out.println("New connection");
                // Triage the socket

                // Read the first line of the stream
                // XXX: This blocks the master thread until a valid command is sent!!
                BufferedReader reader = new BufferedReader(new InputStreamReader(incoming_socket.getInputStream()));
                Option<IncomingCommand> incoming_command = IncomingCommand.parse(reader.readLine());

                System.out.println(incoming_command.present());

                incoming_command.map(new Function<IncomingCommand, Boolean>() {
                    public Boolean apply(IncomingCommand command) {
                        try {
                            switch (command.getCmd()) {
                                case PUBLISH:
                                    System.out.println("PUBLISH: " + command.getStream_name());
                                    local_streams.putIfAbsent(command.getStream_name(), new ArrayBlockingQueue<byte[]>(2048));

                                    StreamPublisher pub = new StreamPublisher(
                                        command.getStream_name(),
                                        local_streams.get(command.getStream_name()),
                                        incoming_socket.getInputStream()
                                    );

                                    pub.start();

                                    break;
                                case CONSUME:
                                    System.out.println("CONSUME: " + command.getStream_name());
                                    Option<BlockingQueue<byte[]>> opt_stream = Option.of(local_streams.get(command.getStream_name()));
                                    if(opt_stream.present()) {
                                        StreamSubscriber sub = new StreamSubscriber(
                                                command.getStream_name(),
                                                opt_stream.get(),
                                                incoming_socket.getOutputStream(),
                                                incoming_socket.getInputStream()
                                        );

                                        sub.start();
                                    } else {
                                        System.out.println("Warning: Attempting to consume from nonexistent queue");
                                        // TODO: Maybe this should create the stream? :thinking_face:
                                    }
                                    break;
                                case PROCESS:
                                    System.out.println("Warning: PROCESS req - not implemented");
                                    break;
                            }
                        } catch (IOException e) {
                            System.err.println("bad");
                            e.printStackTrace();
                        }
                        return true;
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("bad2");
            e.printStackTrace();
        }
    }
}
