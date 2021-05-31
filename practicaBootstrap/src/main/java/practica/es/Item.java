package practica.es;

import java.text.DecimalFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private double original_price;
	private double final_price;
	private String percentage;
	private String description;
	private String label1;
	
	public Item(String title, double original_price, double final_price, String description, String label1) {
		super();
		this.title = title;
		this.original_price = original_price;
		this.final_price = final_price;
		this.percentage = getPercentage();
		this.description = description;
		this.label1 = label1;
	}
	
	public Item(Long id, String title, double original_price, double final_price, String description, String label1) {
		super();
		this.id = id;							//this constructor is to modify products, that's why it receives an id as parameter.
		this.title = title;
		this.original_price = original_price;
		this.final_price = final_price;
		this.percentage = getPercentage();
		this.description = description;
		this.label1 = label1;
	}
	
	public Item() {	   							//An empty constructor, it's neccessary.
	} 
	
	public String format(double percentage_format) {
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(percentage_format);
	}

/*********** Getters and Setters ******************/
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public double getOriginal_price() {
		return original_price;
	}
	
	public void setOriginal_price(double original_price) {
		this.original_price = original_price;
	}
	
	public double getFinal_price() {
		return final_price;
	}
	
	public void setFinal_price(double final_price) {
		this.final_price = final_price;
	}
	
	public String getPercentage() {
		return format(100 - (final_price*100)/original_price);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLabel1() {
		return label1;
	}
	
	public void setLabel1(String label1) {
		this.label1 = label1;
	}
	
	@Override						//ToString used to make an only one String with the Elements of an Item.
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", original_price=" + original_price + ", final_price="
				+ final_price + ", percentage=" + percentage + ", description=" + description + ", label1=" + label1 +"]";
	}	
}