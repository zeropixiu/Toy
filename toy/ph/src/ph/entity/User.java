package ph.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int id;//����
	private String role;
//	role��ֵΪadmin��ʾϵͳ����Ա��role��ֵΪcustomer��ʾ�ͻ�
	private String name;//�������˻���
	private String pwd;//����
	private String tel;//�ͻ��绰
	private String address;//�ͻ���ַ
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
