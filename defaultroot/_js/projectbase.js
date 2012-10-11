
/**
*
*
*/
function checkAll(e, itemName) {
	var aa = document.getElementsByName(itemName);
	for (var i = 0; i < aa.length; i++) {
		aa[i].checked = e.checked;
	}
}
function checkItem(e, allName) {
	var all = document.getElementsByName(allName)[0];
	if (!e.checked) {
		all.checked = false;
	} else {
		var aa = document.getElementsByName(e.name);
		for (var i = 0; i < aa.length; i++) {
			if (!aa[i].checked) {
				return;
			}
			all.checked = true;
		}
	}
}

