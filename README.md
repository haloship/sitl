# sitl
Repository to hold software-in-the-loop files for the rocket avionics

### Directory
1. [Overview](#overview)
1. [Working with socat](#Working-with-socat)
1. [Compiling/Usage](#Compilation-and-Usage)

## Overview

The OpenRocket extension is meant to provide the simulation environment for your flight software. While OpenRocket provides several useful events and values, this extension makes use of the following simple outputs:

* acceleration

(near-future)
* velocity
* world coordinates
* rotational coordinates

These values are provided over a serial port (virtual ports included). Commands can be sent back to the simulation to carry out events (chute deployment, airbrake deployment, fin movement). As a result, your flight software controls the actuation of the rocket in the OpenRocket simulation, leading to software-in-the-loop testing.

# Usage
Coming soon!

# Development
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

## Compilation and Usage

1. Change directory to ```orksim```
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
