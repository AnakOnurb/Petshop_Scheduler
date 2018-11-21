package pack.VO;

public class Funcionario 
{
	private int id;
	private String cpf;
	private String nome;
	private String endereco;
	private String telefone;
	private String celular;
	private String observacoes;
	private double salario;

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
   
    public String getCpf() { return this.cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEndereco() { return this.endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public String getTelefone() { return this.telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getCelular() { return this.celular; }
    public void setCelular(String celular) { this.celular = celular; }
    
    public String getObservacoes() { return this.observacoes; }
    public void setObservacoes(String obs) { this.observacoes = obs; }
    
    public double getSalario() { return this.salario; }
    public void setSalario(double salario) { this.salario = salario; }
	
}
