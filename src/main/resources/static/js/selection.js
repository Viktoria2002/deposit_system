window.onload = function () {
    let interestRateForCalculation = document.getElementById('interestRateForCalculation');
    let capitalization = document.getElementById('capitalization');
    let endAmount = document.getElementById('endAmount');
    let depositAmount = document.getElementById('depositAmount');
    let depositTerm = document.getElementById('depositTerm');
    let income = document.getElementById('income');
    let depositCurrency = document.getElementById('depositCurrency');
    let depositEndCurrency = document.getElementById('depositEndCurrency');
    let incomeCurrency = document.getElementById('incomeCurrency');
    let form1 = document.getElementById('form1');
    let form2 = document.getElementById('form2');
    let form3 = document.getElementById('form3');
    let form4 = document.getElementById('form4');
    let banks = document.getElementById('banks');
    let amountField = document.getElementById('amountField');
    let termField = document.getElementById('termField');
    let optimalSearchBtn = document.getElementById('optimalSearchBtn');
    let resetBtn = document.getElementById('resetBtn');
    let resetBtn2 = document.getElementById('resetBtn2');
    let termDepositsBtn = document.getElementById('termDepositsBtn');
    let demandDepositsBtn = document.getElementById('demandDepositsBtn');
    amountField.value = "";
    termField.value = "";
    for (let option of banks.options) {
        if (option.text == "Любой") option.selected = true;
    }

    if (resetBtn) {
        resetBtn.addEventListener('click', function (e) {
            termDepositsBtn.click();
            amountField.value = "";
            termField.value = "";
        });
    }

    if (resetBtn2) {
        resetBtn2.addEventListener('click', function (e) {
            demandDepositsBtn.click();
            amountField.value = "";
            termField.value = "";
        });
    }

    optimalSearchBtn.addEventListener('click', function (e) {
        let arr = [];
        for (let option of banks.options) {
            arr.push(option.value);
            arr.push(option.text);
        }
        let bank = banks.options[banks.selectedIndex].value;
        localStorage.setItem('banks', JSON.stringify(arr));
        localStorage.setItem('bank', bank);
        localStorage.setItem('amount', amountField.value);
        localStorage.setItem('term', termField.value);
    });

    if (localStorage.getItem('banks')) {
        let array = JSON.parse(localStorage.getItem("banks"));
        for (let i = 0; i < array.length; i++) {
            let option = document.createElement('option');
            option.value = array[i];
            option.text = array[i + 1];
            if (option.value === localStorage.getItem('bank')) {
                option.selected = true;
            }
            ++i;
            document.getElementById("banks").add(option);
            let amount = localStorage.getItem('amount');
            let term = localStorage.getItem('term');
            if (amount != 0) amountField.value = amount;
            if (term != 0) termField.value = term;
        }
    }

    optimalSearchBtn.addEventListener('click', function (e) {
        if (amountField.value == "") amountField.value = 0;
        if (termField.value == "") termField.value = 0;
    });

    const f = x => ((x.toString().includes('.')) ? (x.toString().split('.').pop().length) : (0));

    amountField.addEventListener('input', function (e) {
        if (f(Number(amountField.value)) > 2) {
            amountField.value = amountField.value.slice(0, amountField.value.length - 1);
        }
    });

    termField.addEventListener('input', function (e) {
        if (e.inputType === "insertText" && !/[0-9]/.test(e.data)) {
            termField.value = termField.value.toString().slice(0, termField.value.length - 1);
        }
    });


    $('#calculatorModalForDemandDeposit').on('shown.bs.modal', function (e) {
        form1.reset();
        form2.reset();
        let button = $(e.relatedTarget);
        let depositId = button.data('atr');
        if (depositId) {
            $.get({
                url: '/demandDeposits/' + depositId,
                success: (data) => {
                    interestRateForCalculation.value = data.interestRate;
                    capitalization.value = data.hasCapitalization;
                    depositCurrency.innerText = data.currency;
                    depositEndCurrency.innerText = data.currency;
                    incomeCurrency.innerText = data.currency;
                }
            });
        }
    });

    $('#calculatorModalForTermDeposit').on('shown.bs.modal', function (e) {
        form3.reset();
        form4.reset();
        let button = $(e.relatedTarget);
        let depositId = button.data('atr');
        if (depositId) {
            $.get({
                url: '/termDeposits/' + depositId,
                success: (data) => {
                    interestRateForCalculation.value = data.interestRate;
                    capitalization.value = data.hasCapitalization;
                    depositTerm.value = data.term;
                    depositCurrency.innerText = data.currency;
                    depositEndCurrency.innerText = data.currency;
                    incomeCurrency.innerText = data.currency;
                }
            });
        }
    });

    depositAmount.addEventListener('input', function (e) {
        if (depositTerm.value != '') {
            if (capitalization.value == "true") {
                let amount = depositAmount.value * Math.pow(1 + interestRateForCalculation.value / 36500, depositTerm.value);
                let inc = amount - depositAmount.value;
                endAmount.value = String(amount.toFixed(2));
                income.value = String(inc.toFixed(2));
            } else {
                let inc = depositAmount.value * interestRateForCalculation.value * 365 / (depositTerm.value * 30) / 100;
                let amount = inc + Number(depositAmount.value);
                income.value = String(inc.toFixed(2));
                endAmount.value = String(amount.toFixed(2));
            }
        }
    });

    depositTerm.addEventListener('input', function (e) {
        if (depositAmount.value != '') {
            if (capitalization.value == "true") {
                let amount = depositAmount.value * Math.pow(1 + interestRateForCalculation.value / 36500, depositTerm.value);
                let inc = amount - depositAmount.value;
                endAmount.value = String(amount.toFixed(2));
                income.value = String(inc.toFixed(2));
            } else {
                let inc = depositAmount.value * interestRateForCalculation.value * 365 / (depositTerm.value * 30) / 100;
                let amount = inc + Number(depositAmount.value);
                income.value = String(inc.toFixed(2));
                endAmount.value = String(amount.toFixed(2));
            }
        }
    });

    depositAmount.addEventListener('input', function (e) {
        if (f(Number(depositAmount.value)) > 2) {
            depositAmount.value = depositAmount.value.slice(0, depositAmount.value.length - 1);
        }
    });

    depositTerm.addEventListener('input', function (e) {
        if (e.inputType === "insertText" && !/[0-9]/.test(e.data)) {
            depositTerm.value = depositTerm.value.toString().slice(0, depositTerm.value.length - 1);
        }
    });



}