function changeBalance() {
    let balanceFromDate = document.getElementById("FromDate").value;
    let balanceToDate = document.getElementById("ToDate").value;
    if (balanceToDate && balanceFromDate) {
        let balanceDate = {
            fromDate: balanceFromDate,
            toDate: balanceToDate
        };
        $.ajax({
            url: "/calculate/sum/balance",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(balanceDate),
        })
            .done(function (data) {
                document.getElementById("Balance").innerHTML = JSON.stringify(data) + " PLN";
            }).fail(function (data) {
            console.log(data);
        });
    }
}

function changeIncome() {
    let IncomeFromDate = document.getElementById("IncomeFromDate").value;
    let IncomeToDate = document.getElementById("IncomeToDate").value;
    if (IncomeFromDate && IncomeToDate) {
        let incomeDate = {
            fromDate: IncomeFromDate,
            toDate: IncomeToDate
        };
        $.ajax({
            url: "/calculate/sum/income",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(incomeDate)
        }).done(function (data) {
            document.getElementById("Incomes").innerHTML = "Incomes : " + JSON.stringify(data) + " PLN";
        }).fail(function (data) {
            console.log(data);
        });
    }
}

function changeOutcome() {
    let OutcomeFromDate = document.getElementById("OutcomeFromDate").value;
    let OutcomeToDate = document.getElementById("OutcomeToDate").value;
    if (OutcomeFromDate && OutcomeToDate) {
        let outcomeDate = {
            fromDate: OutcomeFromDate,
            toDate: OutcomeToDate
        };
        $.ajax({
            url: "/calculate/sum/outcome",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(outcomeDate)
        }).done(function (data) {
            document.getElementById("Outcomes").innerHTML = "Outcomes : " + JSON.stringify(data) + " PLN";
        }).fail(function (data) {
            console.log(data);
        });
    }
}