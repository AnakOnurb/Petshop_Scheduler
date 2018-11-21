package pack.VO;

public class Pacote 
{
	private int id;
	private int petId;
	private int tipo;
	private int funcionarioId;
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	   
	public int getPetId() { return this.petId; }
	public void setPetId(int petId) { this.petId = petId; }
	
	public int getTipo() { return this.tipo; }
	public void setTipo(int tipo) { this.tipo = tipo; }
	
	public int getFuncionarioId() { return this.funcionarioId; }
	public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }
}
