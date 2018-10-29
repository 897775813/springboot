package com.zhenguo.bean;

import java.awt.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="person")
public class Person {
	
	private String lastName;
	private int age;
	private Dog dog;
	private Map<String, Object> map;
	private List list;
	private boolean boss;
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Dog getDog() {
		return dog;
	}
	public void setDog(Dog dog) {
		this.dog = dog;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public boolean isBoss() {
		return boss;
	}
	public void setBoss(boolean boss) {
		this.boss = boss;
	}
	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", age=" + age + ", dog=" + dog + ", map=" + map + ", list=" + list
				+ ", boss=" + boss + "]";
	}
	
	

}
