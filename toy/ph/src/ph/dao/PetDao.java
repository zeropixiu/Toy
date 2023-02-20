package ph.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import ph.entity.Pet;
import ph.utils.DBUtils;

public class PetDao {
	public List<Pet> getPetsByOwnerId(int ownerId) throws Exception{
		List<Pet> pets = new ArrayList<Pet>();
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement("SELECT * FROM t_pet WHERE ownerId=?");
			pstmt.setInt(1, ownerId);
			rs= pstmt.executeQuery();
			while(rs.next()) {
				Pet pet = new Pet();
				pet.setId(rs.getInt("id"));
				pet.setName(rs.getString("name"));
				pet.setOwnerId(ownerId);
				pet.setPhoto(rs.getString("photo"));
				pets.add(pet);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常："+ e );
		}finally {
			DBUtils.release(rs, pstmt, connection);
		}
		return pets;
		
	}
	public void delete(int petId)throws Exception {
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		try {
//			先删除玩具关联的修理情况，再删除玩具
			connection.setAutoCommit(false);
			String sql = "DELETE FROM t_visit WHERE petId=" + petId;
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			sql="DELETE FROM t_pet WHERE id=" + petId;
			pstmt.executeUpdate(sql);
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (connection !=null) {
				connection.rollback();
			}
			throw new Exception("数据库访问出现异常："+ e );
		}finally {
			DBUtils.release( pstmt, connection);
		}
	}
//	添加新玩具
	public void save(Pet pet) throws Exception {
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt=connection.prepareStatement("INSERT INTO t_pet VALUES(null,?,?,?,?)");
			pstmt.setString(1, pet.getName());
			pstmt.setString(2, pet.getBirthdate());
			pstmt.setString(3, pet.getPhoto());
			pstmt.setInt(4, pet.getOwnerId());
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常："+ e);
		}finally {
			DBUtils.release(pstmt, connection);
		}
		
	}

}
