import socket

# Create the connection
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(('localhost', 4184))

# Start being a publisher to `test`
s.send(b'+test\n')

# Send some data

data_to_send = "this is some example data let's send this!".split();
for string, index in enumerate(data_to_send):
    s.send(bytes(str(string) + ' ' + str(index), 'utf-8'))

s.close()
