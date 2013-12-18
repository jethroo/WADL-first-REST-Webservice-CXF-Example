package de.jethroo.rest.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Thingy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer id;
	
	@Column
	private String attribute_name;


	public String getAttribute_name() {
		return attribute_name;
	}

	public Thingy(){
		super();
	}
	
	public Thingy(String attribute_name){
		super();
		this.attribute_name = attribute_name;
	}

	
	public Thingy(Integer id, String attribute_name) {
		super();
		this.attribute_name = attribute_name;
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}

}
