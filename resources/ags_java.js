function toggleVisible(elementID) {
	  
    var element = document.getElementById(elementID);

    if (element.style.display == 'none') {
        element.style.display = 'inherit';
    }
    else {
        element.style.display = 'none';
    }
}
/*
function addRectangle(x, y , h, b)  {
/* 	 <svg width="220px" height="120px">
	 
     <text font-size="32"  x="25" y="60" fill="green">
            Hello, World!
     </text>
	
	 </svg> 
}
function loadGeol(n1 ) {
	
}

function loadDescription(n1 ) {
	
}

function loadSVGL() {
		n1 = document.getElementById("boreholeLog")
		LoadGeol(n1);
		LoadDescription(n1);
	
}
*/