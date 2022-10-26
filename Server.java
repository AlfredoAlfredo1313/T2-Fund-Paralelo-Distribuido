import java.io.OutputStream;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Server {
    static Scanner in = new Scanner(System.in);
    static PrintWriter print;
    static OutputStream out;
    static Registry reg;
    static ProcessoAdministracao process;
    
    public Server(boolean DEBUG)
    {
        try {
            //Definicao do ip onde o servico ira funcionar
            System.setProperty("java.rmi.server.hostname", "localhost");
            //Registro do servico em uma porta
            reg = LocateRegistry.createRegistry(1099);
            //Cria o objeto que implementa os metodos que serao servidos
            process = new AdminImp(DEBUG);
            //Coloca na porta registrada o servico da calculadora
            Naming.bind("AdminServer", (Remote) process);
            System.out.println("Conexao estabelecida.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(args.length > 0);
    }
}
