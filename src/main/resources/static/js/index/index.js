import {DisappearingModeBG} from "../common/login-form.js";
import {getCookie} from "../utils/cookie-service.js";

const anchors = document.getElementsByClassName('pageAnchor');

let isUserAuthorized = getCookie('is_user_authorized') ;

for (let i = 0; i < anchors.length ; i++) {
    console.log(getCookie('is_user_authorized'));
    anchors[i].addEventListener("click",
        function (event) {
            event.preventDefault();
            if (!isUserAuthorized) {
                openLoginPopUp()
            }else{
                window.location = this.href;
            }
        },
        false);
}
const openLoginPopUp = () => {
    document.querySelector(".login-popup").classList.add("active");
    DisappearingModeBG(1);
}
window.addEventListener('load', () => {
    initializePage();
});


const initializePage = () => {
    loadFuelPrice();
}
async function loadFuelPrice () {
    try {
        const params = new URLSearchParams({
            currency: 'UAH'
        });

        const settings = {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            }
        };
        let request =  `/api/v2/fuel-prices?` + params.toString();
        const response =
            await fetch(request, settings);
        const responseBody = await response.json();
        setFuelPriceData(responseBody.data);

    } catch (e) {
        console.error("Error: " + e );
    }
}
const setFuelPriceData = (data) =>{

    data.forEach((fuelPrice) => {
        let fuelType = fuelPrice.fuelType.toLowerCase();
        let priceChangePct = fuelPrice.priceChangePct;
        let fuelPriceSpan = document.getElementById(fuelType + '-price');
        fuelPriceSpan.innerHTML = roundPrice(fuelPrice.price);
        fuelPriceSpan.classList.add(getPriceChangeStyleClass(priceChangePct));

        let priceChangePctSpan = document.getElementById(fuelType + '-pct');
        priceChangePctSpan.innerHTML = calculatePercentChange(priceChangePct);
        priceChangePctSpan.classList.add(getPriceChangeStyleClass(priceChangePct));

        let priceChangeArrow = document.getElementById(fuelType + '-arrow');
        priceChangeArrow.innerHTML = getPriceChangeArrow(priceChangePct);
        priceChangeArrow.classList.add(getPriceChangeStyleClass(priceChangePct));


    });
}

const roundPrice = (price) =>{
    return (Math.round(price * 10000) / 10000).toFixed(4);
}
const calculatePercentChange = (percentChange) =>{

    if(percentChange >= 0){
        return '(+' + (Math.round(percentChange * 100) / 100).toFixed(2) + '%)';
    }else{
        return '(' + (Math.round(percentChange * 100) / 100).toFixed(2) + '%)';
    }
}
const getPriceChangeArrow = (percentChange) =>{

    if(percentChange >= 0){
        return 'arrow_drop_up';
    }else{
        return 'arrow_drop_down';
    }
}
const getPriceChangeStyleClass = (percentChange) =>{
    if(percentChange >= 0){
        return 'price-raise';
    }else{
        return 'price-reduction';
    }
}
