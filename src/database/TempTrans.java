package database;

public class TempTrans {
	private String id, name;
	private Integer price, qty, qtyPrice;
	public TempTrans(String id, String name, Integer price, Integer qty, Integer qtyPrice) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.qty = qty;
		this.qtyPrice = qtyPrice;
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getQtyPrice() {
		return qtyPrice;
	}
	public void setQtyPrice(Integer qtyPrice) {
		this.qtyPrice = qtyPrice;
	}
	
}