package ph.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int id;//主键
	private String role;
//	role的值为admin表示系统管理员；role的值为customer表示客户
	private String name;//姓名或账户名
	private String pwd;//密码
	private String tel;//客户电话
	private String address;//客户地址
	private List<Pet> pets=new ArrayList<Pet>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Pet> getPets() {
		return pets;
	}
	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
	
	
}
