import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class AdminImp extends UnicastRemoteObject implements ProcessoAdministracao{
    final boolean DEBUG;
    static Scanner in = new Scanner(System.in);
    static Map<String, Conta> contas = new HashMap<>();
    int codigo = -1;

    public AdminImp(boolean DEBUG) throws RemoteException {
        super();
        this.DEBUG = DEBUG;
        System.out.printf("\nMODO DEBUG = %b\n", DEBUG);
    }

    void stall() throws RemoteException
    {
        if(!DEBUG) return;
        System.out.printf("\n----------MODO DEBUG----------\nINSIRA 0 NO TERMINAL PARA LANCAR UMA EXCEPTION\n");
        if(in.nextInt() == 0) throw new RemoteException();
    }

    @Override
    public boolean manipulaConta(String numero, double valor, int codigoTransferencia) throws RemoteException  {
        if(codigoTransferencia == codigo) return false;
        System.out.printf("\nIniciando operacao\n");
        try {
            stall();
            Conta c = contas.get(numero);
        //if(valor == 0) throw new RemoteException();
            c.manipula(valor);
            codigo = codigoTransferencia;
            System.out.printf("\nEncerrando operacao\n");
            stall();
            System.out.println("ENCERRANDO OPERACAO");
            return true;
        } catch (RemoteException e) {
            throw new RemoteException();
        }
    }

    @Override
    public boolean fechaConta(String numero, int codigoTransferencia) throws RemoteException {
        if(codigoTransferencia == codigo) return false;
        System.out.printf("\nIniciando operacao\n");
        try {
            stall();
            contas.remove(numero);
            codigo = codigoTransferencia;
            System.out.printf("\nEncerrando operacao\n");
            stall();
            System.out.println("ENCERRANDO OPERACAO");
            return true;
        } catch (RemoteException e) {
            throw new RemoteException();
        }
    }

    @Override
    public boolean autenticaConta(String numero, String codigo) {
        try{
            Conta c = contas.get(numero);
            return (c.getCodigo().equals(codigo));
        }catch(NullPointerException e) {
            return false;
        }
    }

    @Override
    public boolean abreConta(String numero, String codigo, int codigoTransferencia) throws RemoteException {
        if(codigoTransferencia == this.codigo) return false;
        if(contas.containsKey(numero)) return false;
        System.out.printf("\nIniciando operacao\n");
        try {
            stall();
            Conta C = new Conta(codigo);
            contas.put(numero, C);
            this.codigo = codigoTransferencia;
            System.out.printf("\nEncerrando operacao\n");
            stall();
            System.out.println("ENCERRANDO OPERACAO");
            return true;
        } catch (RemoteException e) {
            throw new RemoteException();
        }
    }

    @Override
    public double consulta(String numero) throws RemoteException {
        return contas.get(numero).getSaldo();
    }

    public boolean isNameUsed(String numero) throws RemoteException {
        return contas.containsKey(numero);
    }
    
}
