const loginBtn = document.getElementById("main-login-button");

const closeLoginFormBtn = document.getElementById("close-login-button");

const loginFormPopUpButton = document.getElementById("login-form-button");


const userButton = document.getElementById("user-button");


const shadowBG = document.getElementById("background-popup");
const controlDisapearingBG = (flag) => {
    shadowBG.style.backgroundColor = "rgba(0, 0, 0, 0.6)";
    (flag)
        ? shadowBG.style.visibility = "visible"
        : shadowBG.style.visibility = "hidden";
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

loginFormPopUpButton.addEventListener('click', () => {
    document.querySelector(".login-popup").classList.add("active");

    controlDisapearingBG(1);
});

const resisterNewUser = () => {
    const label = document.getElementById('registration-label');

    const name = document.getElementById('registration-name').value;
    const surname = document.getElementById('registration-surname').value;
    const email = document.getElementById('registration-email').value;
    const password = document.getElementById('registration-password').value;
    const dubpassword = document.getElementById('repeat-password').value;
    if (name === '' || surname === '' || email === '' || password === '' || dubpassword === '') {
        label.innerHTML = '*Заповніть всі поля!';
        return;
    }
    else {
        label.innerHTML = '';
    }
    const mailFormat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(mailFormat.test(email)) {
        label.innerHTML = '';
    }
    else {
        label.innerHTML = '*Цей формат емейлу не відповідає нормам!';
        return;
    }

    if (password !== dubpassword) {
        label.innerHTML = '*Введені паролі не збігаються!';
        return;
    } else {
        label.innerHTML = '';
    }

    const data = {
        name: name,
        surname: surname,
        email: email,
        password: password
    }
    createNewUser(data);
}

const createNewUser = (data) => {
    console.log(data);
    console.log(data.password);
    fetch('/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            setUserData(data, data.password);
            removePopUps();
        })
        .catch(error => {
            document.getElementById('registration-label').innerHTML='*Цей email вже зайнято!';
            console.error('Error:', error);
        });
}

const loginUser = () => {
    const label = document.getElementById('login-label');

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (email === '' || password === '') {
        label.innerHTML = '*Заповніть всі поля!';
        return ;
    }
    else {
        label.innerHTML = '';
    }
    const mailFormat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(mailFormat.test(email)) {
        label.innerHTML = '';
    }
    else {
        label.innerHTML = '*Цей формат емейлу не відповідає нормам!';
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
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('login-label').innerHTML='';
            setUserData(data, input.password);
            removePopUps();
        })
        .catch(error => {
            document.getElementById('login-label').innerHTML='*Перевірте введені вами поля!';
            console.error('Error:', error);
        });

}

const setUserData = (response, password) => {
    sessionStorage.setItem('id', response.data.id);
    sessionStorage.setItem('name', response.data.name);
    sessionStorage.setItem('password', password);
    sessionStorage.setItem('email', response.data.email);
    location.reload();
}

const removePopUps = () => {
    document.querySelector(".login-popup").classList.remove("active");

    controlDisapearingBG(0);
}