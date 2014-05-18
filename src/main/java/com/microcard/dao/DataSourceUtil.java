package com.microcard.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceUtil {

	private static DataSource dataSource = null;  
	
	public static DataSource getDataSource() throws NamingException, SQLException{
		if(dataSource == null){
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/microcard");
		}
		return dataSource;
	}
	
	public static Connection getConnection() throws NamingException, SQLException{	
		return DataSourceUtil.getDataSource().getConnection();
	}
	
	public static boolean isConnectedOK(){
		boolean ok = false;
		Connection conn = null;
		Exception ex = null;
		try{
			conn = DataSourceUtil.getDataSource().getConnection();
			if(! conn.isClosed()){
				ok = true;
			}
		} catch(SQLException e){
			ex = e;
		} catch(Exception e2){
			ex = e2;
		}  finally{
			if( conn != null){
				try{
					conn.close();
				} catch(SQLException e){
					if(ex == null){
						ex = e;
					}
				}
			}
		}
		
		if(ex != null){
			throw new RuntimeException(ex);
		}
		return ok;
	}
}
