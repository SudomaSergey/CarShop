package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Car {

	private int id;	
	private String name;
	private int price;
	private java.sql.Blob picture;
	private int quantity;
	private Stock stock;

	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}	
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@ManyToOne
	@JoinColumn(name = "stock_id")
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	@Lob
    @Column(columnDefinition = "mediumblob")
	public java.sql.Blob getPicture() {
		return picture;
	}
	public void setPicture(java.sql.Blob blob) {
		this.picture = blob;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
