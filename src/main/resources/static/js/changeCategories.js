function changeIncomeCategories() {
    let IncomeFromDate = document.getElementById("IncomeFromDate").value;
    let IncomeToDate = document.getElementById("IncomeToDate").value;
    if (IncomeFromDate && IncomeToDate) {
        let incomeData = {
            fromDate: IncomeFromDate,
            toDate: IncomeToDate,
            type: "income"
        };
        $.ajax({
            url: "/categories-filter/by-date",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(incomeData)
        }).done(function (data) {
            let incomesColumn = document.getElementById("incomesColumn");
            incomesColumn.innerHTML = ' ';
            let categoriesIncomeRanking = data;
            let firstDiv;
            let secondDiv;
            let thirdDiv;
            if (categoriesIncomeRanking.length >= 1) {
                firstDiv = ` 
                <div class="input-group">
                    <i class="col-12 transaction-category-name-progress-bar">${categoriesIncomeRanking[0].name}</i>
                    <div class="progress progress-bar-dimensions">
                        <div id="firstDivProgressBar" class="progress-bar progress-bar-striped bg-success progress-bar-animated" 
                        role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100">
                        ${Math.round(categoriesIncomeRanking[0].percentageContribution * 10) / 10 + '%'}</div>
                    </div>
                    <i class="transaction-category-amount-progress-bar">${categoriesIncomeRanking[0].amount + ' zł'}</i>
                </div>
                `;
                incomesColumn.innerHTML = firstDiv;
                let firstProgressBar = document.getElementById("firstDivProgressBar");
                firstProgressBar.style.width = `${categoriesIncomeRanking[0].percentageContribution}%`;
            }
            if (categoriesIncomeRanking.length >= 2) {
                secondDiv = `
                <hr class="col-11">
                <div class="input-group">
                    <i class="col-12 transaction-category-name-progress-bar">${categoriesIncomeRanking[1].name}</i>
                    <div class="progress progress-bar-dimensions">
                        <div id="secondDivProgressBar" class="progress-bar progress-bar-striped progress-bar-animated" 
                        role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100">
                        ${Math.round(categoriesIncomeRanking[1].percentageContribution * 10) / 10 + '%'}</div>
                    </div>
                    <i class="transaction-category-amount-progress-bar">${categoriesIncomeRanking[1].amount + ' zł'}</i>
                </div>
                `;
                incomesColumn.innerHTML += secondDiv;
                let secondProgressBar = document.getElementById("secondDivProgressBar");
                secondProgressBar.style.width = `${categoriesIncomeRanking[1].percentageContribution}%`;
            }
            if (categoriesIncomeRanking.length === 3) {
                thirdDiv = `
                <hr class="col-11">
                <div class="input-group">
                    <i class="col-12 transaction-category-name-progress-bar">${categoriesIncomeRanking[2].name}</i>
                    <div class="progress progress-bar-dimensions">
                        <div id="thirdDivProgressBar" class="progress-bar progress-bar-striped bg-danger progress-bar-animated" 
                             role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100">
                             ${Math.round(categoriesIncomeRanking[2].percentageContribution * 10) / 10 + '%'}</div>
                   </div>
                    <i class="transaction-category-amount-progress-bar">${categoriesIncomeRanking[2].amount + ' zł'}</i>
                </div>
            `;
                incomesColumn.innerHTML += thirdDiv;
                let thirdProgressBar = document.getElementById("thirdDivProgressBar");
                thirdProgressBar.style.width = `${categoriesIncomeRanking[2].percentageContribution}%`;
            }
            console.log(data)
        }).fail(function (data) {
            console.log(data);
        });
    }
}

function changeOutcomeCategories() {
    let OutcomeFromDate = document.getElementById("OutcomeFromDate").value;
    let OutcomeToDate = document.getElementById("OutcomeToDate").value;
    if (OutcomeFromDate && OutcomeToDate) {
        let outcomeData = {
            fromDate: OutcomeFromDate,
            toDate: OutcomeToDate,
            type: "outcome"
        };
        $.ajax({
            url: "/categories-filter/by-date",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(outcomeData)
        }).done(function (data) {
            let outcomesColumn = document.getElementById("outcomesColumn");
            outcomesColumn.innerHTML = ' ';
            let categoriesOutcomeRanking = data;
            let firstDiv;
            let secondDiv;
            let thirdDiv;
            if (categoriesOutcomeRanking.length >= 1) {
                firstDiv = ` 
                <div class="input-group">
                    <i class="col-12 transaction-category-name-progress-bar">${categoriesOutcomeRanking[0].name}</i>
                    <div class="progress progress-bar-dimensions">
                        <div id="firstOutcomeProgressBar" class="progress-bar progress-bar-striped bg-success progress-bar-animated" 
                        role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100">
                        ${Math.round(categoriesOutcomeRanking[0].percentageContribution * 10) / 10 + '%'}</div>
                    </div>
                    <i class="transaction-category-amount-progress-bar">${categoriesOutcomeRanking[0].amount + ' zł'}</i>
                </div>
                `;
                outcomesColumn.innerHTML = firstDiv;
                let firstProgressBar = document.getElementById("firstOutcomeProgressBar");
                firstProgressBar.style.width = `${categoriesOutcomeRanking[0].percentageContribution}%`;
            }
            if (categoriesOutcomeRanking.length >= 2) {
                secondDiv = `
                <hr class="col-11">
                <div class="input-group">
                    <i class="col-12 transaction-category-name-progress-bar">${categoriesOutcomeRanking[1].name}</i>
                    <div class="progress progress-bar-dimensions">
                        <div id="secondOutcomeProgressBar" class="progress-bar progress-bar-striped progress-bar-animated" 
                        role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100">
                        ${Math.round(categoriesOutcomeRanking[1].percentageContribution * 10) / 10 + '%'}</div>
                    </div>
                    <i class="transaction-category-amount-progress-bar">${categoriesOutcomeRanking[1].amount + ' zł'}</i>
                </div>
                `;
                outcomesColumn.innerHTML += secondDiv;
                let secondProgressBar = document.getElementById("secondOutcomeProgressBar");
                secondProgressBar.style.width = `${categoriesOutcomeRanking[1].percentageContribution}%`;
            }
            if (categoriesOutcomeRanking.length === 3) {
                thirdDiv = `
                <hr class="col-11">
                <div class="input-group">
                    <i class="col-12 transaction-category-name-progress-bar">${categoriesOutcomeRanking[2].name}</i>
                    <div class="progress progress-bar-dimensions">
                        <div id="thirdOutcomeProgressBar" class="progress-bar progress-bar-striped bg-danger progress-bar-animated"
                             role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100">
                             ${Math.round(categoriesOutcomeRanking[2].percentageContribution * 10) / 10 + '%'}</div>
                   </div>
                    <i class="transaction-category-amount-progress-bar">${categoriesOutcomeRanking[2].amount + ' zł'}</i>
                </div>
            `;
                outcomesColumn.innerHTML += thirdDiv;
                let thirdProgressBar = document.getElementById("thirdOutcomeProgressBar");
                thirdProgressBar.style.width = `${categoriesOutcomeRanking[2].percentageContribution}%`;
            }
            console.log(data)
        }).fail(function (data) {
            console.log(data);
        });
    }
}