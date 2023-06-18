const personalAccountDropdown = document.getElementById("personal-account-list");
let isUserAuthorized = sessionStorage.getItem('isUserAuthorized') === "true";
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
        controlDisapearingBG(0);
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

async function loadUserPersonalGasAccounts () {
    try {
        const params = new URLSearchParams({
            userId: sessionStorage.getItem("userId"),
            authId: sessionStorage.getItem("authId"),
        });

        const settings = {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            }
        };
        let request =  "/api/v2/personal-accounts?" + params.toString();
        const response =
            await fetch(request, settings);
        const responseBody = await response.json();
        setUserPersonalAccounts(responseBody.data);

    } catch (e) {
        console.error("Error: " + e );
    }
}
const setUserPersonalAccounts = (userPersonalGasAccounts) => {


    userPersonalGasAccounts.forEach((personalAccount) => {

        let personalAccountDiv = document.createElement('div');
        personalAccountDiv.setAttribute('id', personalAccount.id);
        personalAccountDiv.setAttribute('class', 'personal-account-item');

        let accountIcon = document.createElement('img');
        accountIcon.setAttribute('src','/img/common/account-icon.png');

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

const logout = () =>{
    document.getElementById("logout").remove();
    sessionLogout();
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
