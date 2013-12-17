package de.jethroo.rest.example;

public class Thingy {
	
	private Integer id;
	private String attribute_name;


	public String getAttribute_name() {
		return attribute_name;
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
