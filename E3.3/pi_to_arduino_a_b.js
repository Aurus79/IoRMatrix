var SerialPort = require('serialport');
var port = new SerialPort('/dev/ttyAMA0', {baudRate: 9600});
var c = 'a';

port.on("open", () => {
    console.log('Port série ouvert');
    setInterval(mafonction, 1000)
});

function mafonction() {
    port.write(c, (err) => {
        if(err) {
            console.log(err);
        }
        else 
        {
            if(c=='a') c='b';
            else c='a';
        }
    });
}