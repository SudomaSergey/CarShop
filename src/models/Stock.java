package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Stock {
	
	private int stockId;
	private List<Car> cars;
	
	@Id
	@GeneratedValue
	public int getStockId() {
		return stockId;
	}
	
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	
	@OneToMany(targetEntity = Car.class, mappedBy = "stock", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Car> getCar() {
		return cars;
	}
	
	public void setCar(List<Car> cars) {
		this.cars = cars;
	}	
}
