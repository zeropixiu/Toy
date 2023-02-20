package ph.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ph.entity.Visit;
import ph.utils.DBUtils;

public class VisitDao {
	public void save(Visit visit)throws Exception {
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement("insert into t_visit value(null,?,?,CURDATE(),?,?)");
			pstmt.setInt(1, visit.getPetId());
			pstmt.setInt(2, visit.getVetId());
			pstmt.setString(3, visit.getDescription());
			pstmt.setString(4, visit.getTreatment());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常："+ e );
		}finally {
			DBUtils.release( pstmt, connection);
		}
	}
	public List<Visit> getVisitsByPetId(int petId) throws Exception{
		List<Visit> visits = new ArrayList<Visit>();
		Connection connection = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement("select visit.*,vet.name from t_visit as visit inner join t_vet as vet on (visit.vetId=vet.id) where visit.petId=?");
			pstmt.setInt(1, petId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Visit visit = new Visit();
				visit.setId(rs.getInt("id"));
				visit.setPetId(petId);
				visit.setVetId(rs.getInt("vetId"));
				visit.setVisitdate(rs.getString("visitdate"));
				visit.setDescription(rs.getString("description"));
				visit.setTreatment(rs.getString("treatment"));
				visit.setVetname(rs.getString("name"));
				visits.add(visit);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库访问出现异常："+ e );
		}finally {
			DBUtils.release( rs,pstmt, connection);
		}
		return visits;
		
	}
}
