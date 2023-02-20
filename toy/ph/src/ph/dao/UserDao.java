package ph.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ph.entity.Pet;
import ph.entity.User;
import ph.utils.DBUtils;

public class UserDao {
	public User getByName(String name) throws Exception {
		User user = null;
//		����DB
//		SQL��䣬����name��User
//		ִ�еý��
//		�ر�DB
		Connection connection = DBUtils.getConnection();
		String sql = "select * from t_user where name=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt =  connection.prepareStatement(sql);
			pstmt.setString(1, name);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setRole(rs.getString("role"));
				user.setName(rs.getString("name"));
				user.setPwd(rs.getString("pwd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
				DBUtils.release(rs, pstmt, connection);
		}
		return user;
	}
	
	
	public List<User> searchCustomer(String customerName) throws Exception{
		List<User> customers = new ArrayList<User>();
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM t_user AS u where u.name LIKE ? AND u.role = 'customer'";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%"+ customerName+ "%");
			rs= pstmt.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setRole(rs.getString("role"));
				user.setName(rs.getString("name"));
				user.setPwd(rs.getString("pwd"));
				user.setTel(rs.getString("tel"));
				user.setAddress(rs.getString("address"));
				customers.add(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("���ݿ���ʳ����쳣��"+ e);
		}finally {
			DBUtils.release(rs, pstmt, connection);
	}
		return customers;
		
	}


	public User getById(int id)throws Exception {
		User customer = null;
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement("SELECT * FROM t_user WHERE id= ? ");
			pstmt.setInt(1, id);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				customer = new User();
				customer.setId(rs.getInt("id"));
				customer.setRole(rs.getString("role"));
				customer.setName(rs.getString("name"));
				customer.setPwd(rs.getString("pwd"));
				customer.setTel(rs.getString("tel"));
				customer.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("���ݿ���ʳ����쳣��"+ e );
		}finally {
			DBUtils.release(rs, pstmt, connection);
	}
		return customer;
	}
//	��ӿͻ�
	public void save(User customer)throws Exception {
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO t_user VALUES(null,?,?,?,?,?)";
//			id�����ݿ��Զ�����
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  customer.getRole());
			pstmt.setString(2,  customer.getName());
			pstmt.setString(3,  customer.getPwd());
			pstmt.setString(4,  customer.getTel());
			pstmt.setString(5,  customer.getAddress());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("���ݿ���ʳ����쳣��"+ e);
		}finally {
			DBUtils.release(pstmt, connection);
		}
	}
	
	public void modify(User customer)throws Exception {
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement("update t_user set tel=?,address=? where id=? ");
			pstmt.setString(1, customer.getTel());
			pstmt.setString(2, customer.getAddress());
			pstmt.setInt(3, customer.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("���ݿ���ʳ����쳣��"+ e);
		}finally {
			DBUtils.release(pstmt, connection);
		}
	}
	
//	ɾ���ͻ�(����ɾ�������)
	public void delete(int cid) throws Exception{
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		try {
			connection.setAutoCommit(false);
			List<Pet> pets = new PetDao().getPetsByOwnerId(cid);
			if(pets.size()>0) {
				for(Pet pet:pets) {
					String sql = "delete from t_visit where petId=" + pet.getId();
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();//ɾ������������
					sql = "delete from t_pet where id=" + pet.getId();
					pstmt.executeUpdate(sql);//ɾ�����
					
				}
				pstmt.close();
			}
			pstmt = connection.prepareStatement("delete from t_user where id=" + cid);
			pstmt.executeUpdate();//ɾ���ͻ�
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(connection != null) {
				connection.rollback();
			}
			throw new Exception("���ݿ���ʳ����쳣��"+ e);
		}finally {
			DBUtils.release(pstmt, connection);
		}
	}
}