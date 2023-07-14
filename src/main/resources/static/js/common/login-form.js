import {saveTokensToCookies, setCookie} from "../utils/cookie-service.js";

const loginBtn = document.getElementById("main-login-button");

const closeLoginFormBtn = document.getElementById("close-login-button");
const loginLabel = document.getElementById("login-label");


const shadowBG = document.getElementById("background-popup");
const DisappearingModeBG = (flag) => {
    shadowBG.style.backgroundColor = "rgba(0, 0, 0, 0.6)";
    (flag)
        ? shadowBG.style.visibility = "visible"
        :
        shadowBG.style.visibility = "hidden"
    clearPopUp();
}


loginBtn.addEventListener('click', () => {
    document.querySelector(".login-popup").classList.add("active");
    DisappearingModeBG(1);
});

closeLoginFormBtn.addEventListener('click', () => {
    document.querySelector(".login-popup").classList.remove("active");
    DisappearingModeBG(0);
});
shadowBG.addEventListener('click', () => {
    document.querySelector(".login-popup").classList.remove("active");
    DisappearingModeBG(0);
})

window.loginUser = function ()  {
    const label = document.getElementById('login-label');

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (email === '' || password === '') {
        label.innerHTML = '* Заповніть усі поля';
        return;
    } else {
        label.innerHTML = '';
    }
    const mailFormat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if (mailFormat.test(email)) {
        label.innerHTML = '';
    } else {
        label.innerHTML = '* Введіть коректну електронну адресу';
        return false;
    }
    const data = {
        email: email,
        password: password
    }
    verifyUser(data);
}

const verifyUser = (input) => {
    fetch('/api/v2/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(input)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text)
                })
            }
            return response.json();
        })
        .then(data => {
            saveTokensToCookies(data.data.accessToken, data.data.refreshToken)
            document.getElementById('login-label').innerHTML = '';
            setUserData();
            removePopUp();
        })
        .catch(error => {
            let errorBody = JSON.parse(error.message);
            document.getElementById('login-label').innerHTML = "*Ви ввели невірні дані облікового запису";
        });

}

const setUserData = () => {
    setCookie('is_user_authorized', 'true', 1);
    //sessionStorage.setItem('is-user-authorized', 'true');
    window.location.href = '../';
}

const removePopUp = () => {
    document.querySelector(".login-popup").classList.remove("active");
    DisappearingModeBG(0);
}
const clearPopUp = () => {
    document.getElementById('email').value = "";
    document.getElementById('password').value = "";
    loginLabel.innerHTML = "";
}

export{DisappearingModeBG}
