package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import pack.VO.Pet;
import pack.petshop.DBConn;

public class PetDAO 
{
	private static ArrayList<Pet> ReadPetSet(ResultSet rs)
	{
		ArrayList<Pet> Pets = new ArrayList<Pet>();
		
		try 
		{
			while (rs.next()) 
			{
			    Pet pet = new Pet();
			    pet.setId(rs.getInt("id"));
			    pet.setIdade(rs.getString("idade"));
			    pet.setNome(rs.getString("nome"));
			    pet.setObservacoes(rs.getString("observacoes"));
			    pet.setEspecieId(rs.getInt("especieId"));
			    pet.setPelagemId(rs.getInt("pelagemId"));
			    pet.setPorteId(rs.getInt("porteId"));
			    pet.setRacaId(rs.getInt("racaId"));
			    Pets.add(pet);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Pets;
	}
	
	public static int Create(String nome, String idade, int especieId, int racaId, int pelagemId, int porteId, String observacoes)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		int returnValue = -1;
		try 
		{
			cstmt = conn.prepareCall("{? = call sp_Pet_Create(?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setString ("nome", nome);
            cstmt.setString ("idade", idade);
            cstmt.setInt ("especieId", especieId);
            cstmt.setInt ("racaId", racaId);
            cstmt.setInt ("pelagemId", pelagemId);
            cstmt.setInt ("porteId", porteId);
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
	
	public static ArrayList<Pet> Read(int id, String nome, int especieId, int racaId, int porteId, int donoId)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pet_Read(?, ?, ?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString("nome", nome);
			cstmt.setInt("especieId", especieId);
			cstmt.setInt("racaId", racaId);
			cstmt.setInt("porteId", porteId);
			cstmt.setInt("donoId", donoId);
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
		        return ReadPetSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static int Update(int id, String nome, String idade, int especieId, int racaId, int pelagemId, int porteId, String observacoes)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		boolean results = false;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pet_Update(?, ?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("nome", nome);
            cstmt.setString ("idade", idade);
            cstmt.setInt ("especieId", especieId);
            cstmt.setInt ("racaId", racaId);
            cstmt.setInt ("pelagemId", pelagemId);
            cstmt.setInt ("porteId", porteId);
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
			cstmt = conn.prepareCall("{call sp_Pet_Create(?, ?, ?, ?, ?, ?, ?)}");
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
