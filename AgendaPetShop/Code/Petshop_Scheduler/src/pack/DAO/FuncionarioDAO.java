package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import pack.VO.Funcionario;
import pack.petshop.DBConn;

public class FuncionarioDAO 
{
	private static ArrayList<Funcionario> ReadFuncionarioSet(ResultSet rs)
	{
		ArrayList<Funcionario> Funcionarios = new ArrayList<Funcionario>();
		
		try 
		{
			while (rs.next()) 
			{
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("id"));
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setEndereco(rs.getString("endereco"));
				funcionario.setTelefone(rs.getString("telefone"));
				funcionario.setCelular(rs.getString("celular"));
				funcionario.setObservacoes(rs.getString("observacoes"));
				funcionario.setSalario(rs.getDouble("salario"));
				Funcionarios.add(funcionario);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Funcionarios;
	}
	
	public static int Create(String cpf, String nome, String endereco, String telefone, String celular, String observacoes, double salario)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		int returnValue = -1;
		try 
		{
			cstmt = conn.prepareCall("{? = call sp_Funcionario_Create(?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setString ("cpf", cpf);
			cstmt.setString ("nome", nome);
            cstmt.setString ("endereco", endereco);
            cstmt.setString ("telefone", telefone);
            cstmt.setString ("celular", celular);
            cstmt.setString ("observacoes", observacoes);
            cstmt.setDouble ("salario", salario);
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
	
	public static ArrayList<Funcionario> Read(int id, String cpf, String nome, String endereco, String telefone, String celular, String observacoes, double salario)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Funcionario_Read(?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("cpf", cpf);
			cstmt.setString ("nome", nome);
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
		        return ReadFuncionarioSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static ArrayList<Funcionario> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Funcionario_ReadSimple()}");
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
		        return ReadFuncionarioSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static int Update(int id, String cpf, String nome, String endereco, String telefone, String celular, String observacoes, double salario)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		boolean results = false;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Funcionario_Update(?, ?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("cpf", cpf);
			cstmt.setString ("nome", nome);
            cstmt.setString ("endereco", endereco);
            cstmt.setString ("telefone", telefone);
            cstmt.setString ("celular", celular);
            cstmt.setString ("observacoes", observacoes);
            cstmt.setDouble ("salario", salario);
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
			cstmt = conn.prepareCall("{call sp_Funcionario_Delete(?)}");
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
