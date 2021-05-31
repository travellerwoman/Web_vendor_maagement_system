

function createItem() {

	var title = document.getElementById('title').value;
	var original_price = document.getElementById('original_price').value;
	var final_price = document.getElementById('final_price').value;
	var description = theDescription.getData();
	var label1 = document.getElementById('label1').value;

	var item = [{title: title  , original_price: original_price ,  final_price: final_price ,  description: description , label1: label1}];
	
	var client = new XMLHttpRequest();

	client.responseType = "json";

	client.addEventListener("load", function() {

		console.log(this.response);
		console.log(client.status);

		location.href=("https://localhost:8440/");
	});
	
	client.open("POST", "/api/items/");

	client.setRequestHeader("Content-type", "application/json");

	var body = JSON.stringify(item);
	
	client.send(body);
}

function loadItems(){
	
    var client = new XMLHttpRequest();
    client.responseType = "json";
    client.addEventListener("load", function(){
    	addItemsToPage(this.response);    	
    });
    
    client.open("GET", "/api/items/");
    
    client.send();
    document.getElementById('pcCategory').href="#";
    document.getElementById('pcCategory').setAttribute("onclick","simpleFilter('pc')");
    document.getElementById('movilCategory').href="#";
    document.getElementById('movilCategory').setAttribute("onclick","simpleFilter('movil')");
    document.getElementById('accessoryCategory').href="#";
    document.getElementById('accessoryCategory').setAttribute("onclick","simpleFilter('accesorio')");
    	
}

function simpleFilter(string){
	var client = new XMLHttpRequest();
    client.responseType = "json";
    
    client.addEventListener("load", function(){
    	addItemsToPage(this.response);    	
    });
    									  
    client.open("GET", "/api/items/filter?label="+string+"");
    
    client.send();
}

function addItemsToPage(items){
    
    var itemsElem = document.getElementById('items'); 			//search the label which id is items	

    //itemsElem.innerHTML='<div class="col-lg-4 col-md-6 mb-4"><div class="card h-100"><div class="card-body" align="middle"><a href="/formPost.html" class="list-group-item"><button type="submit"><img src="https://png.pngtree.com/png-vector/20190214/ourlarge/pngtree-vector-plus-icon-png-image_515260.jpg" width="80" ></img></button></a></div></div></div>';	
    itemsElem.innerHTML='';
    
    for(var item of items){										//create node tree
    	var div1 = document.createElement('div');
        div1.className="col-lg-4 col-md-6 mb-4";
        var div2 = document.createElement('div');
        div2.className="card h-100";
        var div3= document.createElement('div');
        div3.className="card-body";
        var div4= document.createElement('div');
        div4.className="card-footer";
    	var small = document.createElement('small')
        small.className="text-muted";
        
        var li1 = document.createElement( 'p' );
        var li2 = document.createElement( 'p' );
        var li3 = document.createElement( 'p' );
        var li4 = document.createElement( 'p' );
        
        var a = document.createElement('a');
        a.setAttribute("href","#");
        a.setAttribute("onclick","jsItem("+item.id+")");
        
        var h4 = document.createElement( 'h4' );
        h4.className="card-title";
        
        var ul = document.createElement( 'ul' );
        
        var title = document.createTextNode(item.title);
        var final_price = document.createTextNode(item.final_price+"€");
        var label = document.createTextNode(item.label1);
        li4.innerHTML = item.description;
        
        var star = document.createTextNode("★ ★ ★ ★ ☆");
 
        a.appendChild(title);
        h4.appendChild(a);
        li1.appendChild(h4);
        li1.appendChild(ul);
        li2.appendChild(final_price);
        li3.appendChild(label);
        small.appendChild(star);
          
        itemsElem.appendChild(div1);
        div1.appendChild(div2);
        div2.appendChild(div3);
        div2.appendChild(div4);
        div3.appendChild(h4);
        div3.appendChild(ul);
        div3.appendChild(li1);
        div4.appendChild(small);
        
        ul.appendChild(li2);
        ul.appendChild(li3);
        ul.appendChild(li4);
        //Insert before.
        itemsElem.insertLast
       
    } 
}

function jsItem(id){
	var client = new XMLHttpRequest();
    client.responseType = "json";
    client.addEventListener("load", function(){
    	console.log(this.response);
    	showItemInPage(this.response);	
    });	
    
    client.open("GET", "/api/items/item/"+id+"");
    client.send();
}


function showItemInPage(item){
	
	
		
	var client = new XMLHttpRequest();
    client.responseType = "json";
    client.addEventListener("load", function(){
    	console.log(this.response);
    	
    	if(this.response==2){
    		
    		var a = document.createElement( 'a' );
    		a.className="list-group-item";
            var a2 = document.createElement( 'a' );
            a2.className="list-group-item";
            var a3 = document.createElement( 'a' );
            a3.className="list-group-item";
            
            a.setAttribute("href","#");
            a.setAttribute("onclick","updateUserFav("+item.id+")");
            a2.setAttribute("href","admin/form/update/"+ item.id +"");
            a3.setAttribute("href","#");
            a3.setAttribute("onclick","deleteItem("+item.id+")");
            
            
            var addFav = document.createTextNode("Añadir a favorito");
        	var modify = document.createTextNode("Modificar");
        	var del = document.createTextNode("Borrar");
        	
        	
        	a.appendChild(addFav);
            a2.appendChild(modify);
            a3.appendChild(del);
            
            div4.appendChild(a);
            div4.appendChild(a2);
            div4.appendChild(a3);
            
    	}else if(this.response==1){
            var a = document.createElement( 'a' );
            a.className="list-group-item";
            a.setAttribute("href","#");
            a.setAttribute("onclick","updateUserFav("+item.id+")");
        	var addFav = document.createTextNode("Añadir a favorito");

            a.appendChild(addFav);

            div4.appendChild(a);
    	}	
    	
    });	
    
    
    
    var itemsElem = document.getElementById('items'); 	//search the label which id is items			
    itemsElem.innerHTML='';
    
    var div1 = document.createElement('div');    		//create node tree
    div1.className="col-lg-12 col-md-6 mb-4";
    var div2 = document.createElement('div');
    div2.className="card h-100";
    var div3= document.createElement('div');
    div3.className="card-body";
    var div4= document.createElement('div');
    div4.className="card-footer";
	var small = document.createElement('small')
    small.className="text-muted";
	
	var ul = document.createElement( 'ul' );
    var li1 = document.createElement( 'p' );
    var li2 = document.createElement( 'p' );
    var li3 = document.createElement( 'p' );
    var li4 = document.createElement( 'p' );
    var li5 = document.createElement( 'p' );
    var li6 = document.createElement( 'p' );
    var p = document.createElement( 'p' );
     
    

    
    var title = document.createTextNode("Nombre: "+ item.title);
    var original_price = document.createTextNode("Precio original: " + item.original_price + "€");
    var final_price = document.createTextNode("Precio final: " + item.final_price + "€");
    var percentage = document.createTextNode("Porcentaje: " + item.percentage + "%");
    var label = document.createTextNode("Etiqueta: " + item.label1);
    var star = document.createTextNode("★ ★ ★ ★ ☆");
	
    li5.innerHTML ="Descripción: " + item.description;
    
    p.appendChild(title);
    li1.appendChild(p);
    li2.appendChild(original_price);
    li3.appendChild(final_price);
    li4.appendChild(percentage);
    li6.appendChild(label);
    small.appendChild(star);
    
    itemsElem.appendChild(div1);
    div1.appendChild(div2);
    div2.appendChild(div3);
    div2.appendChild(div4);
    div3.appendChild(ul);
    div4.appendChild(small);
    ul.appendChild(li1);
    ul.appendChild(li2);
    ul.appendChild(li3);
    ul.appendChild(li4);
    ul.appendChild(li5);
    ul.appendChild(li6);
    
    
    client.open("GET", "/api/users/whoami");
    client.send();
}

function loggedUser(){
	var client = new XMLHttpRequest();
    client.responseType = "json";
    client.addEventListener("load", function(){
    	console.log(this.response);
    });	
    
    client.open("GET", "/api/users/profile");
    client.send();
}

function updateItem(id) {
	
	var title = document.getElementById('title').value;
	var original_price = document.getElementById('original_price').value;
	var final_price = document.getElementById('final_price').value;
	var description = theDescription.getData();
	var label1;
	
	if(labelPc.checked==true){
		label1=document.getElementById('labelPc').value;
	}else if(labelMovil.checked==true){
		label1=document.getElementById('labelMovil').value;
	}else{
		label1=document.getElementById('labelAccessory').value;
	}
	
	var item = {title: title  , original_price: original_price ,  final_price: final_price ,  description: description , label1: label1};
	
	var client = new XMLHttpRequest();

	client.responseType = "json";

	client.addEventListener("load", function() {

		console.log(this.response);
		console.log(client.status);
		location.href=("https://localhost:8440/");
		
	});
	
	client.open("PUT", "/api/items/"+id+"");

	client.setRequestHeader("Content-type", "application/json");

	var body = JSON.stringify(item);
	
	client.send(body);
}

function deleteItem(id){
	var client = new XMLHttpRequest();
    client.responseType = "json";
    
    client.addEventListener("load", function(){
    	location.href=("https://localhost:8440/");    	
    });
    									  
    client.open("DELETE", "/api/items/"+id+"");
    
    client.send();
}

function updateUserFav(id){
	var client = new XMLHttpRequest();
    client.responseType = "json";
    
    client.addEventListener("load", function(){
    	location.href=("https://localhost:8440/");     	
    });
    									  
    client.open("PATCH", "/api/users/user/updateUserFav/"+id+"");
    
    client.send();
}

function advancedFilter(){
	var title = document.getElementById('title').value;
	var min_price = document.getElementById('min_price').value;
	var max_price = document.getElementById('max_price').value;
	var pc = document.getElementById('pc');
	if(pc.checked==true){
		pc="pc";
	}else{
		pc=null;
	}
	var movil = document.getElementById('movil');
	
	if(movil.checked==true){
		movil="movil";
	}else{
		movil=null;
	}
	var accessory = document.getElementById('accessory');
	if(accessory.checked==true){
		accessory="accesorio";
	}else{
		accessory=null;
	}
	if(pc==null && movil==null && accessory==null){
		pc="pc";
		movil="movil";
		accessory="accesorio";
	}
	var orderByMin = document.getElementById('orderByMin');
	var orderBy;
	if(orderByMin.checked==true){
		orderBy=document.getElementById('orderByMin').value;
	}else{
		orderBy=document.getElementById('orderByMax').value;
	}
	var client = new XMLHttpRequest();
    client.responseType = "json";
    
    client.addEventListener("load", function(){
    	addItemsToPage(this.response);     	
    });
    									  
    client.open("GET", "/api/items/advancedFilter?title="+title+"&min_price="+min_price+"&max_price="+max_price+"&pc="+pc+"&movil="+movil+"&accessory="+accessory+"&orderBy="+orderBy+"");
    
    client.send();
}

