	
	
	function printSelects(id){
		 	var s=document.getElementById(id); 
  			for(var i = 1; i < 6; i++ ){
 				 s.options[s.options.length] = new Option(i,i);
 			 } 
		}
		
	function getSelectedValue(selectName){
		var obj=document.getElementById(selectName);
	 		return obj.value;		
	}