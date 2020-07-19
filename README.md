# sitl
Repository to hold software-in-the-loop files for the rocket avionics

## Working with ```socat```

### Install
```
sudo apt install socat
```

### Start virtual ports
```
sudo socat PTY,link=/dev/ttyp1 PTY,link=/dev/ttyp2
 ```
