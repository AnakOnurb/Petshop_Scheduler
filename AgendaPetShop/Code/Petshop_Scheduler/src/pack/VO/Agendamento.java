package pack.VO;

import java.time.LocalTime;
import java.util.Date;

public class Agendamento 
{
	private int id;
	private int petId;
	private Date data;
	private LocalTime horario;
	private int servicoId;
	private int funcionarioId;
	private int pacoteId;
	private int pagamentoId;
	private boolean cancelado;
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public int getPetId() { return this.petId; }
	public void setPetId(int petId) { this.petId = petId; }
	   
	public Date getData() { return this.data; }
	public void setData(Date data) { this.data = data; }
	
	public LocalTime getHorario() { return this.horario; }
	public void setHorario(LocalTime horario) { this.horario = horario; }
    
	public int getServicoId() { return this.servicoId; }
	public void setServicoId(int servicoId) { this.servicoId = servicoId; }
	
	public int getFuncionarioId() { return this.funcionarioId; }
	public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }
	
	public int getPacoteId() { return this.pacoteId; }
	public void setPacoteId(int pacoteId) { this.pacoteId = pacoteId; }
	
	public int getPagamentoId() { return this.pagamentoId; }
	public void setPagamentoId(int pagamentoId) { this.pagamentoId = pagamentoId; }
	
	public boolean getCancelado() { return this.cancelado; }
	public void setCancelado(boolean cancelado) { this.cancelado = cancelado; }
}
