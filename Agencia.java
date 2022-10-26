import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.Scanner;

public class Agencia{
    public static void main(String[] args) throws MalformedURLException, NotBoundException {
        ProcessoAdministracao process = null;
        Scanner in = new Scanner(System.in);
        Random rand = new Random();
        String numero = "";
        String senha = "";
        double d = 0;
        boolean exec = true;
        int input = -1;
        int codigo = -1;
        int temp = -1;
        while(exec)
        {
            try {
                process = (ProcessoAdministracao) Naming.lookup("rmi://localhost:1099/AdminServer");
                System.out.printf("\n0 - Autenticar Conta\n1 - Abrir Conta\n2 - Sair\n");
                if(!numero.equals("")) System.out.printf("-----OPERACOES SOBRE A CONTA %s------\n3 - Extrair Saldo\n4 - Depositar Saldo\n5 - Consultar Saldo\n6 - Fechar Conta\n", numero);
                input = in.nextInt();
                while(temp == codigo) temp = rand.nextInt(1000);
                codigo = temp;

                switch(input) {
                    //Autentifica Conta
                    case 0:
                        while(true)
                        {
                            System.out.println("Insira o Numero de sua Conta OU Insira v para voltar");  
                            numero = in.next();
                            if(numero.equals("v")) 
                            {
                                numero = "";
                                break;
                            }
                            System.out.println("Insira a Senha de sua Conta");
                            senha = in.next();
                            if(!process.autenticaConta(numero, senha)) 
                            {
                                System.out.println("Senha ou Numero incorreto");
                                continue;
                            }
                            System.out.printf("\nConta %s Autenticada.", numero);
                            break;
                        }
                        break;

                    //Abre Conta
                    case 1:
                        while(true)
                        {
                            System.out.println("Insira uma sequencia numerica para identificar a sua conta OU insira v para voltar");
                            numero = in.next();
                            if(numero.equals("v")) 
                            break;
                            if(!process.isNameUsed(numero)) 
                            {
                                break;
                            }
                            System.out.println("Ja ha uma conta com este indentificador");
                        }
                        if(numero.equals("v")) 
                        {
                            numero = "";
                            break;
                        }
                        System.out.println("Insira a Senha de sua Conta");
                        senha = in.next();
                        System.out.println(process.abreConta(numero, senha, codigo));
                        System.out.printf("\nConta %s Criada.", numero);
                        break;

                    //Sair
                    case 2:
                        exec = false;
                    break;
                    
                    //Extração
                    case 3:
                    if(numero.equals("")) {
                        System.out.println("Comando nao reconhecido");
                        break;
                    }
                    System.out.println("Insira o valor que desejas extrair");
                    d = in.nextDouble();
                    d = Math.abs(d) * -1;
                    process.manipulaConta(numero, d, codigo);
                    break;
                    //Deposito
                    case 4:
                    if(numero.equals("")) {
                        System.out.println("Comando nao reconhecido");
                        break;
                    }
                    System.out.println("Insira o valor que desejas depositar");
                    d = in.nextDouble();
                    d = Math.abs(d);
                    process.manipulaConta(numero, d, codigo);
                    break;

                    //Consulta
                    case 5:
                    if(numero.equals("")) {
                        System.out.println("Comando nao reconhecido");
                        break;
                    }
                        System.out.printf("\nSALDO DE %s: %.2f REAIS", numero, process.consulta(numero));
                        break;

                    //Fechar conta
                    case 6:
                    if(numero.equals("")) {
                        System.out.println("Comando nao reconhecido");
                        break;
                    }
                        process.fechaConta(numero, codigo);
                        numero = "";
                        senha = "";
                        break;

                    default:
                        System.out.println("Comando nao reconhecido");
                        break;
                }
            } catch(RemoteException e)
            {
                int attempts = 0;
                System.out.printf("\nOcorreu um erro servidor durante a execucao da operacao\nTentaremos mais uma vez");
                switch(input)
                {
                    case 1:
                        while(attempts < 3){
                            try {
                                process.abreConta(numero, senha, codigo);
                            } catch (RemoteException e1) {
                                attempts++;
                                continue;
                            }
                            break;
                        }
                    break;

                    case 3:
                    case 4:
                    while(attempts < 3){
                        try {
                            process.manipulaConta(numero, d, codigo);
                        } catch (RemoteException e1) {
                            attempts++;
                            continue;
                        }
                        break;
                    }
                    break;

                    case 6:
                    while(attempts < 3){
                        try {
                            process.fechaConta(numero, codigo);
                        } catch (RemoteException e1) {
                            attempts++;
                            continue;
                        }
                        break;
                    }
                    break;

                    default:
                }

                if(attempts == 3)
                { 
                    System.out.printf("\nA OPERACAO FALHOU\n");
                    e.printStackTrace();
                    System.exit(0);
                }
                System.out.printf("\nOPERACAO REALIZADA COM SUCESSO\n");
            }
        }
    }   
}