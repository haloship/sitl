# sitl
Repository to hold software-in-the-loop files for the rocket avionics

## Working with ```socat```

1. Install
```
sudo apt install socat
```

2. Start virtual ports
```
sudo socat PTY,link=/dev/ttyUSB98 PTY,link=/dev/ttyUSB99

 ```
3. Elevate user privilege to operate ports
```
sudo chmod a+rw /dev/ttyUSB98 && sudo chmod a+rw /dev/ttyUSB99
```

## Compiling and using the extension

1. Change directory to ```ork-extension```
```
cd ork-extension
```
2. Compile program
```
ant
```
3. Copy the ```.jar``` file to OpenRocket plug-in
```
cp ./haloship.jar ~/.openrocket/Plugins/haloship.jar
