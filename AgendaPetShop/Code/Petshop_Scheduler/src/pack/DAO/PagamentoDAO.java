package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.sql.Date;

import pack.VO.Pagamento;
import pack.petshop.DBConn;

public class PagamentoDAO 
{
	private static ArrayList<Pagamento> ReadPagamentoSet(ResultSet rs)
	{
		ArrayList<Pagamento> Pagamentos = new ArrayList<Pagamento>();
		
		try 
		{
			while (rs.next()) 
			{
				Pagamento pagamento = new Pagamento();
				pagamento.setId(rs.getInt("id"));
				pagamento.setData(rs.getDate("data"));
				pagamento.setTipoId(rs.getInt("tipoId"));
			    Pagamentos.add(pagamento);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Pagamentos;
	}
	
	public static int Create(Date data, int tipoId)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		int returnValue = -1;
		try 
		{
			cstmt = conn.prepareCall("{? = call sp_Pagamento_Create(?, ?)}");
            cstmt.setDate ("data", data);
            cstmt.setInt ("tipoId", tipoId);
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
	
	public static ArrayList<Pagamento> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pagamento_ReadSimple()}");
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
		        return ReadPagamentoSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
