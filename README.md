# sitl
Repository to hold software-in-the-loop files for the rocket avionics

## Working with ```socat```

### Install
```
sudo apt install socat
```

### Start virtual ports
```
sudo socat PTY,link=/dev/ttyUSB98 PTY,link=/dev/ttyUSB99

 ```
### Elevate user privilege to operate ports
```
sudo chmod a+rw /dev/ttyUSB98 && sudo chmod a+rw /dev/ttyUSB99
```
