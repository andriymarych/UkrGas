const loginBtn = document.getElementById("main-login-button");

const closeLoginFormBtn = document.getElementById("close-login-button");
const loginLabel = document.getElementById("login-label");


const shadowBG = document.getElementById("background-popup");
const controlDisapearingBG = (flag) => {
    shadowBG.style.backgroundColor = "rgba(0, 0, 0, 0.6)";
    (flag)
        ? shadowBG.style.visibility = "visible"
        :
        shadowBG.style.visibility = "hidden"
    clearPopUp();
}


loginBtn.addEventListener('click', () => {
    document.querySelector(".login-popup").classList.add("active");
    controlDisapearingBG(1);
});

closeLoginFormBtn.addEventListener('click', () => {
    document.querySelector(".login-popup").classList.remove("active");
    controlDisapearingBG(0);
});
shadowBG.addEventListener('click', () => {
    document.querySelector(".login-popup").classList.remove("active");
    controlDisapearingBG(0);
})

const loginUser = () => {
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
    fetch('/auth/login', {
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
            document.getElementById('login-label').innerHTML = '';
            setUserData(data);
            removePopUp();
        })
        .catch(error => {
            let errorBody = JSON.parse(error.message);
            document.getElementById('login-label').innerHTML = errorParser(errorBody.message);
        });

}

const setUserData = (response) => {
    sessionStorage.setItem('isUserAuthorized', 'true');
    sessionStorage.setItem('userId', response.data.userId);
    sessionStorage.setItem('authId', response.data.authId);
    window.location.href = '../';
}
const sessionLogout = () => {
    sessionStorage.setItem('isUserAuthorized', 'false');
    sessionStorage.removeItem('current-personal-account');
    sessionStorage.removeItem('userId');
    sessionStorage.removeItem('authId');
    location.reload();
}

const removePopUp = () => {
    document.querySelector(".login-popup").classList.remove("active");
    controlDisapearingBG(0);
}
const clearPopUp = () => {
    document.getElementById('email').value = "";
    document.getElementById('password').value = "";
    loginLabel.innerHTML = "";
}
const errorParser = (errorMessage) => {
    if (errorMessage.includes("password")) {
        return "* Ви ввели невірний пароль"
    } else {
        return "* Користувача із введеним логіном не існує"
    }
}
