package zhangchx.spring.bean;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = 2L;

	String id;
	
	String name;
	
	String address;
	
	String sex;
	
	String age;
	
	private User father;
	
	private User mother;
	
	public User() {
		System.out.println("create User Bean!");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public User getFather() {
		return father;
	}

	public void setFather(User father) {
		this.father = father;
	}

	public User getMother() {
		return mother;
	}

	public void setMother(User mother) {
		this.mother = mother;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", name=").append(name)
				.append(", address=").append(address).append(", sex=")
				.append(sex).append(", age=").append(age).append(", father=")
				.append(father).append(", mother=").append(mother).append("]");
		return builder.toString();
	}
}
