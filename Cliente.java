import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.Scanner;

public class Cliente {
     
    public static void main(String[] args) {
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
            try
            {
                process = (ProcessoAdministracao) Naming.lookup("rmi://localhost:1099/AdminServer");
                System.out.printf("\n0 - Autenticar Conta\n1 - Sair\n");
                if(!numero.equals("")) System.out.printf("-----OPERACOES SOBRE A CONTA %s------\n2 - Extrair Saldo\n3 - Depositar Saldo\n4 - Consultar Saldo\n", numero);
                input = in.nextInt();
                codigo = rand.nextInt(1000);
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

                    //Sair
                    case 1:
                        exec = false;
                    break;
                    
                    //Extração
                    case 2:
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
                    case 3:
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
                    case 4:
                    if(numero.equals("")) {
                        System.out.println("Comando nao reconhecido");
                        break;
                    }
                        System.out.printf("\n%.2f REAIS", process.consulta(numero));
                        break;

                    default:
                        System.out.println("Comando nao reconhecido");
                        break;
                }
            } catch(Exception e)
            {
                int attempts = 0;
                switch(input)
                {
                    case 2:
                    case 3:
                    while(attempts < 3){
                        System.out.printf("\nOcorreu um erro servidor durante a execução da operação\nTentaremos mais uma vez");
                        try {
                            process.manipulaConta(numero, d, codigo);
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
                    System.out.printf("\nA OPERACAO FALHOU\nSERVER SERA DESCONECTADO");
                    e.printStackTrace();
                    System.exit(0);
                }
                System.out.printf("\nOPERACAO REALIZADA COM SUCESSO\n");
            }
        }
    }
}
