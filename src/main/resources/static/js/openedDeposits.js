window.onload = function () {
    let amount = document.getElementById("amount");
    let amountError = document.getElementById("amountErrorOpenedDeposit");
    let openDepositBtn = document.getElementById("openDepositBtn");
    let minAmount = document.getElementById("minAmount");
    let cards = document.getElementById("cards");
    let cardBalance = cards.options[cards.selectedIndex].dataset.calcValue;
    let cardBalanceError = document.getElementById("cardBalanceError");


    openDepositBtn.addEventListener('click', function (e){
        if(Number(amount.value) < Number(minAmount.value)) {
            e.preventDefault();
            amountError.style.display = 'block';
            amount.classList.add('error');
        }
        if(Number(amount.value) > Number(cardBalance)) {
            e.preventDefault();
            cardBalanceError.style.display = 'block';
            cards.classList.add('error');
        }
    });

    amount.addEventListener('click', function (e) {
        amount.classList.remove('error');
        amountError.style.display = 'none';
    });

    cards.addEventListener('click', function (e) {
        cards.classList.remove('error');
        cardBalanceError.style.display = 'none';
    });

    const f = x => ( (x.toString().includes('.')) ? (x.toString().split('.').pop().length) : (0) );

    amount.addEventListener('input', function (e) {
        if(f(Number(amount.value)) > 2) {
            amount.value = amount.value.slice(0, amount.value.length - 1);
        }
    });
}