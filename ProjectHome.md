**Description**


Design and develop a set of Java, C, C++, or C# program modules to implement the following distributed system. In this system, there is a wired sub-network of regular PCs and a wireless sub-network of laptops with wireless network cards. The nodes in the two sub-networks communicate through a CS-Router (within the CS Department) and a Server/Router, S. The Server/Router is an ordinary PC in the CS-Lab. The Server/Router S code also implements a routing table which you will build as a part of the simulation.


Suppose that a node M is to send a request or message to a node N (either in the same sub-network or in the other sub-network), consider the following scenario to help you conceptualize the problem and its solution:


_**1.Node M sends a request to the CS-Router**_

_**2.CS-Router directs the request to the Server/Router S**_

_**3.Server/Router S's routing table is looked-up to find the destination address of the node N. (The routing table contains the IP addresses, port #s and interface-link numbers.)**_

_**4.Once N's address and port number are found in the table, the request or message is forwarded to it through the CS-Router. A reply from N follows the reverse path.**_


In this distributed system each computer node is perceived as a client as well as a server. In the following diagram, the nodes have pre-assigned port numbers xxxx (e.g., 9999) and the interfaces of the nodes are numbered to facilitate the mapping. You need to determine the corresponding IP-address using finger, nslookup, or ipconfig, or similar protocol for the clusters of PCs you select in the CS-Lab in order to generate the needed sockets in your program modules. Recall that a socket is a concatenated IP:port# such as IP:xxxx (e.g. 192.168.0.23:4444).


The subnet of wireless-enabled laptops will communicate to the CS-Router via Linksys-type base station (or access point – AP). The AP will be provided for the project by the CS Department and the laptops will be student-owned systems, which will all be configured via DHCP (Dynamic Host Configuration Protocol) addressing method. The subnet of wired PCs will be regular PCs in the CS-lab, including the AP, which will be plugged into the wall to connect to the CS-Router. You may increase the number of PCs and laptops in the subnets.


![http://javanetworksimulator.googlecode.com/files/Figure1.gif](http://javanetworksimulator.googlecode.com/files/Figure1.gif)



Figure 1: A conceptual network of two subnets (a wired and a wireless)


Use any of the following Java’s facilities or API for your network programming and communication. (You may also use a C, C++ or C# environment or IDE for the network programming.)


> Socket class for TCP protocol or
> DatagramSocket class for UDP protocol
> Remote object ‘Remote’ Interface
> java.io.Serializable Interface
> java.rmi.server, including interfaces for remote method invocation
> RemoteServer class
> UnicastRemoteObject class or MulticastRemoteObject class



**What to Do and Turn In**


Start-up code for the client-side, the server-side, and a skeletal code of the Server/Router-side will be given to your group. These code segments are in Java, but, as indicated above, you have the option of using a different language. Complete the development/revision/rewriting of the requisite algorithms, data structures, and program modules to implement the above scenario.


Test your system with several permutations of input strings/messages. Collect and analyze data for average message sizes, average transmission time, average time for the routing-table lookup, and any other of interest. Write a 5-10 page report of your findings including 1) the algorithms used, 2) data structures used, 3) tables of data collected and statistics, 4) findings/conclusion and 5) literature references. (Note: You will need to generate a large volume of data traffic to achieve significant statistics. You may research any Java, C, C++, or C# environments or literature resources that will be helpful.)