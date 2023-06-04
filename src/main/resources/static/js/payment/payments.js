import {formatDate} from "../utils/date-formatter.js";
import {formatFractionalPart} from "../utils/number-formatter.js";

const paymentContainer = document.getElementById('payment-container');
const paymentAmountInput = document.getElementById('payment-amount-input')
const totalPriceSpan = document.getElementById('total-price');
const payButton = document.getElementById("pay-button");
window.addEventListener('load', () => {
    initializePage();
});
payButton.addEventListener("click", () =>{
    savePayment();
})
paymentAmountInput.addEventListener('input', function (evt) {
    if(this.value > 0) {
        totalPriceSpan.innerHTML = this.value + " грн";
    }else{
        totalPriceSpan.innerHTML = "0 грн";
    }
});

const initializePage = () => {
    loadUserPayments();
}
async function loadUserPayments () {
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
        let accountId  = sessionStorage.getItem('current-personal-account');
        let request =  `/api/v1/personal-account/${accountId}/payments/?` + params.toString();
        const response =
            await fetch(request, settings);
        const responseBody = await response.json();
        setPaymentsData(responseBody.data);

    } catch (e) {
        console.error("Error: " + e );
    }
}
const setPaymentsData = (data) =>{
    setBalance(data);
    setPayments(data);
}
const setBalance = (data) => {
    const balanceDiv = document.getElementById('balance');
    const balanceValueSpan = document.getElementById('balance-value-span');
    const balanceStatus = document.getElementById('balance-status');
    const balanceValue = formatFractionalPart(data.personalGasAccount.balance);
    balanceValueSpan.innerHTML =  balanceValue + ' грн';
    if(balanceValue >= 0) {
        balanceStatus.innerHTML = 'Передплата';
        balanceValueSpan.setAttribute('class','overpaid-status');
        balanceStatus.setAttribute('class','overpaid-status');
    }else{
        balanceStatus.innerHTML = 'Заборгованість';
        balanceValueSpan.setAttribute('class','debt-status');
        balanceStatus.setAttribute('class','debt-status');
    }


}
const setPayments = (data) => {


    const payments = data.payments;

    payments.sort((a,b) => b.id - a.id);

    payments.forEach((payment) => {

        let paymentRecordP = document.createElement('p');
        paymentRecordP.setAttribute('class', "payment-record");

        let paymentNumberSpan = document.createElement('span');
        paymentNumberSpan.setAttribute('class','payment-number');
        paymentNumberSpan.innerHTML = '№' + payment.id;

        let paymentDate = document.createElement('span');
        paymentDate.setAttribute('class','payment-date');
        paymentDate.innerHTML = formatDate(payment.timestamp);

        let paymentAmountPaid = document.createElement('span');
        paymentAmountPaid.setAttribute('class','payment-amount-paid');
        paymentAmountPaid.innerHTML = formatFractionalPart(payment.amountPaid) + ' грн';

        paymentRecordP.appendChild(paymentNumberSpan);
        paymentRecordP.appendChild(paymentDate);
        paymentRecordP.appendChild(paymentAmountPaid);

        paymentContainer.appendChild(paymentRecordP);


    });
}
const savePayment = () => {

    const label = document.getElementById('payment-label');

    if (paymentAmountInput.value === '') {
        label.innerHTML = '* Заповніть поле';
        return;

    }else if(isNaN(paymentAmountInput.value)){
        label.innerHTML = '* Введіть числове значення';
        return;

    }else if(paymentAmountInput.value <= 0){
        label.innerHTML = '* Введіть  невідʼємне значення';
        return;
    }
    else {
        label.innerHTML = '';
    }
    const userSession = {
        userId: sessionStorage.getItem("userId"),
        authId: sessionStorage.getItem("authId"),
    }
    const data = {
        userSession: userSession,
        gasAccountId : sessionStorage.getItem('current-personal-account'),
        amountPaid : paymentAmountInput.value
    }
    sendPaymentData(data);
    setTimeout(() => window.location.reload(), 250);
}
const sendPaymentData = (data) => {
    fetch('/api/v1/personal-account/payment/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text)
                })
            }
            return response.json();
        })
}