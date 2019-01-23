import socket

# Create the connection
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(('localhost', 4184))

# Start being a subscriber to `test`
s.send(b'-test\n')
# Get some data
try:
    while True:
        print(s.recv(1024).decode('utf-8'))
        print('sending ack')
        s.send(b'.')
except KeyboardInterrupt:
    s.close()

