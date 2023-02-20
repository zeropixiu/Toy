package ph.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import ph.entity.Speciality;
import ph.utils.DBUtils;

public class SpecialityDao {
	public List<Speciality> getAll() throws Exception{
		List<Speciality> specs =new ArrayList<Speciality>();
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from t_speciality";
			pstmt=(PreparedStatement) connection.prepareStatement(sql);
			rs= (ResultSet) pstmt.executeQuery();
			while (rs.next()) {
				Speciality s =new Speciality();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				specs.add(s);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常："+e);
		}finally {
			DBUtils.release(rs, pstmt, connection);
		}
		return specs;
		
	}

}
