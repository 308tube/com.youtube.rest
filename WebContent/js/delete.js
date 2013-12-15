/**
 * js file for post.html
 * Please use modern web browser as this file will not attempt to be
 * compatible with older browsers. Use Chrome and open javascript console
 * or Firefox with developer console.
 * 
 * jquery is required
 */
$(document).ready(function() {
	
	getInventory();
	
	$(document.body).on('click', ':button, .DELETE_BTN', function(e) {
		//console.log(this);
		var $this = $(this)
			, PC_PARTS_PK = $this.val()
			, obj = {PC_PARTS_PK : PC_PARTS_PK}
			, $tr = $this.closest('tr')
			, PC_PARTS_MAKER = $tr.find('.CL_PC_PARTS_MAKER').text()
			, PC_PARTS_CODE = $tr.find('.CL_PC_PARTS_CODE').text();
		
		deleteInventory(obj, PC_PARTS_MAKER, PC_PARTS_CODE);
	});
});

function deleteInventory(obj, maker, code) {
	
	ajaxObj = {  
			type: "DELETE",
			url: "http://localhost:7001/com.youtube.rest/api/v3/inventory/" + maker + "/" + code,
			data: JSON.stringify(obj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) {
				//console.log(data);
				$('#delete_response').text( data[0].MSG );
			},
			complete: function(XMLHttpRequest) {
				//console.log( XMLHttpRequest.getAllResponseHeaders() );
				getInventory();
			}, 
			dataType: "json" //request JSON
		};
		
	return $.ajax(ajaxObj);
}

function getInventory() {
	
	var d = new Date()
		, n = d.getTime();
	
	ajaxObj = {  
			type: "GET",
			url: "http://localhost:7001/com.youtube.rest/api/v1/inventory", 
			data: "ts="+n,
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) { 
				//console.log(data);
				var html_string = "";
				
				$.each(data, function(index1, val1) {
					//console.log(val1);
					html_string = html_string + templateGetInventory(val1);
				});
				
				$('#get_inventory').html("<table border='1'>" + html_string + "</table>");
			},
			complete: function(XMLHttpRequest) {
				//console.log( XMLHttpRequest.getAllResponseHeaders() );
			}, 
			dataType: "json" //request JSON
		};
		
	return $.ajax(ajaxObj);
}

function templateGetInventory(param) {
	return '<tr>' +
				'<td class="CL_PC_PARTS_MAKER">' + param.PC_PARTS_MAKER + '</td>' +
				'<td class="CL_PC_PARTS_CODE">' + param.PC_PARTS_CODE + '</td>' +
				'<td class="CL_PC_PARTS_TITLE">' + param.PC_PARTS_TITLE + '</td>' +
				'<td class="CL_PC_PARTS_AVAIL">' + param.PC_PARTS_AVAIL + '</td>' +
				'<td class="CL_PC_PARTS_DESC">' + param.PC_PARTS_DESC + '</td>' +
				'<td class="CL_PC_PARTS_BTN"> <button class="DELETE_BTN" value=" ' + param.PC_PARTS_PK + ' " type="button">Delete</button> </td>' +
			'</tr>';
}