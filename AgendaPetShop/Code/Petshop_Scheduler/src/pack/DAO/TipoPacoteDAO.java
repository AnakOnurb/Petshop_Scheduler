package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import pack.VO.Servico;
import pack.VO.TipoPacote;
import pack.petshop.DBConn;

public class TipoPacoteDAO 
{
	private static ArrayList<TipoPacote> ReadTipoPacoteSet(ResultSet rs)
	{
		ArrayList<TipoPacote> TipoPacotes = new ArrayList<TipoPacote>();
		
		try 
		{
			while (rs.next()) 
			{
				TipoPacote tipoPacote = new TipoPacote();
				tipoPacote.setId(rs.getInt("id"));
				tipoPacote.setDescricao(rs.getString("descricao"));
				TipoPacotes.add(tipoPacote);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return TipoPacotes;
	}
	
	public static int Create(String descricao)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		int returnValue = -1;
		try 
		{
			cstmt = conn.prepareCall("{? = call sp_TipoPacote_Create(?)}");
			cstmt.setString ("descricao", descricao);
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
	
	public static ArrayList<TipoPacote> Read(int id, String descricao)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ArrayList<TipoPacote> pacotes = new ArrayList<TipoPacote>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_TipoPacote_Read(?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("descricao", descricao);
			boolean results = cstmt.execute();
			int rowsAffected = 0;
	 
			while (results || rowsAffected != -1) 
			{
				if (results) 
				{
					rs = cstmt.getResultSet();
					pacotes = ReadTipoPacoteSet(rs);
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
		return pacotes;
	}
	
	public static ArrayList<TipoPacote> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_TipoPacote_ReadSimple()}");
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
				return ReadTipoPacoteSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static int Update(int id, String descricao)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		boolean results = false;
		try 
		{
			cstmt = conn.prepareCall("{call sp_TipoPacote_Update(?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("descricao", descricao);
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
			cstmt = conn.prepareCall("{call sp_TipoPacote_Delete(?)}");
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
