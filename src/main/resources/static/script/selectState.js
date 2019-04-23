function dropDownSelection() {
    if(document.getElementById("myStateDropDown").style.display=="none"){
        document.getElementById("myStateDropDown").style.display = "block";
    }else{
        document.getElementById("myStateDropDown").style.display = "none";
    }
}
// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches('.select-state-btn')) {
        document.getElementById("myStateDropDown").style.display = "none";
    }
}