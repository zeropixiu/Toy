package ph.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import ph.entity.Speciality;
import ph.entity.Vet;
import ph.utils.DBUtils;


public class VetDao {
	public List<Vet>search(String vetName,String specName)throws Exception{
//	链接DB
//	SQL语句，根据name查vet
//	执行得结果
//	关闭DB
		Connection connection = DBUtils.getConnection();
		List<Vet>vets = new ArrayList<Vet>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT distinct v.* "
				+ "FROM t_vet as v "
				+ "JOIN t_vet_speciality as vs ON v.id=vs.vetId "
				+ "JOIN t_speciality as s ON s.id=vs.specId "
				+ "WHERE v.name LIKE ? AND s.name like ?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%"+vetName+"%" );
			pstmt.setString(2, "%"+specName+"%" );
			rs =  pstmt.executeQuery();
			while(rs.next()) {
				Vet v = new Vet();
				v.setId(rs.getInt("id"));
				v.setName(rs.getString("name"));
				vets.add(v);
			}
			for(Vet v:vets) {
				rs = pstmt.executeQuery("SELECT s.* FROM t_vet_speciality as vs "
						+ "JOIN t_speciality as s ON s.id=vs.specId "
						+ "JOIN t_vet as v ON v.id = vs.vetId "
						+ "WHERE v.id="+v.getId());
				while(rs.next()){
					Speciality spec = new Speciality();
					spec.setId(rs.getInt("id"));
					spec.setName(rs.getString("name"));
					v.getSpecs().add(spec);
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.release(rs, pstmt, connection);
		}
		
		return vets;
	}

	public void save(Vet vet) throws Exception {
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection.setAutoCommit(false);
			String sql = "insert into t_vet value(null,?)";
			pstmt =  connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, vet.getName());
			pstmt.executeUpdate();
			rs= pstmt.getGeneratedKeys();
			if(rs.next()) {
				vet.setId(rs.getInt(1));
			}
			sql = "insert into t_vet_speciality values";
//			判断是否为第一条
			boolean first = true;
			for(Speciality spec : vet.getSpecs()) {
				if(first) {
					sql += "("+vet.getId()+","+spec.getId()+")";
					first=false;
				}else {
					sql += ",("+vet.getId()+","+spec.getId()+")";
					
				}
			}
			pstmt.executeUpdate(sql);
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if(connection != null)
				connection.rollback();
			throw new Exception("数据库访问异常"+e);
		}finally {
			DBUtils.release(rs, pstmt, connection);
		}
	}
	
//	查询所有玩具修理师傅
	public List<Vet> getAll() throws Exception {
		List<Vet> vets = new ArrayList<Vet>();
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt=connection.prepareStatement("select * from t_vet");
			rs=pstmt.executeQuery();
			while (rs.next()) {
				Vet vet=new Vet();
				vet.setId(rs.getInt("id"));
				vet.setName(rs.getString("name"));
				vets.add(vet);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问异常"+e);
		}finally {
			DBUtils.release(rs, pstmt, connection);
		}
		return vets;
	}

}
