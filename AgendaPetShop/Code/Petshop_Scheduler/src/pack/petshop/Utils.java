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
import pack.VO.Funcionario;
import pack.VO.Pelagem;
import pack.VO.Pet;
import pack.VO.Porte;
import pack.VO.Raca;
import pack.VO.Servico;
import pack.VO.TipoPagamento;

public class Utils 
{
	public static ArrayList<Raca> racas = new ArrayList<Raca>();
	public static ArrayList<Especie> especies = new ArrayList<Especie>();
	public static ArrayList<Dono> donos = new ArrayList<Dono>();
	public static ArrayList<Pet> pets = new ArrayList<Pet>();
	public static ArrayList<Porte> portes = new ArrayList<Porte>();
	public static ArrayList<Pelagem> pelagens = new ArrayList<Pelagem>();
	public static ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
	public static ArrayList<Servico> servicos = new ArrayList<Servico>();
	public static ArrayList<TipoPagamento> pagamentos = new ArrayList<TipoPagamento>();
	
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

	public static Dono ReadDono(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<Dono> donos = new ArrayList<Dono>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Dono_Read(?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("cpf", "");
			cstmt.setString ("nome", "");
			cstmt.setInt ("petId", -1);
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
		return donos.get(0);
	}
	
	public static void ReadPet()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pet_ReadSimple()}");
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
		            while (rs.next()) 
					{
					    Pet pet = new Pet();
					    pet.setId(rs.getInt("id"));
					    pet.setNome(rs.getString("nome"));
					    pets.add(pet);
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
	
	public static Pet ReadPet(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<Pet> pets = new ArrayList<Pet>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pet_Read(?, ?, ?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("nome", "");
			cstmt.setInt ("especieId", -1);
			cstmt.setInt ("racaId", -1);
			cstmt.setInt ("porteId", -1);
			cstmt.setInt ("donoId", -1);
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                while (rs.next()) 
	    			{
	    			    Pet pet = new Pet();
	    			    pet.setId(rs.getInt("id"));
	    			    pet.setNome(rs.getString("nome"));
	    			    pets.add(pet);
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
		return pets.get(0);
	}

	public static void ReadFuncionario()
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
		            while (rs.next()) 
					{
					    Funcionario func = new Funcionario();
					    func.setId(rs.getInt("id"));
					    func.setNome(rs.getString("nome"));
					    funcionarios.add(func);
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

	public static Funcionario ReadFuncionario(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Funcionario_Read(?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("cpf", "");
			cstmt.setString ("nome", "");
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                while (rs.next()) 
	    			{
	    			    Funcionario func = new Funcionario();
	    			    func.setId(rs.getInt("id"));
	    			    func.setNome(rs.getString("nome"));
	    			    funcionarios.add(func);
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
		return funcionarios.get(0);
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

	public static void ReadServico()
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
		            while (rs.next()) 
					{
					    Servico servico = new Servico();
					    servico.setId(rs.getInt("id"));
					    servico.setDescricao(rs.getString("descricao"));
					    servicos.add(servico);
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

	public static Servico ReadServico(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<Servico> servicos = new ArrayList<Servico>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Servico_Read(?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("descricao", "");
			cstmt.setDouble ("preco", -1);
			cstmt.setInt ("duracao", -1);
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                while (rs.next()) 
	    			{
	    			    Servico servico = new Servico();
	    			    servico.setId(rs.getInt("id"));
	    			    servico.setDescricao(rs.getString("descricao"));
	    			    servicos.add(servico);
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
		return servicos.get(0);
	}

	public static void ReadTipoPagamento()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_TipoPagamento_ReadSimple()}");
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
		            while (rs.next()) 
					{
					    TipoPagamento pgto = new TipoPagamento();
					    pgto.setId(rs.getInt("id"));
					    pgto.setDescricao((rs.getString("descricao")));
					    pagamentos.add(pgto);
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

	public static TipoPagamento ReadTipoPagamento(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<TipoPagamento> pagamentos = new ArrayList<TipoPagamento>();
		try 
		{
			cstmt = conn.prepareCall("{call sp_Dono_Read(?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setString ("cpf", "");
			cstmt.setString ("nome", "");
			cstmt.setInt ("petId", -1);
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                while (rs.next()) 
	    			{
	                	TipoPagamento pagto = new TipoPagamento();
	                	pagto.setId(rs.getInt("id"));
	                	pagto.setDescricao(rs.getString("descricao"));
	    			    pagamentos.add(pagto);
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
		return pagamentos.get(0);
	}
}
