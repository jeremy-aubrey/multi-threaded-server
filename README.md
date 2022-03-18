# Multi-threaded Client-Server Application

A multi-threaded server accepts integer data (2 integers) from a client and returns the following 
statistics to the client:

- Primes (within range of the integers)
- Sum (of primes)
- Mean (of primes)
- Standard Deviation (of primes)

### To Build:
Download the java source code and run the following command in the same directory:
```powershell
javac *.java
```
![Screenshot](docs/images/compile.png)

### Run:
Start the server

```powershell
java Server
```

![Screenshot](docs/images/start-server.png)

Start the client

```powershell
java Client
```

![Screenshot](docs/images/start-client.png)

(both running on localhost for demo purposes)
![Screenshot](docs/images/client-server-connected.png)

### Use:
Upload inventory items and cashier data by providing the path to the source file:

Inventory ([file format](src/Inventory.txt))
![Screenshot](docs/images/uploaded-inventory.png)

Cashiers (Optional) ([file format](src/Cashier.txt))
![Screenshot](docs/images/uploaded-cashiers.png)

#### Menu:
The main menu allows the user to navigate and perform the following actions:

- 1 Select Items
- 2 Show Cash Register
- 3 Clear Cash Register
- 4 Show Inventory
- 5 Check Out
- 6 Quit

![Screenshot](docs/images/menu-options.png)

---

(show available inventory imported from [file](src/Inventory.txt))
![Screenshot](docs/images/show-inventory.png)

---

(purchase an item)
![Screenshot](docs/images/purchase.png)

---

(show items added to register list for purchase)
![Screenshot](docs/images/show-register.png)

---

(check out with current register items) 
![Screenshot](docs/images/checkout.png)

---

Upon checkout a reciept file is generated
![Screenshot](docs/images/reciept.png)