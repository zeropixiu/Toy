package ph.utils;

import java.sql.*;

public class DBUtils {

	//������ݿ�����
    public static Connection getConnection() {

        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_ph?useUnicode=true&characterEncoding=utf8",
                    "root", "123456");

        } catch (ClassNotFoundException e) {
            System.out.println("�����쳣��" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("�������쳣��" + e.getMessage());
        }
        return con;
    }

    //��������
//    public static void main(String[] args) {
//
//    	System.out.println(getConnection());
//    }

    //�ͷ����ݿ���Դ����������ɾ����
    public static void release(Statement stmt, Connection connection){
        try{
            if(stmt!=null) stmt.close();
            if(connection!=null) connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //�ͷ����ݿ���Դ�������ڲ�ѯ
    public static void release(ResultSet rs, Statement stmt, Connection connection){
        try{
            if(rs!=null) rs.close();
            release(stmt, connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
