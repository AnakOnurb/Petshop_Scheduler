package pack.VO;

public class Servico 
{
	private int id;
	private String descricao;
	private double preco;
	private int duracao;
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	   
	public String getDescricao() { return this.descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }
	
	public double getPreco() { return this.preco; }
    public void setPreco(double preco) { this.preco = preco; }
    
    public int getDuracao() { return this.duracao; }
	public void setDuracao(int duracao) { this.duracao = duracao; }
	
	@Override
	public String toString() 
	{
	    return this.getDescricao();
	}
}
