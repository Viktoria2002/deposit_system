window.onload = function () {
    let form = document.getElementById("form");
    let banks = document.getElementById('banks');
    let name = document.getElementById("depositName");
    let nameError = document.getElementById("nameError");
    let interestRate = document.getElementById("interestRate");
    let interestRateError = document.getElementById("interestRateError");
    let amount = document.getElementById("amount");
    let amountError = document.getElementById("amountError");
    let currency = document.getElementById("currency");
    let currencyError = document.getElementById("currencyError");
    let term = document.getElementById("term");
    let termError = document.getElementById("termError");
    let amountField = document.getElementById('amountField');
    let termField = document.getElementById('termField');
    let optimalSearchBtn = document.getElementById('optimalSearchBtn');

    form.addEventListener('click', function (e) {
        let arr = [];
        for(let option of banks.options) {
            arr.push(option.value);
            arr.push(option.text);
        }
        let bank = banks.options[banks.selectedIndex].value;
        localStorage.setItem('banks', JSON.stringify(arr));
        localStorage.setItem('bank', bank);
    });

    if(localStorage.getItem('banks')) {
        let array = JSON.parse(localStorage.getItem("banks"));
        for(let i = 0; i < array.length; i++) {
            let option = document.createElement('option');
            option.value = array[i];
            option.text = array[i+1];
            if(option.value === localStorage.getItem('bank')) {
                option.selected = true;
            }
            ++i;
            document.getElementById("banks").add(option);
        }
    }

    optimalSearchBtn.addEventListener('click', function (e){
        if(amountField.value == "") amountField.value = 0;
        if(termField.value == "") termField.value = 0;
    });

    const f = x => ( (x.toString().includes('.')) ? (x.toString().split('.').pop().length) : (0) );

    interestRate.addEventListener('input', function (e) {
        if(f(Number(interestRate.value)) > 2) {
            interestRate.value = interestRate.value.slice(0, interestRate.value.length - 1);
        }
    });

    amount.addEventListener('input', function (e) {
        if(f(Number(amount.value)) > 2) {
            amount.value = amount.value.slice(0, amount.value.length - 1);
        }
    });

    term.addEventListener('input', function (e) {
        if(e.inputType === "insertText" && !/[0-9]/.test(e.data)) {
            term.value = term.value.toString().slice(0, term.value.length - 1);
        }
    });

    name.addEventListener('click', function (e) {
        name.classList.remove('error');
        nameError.style.display = 'none';
    });

    interestRate.addEventListener('click', function (e) {
        interestRate.classList.remove('error');
        interestRateError.style.display = 'none';
    });

    amount.addEventListener('click', function (e) {
        amount.classList.remove('error');
        amountError.style.display = 'none';
    });

    currency.addEventListener('click', function (e) {
        currency.classList.remove('error');
        currencyError.style.display = 'none';
    });

    term.addEventListener('click', function (e) {
        term.classList.remove('error');
        termError.style.display = 'none';
    });

}