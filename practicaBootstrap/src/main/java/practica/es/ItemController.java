package practica.es;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
	 
	@Autowired
	private StockService stockServices;	
	
	@Autowired
	private authority authority;	
	
	@RequestMapping("/")
	public String  showProducts(Model model) {
		return stockServices.addToModel(model, stockServices.showProducts());
	}
	
	@RequestMapping("/index2")
	public String  showProducts2(Model model) {
		return stockServices.addToModel2(model, stockServices.showProducts());
	}
	
	@RequestMapping ("/admin/form/post")
	public String formPostItem(@RequestParam String title, @RequestParam float original_price, @RequestParam float final_price,
		   @RequestParam String description ,@RequestParam String label1) {
		Map <Integer, Item> collection_item = new ConcurrentHashMap <>();
		Item item = new Item(title, original_price,  final_price,  description ,  label1);
		collection_item.put(1, item);
		if(stockServices.postProduct(collection_item.values())){
			return "success";	
		}else {
			return "error";
		}
	}
	
	@RequestMapping ("/admin/form/update")    // the id is used to update
    public String formUpdateItem2(@RequestParam Long id , @RequestParam String title, @RequestParam float original_price, @RequestParam float final_price,
           @RequestParam String description ,@RequestParam String label1) {
       Item newItem = new Item(id ,title, original_price, final_price, description, label1);
       if(stockServices.updateProduct( id, newItem)) {
    	   return "success";
       }else {
    	   return "error";
       }
    }
	
	@RequestMapping ("/admin/formPost")     //This function is used to show the current Item when you want to modify it.
	public String formPost() {
		return "formPost";
	}
	
	@RequestMapping ("/admin/form/update/{id}")     //This function is used to show the current Item when you want to modify it.
	public String formUpdateItem(Model model , @PathVariable Long id ) {
		model.addAttribute("item" , stockServices.getItem(id));
		model.addAttribute("admin", authority.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
		return "formUpdate";
	}
	
	@RequestMapping ("/admin/form/delete/{id}")     //the id is used to remove the item from stock
	public String formDeleteItem(@PathVariable Long id) {
		if(stockServices.deleteProduct(id)) {
			return "success";
		}else {
			return "error";
		}
	}
	
	@GetMapping("/buttonPc")				  //Filter items according to their label.
	public String showPc(Model model) {
		return stockServices.addToModel(model, stockServices.buttons("pc"));
	}
	
	@GetMapping("/buttonMovil")				  //The same as /buttonPc but with Movil category.
	public String showMovil(Model model) {
		return stockServices.addToModel(model, stockServices.buttons("movil"));
	}
		
	@GetMapping("/buttonAccessory")			  //The same as /botonPc but at Accessory category.
	public String showAccessory(Model model) {
		return stockServices.addToModel(model, stockServices.buttons("accesorio"));
	}
	
	@GetMapping("/item/{num}")
	public String showItem(Model model, @PathVariable Long num) {
		return stockServices.showItem(model, num);
	}
	
	@RequestMapping("/advancedFilter")
	public String filterAdvanced(Model model, @RequestParam String title , @RequestParam int min_price , @RequestParam int max_price, String pc , String movil , String accessory , String orderBy) {
		
		return stockServices.addToModel(model , stockServices.advancedQuery(title , min_price , max_price , pc ,movil ,accessory , orderBy));
	}
}

