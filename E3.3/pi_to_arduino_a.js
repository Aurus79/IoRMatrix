var SerialPort = require('serialport');
var port = new SerialPort('/dev/ttyAMA0', {baudRate: 9600});
port.on("open", () => {
    console.log('serial port open');
    port.write('a', (err) => {
        if(err) {
            console.log(err);
        }
        else {
            console.log('message ecrit');
        }
    });
});