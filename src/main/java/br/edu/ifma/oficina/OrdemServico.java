package br.edu.ifma.oficina;

public class OrdemServico {

    private static int proximoNumero = 1;

    private final int numero;
    private String cliente;
    private String veiculo;
    private String servico;
    private double valor;

    public OrdemServico(String cliente, String veiculo, String servico, double valor) {
        this.numero = proximoNumero;
        proximoNumero++;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.servico = servico;
        this.valor = valor;
    }

    public int getNumero() {
        return numero;
    }

    public String getCliente() {
        return cliente;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public String getServico() {
        return servico;
    }

    public double getValor() {
        return valor;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}