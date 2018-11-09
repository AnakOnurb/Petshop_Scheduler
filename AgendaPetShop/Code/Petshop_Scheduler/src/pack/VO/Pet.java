package pack.VO;

public class Pet 
{
	private int id;
	private String nome;
	private String idade;
	private int racaId;
	private int pelagemId;
	private int porteId;
	private String observacoes;

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getIdade() { return this.idade; }
    public void setIdade(String idade) { this.idade = idade; }
    
    public int getRacaId() { return this.racaId; }
    public void setRacaId(int id) { this.racaId = id; }
    
    public int getPelagemId() { return this.pelagemId; }
    public void setPelagemId(int id) { this.pelagemId = id; }
    
    public int getPorteId() { return this.porteId; }
    public void setPorteId(int id) { this.porteId = id; }
    
    public String getObservacoes() { return this.observacoes; }
    public void setObservacoes(String obs) { this.observacoes = obs; }
}
