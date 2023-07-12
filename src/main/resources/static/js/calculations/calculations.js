import {formatMonth} from "../utils/date-formatter.js";
import {formatFractionalPart} from "../utils/number-formatter.js";

const meterReadingsContainer = document.getElementById('calculation-records-container');


window.addEventListener('load', () => {
    initializePage();
});


const initializePage = () => {
    loadUserCalculations();
}
async function loadUserCalculations () {
    try {

        const settings = {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            }
        };
        let accountId  = sessionStorage.getItem('current-personal-account');
        let request =  `/api/v2/personal-accounts/${accountId}/calculations`;
        const response =
            await fetch(request, settings);
        const responseBody = await response.json();
        setCalculationsData(responseBody.data);

    } catch (e) {
        console.error("Error: " + e );
    }
}
const setCalculationsData = (data) =>{
    setCalculations(data);
}
const setCalculations = (data) => {


    const calculationsData = data.calculations;




    calculationsData.forEach((calculation) => {

        let calculationRecordP = document.createElement('p');
        calculationRecordP.setAttribute('class', "calculation-record");

        let monthSpan = document.createElement('span');
        monthSpan.setAttribute('class','month');
        monthSpan.innerHTML = formatMonth(calculation.date);

        let tariffSpan = document.createElement('span');
        tariffSpan.setAttribute('class','tariff');
        tariffSpan.innerHTML = calculation.tariff.plan;

        let priceSpan = document.createElement('span');
        priceSpan.setAttribute('class','price-span');
        priceSpan.innerHTML = calculation.tariff.price;

        let amountConsumedSpan = document.createElement('span');
        amountConsumedSpan.setAttribute('class','price');
        amountConsumedSpan.innerHTML = formatFractionalPart(calculation.amountConsumed);

        let accruedPayment = document.createElement('span');
        accruedPayment.setAttribute('class','accrued-payment-span');
        accruedPayment.innerHTML = formatFractionalPart(calculation.accruedPayment);

        let paidPayment = document.createElement('span');
        paidPayment.setAttribute('class','paid-payment-span');
        paidPayment.innerHTML = formatFractionalPart(calculation.paidPayment);

        let balance = document.createElement('span');
        balance.setAttribute('class','balance');
        balance.innerHTML = formatFractionalPart(calculation.balance);


        calculationRecordP.appendChild(monthSpan);
        calculationRecordP.appendChild(tariffSpan);
        calculationRecordP.appendChild(priceSpan);
        calculationRecordP.appendChild(amountConsumedSpan);
        calculationRecordP.appendChild(accruedPayment);
        calculationRecordP.appendChild(paidPayment);
        calculationRecordP.appendChild(balance);

        meterReadingsContainer.appendChild(calculationRecordP);


    });
}
