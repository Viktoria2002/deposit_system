window.onload = function () {
    let file = document.getElementById("logo");
    let img = document.getElementById("logo-field");
    let hidden = document.getElementById('hidden');
    let name = document.getElementById('bankName');
    let nameError = document.getElementById('nameError');
    let address = document.getElementById('address');
    let addressError = document.getElementById('addressError');
    let a1Number = document.getElementById('A1Number');
    let a1Error = document.getElementById('a1Error');
    let mtsNumber = document.getElementById('MtsNumber');
    let mtsError = document.getElementById('mtsError');
    let lifeNumber = document.getElementById('LifeNumber');
    let lifeError = document.getElementById('lifeError');

    file.addEventListener('change', function (e){
        let fileName = "/logos/" + file.files[0].name;
        img.srcset = fileName;
        hidden.value = "";
        localStorage.setItem('fileName', fileName);
    });

    if(localStorage.getItem('fileName')) {
        img.srcset = localStorage.getItem('fileName');
    }

    name.addEventListener('click', function (e) {
        name.classList.remove('error');
        nameError.style.display = 'none';
    });

    address.addEventListener('click', function (e) {
        address.classList.remove('error');
        addressError.style.display = 'none';
    });

    a1Number.addEventListener('click', function (e) {
        a1Number.classList.remove('error');
        a1Error.style.display = 'none';
    });

    mtsNumber.addEventListener('click', function (e) {
        mtsNumber.classList.remove('error');
        mtsError.style.display = 'none';
    });

    lifeNumber.addEventListener('click', function (e) {
        lifeNumber.classList.remove('error');
        lifeError.style.display = 'none';
    });
}
