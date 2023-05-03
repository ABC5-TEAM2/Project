function getDb(data1){
	   //지도 표시
	   console.log(data1);
	   
	   const map= new google.maps.Map(document.getElementById("map"),{
        zoom:13, 
        center : new google.maps.LatLng(35.11, 129.04),
        mapTypeId: google.maps.MapTypeId.ROADMAP,
   		 });
	    const infowindow = new google.maps.InfoWindow();
	    let i,res;
	    let marker;
	    
	    //초기 화면 	
	    if(data1==null){
	  //DB자료 기반으로 마크 for문 
		    let lat = [[${tourist_Spot.lat}]];
		    let lng = [[${tourist_Spot.lng}]];
	   
	    	const main_title = [[${tourist_Spot.main_title}]];
	    	const itemcntnts= [[${tourist_Spot.itemcntnts}]];
	 	    	
	   	    
	 	    	 //로케이션 별로 마크 생성
	          marker = new google.maps.Marker({
	              position: new google.maps.LatLng(lat,lng),
	              map:map,
	              animation: google.maps.Animation.DROP,
	            	//icon: 'your-icon.png'
	          });
	   	    	 
	            const positions= new google.maps.LatLng(lat,lng);
	            
	            // 마크를 글릭햇을 때 보여주는 경로
	            google.maps.event.addListener(
	                marker,
	                "click",
	                (function(marker){
	                    return function (){
	                        infowindow.setContent("<h3>"+main_title+"</h3>"+"<p>"+itemcntnts+"<p>"+positions+"<p>"
	                        //+ "<button type='button' class='btn btn-outline-primary' onclick='calcRoute()'>출발</button> "
	                        +"<button type='button' class='btn btn-outline-primary' onclick='calcRouteByRail(" +lat+","+lng + ")'>Rail로 도착</button> "
	                        +"<button type='button' class='btn btn-outline-primary' onclick='calcRouteByBus(" +lat+","+lng + ")'>Bus로 도착</button> "+"<p>"
	                        );
	                        infowindow.open(map,marker);
	                        if (marker.getAnimation() !== null) {
		                        marker.setAnimation(null);
		                      } else {
		                        marker.setAnimation(google.maps.Animation.BOUNCE);
		                      }
	                    };
	                    })(marker,i)
	            	);
	         // 내 위치 파악하기
	         if (navigator.geolocation) {
	           navigator.geolocation.getCurrentPosition(
	             (position) => {
	           	  const myLat = position.coords.latitude;
	               const myLng = position.coords.longitude;	 
	             
	               const pos = new google.maps.LatLng(myLat,myLng);
	              	
	               marker = new google.maps.Marker({
			                position: pos,
			                map:map,
			            });
	               marker.setAnimation(google.maps.Animation.BOUNCE);
	               infowindow.setPosition(pos);
	               infowindow.setContent("내위치"+"<p>"+pos);
	               infowindow.open(map);
	               map.setCenter(pos);
	             },
	             () => {
	               handleLocationError(true, infowindow, map.getCenter());
	             }
	           );
		            } else {
		              // Browser doesn't support Geolocation
		              handleLocationError(false, infowindow, map.getCenter());
		            }
	         
		} else{ //맛집 검색 누르면 
			for(i=0; i<data1.length;i++){
		    	res = data1.find((item,index)=>{
		    		if(index==i){
		    			let lat=[];
		    		 	let lng=[];
		    	    	lat +=item["lat"]; 
		    	    	lng +=item["lng"];	
		    	    	const main_title = item["main_title"];
		    	    	const itemcntnts= item["itemcntnts"];
		    	    	 //로케이션 별로 마크 생성
			            marker = new google.maps.Marker({
			                position: new google.maps.LatLng(lat,lng),
			                map:map,
			                animation: google.maps.Animation.DROP,
			              	//icon: 'your-icon.png'
			            });
			            const positions= new google.maps.LatLng(lat,lng);
			            // 마크를 글릭햇을 때 보여주는 경로
			            google.maps.event.addListener(
			                marker,
			                "click",
			                (function(marker){
			                    return function (){
			                        infowindow.setContent("<h3>"+main_title+"</h3>"+"<p>"+itemcntnts+"<p>"+positions+"<p>"
			                        );
			                        infowindow.open(map,marker);
			                        if (marker.getAnimation() !== null) {
				                        marker.setAnimation(null);
				                      } else {
				                        marker.setAnimation(google.maps.Animation.BOUNCE);
				                      }
			                    };
			                    })(marker,i)
			            	);
		    			return item
		    		}return false;
		    	})
	    	}
		}

	   
	    
	    
	    
	          function handleLocationError(browserHasGeolocation, infowindow, pos) {
	            infowindow.setPosition(pos);
	            infowindow.setContent(
	              browserHasGeolocation
	                ? "Error: The Geolocation service failed."
	                : "Error: Your browser doesn't support geolocation."
	            );
	            infowindow.open(map);
	          }
		}
		   
		
		
		
		//전철 기능 
         function calcRouteByRail(lat,lng) {
     	   	navigator.geolocation.getCurrentPosition(
     	         (position) => {
     	       	   const myLat = position.coords.latitude;
     	           const myLng = position.coords.longitude;	 
     	           const mypos = new google.maps.LatLng(myLat,myLng);
     	              const arrivePos = new google.maps.LatLng(lat,lng);
     	
     		        var request = {
     		            origin:mypos,
     		            destination:arrivePos,
     		            travelMode: "TRANSIT",
     		            transitOptions:{
     		            	modes:['RAIL'],
     		            }
     		        };
     		        
     		        var directionsService = new google.maps.DirectionsService();
     		        var directionsDisplay = new google.maps.DirectionsRenderer();
     		        maps = new google.maps.Map(document.getElementById('map'), map);
     		        directionsDisplay.setMap(maps);
     		        
     		        directionsService.route(request, function(response, status) {
     		          alert(status);  // 확인용 Alert..미사용시 삭제
     			          if (status === google.maps.DirectionsStatus.OK) {
     			        	  directionsDisplay.setDirections(response);
     			        	  for (i = 0; i < response.routes.length; i++) {
     			        		  let route = response.routes[i];
     			        	        for (j = 0; j < route.legs.length; j++) {
     			        	        	let leg = route.legs[j];
     			        	            // Do something with leg attributes...
     			        	            let instruction1 ='';
     			        	            for (k = 0; k < leg.steps.length; k++) {
     			        	            	let step = leg.steps[k];
     			        	            	let distance = step.distance.text;    // fine
     			        	            	let duration = step.duration.text;    // fine
     			        	            	let instruction = step.instructions +" "+duration+",";   ////////////////////
     			        	                
     			        	                instruction1 += instruction;
     			        	                let mode = step.travel_mode;          // works
     			        	            }
     			        	            let result1 = instruction1.slice(0, -1);
     			        	                console.log(result1); 
     			        	            $("#mapContent").append(result1);
     			        	        }        
     			        	    }
     		        	  }else{
     		        		  return getDb()
     		        		  };
     		        }
     	         	)
     	         })}
   	          //버스 
           function calcRouteByBus(lat,lng) {
           	navigator.geolocation.getCurrentPosition(
       	         (position) => {
               	   const myLat = position.coords.latitude;
       	           const myLng = position.coords.longitude;	 
       	           const mypos = new google.maps.LatLng(myLat,myLng);
                      const arrivePos = new google.maps.LatLng(lat,lng);

       		        var request = {
       		            origin:mypos,
       		            destination:arrivePos,
       		            travelMode: "TRANSIT",
       		            transitOptions:{
       		            	modes:['BUS'],
       		            }
       		        };
       		        let directionsService = new google.maps.DirectionsService();
       		        let directionsDisplay = new google.maps.DirectionsRenderer();
       		        maps = new google.maps.Map(document.getElementById('map'), map);
       		        directionsDisplay.setMap(maps);
       		        directionsService.route(request, function(response, status) {
       		          alert(status);  // 확인용 Alert..미사용시 삭제
       			          if (status === google.maps.DirectionsStatus.OK) {
       			        	  directionsDisplay.setDirections(response);
       			        	  for (i = 0; i < response.routes.length; i++) {
       			        		  let route = response.routes[i];
       			        	        for (j = 0; j < route.legs.length; j++) {
       			        	        	let leg = route.legs[j];
       			        	            // Do something with leg attributes...
       			        	            let instruction1 ='';
       			        	            for (k = 0; k < leg.steps.length; k++) {
       			        	            	let step = leg.steps[k];
       			        	            	let distance = step.distance.text;    // fine
       			        	            	let duration = step.duration.text;    // fine
       			        	            	let instruction = step.instructions +" "+duration+",";   ////////////////////
       			        	                instruction1 += instruction;
       			        	                let mode = step.travel_mode;          // works
       			        	            }
       			        	            let result1 = instruction1.slice(0, -1);
       			        	                console.log(result1); 
       			        	            $("#mapContent").append(result1);
       			        	        }        
       			        	    }
       		        	  }else{
       		        		  return getDb()
       		        		  };
       		     		 }
    	         	)
    	         })}
	              //좋아요 기능
	              function like(touristSpotId) {
	            	  console.log(touristSpotId);
	            	  
	            	  $.ajax({
	            	    url: "like",
	            	    type: "POST",
	            	    data: { touristSpotId: touristSpotId },
	            	    success: function (result) {
	            	    	        if (result.liked===true) {
	            	    	          alert("좋아요가 추가되었습니다.");
	            	    	          document.getElementById("like-btn").classList.remove("btn-danger");
	            	    	          document.getElementById("like-btn").classList.add("btn-success");
	            	    	          document.getElementById("like-btn").value = "좋아요 취소";
	            	    	        } else {
	            	    	          alert("좋아요가 삭제되었습니다.");
	            	    	          document.getElementById("like-btn").classList.remove("btn-success");
	            	    	          document.getElementById("like-btn").classList.add("btn-danger");
	            	    	          document.getElementById("like-btn").value = "좋아요";
	            	    	        }
	            	    	    },
	            	    error: function (xhr, status, error) {
	            	      console.error(error);
	            	    }
	            	  });
	            	}       
	              //찜 기능
	              function myList(touristSpotId) {
	            	  console.log(touristSpotId);
	            	  
	            	  $.ajax({
	            	    url: "myList",
	            	    type: "POST",
	            	    data: { touristSpotId: touristSpotId },
	            	    success: function (result) {
	            	    	        if (result.jjim===true) {
	            	    	          alert("찜 목록에 추가되었습니다.");
	            	    	          document.getElementById("myList-btn").classList.remove("btn-primary");
	            	    	          document.getElementById("myList-btn").classList.add("btn-warning");
	            	    	          document.getElementById("myList-btn").value = "찜하기 취소";
	            	    	        } else {
	            	    	          alert("찜 목록에서 삭제되었습니다.");
	            	    	          document.getElementById("myList-btn").classList.remove("btn-warning");
	            	    	          document.getElementById("myList-btn").classList.add("btn-primary");
	            	    	          document.getElementById("myList-btn").value = "찜하기";
	            	    	        }
	            	    	    },
	            	    error: function (xhr, status, error) {
	            	      console.error(error);
	            	    }
	            	  });
	            	}         
	              
             function getRestaurant(gugun_nm){
      	    	let restaurant_id;
   	             console.log(gugun_nm);
      	    	
      	    	
      	         $.ajax({
      	           url: "/restaurant/" +restaurant_id,
      	           type: "post",
      	          
      	           success: function (data, success, xhr) {
      	            //지역 정하기 
      	             const data1 = data.filter((item)=>{
      	            	 return item.gugun_nm==gugun_nm ;
      	            	 })
      	          	getDb(data1)
      	           },
      	           error: function (xhr, status, error) {
      	             console.log(status);
      	           }
      	         });
      	    	}	
           		             
	$(function () {	
		getDb();
		      });