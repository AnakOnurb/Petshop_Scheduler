package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import pack.VO.Servico;
import pack.petshop.DBConn;

public class ServicoDAO 
{
	private static ArrayList<Servico> ReadServicoSet(ResultSet rs)
	{
		ArrayList<Servico> Servicos = new ArrayList<Servico>();
		
		try 
		{
			while (rs.next()) 
			{
				Servico servico = new Servico();
				servico.setId(rs.getInt("id"));
				servico.setDescricao(rs.getString("descricao"));
				servico.setPreco(rs.getDouble("preco"));
				servico.setDuracao(rs.getInt("duracao"));
				Servicos.add(servico);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Servicos;
	}
	
	public static int Create(String descricao, double preco, int duracao)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		int returnValue = -1;
		try 
		{
			cstmt = conn.prepareCall("{? = call sp_Servico_Create(?, ?, ?)}");
			cstmt.setString ("descricao", descricao);
			cstmt.setDouble ("preco", preco);
			cstmt.setInt ("duracao", duracao);
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
	
	public static ArrayList<Servico> Read(int id, String descricao, double preco, int duracao)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Servico_Read(?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("descricao", descricao);
			cstmt.setDouble ("preco", preco);
			cstmt.setInt ("duracao", duracao);
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
				return ReadServicoSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static ArrayList<Servico> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Servico_ReadSimple()}");
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
				return ReadServicoSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static int Update(int id, String descricao, double preco, int duracao)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		boolean results = false;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Servico_Update(?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("descricao", descricao);
			cstmt.setDouble ("preco", preco);
			cstmt.setInt ("duracao", duracao);
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
			cstmt = conn.prepareCall("{call sp_Servico_Delete(?)}");
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
