import {cookiesLogout, getCookie, setCookie} from "../utils/cookie-service.js";
import {DisappearingModeBG} from "./login-form.js";


const personalAccountDropdown = document.getElementById("personal-account-list");
let isUserAuthorized;

const navbar = document.getElementById("nav-bar-list");
let logoutButton;
let preventClose;


window.addEventListener('load', () => {
    initializeHeader();
});


const userButton = document.getElementById("user-button");

userButton.addEventListener('click', () => {
    if (!personalAccountDropdown.classList.contains("selected")) {
        personalAccountDropdown.classList.add("selected");
        preventClose = true;
    } else {
        personalAccountDropdown.classList.remove("selected");
    }

});

document.addEventListener('click', () => {
    if (personalAccountDropdown.classList.contains("selected") && preventClose === true) {
        preventClose = false;
        return
    }
    if (personalAccountDropdown.classList.contains("selected") && !preventClose) {
        personalAccountDropdown.classList.remove("selected");
        DisappearingModeBG(0);
    }
})


personalAccountDropdown.addEventListener("click", function (e) {
    if(e.target.id === "logout" || e.target.parentElement.id === "logout"){
        return ;
    }
    if(e.target.id === ""){
        let target = e.target;
        let parent = target.parentElement;
        sessionStorage.setItem("current-personal-account", parent.id);
    }else {
        sessionStorage.setItem("current-personal-account", e.target.id);
    }
    location.reload();
});

const initializeHeader = () => {
    initializeNavBar();
    setAuthorizeButton();
}
const initializeNavBar = () => {
    isUserAuthorized = getCookie('is_user_authorized') === 'true';
    if (!isUserAuthorized) {
        initializeUnauthorizedNavBar();
    } else {
        initializeAuthorizedNavBar();
    }
}
const initializeUnauthorizedNavBar = () => {
    initializeCommonMenuItem();
}
const initializeAuthorizedNavBar = () => {
    initializeCommonMenuItem();
    const profile = document.createElement("a");
    profile.setAttribute("href", "/profile");

    const profileLi = document.createElement("li");
    profileLi.innerHTML = "Профіль";

    profile.appendChild(profileLi);

    navbar.appendChild(profile);
}

const setAuthorizeButton = () => {
    if (!isUserAuthorized) {
        document.getElementById('main-login-button').hidden = false;
    } else {
        initializePersonalAccountsDropdown();
        document.getElementById('main-login-button').hidden = true;
        const userButton = document.getElementById('user-button');
        userButton.hidden = false;
    }
}

const initializePersonalAccountsDropdown = () => {
    loadUserPersonalGasAccounts();
}


async function loadUserPersonalGasAccounts() {

    fetch('/api/v2/personal-accounts', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                logout();
                return response.text().then(text => {
                    throw new Error(text)
                })
            }
            return response.json();
        })
        .then(data => {
            setUserPersonalAccounts(data.data);
            setCookie('is_user_authorized', 'true', 1);
            sessionStorage.setItem('is-user-authorized', 'true');
            console.log(sessionStorage.getItem('is-user-authorized'));

        }).catch(() => {
        setCookie('is_user_authorized', 'false', 1);
    });


}
const setUserPersonalAccounts = (userPersonalGasAccounts) => {


    userPersonalGasAccounts.forEach((personalAccount) => {

        let personalAccountDiv = document.createElement('div');
        personalAccountDiv.setAttribute('id', personalAccount.id);
        personalAccountDiv.setAttribute('class', 'personal-account-item');

        let accountIcon = document.createElement('img');
        accountIcon.setAttribute('src', '/images/common/account-icon.png');

        let accountNumberSpan = document.createElement('span');
        accountNumberSpan.setAttribute('class', 'account-number-span');
        accountNumberSpan.innerHTML = personalAccount.accountNumber;

        personalAccountDiv.appendChild(accountIcon);
        personalAccountDiv.appendChild(accountNumberSpan);

        personalAccountDropdown.appendChild(personalAccountDiv);

    });
    setCurrentUserAccount();
    addUserExitButton();

}

const setCurrentUserAccount = () =>{
    let currentPersonalAccount = sessionStorage.getItem('current-personal-account');
    if(currentPersonalAccount !== null ){
        document.getElementById(currentPersonalAccount).classList.add("current-account");
    }else{
        let firstPersonalAccount = personalAccountDropdown.firstElementChild;
        sessionStorage.setItem('current-personal-account', firstPersonalAccount.id);
        firstPersonalAccount.classList.add("current-account");
    }
}
const addUserExitButton = () =>{

    let logoutDiv = document.createElement('div');
    logoutDiv.setAttribute('id','logout');
    logoutDiv.setAttribute('class','personal-account-item');
    logoutDiv.setAttribute('onclick','logout()');


    let logoutIcon = document.createElement('span');
    logoutIcon.innerHTML = 'logout';
    logoutIcon.setAttribute('class', 'material-symbols-outlined logout-button-icon');

    let logoutSpan = document.createElement('span');
    logoutSpan.innerHTML = 'Вийти';
    logoutSpan.setAttribute('class','exit-button-span');

    logoutDiv.appendChild(logoutIcon);
    logoutDiv.appendChild(logoutSpan);

    personalAccountDropdown.appendChild(logoutDiv);
    logoutButton = logoutDiv;
}
window.logout = () => {
    document.getElementById("logout").remove();
    cookiesLogout();
    window.location.href='../';

}

const initializeCommonMenuItem = () => {

    const feedback = document.createElement("a");
    feedback.setAttribute("href", "/feedback");

    const feedbackLi = document.createElement("li");
    feedbackLi.innerHTML = "Напишіть нам";

    feedback.appendChild(feedbackLi);

    navbar.appendChild(feedback);

    const contacts = document.createElement("a");
    contacts.setAttribute("href", "/contacts");

    const contactsLi = document.createElement("li");
    contactsLi.innerHTML = "Контакти";

    contacts.appendChild(contactsLi);

    navbar.appendChild(feedback);
    navbar.appendChild(contacts);

}
