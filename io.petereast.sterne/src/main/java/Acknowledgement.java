public class Acknowledgement {
    // This is the thing that

    public static byte[] create() {
        return new byte[] {'.'};
    }

    public static boolean parse(byte[] incoming_ack) {
        // Basically, the first char should be '.'
        // Otherwise it failed
        //
        //
        return incoming_ack[0] == '.';
    }
}
