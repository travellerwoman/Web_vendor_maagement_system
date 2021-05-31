package practica.es;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	
	@Column(unique=true)
	private String name;
	
	@Column(unique=true)
	private String email;
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;
	
	@ManyToOne
	private Item idFavProduct;
	
	public User(Long idUser, String name, String email, String password , Item fav , String... roles) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.email = email;
		this.password =  new BCryptPasswordEncoder().encode(password);
		this.idFavProduct = fav;
		this.roles = new ArrayList<>(Arrays.asList(roles));
	}
	
	public User(Long idUser, String name, String email, String password) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.email = email;
		this.password =  new BCryptPasswordEncoder().encode(password);
	}

	public User(String name, String email, String password , Item fav , String... roles) {
		super();
		this.name = name;
		this.email = email;
		this.password =  new BCryptPasswordEncoder().encode(password);
		this.idFavProduct = fav;
		this.roles = new ArrayList<>(Arrays.asList(roles));
	}
	
	public User(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password =  password;
	}

	public User() {
	}

	/*********** Getters and Setters ******************/
	@JsonIgnore
	public Long getIdUser() {
		return idUser;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonIgnore
	@JsonProperty(value = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Item getIdFavProduct() {
		return idFavProduct;
	}

	public void setIdFavProduct(Item idFavProduct) {
		this.idFavProduct = idFavProduct;
	}
	
	@JsonIgnore
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", name=" + name + ", email=" + email + ", password=" + password + ", roles="
				+ roles + ", idFavProduct=" + idFavProduct + "]";
	}
}