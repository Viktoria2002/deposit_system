window.onload = function () {
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