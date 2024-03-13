package br.com.dennon;

public class Greeting {
	
	private Long id;
	private String name;
	
	public Greeting(Long id, String content) {
		this.id = id;
		this.name = content;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return name;
	}
	public void setContent(String content) {
		this.name = content;
	}
}
