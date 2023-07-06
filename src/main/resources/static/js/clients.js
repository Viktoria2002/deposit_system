window.onload = function () {
    let password = document.getElementById("password");
    let passwordError = document.getElementById("passwordError");
    let passwordConfirmation = document.getElementById("passwordConfirmation");
    let passwordConfirmationError = document.getElementById("passwordConfirmationError");
    let passwordConfirmationError2 = document.getElementById("passwordConfirmationError2");
    let editPasswordBtn = document.getElementById("editPasswordBtn");
    let editPassword = document.getElementById("editPassword");
    let email = document.getElementById("email");
    let clientId = document.getElementById("clientId");
    let surname = document.getElementById("surname");
    let surnameError = document.getElementById("surnameError");
    let name = document.getElementById("name");
    let nameError = document.getElementById("nameError");
    let patronymic = document.getElementById("patronymic");
    let patronymicError = document.getElementById("patronymicError");
    let radios = document.querySelectorAll('input[type="radio"]');
    let dateOfBirth = document.getElementById("dateOfBirth");
    let dateOfBirthError = document.getElementById("dateOfBirthError");
    let phoneNumber = document.getElementById("phoneNumber");
    let phoneNumberError = document.getElementById("phoneNumberError");
    let editGeneralInfo = document.getElementById("editGeneralInfo");
    let editGeneralInfoBtn = document.getElementById("editGeneralInfoBtn");
    let passportId = document.getElementById("passportId");
    let userId = document.getElementById("userId");
    let passportDataId = document.getElementById("passportDataId");
    let passportNumber = document.getElementById("passportNumber");
    let passportNumberError = document.getElementById("passportNumberError");
    let organization = document.getElementById("organization");
    let organizationError = document.getElementById("organizationError");
    let dateOfIssue = document.getElementById("dateOfIssue");
    let dateOfIssueError = document.getElementById("dateOfBirthError");
    let passportValidityPeriod = document.getElementById("passportValidityPeriod");
    let passportValidityPeriodError = document.getElementById("passportValidityPeriodError");
    let editPassport = document.getElementById("editPassport");
    let editPassportBtn = document.getElementById("editPassportBtn");

    editPassword.addEventListener("click", function (e) {
        $.getJSON('/users/index').done(function (data) {
            email.value = data.email;
        });
    });

    editGeneralInfo.addEventListener("click", function (e) {
        $.getJSON('/generalInfo/personalData').done(function (data) {
            clientId.value = data.clientId;
            surname.value = data.surname;
            name.value = data.name;
            patronymic.value = data.patronymic;
            for (let radio of radios) {
                if (radio.value === data.gender) {
                    radio.checked = true;
                }
            }
            dateOfBirth.value = data.dateOfBirth;
            phoneNumber.value = data.phoneNumber;
            userId.value = data.user.id;
            passportId.value = data.passportData.id;
        });
    });

    editPassport.addEventListener("click", function (e) {
        $.getJSON('/passports/personalData').done(function (data) {
            passportDataId.value = data.id;
            passportNumber.value = data.passportNumber;
            organization.value = data.organization;
            dateOfIssue.value = data.dateOfIssue;
            passportValidityPeriod.value = data.validityPeriod;
        });
    });

    editPasswordBtn.addEventListener("click", function (e) {
        if (!password.classList.contains('error')) {
            let user = {
                email: document.getElementById('email').value,
                password: document.getElementById('password').value
            };

            $.ajax({
                url: '/users/update',
                type: 'POST',
                data: JSON.stringify(user),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        }
    });

    editGeneralInfoBtn.addEventListener("click", function (e) {
        if (!surname.classList.contains('error') && !name.classList.contains('error') && !patronymic.classList.contains('error') &&
            !dateOfBirth.classList.contains('error') && !phoneNumber.classList.contains('error')) {
            let gender;
            let genders = document.querySelectorAll('input[type="radio"]');
            for (let g of genders) {
                if (g.checked) gender = g.value;
            }
            let client = {
                clientId: clientId.value,
                surname: surname.value,
                name: name.value,
                patronymic: patronymic.value,
                gender: gender,
                dateOfBirth: dateOfBirth.value,
                phoneNumber: phoneNumber.value,
                passportData: {
                    id: passportId.value
                },
                user: {
                    id: userId.value
                }
            };

            $.ajax({
                url: '/generalInfo/update',
                type: 'POST',
                data: JSON.stringify(client),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        }
    });

    editPassportBtn.addEventListener("click", function (e) {
        if (!passportNumber.classList.contains('error') && !organization.classList.contains('error')
            && !dateOfIssue.classList.contains('error') && !passportValidityPeriod.classList.contains('error')) {
            let passport = {
                id: passportDataId.value,
                passportNumber: passportNumber.value,
                organization: organization.value,
                dateOfIssue: dateOfIssue.value,
                validityPeriod: passportValidityPeriod.value
            };

            $.ajax({
                url: '/passports/update',
                type: 'POST',
                data: JSON.stringify(passport),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        }
    });

    password.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            passwordError.style.display = 'block';
            password.classList.add('error');
        }
    });

    passwordConfirmation.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            passwordConfirmationError2.style.display = 'block';
            passwordConfirmation.classList.add('error');
        }
    });

    surname.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            surnameError.style.display = 'block';
            surname.classList.add('error');
        }
    });

    name.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            nameError.style.display = 'block';
            name.classList.add('error');
        }
    });

    patronymic.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            patronymicError.style.display = 'block';
            patronymic.classList.add('error');
        }
    });

    dateOfBirth.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            dateOfBirthError.style.display = 'block';
            dateOfBirth.classList.add('error');
        }
    });

    phoneNumber.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            phoneNumberError.style.display = 'block';
            phoneNumber.classList.add('error');
        }
    });

    passportNumber.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            passportNumberError.style.display = 'block';
            passportNumber.classList.add('error');
        }
    });

    organization.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            organizationError.style.display = 'block';
            organization.classList.add('error');
        }
    });

    dateOfIssue.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            dateOfIssueError.style.display = 'block';
            dateOfIssue.classList.add('error');
        }
    });

    passportValidityPeriod.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid) {
            passportValidityPeriodError.style.display = 'block';
            passportValidityPeriod.classList.add('error');
        }
    });

    password.addEventListener('click', function (e) {
        password.classList.remove('error');
        passwordError.style.display = 'none';
    });

    passwordConfirmation.addEventListener('click', function (e) {
        passwordConfirmation.classList.remove('error');
        passwordConfirmationError.style.display = 'none';
        passwordConfirmationError2.style.display = 'none';
    });

    surname.addEventListener('click', function (e) {
        surname.classList.remove('error');
        surnameError.style.display = 'none';
    });

    name.addEventListener('click', function (e) {
        name.classList.remove('error');
        nameError.style.display = 'none';
    });

    patronymic.addEventListener('click', function (e) {
        patronymic.classList.remove('error');
        patronymicError.style.display = 'none';
    });

    dateOfBirth.addEventListener('click', function (e) {
        dateOfBirth.classList.remove('error');
        dateOfBirthError.style.display = 'none';
    });

    phoneNumber.addEventListener('click', function (e) {
        phoneNumber.classList.remove('error');
        phoneNumberError.style.display = 'none';
    });

    passportNumber.addEventListener('click', function (e) {
        passportNumber.classList.remove('error');
        passportNumberError.style.display = 'none';
    });

    organization.addEventListener('click', function (e) {
        organization.classList.remove('error');
        organizationError.style.display = 'none';
    });

    dateOfIssue.addEventListener('click', function (e) {
        dateOfIssue.classList.remove('error');
        dateOfIssueError.style.display = 'none';
    });

    passportValidityPeriod.addEventListener('click', function (e) {
        passportValidityPeriod.classList.remove('error');
        passportValidityPeriodError.style.display = 'none';
    });

    editPasswordBtn.addEventListener("click", function (e) {
        if (password.value !== passwordConfirmation.value) {
            e.preventDefault();
            passwordConfirmationError.style.display = 'block';
            passwordConfirmation.classList.add('error');
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