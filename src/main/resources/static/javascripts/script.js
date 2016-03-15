$(document).ready(function() {

			// Clone portfolio items to get a second collection for Quicksand

	
	var handleResponse=function(res,ct){
		var title="You Smart";
		var content;
	    if (ct.indexOf('json') > -1) {
	    	title=res.title;
	    	content=res.question;
	    }else{
	    	content=res;
	    }
	    $("#dvPopUp").html('<span class="spnQue">'+content+'</span>').dialog({
			resizable : true,
			width : 1200,
			height : 520,
			modal : true,
			title : title,
			open: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog | ui).show(); }
		});
	};
	
	
	/*var init=function(){
		$("#dvPopUp").html("Please wait.. Initializing").dialog({
			resizable : true,
			width : 1200,
			height : 520,
			modal : true,
			title : 'wait..',
			open: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog | ui).hide(); }
			
		});
		$.ajax({
			url : "/init",
			type : "post",
			success : function(res) {
				$("#dvPopUp").dialog('close');
			}
		});
	};
	init();*/
});
