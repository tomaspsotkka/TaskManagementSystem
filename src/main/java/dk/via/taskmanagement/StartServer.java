package dk.via.taskmanagement;

import dk.via.taskmanagement.server.RemoteConnector;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class StartServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(1099);
        RemoteConnector connector = new RemoteConnector();

        Remote remote = UnicastRemoteObject.exportObject(connector, 0);
        registry.bind("Connector", remote);
        System.out.println("Server started");
    }
}
