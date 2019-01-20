public class Main {
    public static void main(String[] args) {
        System.out.println("hello world!");
        IncomingRequestHandler instance = new IncomingRequestHandler();
        // build a thing
        // API/master thread to handle meta stuff
        // Emitter API - takes stream_id and data and publishes it onto stream
        // Consumer API - takes stream_id and index? and receives something from the stream
        // Stream API - Two variants: Map - atomically transforms an item on the stream
        //                            Reduce - transforms more than one item from the stream
        //              A conceptual stream is made up of composed stream modifiers terminated by emmitters and consumers
        // One thread per stream - some consideration to clustering the thing

        // In the main process, maintain this struct
        // Provide a method for creating and deleting new topics
    }
}
