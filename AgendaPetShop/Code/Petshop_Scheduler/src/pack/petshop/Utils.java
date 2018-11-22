package pack.petshop;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import pack.VO.Dono;
import pack.VO.Especie;
import pack.VO.Pelagem;
import pack.VO.Pet;
import pack.VO.Porte;
import pack.VO.Raca;

public class Utils 
{
	public static ArrayList<Raca> racas = new ArrayList<Raca>();
	public static ArrayList<Especie> especies = new ArrayList<Especie>();
	public static ArrayList<Dono> donos = new ArrayList<Dono>();
	public static ArrayList<Porte> portes = new ArrayList<Porte>();
	public static ArrayList<Pelagem> pelagens = new ArrayList<Pelagem>();
	
	private static byte[] Encrypt(String data)
	{
		return Hash(data);
	}
	
	private static byte[] Hash(String data)
	{
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		KeySpec spec = new PBEKeySpec(data.toCharArray(), salt, 65536, 128);
		byte[] hash;
		try 
		{
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
			return hash;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} 
		catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void ReadRacas()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Raca_ReadSimple()}");
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
		            while (rs.next()) 
					{
					    Raca raca = new Raca();
					    raca.setId(rs.getInt("id"));
					    raca.setDescricao(rs.getString("descricao"));
					    racas.add(raca);
					}
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
	}

	public static Raca ReadRacas(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<Raca> racas = new ArrayList<Raca>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Raca_Read(?)}");
			cstmt.setInt ("id", id);
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                while (rs.next()) 
	    			{
	    			    Raca raca = new Raca();
	    			    raca.setId(rs.getInt("id"));
	    			    raca.setDescricao(rs.getString("descricao"));
	    			    racas.add(raca);
	    			}
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
		return racas.get(0);
	}
	
	public static void ReadEspecie()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Especie_ReadSimple()}");
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
		            while (rs.next()) 
					{
					    Especie esp = new Especie();
					    esp.setId(rs.getInt("id"));
					    esp.setDescricao(rs.getString("descricao"));
					    especies.add(esp);
					}
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
	}

	public static Especie ReadEspecie(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<Especie> especies = new ArrayList<Especie>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Especie_Read(?)}");
			cstmt.setInt ("id", id);
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                while (rs.next()) 
	    			{
	    			    Especie esp = new Especie();
	    			    esp.setId(rs.getInt("id"));
	    			    esp.setDescricao(rs.getString("descricao"));
	    			    especies.add(esp);
	    			}
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
		return especies.get(0);
	}
	
	public static void ReadDono()
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
		            while (rs.next()) 
					{
					    Dono dono = new Dono();
					    dono.setId(rs.getInt("id"));
					    dono.setNome(rs.getString("nome"));
					    donos.add(dono);
					}
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
	}

	public static void ReadPorte()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Porte_ReadSimple()}");
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
		            while (rs.next()) 
					{
					    Porte porte = new Porte();
					    porte.setId(rs.getInt("id"));
					    porte.setDescricao(rs.getString("descricao"));
					    portes.add(porte);
					}
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
	}

	public static Porte ReadPorte(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<Porte> portes = new ArrayList<Porte>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Porte_Read(?)}");
			cstmt.setInt ("id", id);
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                while (rs.next()) 
	    			{
	    			    Porte porte = new Porte();
	    			    porte.setId(rs.getInt("id"));
	    			    porte.setDescricao(rs.getString("descricao"));
	    			    portes.add(porte);
	    			}
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
		return portes.get(0);
	}
	
	public static void ReadPelagem()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pelagem_ReadSimple()}");
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
		            while (rs.next()) 
					{
					    Pelagem pel = new Pelagem();
					    pel.setId(rs.getInt("id"));
					    pel.setDescricao(rs.getString("descricao"));
					    pelagens.add(pel);
					}
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
	}

	public static Pelagem ReadPelagem(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<Pelagem> pelagens = new ArrayList<Pelagem>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pelagem_Read(?)}");
			cstmt.setInt ("id", id);
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                while (rs.next()) 
	    			{
	    			    Pelagem pelagem = new Pelagem();
	    			    pelagem.setId(rs.getInt("id"));
	    			    pelagem.setDescricao(rs.getString("descricao"));
	    			    pelagens.add(pelagem);
	    			}
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
		return pelagens.get(0);
	}
}
