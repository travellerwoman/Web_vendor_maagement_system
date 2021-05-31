package practica.es;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemRestController {
	
	@Autowired
	private StockService stockServices;	
	
	@GetMapping("/")
	public Collection<Item> getStock() {
		return stockServices.showProducts();
	}
	
	@GetMapping("/filter")
	public Collection<Item> getItem(@RequestParam String label) {
		return stockServices.buttons(label);
	}
	
	@RequestMapping("/item/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Item> getOneItem(@PathVariable long id) {
		Item item = stockServices.getItem(id);
		if (item != null) {
			return new ResponseEntity<>(item, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<Item> createItem(@RequestBody Collection<Item> items) {
		if(stockServices.postProduct(items)) {
			return new ResponseEntity<Item>(HttpStatus.CREATED);      //STATUS 201
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  //400 ERROR
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item newItem) {
		if(stockServices.updateProduct(id , newItem)) {
			return new ResponseEntity<>(newItem, HttpStatus.CREATED);       //STATUS 201
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);   //400 ERROR
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Item> deleteItem(@PathVariable Long id) {
		if(stockServices.getItem(id)!=null) {
			if (stockServices.deleteProduct(id)==true) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/advancedFilter")
	public List<Item> advancedRestQuery(@RequestParam String title , @RequestParam int min_price , @RequestParam int max_price , @RequestParam String pc , @RequestParam String movil, @RequestParam String accessory , @RequestParam String orderBy) {
		return stockServices.advancedQuery(title , min_price , max_price , pc , movil , accessory , orderBy);
	}
	
}	



















