package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import pack.VO.Pet;
import pack.petshop.DBConn;

public class PetDAO 
{
	public static void Create()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		try 
		{
			cstmt = conn.prepareCall("{? = call sp_Pet_Create(?, ?, ?, ?, ?, ?)}");
			cstmt.setString (2, "Thor");
            cstmt.setString (3, "4 anos");
            cstmt.setInt (4, 2);
            cstmt.setInt (5, 2);
            cstmt.setInt (6, 2);
            cstmt.setString (7, "coment");
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.execute();
            int returnValue = cstmt.getInt(1);
            System.out.println(returnValue);
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
	}
	
	public static void Read()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pet_Read(?, ?, ?, ?, ?)}");
			cstmt.setInt ("id", 14);
			cstmt.setString("nome", null);
			cstmt.setInt("racaId", -1);
			cstmt.setInt("porteId", -1);
			cstmt.setInt("donoId", -1);
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        ResultSet rs = null;
	        while (results || rowsAffected != -1) {
	            if (results) {
	                rs = cstmt.getResultSet();
	                break;
	            } else {
	                rowsAffected = cstmt.getUpdateCount();
	            }
	            results = cstmt.getMoreResults();
	        }
	 
	        while (rs.next()) {
	            Pet pet = new Pet();
	            pet.setId(rs.getInt("id"));
	            pet.setIdade(rs.getString("idade"));
	            pet.setNome(rs.getString("nome"));
	            pet.setObservacoes(rs.getString("observacoes"));
	            pet.setPelagemId(rs.getInt("pelagemId"));
	            pet.setPorteId(rs.getInt("porteId"));
	            pet.setRacaId(rs.getInt("racaId"));
	            System.out.println(pet.getId() + pet.getNome() + pet.getObservacoes());
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
	}
}
