package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pack.VO.TipoPagamento;
import pack.petshop.DBConn;

public class TipoPagamentoDAO 
{
	private static ArrayList<TipoPagamento> ReadPelagemSet(ResultSet rs)
	{
		ArrayList<TipoPagamento> TipoPagamentos = new ArrayList<TipoPagamento>();
		
		try 
		{
			while (rs.next()) 
			{
				TipoPagamento tipoPagamento = new TipoPagamento();
				tipoPagamento.setId(rs.getInt("id"));
				tipoPagamento.setDescricao(rs.getString("descricao"));
				TipoPagamentos.add(tipoPagamento);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return TipoPagamentos;
	}
	
	public static ArrayList<TipoPagamento> ReadSimple()
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
		        return ReadPelagemSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
