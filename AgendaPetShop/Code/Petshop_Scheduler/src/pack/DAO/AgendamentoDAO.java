package pack.DAO;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;

import pack.VO.Agendamento;
import pack.VO.Pet;
import pack.petshop.DBConn;

public class AgendamentoDAO 
{
	private static ArrayList<Agendamento> ReadAgendamentoSet(ResultSet rs)
	{
		ArrayList<Agendamento> Agendamentos = new ArrayList<Agendamento>();
		
		try 
		{
			while (rs.next()) 
			{
				Agendamento agendamento = new Agendamento();
			    agendamento.setId(rs.getInt("id"));
			    agendamento.setPetId(rs.getInt("petId"));
			    agendamento.setData(rs.getDate("data"));
			    agendamento.setHorario(rs.getTime("horario"));
			    agendamento.setServicoId(rs.getInt("servicoId"));
			    agendamento.setFuncionarioId(rs.getInt("funcionarioId"));
			    agendamento.setPacoteId(rs.getInt("pacoteId"));
			    agendamento.setPagamentoId(rs.getInt("pagamentoId"));
			    agendamento.setCancelado(rs.getBoolean("cancelado"));
			    Agendamentos.add(agendamento);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Agendamentos;
	}
	
	public static int Create(int petId, Date data, Time horario, int servicoId, int funcionarioId, int pacoteId)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		int returnValue = -1;
		try 
		{
			cstmt = conn.prepareCall("{? = call sp_Agendamento_Create(?, ?, ?, ?, ?, ?)}");
			cstmt.setInt ("petId", petId);
            cstmt.setDate ("data", data);
            cstmt.setTime ("horario", horario);
            cstmt.setInt ("servicoId", servicoId);
            cstmt.setInt ("funcionarioId", funcionarioId);
            cstmt.setInt ("pacoteId", pacoteId);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.execute();
            returnValue = cstmt.getInt(1);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				conn.close();
				return returnValue;
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public static ArrayList<Agendamento> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Agendamento_ReadSimple()}");
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                break;
	            } 
	            else 
	            {
	                rowsAffected = cstmt.getUpdateCount();
	            }
	            results = cstmt.getMoreResults();
	        }
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				conn.close();				
		        return ReadAgendamentoSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static ArrayList<Agendamento> ReadAtivo()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<Agendamento> agendamentos = new ArrayList<Agendamento>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Agendamento_ReadAtivo()}");
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                agendamentos = ReadAgendamentoSet(rs);
	                break;
	            } 
	            else 
	            {
	                rowsAffected = cstmt.getUpdateCount();
	            }
	            results = cstmt.getMoreResults();
	        }
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				conn.close();				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return agendamentos;
	}
	
	public static int Update(int id, int petId, Date data, Time horario, int servicoId, int funcionarioId, int pacoteId)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		boolean results = false;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Agendamento_Update(?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setInt ("petId", petId);
            cstmt.setDate ("data", data);
            cstmt.setTime ("horario", horario);
            cstmt.setInt ("servicoId", servicoId);
            cstmt.setInt ("funcionarioId", funcionarioId);
            cstmt.setInt ("pacoteId", pacoteId);
            results = cstmt.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				conn.close();
				if(results)
					return 1;
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public static int Delete(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		boolean results = false;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Agendamento_Delete(?)}");
			cstmt.setInt("id", id);
            results = cstmt.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				conn.close();
				if(results)
					return 1;
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public static int Desmarcar(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		boolean results = false;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Agendamento_Desmarcar(?)}");
			cstmt.setInt("id", id);
            results = cstmt.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				conn.close();
				if(results)
					return 1;
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return -1;
	}
}
