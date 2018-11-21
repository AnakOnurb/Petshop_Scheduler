package pack.VO;

import java.util.Date;

public class Pagamento 
{
	private int id;
	private Date data;
	private int tipoId;
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	   
	public Date getData() { return this.data; }
	public void setData(Date data) { this.data = data; }
    
    public int getTipoId() { return this.tipoId; }
	public void setTipoId(int tipoId) { this.tipoId = tipoId; }
}
