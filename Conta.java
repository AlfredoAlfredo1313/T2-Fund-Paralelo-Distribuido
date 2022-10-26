public class Conta {
    private String codigo;
    private double saldo = 0;
    private int numCompra = -1;

    public Conta(String codigo)
    {
        this.codigo = codigo;
    }

    public void manipula(double valor)
    {
        saldo += valor;
    }

    public double getSaldo()
    {
        return saldo;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setNumCompra(int numCompra)
    {
        this.numCompra = numCompra;
    }

    public int getNumCompra()
    {
        return numCompra;
    }
}
