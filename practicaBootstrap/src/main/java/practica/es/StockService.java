package practica.es;

import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.data.domain.Sort;

@Service
public class StockService {

	@Autowired
	private authority authority;
	
	@Autowired
	private ProductsRepository repository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private HttpSession session;
	
	@PostConstruct
	public void init() {
		repository.save(new Item("MSI",1149,898,"<a href='https://www.pccomponentes.com/msi-gf63-thin-9sc-047xes-intel-corei7-9750h-16gb-512gb-ssd-gtx-1650-156?gclid=CjwKCAjwguzzBRBiEiwAgU0FT0Wzj-98Radi--ErK0ShxqCyCm2O77c4Jdh5gTLlQQTdIAMk_dMTehoCX_YQAvD_BwE'>MSI PC Gaming</a>","pc"));
		repository.save(new Item("Huawei P30",600,550,"<a href='https://translate.google.com/?hl=es#view=home&op=translate&sl=es&tl=en&text=texto%20enriquecido'>link</a>","movil"));
		repository.save(new Item ("EarPhones",100,80,"Good for IPhone and Samsung.","accesorio"));
		repository.save(new Item ("Funda de movil Dragon Ball",5,4.5,"Fundas para todo tipo de moviles.","accesorio"));
		repository.save(new Item ("HP Omen",999,950,"Ordenador Gaming con Grafica integrada.","pc"));
		repository.save(new Item ("Asus Gaming RGB",750,650,"Ordenador Gaming con Luces RGB.","pc"));

	}
	
	public Collection<Item> showProducts(){
		return repository.findAll(Sort.by(Sort.Direction.DESC,"id"));
	}
	
	public boolean postProduct(Collection<Item> items) {
		boolean check = true;
		for(Item item : items) {	//Iterator used to go over the Item Collection.
			String s=item.getDescription();
			s = s.replaceAll("<strong>", "!b-etiqueta!");
			s = s.replaceAll("</strong>", "!b-etiqueta-cierre!");
			s = s.replaceAll("<p>", "!p-etiqueta!");
			s = s.replaceAll("</p>", "!p-etiqueta-cierre!");
			s=s.replaceAll("<script>", "");
			s=s.replaceAll("</script>", "");
			s=s.replaceAll("@", "");
			s=s.replaceAll("<", "");
			s=s.replaceAll(">", "");
			s=s.replaceAll("'", "");
			s=s.replaceAll(";", "");
			s = s.replaceAll("!b-etiqueta!", "<strong>");
			s = s.replaceAll("!b-etiqueta-cierre!", "</strong>");
			s = s.replaceAll("!p-etiqueta!","<p>");
			s = s.replaceAll("!p-etiqueta-cierre!","</p>");
			item.setDescription(s);
			if (!(item.getTitle().length()<= 50 && item.getOriginal_price() <=10000 && item.getFinal_price() < item.getOriginal_price() && item.getDescription().length() <= 1000 &&	
			   (item.getLabel1().equals("pc") || item.getLabel1().equals("accesorio") || item.getLabel1().equals("movil")))) {				//Form restrictions.
				check = false;
			}
		}
		if(check) {
			for(Item item : items) {
				repository.save(item);
			}
		}
		return check;
	}
	
	public boolean updateProduct(Long id , Item newItem) {
		Item oldItem = repository.findById(id).get();
		String s=newItem.getDescription();
		s = s.replaceAll("<strong>", "!b-etiqueta!");
		s = s.replaceAll("</strong>", "!b-etiqueta-cierre!");
		s = s.replaceAll("<p>", "!p-etiqueta!");
		s = s.replaceAll("</p>", "!p-etiqueta-cierre!");
		s=s.replaceAll("<script>", "");
		s=s.replaceAll("</script>", "");
		s=s.replaceAll("@", "");
		s=s.replaceAll("<", "");
		s=s.replaceAll(">", "");
		s=s.replaceAll("'", "");
		s=s.replaceAll(";", "");
		s = s.replaceAll("!b-etiqueta!", "<strong>");
		s = s.replaceAll("!b-etiqueta-cierre!", "</strong>");
		s = s.replaceAll("!p-etiqueta!","<p>");
		s = s.replaceAll("!p-etiqueta-cierre!","</p>");
		newItem.setDescription(s);
		
		if (!oldItem.equals(null)  && newItem.getTitle().length()<= 50 && newItem.getOriginal_price() <=10000 && newItem.getFinal_price() < newItem.getOriginal_price() &&
			newItem.getDescription().length() <= 1000 && (newItem.getLabel1().equals("pc") || newItem.getLabel1().equals("accesorio") || newItem.getLabel1().equals("movil"))) {
			oldItem.setTitle(newItem.getTitle());
			oldItem.setOriginal_price(newItem.getOriginal_price());
			oldItem.setFinal_price(newItem.getFinal_price());
			oldItem.setDescription(newItem.getDescription());
			oldItem.setLabel1(newItem.getLabel1());
            repository.save(oldItem);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean deleteProduct(Long id) {
		if(repository.getOne(id) == null) {
			return false;
		}
		repository.deleteById(id);
		return true;
	}	
	
	public List<Item> buttons(String button) {
		TypedQuery<Item> q1 = entityManager.createQuery("SELECT c FROM Item c WHERE c.label1 = '" + button + "'" , Item.class);
		return q1.getResultList();
	}
	
	public String addToModel(Model model , Collection<Item> stockButton) { //add Item collection to model to be seen in the Index
		model.addAttribute("items", stockButton);
		userCheck(model);
		return "index";
	}
	
	public String addToModel2(Model model , Collection<Item> stockButton) { //add Item collection to model to be seen in the Index
		model.addAttribute("items", stockButton);
		userCheck(model);
		return "index2";
	}
	
	public String showItem(Model model, Long num) {
		Item item = getItem(num);
		model.addAttribute("item", item);
		userCheck(model);
		return "showItem";
	}
	
	public void userCheck(Model model) {
		if(authority.isLoggedUser()) {
			model.addAttribute("admin", authority.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
			model.addAttribute("user", authority.getLoggedUser().getRoles().contains("ROLE_ADMIN")||authority.getLoggedUser().getRoles().contains("ROLE_REGUSER"));
			model.addAttribute("userRight", authority.getLoggedUser().getRoles().contains("ROLE_ADMIN")||authority.getLoggedUser().getRoles().contains("ROLE_REGUSER"));
			model.addAttribute("name" , authority.getLoggedUser().getName());
		}else if(!(session.getAttribute("oauth") == null)){
			model.addAttribute("userRight", session.getAttribute("oauth").toString().equals("yes"));
			model.addAttribute("name" , session.getAttribute("name").toString());
		}else{
			model.addAttribute("unLogged", 1);
		}
	}
	
	public Item getItem(Long id) {
		Item item=repository.findById(id).get(); 
		return item;
	}
	
	//Dynamic Query
	
	public List<Item> advancedQuery(String title , int min_price , int max_price , String pc , String movil, String accessory , String orderBy){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> c = criteriaQuery.from(Item.class);
        Predicate minPricePredicate = criteriaBuilder.between(c.get("final_price"), min_price, max_price);
        Predicate findByTitle = null;
        Predicate pcPredicate;
        Predicate movilPredicate;
        Predicate accessoryPredicate;
        Predicate comparation = null;
        Predicate totalPredicate;
        
        String title1= "%"+title+"%";

        if(!(title == null)) {
        	findByTitle = criteriaBuilder.like(c.get("title"), title1);
        }else {
        	findByTitle = minPricePredicate;
        }
        
        if(!(pc == null && movil == null && accessory == null)) {
        	pcPredicate = criteriaBuilder.equal(c.get("label1"), pc);
        	movilPredicate = criteriaBuilder.equal(c.get("label1"), movil);
        	accessoryPredicate = criteriaBuilder.equal(c.get("label1"), accessory);
        	comparation = criteriaBuilder.or(pcPredicate, movilPredicate, accessoryPredicate);
        }else {
        	pcPredicate = null;
            movilPredicate = null;
            accessoryPredicate = null;
            comparation = minPricePredicate;
        }
        totalPredicate = criteriaBuilder.and(minPricePredicate , findByTitle , comparation);
        criteriaQuery.where(totalPredicate);
        
        if(orderBy.equals("order_by_min")) {
        	criteriaQuery.orderBy(criteriaBuilder.asc(c.get("final_price")));
        }else {
        	criteriaQuery.orderBy(criteriaBuilder.desc(c.get("final_price")));
        }

        TypedQuery<Item> query = entityManager.createQuery(criteriaQuery);
    	return query.getResultList();
	}
	
}