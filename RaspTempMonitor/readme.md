Temperature Monitoring with Raspberry Pi

Architecture
See image RaspTempMonitor Architecture.png

How it work?
1.  Raspberry Pi read temperature from sensor.
2.  Raspberry communicate with PIR Sensor via GPIO Pin 11.
3.  Raspberry send/put data to server.
4.  A application on Android phone read and display data.
5.  When temperature exceed safety threshsold or PIR sensor detected a symbol, local server send a post method to Firebase Server.
6.  Firebase Server send notification to android phones, installed this app.
