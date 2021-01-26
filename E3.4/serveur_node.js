var SerialPort = require('serialport');
var port = new SerialPort('/dev/ttyAMA0', { baudRate: 9600 });
var server = require('net').createServer((socket) => {
    socket.on('data', (data) => {
        port.write(data.toString(), (err) => {
            if (err) {
                console.log(err);
            }
            else {
                console.log('message ecrit');
                console.log(data.toString());
            }
        });
    });
});
console.log("Script lancé !");

server.listen(8080, () => {
    console.log('Opened server on ', server.address().port);
});