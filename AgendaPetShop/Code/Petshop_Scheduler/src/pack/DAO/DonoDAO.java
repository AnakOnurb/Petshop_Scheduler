package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import pack.VO.Dono;
import pack.VO.Pet;
import pack.petshop.DBConn;

public class DonoDAO 
{
	private static ArrayList<Dono> ReadDonoSet(ResultSet rs)
	{
		ArrayList<Dono> Donos = new ArrayList<Dono>();
		
		try 
		{
			while (rs.next()) 
			{
				Dono dono = new Dono();
				dono.setId(rs.getInt("id"));
				dono.setCpf(rs.getString("cpf"));
				dono.setNome(rs.getString("nome"));
				dono.setEndereco(rs.getString("endereco"));
				dono.setTelefone(rs.getString("telefone"));
				dono.setCelular(rs.getString("celular"));
				dono.setObservacoes(rs.getString("observacoes"));
				Donos.add(dono);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Donos;
	}
	
	public static int Create(String cpf, String nome, String endereco, String telefone, String celular, String observacoes)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		int returnValue = -1;
		try 
		{
			cstmt = conn.prepareCall("{? = call sp_Dono_Create(?, ?, ?, ?, ?, ?)}");
			cstmt.setString ("cpf", cpf);
			cstmt.setString ("nome", nome);
            cstmt.setString ("endereco", endereco);
            cstmt.setString ("telefone", telefone);
            cstmt.setString ("celular", celular);
            cstmt.setString ("observacoes", observacoes);
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
	
	public static ArrayList<Dono> Read(int id, String cpf, String nome, int petId)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<Dono> donos = new ArrayList<Dono>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Dono_Read(?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("cpf", cpf);
			cstmt.setString ("nome", nome);
			cstmt.setInt ("petId", petId);
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	            	if (results) 
		            {
		                rs = cstmt.getResultSet();
		                donos = ReadDonoSet(rs);
		                break;
		            } 
		            else 
		            {
		                rowsAffected = cstmt.getUpdateCount();
		            }
		            results = cstmt.getMoreResults();
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
		return donos;
	}
	
	public static ArrayList<Dono> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Dono_ReadSimple()}");
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
		        return ReadDonoSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static int Update(int id, String cpf, String nome, String endereco, String telefone, String celular, String observacoes)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		boolean results = false;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Dono_Update(?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("cpf", cpf);
			cstmt.setString ("nome", nome);
            cstmt.setString ("endereco", endereco);
            cstmt.setString ("telefone", telefone);
            cstmt.setString ("celular", celular);
            cstmt.setString ("observacoes", observacoes);
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
			cstmt = conn.prepareCall("{call sp_Dono_Delete(?)}");
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
