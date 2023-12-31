import {formatDate} from "../utils/date-formatter.js";
import {formatFractionalPart} from "../utils/number-formatter.js";

const meterReadingsContainer = document.getElementById('meter-readings-container');
let lastMeterReadingValue;
const saveButton = document.getElementById("save-button");
window.addEventListener('DOMContentLoaded', function() {


});
window.addEventListener('load', () => {

    initializePage();
});
saveButton.addEventListener("click", () =>{
    saveMeterReading();
})

const initializePage = () => {
    loadUserMeterReadings();
}
async function loadUserMeterReadings () {

    try {
        const settings = {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            }
        };
        let accountId  = sessionStorage.getItem('current-personal-account');
        let request =  `/api/v2/personal-accounts/${accountId}/meter-readings`
        const response =
            await fetch(request, settings);
        const responseBody = await response.json();
        setMeterReadingsData(responseBody.data);

    } catch (e) {
        console.error("Error: " + e );
    }
}
const setMeterReadingsData = (data) =>{
    setMeterNumber(data);
    setCurrentDate();
    setMeterReadings(data);
}
const setCurrentDate = () => {
    const currentDate = document.getElementById("current-date-span");
    currentDate.innerHTML = formatDate(Date.now());
}
const setMeterNumber = (data) => {
    document.getElementById("meter-number").innerHTML = data.personalGasAccount.gasMeterNumber;
}
const setMeterReadings = (data) => {

    let  meterNumber =  data.personalGasAccount.gasMeterNumber;
    const meterReadingsData = data.meterReadings;

    meterReadingsData.sort((a,b) => b.id - a.id);
    let lastMeterReading = meterReadingsData[0];
    if(!checkReadingDate(lastMeterReading)){
        initializeInputBox();
    }else{

    }
    lastMeterReadingValue = meterReadingsData[0].meterReading;
    console.log(lastMeterReadingValue);

    meterReadingsData.forEach((meterReading) => {

        let readingRecordP = document.createElement('p');
        readingRecordP.setAttribute('class', "reading-record");

        let meterNumberSpan = document.createElement('span');
        meterNumberSpan.setAttribute('class','gas-meter-number');
        meterNumberSpan.innerHTML = '№' + meterNumber;

        let readingDate = document.createElement('span');
        readingDate.setAttribute('class','reading-date');
        readingDate.innerHTML = formatDate(meterReading.date);

        let meterReadingValue = document.createElement('span');
        meterReadingValue.setAttribute('class','meter-reading-value');
        meterReadingValue.innerHTML = formatFractionalPart(meterReading.value) + ' м³';

        readingRecordP.appendChild(meterNumberSpan);
        readingRecordP.appendChild(readingDate);
        readingRecordP.appendChild(meterReadingValue);

        meterReadingsContainer.appendChild(readingRecordP);


    });
}
const checkReadingDate = (lastMeterReading) => {
    const lastMeterReadingMonth = new Date(lastMeterReading.date).getMonth();
    const currentMonth = new Date(Date.now()).getMonth();
    return lastMeterReadingMonth !== currentMonth;
}
const initializeInputBox = () =>{

    let feedbackResponseDiv  = document.createElement("div");
    feedbackResponseDiv.setAttribute("id","feedback-response");

    let inputBox = document.getElementById("input-box");

    let firstLineResponse = document.createElement("p");
    firstLineResponse.setAttribute("id","first-line-response");
    firstLineResponse.innerHTML = "Розрахунковий період закрито";

    let secondLineResponse = document.createElement("p");
    secondLineResponse.setAttribute("id","second-line-response");
    secondLineResponse.innerHTML = "Показання за поточний місяць уже передано" ;

    document.getElementById("input-container").style.display = "none";

    feedbackResponseDiv.appendChild(firstLineResponse);
    feedbackResponseDiv.appendChild(secondLineResponse);

    inputBox.appendChild(feedbackResponseDiv);

}
const saveMeterReading = () => {
    const meterReadingInput = document.getElementById('meter-reading-input').value;
    const label = document.getElementById('meter-reading-label');

    if (meterReadingInput === '') {
        label.innerHTML = '* Заповніть поле';
        return;

    }else if(isNaN(meterReadingInput)){
        label.innerHTML = '* Введіть числове значення';
        return;

    }else if(meterReadingInput < lastMeterReadingValue){
        label.innerHTML = '* Введіть  значення показання більше ніж за попередній місяць';
        return;
    }
    else {
        label.innerHTML = '';
    }
    const data = {
        meterReading : meterReadingInput
    }
    sendMeterReadingData(data);
    setTimeout(() => window.location.reload(), 350);
}
const sendMeterReadingData = (data) => {
    let accountId  = sessionStorage.getItem('current-personal-account');

    fetch(`/api/v2/personal-accounts/${accountId}/meter-readings`, {
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