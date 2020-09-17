function filterByDate() {
    let balanceFromDate = document.getElementById("FromDate").value;
    let balanceToDate = document.getElementById("ToDate").value;
    if(balanceToDate && balanceFromDate) {
        let balanceDate = {
            fromDate: balanceFromDate,
            toDate: balanceToDate
        };
        $.ajax({
            url: "/filter/by-date",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(balanceDate),
        }).done(function (data){
        }).fail(function (data){
            console.log(data)
        });
    }
}




// function changeBalance() {
//     let balanceFromDate = document.getElementById("FromDate").value;
//     let balanceToDate = document.getElementById("ToDate").value;
//     if(balanceToDate && balanceFromDate) {
//         let balanceDate = {
//             fromDate: balanceFromDate,
//             toDate: balanceToDate
//         };
//         $.ajax({
//             url: "/balance/calculate",
//             method: "POST",
//             contentType: "application/json",
//             data: JSON.stringify(balanceDate),
//         })
//             .done(function (data) {
//                 document.getElementById("Balance").innerHTML = JSON.stringify(data) + " PLN";
//             }).fail(function (data) {
//             console.log(data);
//         });
//     }
// }