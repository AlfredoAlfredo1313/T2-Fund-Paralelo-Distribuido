import java.rmi.*;

public interface ProcessoAdministracao extends Remote{
    public boolean abreConta(String numero, String codigo, int codigoTransferencia) throws RemoteException;
    public boolean manipulaConta(String numero, double valor, int codigoTransferencia) throws RemoteException;
    public boolean fechaConta(String numero, int codigoTransferencia)throws RemoteException;
    public boolean autenticaConta(String numero, String codigo)  throws RemoteException;
    public double consulta(String numero) throws RemoteException;
    public boolean isNameUsed(String numero) throws RemoteException;
}