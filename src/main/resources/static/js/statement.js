window.onload = function () {
    let cardForReplenishment = document.getElementById("cardForReplenishment");
    let cardForWithdrawal = document.getElementById("cardForWithdrawal");
    let cardForEarlyClosure = document.getElementById("cardForEarlyClosure");
    let replenishmentAmount = document.getElementById("replenishmentAmountOfDemandDeposit");
    let earlyClosureAmount = document.getElementById("amountOfDepositForClosure");
    let withdrawalAmount = document.getElementById("withdrawalAmountOfDemandDeposit");
    let amountOfDemandDeposit = document.getElementById("amountOfDemandDeposit");
    let replenishmentBtn = document.getElementById("replenishmentOfDemandDepositBtn");
    let withdrawalBtn = document.getElementById("withdrawalOfDemandDepositBtn");
    let earlyClosureBtn = document.getElementById("earlyClosureOfDemandDepositBtn");
    let cardsError = document.getElementById("cardForReplenishmentError");
    let openedDemandDepositId = document.getElementById("openedDemandDepositId");
    let openedDemandDepositId2 = document.getElementById("openedDemandDepositId2");
    let openedDemandDepositId3 = document.getElementById("openedDemandDepositId3");
    let amountOfOpenedDemandDeposit = document.getElementById("amountOfOpenedDemandDeposit");

    let amountForWithdrawalError = document.getElementById("amountForWithdrawalError");
    let cardForReplenishment2 = document.getElementById("cardForReplenishment2");
    let cardForWithdrawal2 = document.getElementById("cardForWithdrawal2");
    let cardForEarlyClosure2 = document.getElementById("cardForEarlyClosure2");
    let replenishmentAmount2 = document.getElementById("replenishmentAmountOfTermDeposit2");
    let earlyClosureAmount2 = document.getElementById("amountOfDepositForClosure2");
    let withdrawalAmount2 = document.getElementById("withdrawalAmountOfTermDeposit2");
    let amountOfTermDeposit = document.getElementById("amountOfTermDeposit");
    let replenishmentBtn2 = document.getElementById("replenishmentOfTermDepositBtn2");
    let withdrawalBtn2 = document.getElementById("withdrawalOfTermDepositBtn2");
    let earlyClosureBtn2 = document.getElementById("earlyClosureOfTermDepositBtn2");
    let cardsError2 = document.getElementById("cardForReplenishmentError2");
    let openedTermDepositId = document.getElementById("openedTermDepositId");
    let openedTermDepositId2 = document.getElementById("openedTermDepositId2");
    let openedTermDepositId3 = document.getElementById("openedTermDepositId3");
    let amountOfOpenedTermDeposit = document.getElementById("amountOfOpenedTermDeposit2");
    let amountForWithdrawalError2 = document.getElementById("amountForWithdrawalError2");
    let earlyClosureLabel = document.getElementById("earlyClosureLabel");

    $('#replenishmentModal').on('shown.bs.modal', function (e) {
        let button = $(e.relatedTarget);
        let depositId = button.data('atr');
        if (depositId) {
            $.get({
                url: '/demandDepositStatement/demandDeposit/' + depositId,
                success: (data) => {
                    openedDemandDepositId.value = data.id;
                }
            });
        }
        if(cardForReplenishment.childElementCount === 0) {
            $.getJSON('/cards/index').done(function (data) {
                $.each(data, function (key, item) {
                    let option = document.createElement("option");
                    option.textContent = item["paymentSystem"].slice(17, -4) + item["number"].toString().replace(/(\w|.){12}/, '****');
                    option.value = item['cardId'];
                    option.title = item['cardBalance'];
                    cardForReplenishment.appendChild(option);
                });
            });
        }
    });

    $('#withdrawalModal').on('shown.bs.modal', function (e) {
        let button = $(e.relatedTarget);
        let depositId = button.data('atr');
        if (depositId) {
            $.get({
                url: '/demandDepositStatement/demandDeposit/' + depositId,
                success: (data) => {
                    openedDemandDepositId2.value = data.id;
                    amountOfDemandDeposit.value = data.demandDeposit.amount;
                    amountOfOpenedDemandDeposit.value = data.amount;
                }
            });
        }
        if(cardForWithdrawal.childElementCount === 0) {
            $.getJSON('/cards/index').done(function (data) {
                $.each(data, function (key, item) {
                    let option = document.createElement("option");
                    option.textContent = item["paymentSystem"].slice(17, -4) + item["number"].toString().replace(/(\w|.){12}/, '****');
                    option.value = item['cardId'];
                    cardForWithdrawal.appendChild(option);
                });
            });
        }
    });

    $('#earlyClosureModal').on('shown.bs.modal', function (e) {
        let button = $(e.relatedTarget);
        let depositId = button.data('atr');
        if (depositId) {
            $.get({
                url: '/demandDepositStatement/demandDeposit/' + depositId,
                success: (data) => {
                    openedDemandDepositId3.value = data.id;
                    earlyClosureAmount.value = data.amount;
                }
            });
        }
        if(cardForEarlyClosure.childElementCount === 0) {
            $.getJSON('/cards/index').done(function (data) {
                $.each(data, function (key, item) {
                    let option = document.createElement("option");
                    option.textContent = item["paymentSystem"].slice(17, -4) + item["number"].toString().replace(/(\w|.){12}/, '****');
                    option.value = item['cardId'];
                    cardForEarlyClosure.appendChild(option);
                });
            });
        }
    });

    replenishmentBtn.addEventListener("click", function (e) {
        let cardBalance = cardForReplenishment.options[cardForReplenishment.selectedIndex].title;
        if (Number(cardBalance) < Number(replenishmentAmount.value)) {
            e.preventDefault();
            cardsError.style.display = 'block';
            cardForReplenishment.classList.add('error');
        } else {
            let info = {
                amount: replenishmentAmount.value,
                type: "REPLENISHMENT",
                deposit: {
                    id: openedDemandDepositId.value
                }
            };
            $.ajax({
                url: '/demandDepositStatement/save',
                type: 'POST',
                data: JSON.stringify(info),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
            let cardId = cardForReplenishment.options[cardForReplenishment.selectedIndex].value;
            $.get({
                url: '/cards/withdrawal/' + cardId + '/' + replenishmentAmount.value
            });
        }
    });

    withdrawalBtn.addEventListener('click', function (e) {
        if (Number(amountOfOpenedDemandDeposit.value) - Number(withdrawalAmount.value) < Number(amountOfDemandDeposit.value)) {
            e.preventDefault();
            amountForWithdrawalError.style.display = 'block';
            withdrawalAmount.classList.add('error');
        } else {
            let info = {
                amount: withdrawalAmount.value,
                type: "WITHDRAWAL",
                deposit: {
                    id: openedDemandDepositId2.value
                }
            };
            $.ajax({
                url: '/demandDepositStatement/save',
                type: 'POST',
                data: JSON.stringify(info),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
            let cardId = cardForWithdrawal.options[cardForWithdrawal.selectedIndex].value;
            $.get({
                url: '/cards/replenishment/' + cardId + '/' + withdrawalAmount.value
            });
        }
    });

    earlyClosureBtn.addEventListener('click', function (e) {
        let info = {
            amount: earlyClosureAmount.value,
            type: "EARLY_CLOSURE",
            deposit: {
                id: openedDemandDepositId3.value
            }
        };
        $.ajax({
            url: '/demandDepositStatement/save',
            type: 'POST',
            data: JSON.stringify(info),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        });
        let cardId = cardForEarlyClosure.options[cardForEarlyClosure.selectedIndex].value;
        $.get({
            url: '/cards/replenishment/' + cardId + '/' + earlyClosureAmount.value
        });
    });

    earlyClosureBtn.addEventListener('click', function (e) {
        alert("Денежные средства успешно переведены с депозитного счета.")
        location.reload();
    })

    $('#replenishmentModal2').on('shown.bs.modal', function (e) {
        let button = $(e.relatedTarget);
        let depositId = button.data('atr');
        if (depositId) {
            $.get({
                url: '/termDepositStatement/termDeposit/' + depositId,
                success: (data) => {
                    openedTermDepositId.value = data.id;
                }
            });
        }
        if(cardForReplenishment2.childElementCount === 0) {
            $.getJSON('/cards/index').done(function (data) {
                $.each(data, function (key, item) {
                    let option = document.createElement("option");
                    option.textContent = item["paymentSystem"].slice(17, -4) + item["number"].toString().replace(/(\w|.){12}/, '****');
                    option.value = item['cardId'];
                    option.title = item['cardBalance'];
                    cardForReplenishment2.appendChild(option);
                });
            });
        }
    });

    $('#withdrawalModal2').on('shown.bs.modal', function (e) {
        let button = $(e.relatedTarget);
        let depositId = button.data('atr');
        if (depositId) {
            $.get({
                url: '/termDepositStatement/termDeposit/' + depositId,
                success: (data) => {
                    console.log(data)
                    openedTermDepositId2.value = data.id;
                    amountOfTermDeposit.value = data.termDeposit.amount;
                    amountOfOpenedTermDeposit.value = data.amount;
                }
            });
        }
        if(cardForWithdrawal2.childElementCount === 0) {
            $.getJSON('/cards/index').done(function (data) {
                $.each(data, function (key, item) {
                    let option = document.createElement("option");
                    option.textContent = item["paymentSystem"].slice(17, -4) + item["number"].toString().replace(/(\w|.){12}/, '****');
                    option.value = item['cardId'];
                    cardForWithdrawal2.appendChild(option);
                });
            });
        }
    });


    $('#earlyClosureModal2').on('shown.bs.modal', function (e) {
        let button = $(e.relatedTarget);
        let depositId = button.data('atr');
        if (depositId) {
            $.get({
                url: '/termDepositStatement/termDeposit/' + depositId,
                success: (data) => {
                    openedTermDepositId3.value = data.id;
                    earlyClosureAmount2.value = data.amount;
                }
            });
            $.get({
                url: '/termDepositStatement/recalculation/' + depositId,
                success: (data) => {
                    if(Number(data.amount) !== Number(earlyClosureAmount2.value)) {
                        earlyClosureLabel.innerText = "Данная операция влечет за собой перерасчет процентов по вкладу! Итоговая сумма вклада составит " + data.amount.toFixed(2) + "" + data.termDeposit.currency + ".";
                    }
                }
            });
        }
        if(cardForEarlyClosure2.childElementCount === 0) {
            $.getJSON('/cards/index').done(function (data) {
                $.each(data, function (key, item) {
                    let option = document.createElement("option");
                    option.textContent = item["paymentSystem"].slice(17, -4) + item["number"].toString().replace(/(\w|.){12}/, '****');
                    option.value = item['cardId'];
                    cardForEarlyClosure2.appendChild(option);
                });
            });
        }
    });


    replenishmentBtn2.addEventListener("click", function (e) {
        let cardBalance = cardForReplenishment2.options[cardForReplenishment2.selectedIndex].title;
        if (Number(cardBalance) < Number(replenishmentAmount2.value)) {
            e.preventDefault();
            cardsError2.style.display = 'block';
            cardForReplenishment2.classList.add('error');
        } else {
            let info = {
                amount: replenishmentAmount2.value,
                type: "REPLENISHMENT",
                deposit: {
                    id: openedTermDepositId.value
                }
            };
            $.ajax({
                url: '/termDepositStatement/save',
                type: 'POST',
                data: JSON.stringify(info),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
            let cardId = cardForReplenishment2.options[cardForReplenishment2.selectedIndex].value;
            $.get({
                url: '/cards/withdrawal/' + cardId + '/' + replenishmentAmount2.value
            });
        }
    });

    withdrawalBtn2.addEventListener('click', function (e) {
        console.log(amountOfOpenedTermDeposit.value)
        console.log(withdrawalAmount2.value)
        console.log(amountOfTermDeposit.value)
        if (Number(amountOfOpenedTermDeposit.value) - Number(withdrawalAmount2.value) < Number(amountOfTermDeposit.value)) {
            e.preventDefault();
            amountForWithdrawalError2.style.display = 'block';
            withdrawalAmount2.classList.add('error');
        } else {
            let info = {
                amount: withdrawalAmount2.value,
                type: "WITHDRAWAL",
                deposit: {
                    id: openedTermDepositId2.value
                }
            };
            $.ajax({
                url: '/termDepositStatement/save',
                type: 'POST',
                data: JSON.stringify(info),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
            let cardId = cardForWithdrawal2.options[cardForWithdrawal2.selectedIndex].value;
            $.get({
                url: '/cards/replenishment/' + cardId + '/' + withdrawalAmount2.value
            });
        }
    });

    earlyClosureBtn2.addEventListener('click', function (e) {
        let info = {
            amount: earlyClosureAmount2.value,
            type: "EARLY_CLOSURE",
            deposit: {
                id: openedTermDepositId3.value
            }
        };
        $.ajax({
            url: '/termDepositStatement/save',
            type: 'POST',
            data: JSON.stringify(info),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        });
        let cardId = cardForEarlyClosure2.options[cardForEarlyClosure2.selectedIndex].value;
        $.get({
            url: '/cards/replenishment/' + cardId + '/' + earlyClosureAmount2.value
        });
    });

    earlyClosureBtn2.addEventListener('click', function (e) {
        alert("Денежные средства успешно переведены с депозитного счета.")
        location.reload();
    });

    const f = x => ((x.toString().includes('.')) ? (x.toString().split('.').pop().length) : (0));

    replenishmentAmount2.addEventListener('input', function (e) {
        if (f(Number(replenishmentAmount2.value)) > 2) {
            replenishmentAmount2.value = replenishmentAmount2.value.slice(0, replenishmentAmount2.value.length - 1);
        }
    });

    withdrawalAmount2.addEventListener('input', function (e) {
        if (f(Number(withdrawalAmount2.value)) > 2) {
            withdrawalAmount2.value = withdrawalAmount2.value.slice(0, withdrawalAmount2.value.length - 1);
        }
    });

    replenishmentAmount.addEventListener('input', function (e) {
        if (f(Number(replenishmentAmount.value)) > 2) {
            replenishmentAmount.value = replenishmentAmount.value.slice(0, replenishmentAmount.value.length - 1);
        }
    });

    withdrawalAmount.addEventListener('input', function (e) {
        if (f(Number(withdrawalAmount.value)) > 2) {
            withdrawalAmount.value = withdrawalAmount.value.slice(0, withdrawalAmount.value.length - 1);
        }
    });

    let tbody = document.getElementById('card-table');
    let modalBtn = document.getElementById('modal-btn');
    let saveCardBtn = document.getElementById('saveCardBtn');
    let cardNumber = document.getElementById('number');
    let numberError = document.getElementById('numberError');
    let validityPeriod = document.getElementById('validityPeriod');
    let validityPeriodError = document.getElementById('validityPeriodError');
    let cvv = document.getElementById('cvv');
    let cvvError = document.getElementById('cvvError');
    let modalView = document.getElementById('cards-id');
    let cardId;

    modalBtn.addEventListener("click", function (e) {
        tbody.innerHTML = "";
        $.getJSON('/cards/index').done(function (data) {
            $.each(data, function (key, item) {
                tbody.innerHTML += '<tr id=' + item['cardId'] + '>' +
                    '<td><img style="margin-right: 40px;" width="35px" src=\'' + item["paymentSystem"] + '\' alt="Изображение...">' +
                    item["paymentSystem"].slice(17, -4) + ' ' +
                    item["number"].toString().replace(/(\w|.){12}/, '****') + '</td>' +
                    '<td><button id=' + item['cardId'] + ' type="button" class="delete-card-btn" onclick="return confirm(\'Вы действительно хотите удалить карту?\')"></button></td>' +
                    '</tr>';
            });
        });
    });
    let button = document.getElementsByClassName('delete-card-btn');

    modalView.addEventListener('mouseenter', function (e) {
        for (let i = 0; i < button.length; i++) {
            button[i].addEventListener('click', function (e) {
                cardId = button[i].getAttribute('id');
                if (cardId) {
                    $.get({
                        url: '/cards/delete/' + cardId,
                        success: () => {
                            let tr = document.getElementById(cardId);
                            tr.innerHTML = "";
                        }
                    });
                }
            })
        }
    });

    cardNumber.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            numberError.style.display = 'block';
            cardNumber.classList.add('error');
        }
    });

    validityPeriod.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            validityPeriodError.style.display = 'block';
            validityPeriod.classList.add('error');
        }
    });

    cvv.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            cvvError.style.display = 'block';
            cvv.classList.add('error');
        }
    });

    cardNumber.addEventListener('click', function (e) {
        cardNumber.classList.remove('error');
        numberError.style.display = 'none';
    });

    validityPeriod.addEventListener('click', function (e) {
        validityPeriod.classList.remove('error');
        validityPeriodError.style.display = 'none';
    });

    cvv.addEventListener('click', function (e) {
        cvv.classList.remove('error');
        cvvError.style.display = 'none';
    });

    saveCardBtn.addEventListener("click", function (e) {
        let arr = ['/payment_systems/Mastercard.png', '/payment_systems/Visa.png', '/payment_systems/Maestro.png'];
        let rand = Math.floor(Math.random() * arr.length);
        let card = {
            paymentSystem: arr[rand],
            number: document.getElementById('number').value.replaceAll(" ", ""),
            month: document.getElementById('validityPeriod').value.slice(0, 2),
            year: '20' + document.getElementById('validityPeriod').value.slice(3),
            cvv: document.getElementById('cvv').value,
            cardBalance: (Math.random() * (3000 - 700) + 700).toFixed(2)
        };

        if (card['number'].length == 16 && card['month'] < 13 && card['year'] > 2022 && card['cvv'].length == 3) {
            $.ajax({
                url: '/cards/save',
                type: 'POST',
                data: JSON.stringify(card),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                error:(err) => {
                    alert("Карта уже привязана к аккаунту.")
                }
            });
        }
    });

    cardNumber.addEventListener("input", (e) => {
        if (e.inputType === "insertText" && !/[0-9]/.test(e.data) || cardNumber.value.length > 19) {
            cardNumber.value = cardNumber.value.slice(0, cardNumber.value.length - 1)
            return;
        }

        let numberValue = cardNumber.value;
        if (e.inputType === "deleteContentBackward" && /[0-9]{4}/.test(numberValue.slice(-4))) {
            cardNumber.value = cardNumber.value.slice(0, cardNumber.value.length - 1)
            return;
        }

        if (/[0-9]{4}/.test(numberValue.slice(-4)) && numberValue.length < 19) {
            cardNumber.value += " ";
        }
    });

    validityPeriod.addEventListener("input", (e) => {
        if (e.inputType === "insertText" && !/[0-9]/.test(e.data) || validityPeriod.value.length > 5) {
            validityPeriod.value = validityPeriod.value.slice(0, validityPeriod.value.length - 1)
            return;
        }

        let periodValue = validityPeriod.value;
        if (e.inputType === "deleteContentBackward" && /[0-9]{2}/.test(periodValue.slice(-2))) {
            validityPeriod.value = validityPeriod.value.slice(0, validityPeriod.value.length - 1)
            return;
        }

        if (/[0-9]{2}/.test(periodValue.slice(-2)) && periodValue.length < 5) {
            validityPeriod.value += "/"
        }
    });

    cvv.addEventListener("input", (e) => {
        if (e.inputType === "insertText" && !/[0-9]/.test(e.data) || cvv.value.length > 3) {
            cvv.value = cvv.value.slice(0, cvv.value.length - 1)
            return;
        }
    });
}