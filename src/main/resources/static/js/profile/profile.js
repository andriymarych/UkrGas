import {formatDate} from "../utils/date-formatter.js";


window.addEventListener('load', () => {
    initializePage();
});


const initializePage = () => {
    loadUserAccountData();
}

async function loadUserAccountData() {
    try {
        const params = new URLSearchParams({
            userId: sessionStorage.getItem("userId"),
            authId: sessionStorage.getItem("authId")
        });

        const settings = {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            }
        };
        let accountId = sessionStorage.getItem('current-personal-account');
        let request = `/api/v2/personal-accounts/${accountId}?` + params.toString();
        const response =
            await fetch(request, settings);
        const responseBody = await response.json();
        setBalance(responseBody.data);
        setPersonalInfo(responseBody.data);
        setTariffInfo(responseBody.data);

    } catch (e) {
        console.error("Error: " + e);
    }
}

const setBalance = (data) => {
    const balanceValue = data.balance;

    const balanceSpan = document.getElementById('balance-value-span');
    if (balanceValue >= 0) {
        balanceSpan.setAttribute('class', 'overpaid-status');
    } else {
        balanceSpan.setAttribute('class', 'debt-status');
    }

    balanceSpan.innerHTML = data.balance + ' грн';
}
const setPersonalInfo = (data) => {

    const ownerName = document.getElementById("owner");
    ownerName.innerHTML = data.person.lastName + " " + data.person.firstName;

    const accountNumber = document.getElementById("personal-account-number");
    accountNumber.innerHTML = data.accountNumber;

    const iecNumber = document.getElementById("iec-number");
    iecNumber.innerHTML = data.iecNumber;

    const address = document.getElementById("address");
    const addressData = data.address;
    address.innerHTML = addressData.region + ", "
        + addressData.city + ", вул. " + addressData.street + ", буд. " + addressData.houseNumber;

}
const setTariffInfo = (data) => {

    const tariffName = document.getElementById("tariff-name");
    tariffName.innerHTML =  data.accountTariff.tariff.plan;

    const tariffPrice = document.getElementById("tariff-price");
    tariffPrice.innerHTML =  data.accountTariff.tariff.price;

    const tariffDiscount = document.getElementById("tariff-discount");
    const tariffDiscountValue =  data.accountTariff.discount;
    tariffDiscount.innerHTML = tariffDiscountValue !== 0 ? tariffDiscountValue + "%" : "Відсутня";

    const tariffPeriod  = document.getElementById("tariff-period");
    tariffPeriod.innerHTML = formatDate(data.accountTariff.startDate)
        + "-" + formatDate(data.accountTariff.endDate);
}
