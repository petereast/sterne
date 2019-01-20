public class IncomingCommand {
    private boolean isValid;
    private Command cmd;
    private String stream_name;

    enum Command {
        PUBLISH, CONSUME, PROCESS
    }

    /* Ok buddy retard,
        Let's rethink this logic here
        PUBLISH: Pubs to a named stream: +<stream_name>
        CONSUME: Consumes from a named stream: -<stream_name>
        PROCESS: Let's think about that one later: %<stream_name>
     */

    public static Option<IncomingCommand> parse(String raw) {
        final IncomingCommand command = new IncomingCommand(raw);

        if (command.isValid)
            return Option.of(command);
        else
            return Option.of(null);
    }

    IncomingCommand(String raw) {
        if(raw != null) {

            final char[] raw_chars = raw.toCharArray();
            final char first = raw_chars[0];

            switch (first) {
                case '+':
                    System.out.println("PUBLISH to Stream");
                    this.stream_name = raw.substring(1);
                    this.cmd = Command.PUBLISH;
                    this.isValid = true;

                    break;
                case '-':
                    System.out.println("CONSUME from Stream");
                    this.stream_name = raw.substring(1);
                    this.cmd = Command.CONSUME;
                    this.isValid = true;

                    break;
                case '%':
                    System.out.println("PROCESS the Stream");
                    this.stream_name = raw.substring(1, raw.indexOf("+"));
                    this.cmd = Command.PROCESS;

                    break;
                default:
                    System.out.println("Error :/");
                    isValid = false;

                    break;
            }
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public Command getCmd() {
        return cmd;
    }

    public String getStream_name() {
        return stream_name;
    }
}
