document.getElementById("FromDate").value = getSavedValue("FromDate");    // set the value to this input
document.getElementById("ToDate").value = getSavedValue("ToDate");   // set the value to this input
document.getElementById("showOnlyFilter").value = getSavedValue("showOnlyFilter");
/* Here you can add more inputs to set value. if it's saved */

//Save the value function - save it to localStorage as (ID, VALUE)
function saveValue(e) {
    let id = e.id;  // get the sender's id to save it .
    let val = e.value; // get the value.
    sessionStorage.setItem(id, val);// Every time user writing something, the localStorage's value will override .
}

//get the saved value function - return the value of "v" from localStorage.
function getSavedValue(v) {
    if (!sessionStorage.getItem(v)) {
        return "";// You can change this to your default value.
    }
    return sessionStorage.getItem(v);
}