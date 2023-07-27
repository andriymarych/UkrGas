const registerNewUser = () => {
    const label = document.getElementById('registration-label');

    const gasAccountNumber = document.getElementById('account-number-input').value;
    const email = document.getElementById('email-input').value;
    const password = document.getElementById('password-input').value;
    const dubpassword = document.getElementById('password-confirmation-input').value;

    if (gasAccountNumber === '' || email === '' || password === '' || dubpassword === '' ) {
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
        gasAccountNumber: gasAccountNumber,
        email: email,
        password: password
    }
   sendUserData(data);
}

const sendUserData = (data) => {
    console.log(data);
    console.log(data.password);
    fetch('api/v2/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) })
            }
            return response.json();
        })
        .then(data => {
            setUserData(data);
        })
        .catch(error => {
            let errorBody = JSON.parse(error.message);
            document.getElementById('registration-label').innerHTML = registrationErrorParser(errorBody.message);

        });
}



const  registrationErrorParser = (errorMessage) => {
    if(errorMessage.includes("Email")){
        return userAlreadyExistsErrorParser(errorMessage);
    }else{
        return personalGasAccountNotFoundErrorParser(errorMessage);
    }
}

const userAlreadyExistsErrorParser = (errorMessage) => {
    var email = errorMessageVariableExtractor(errorMessage);
    return "Адреса " + email + " вже зареєстрована у системі"
}
const personalGasAccountNotFoundErrorParser = (errorMessage) => {
    let accountNumber = errorMessageVariableExtractor(errorMessage);
    return "Особовий рахунок №" + accountNumber + " не знайдено"
}
const errorMessageVariableExtractor = (errorMessage) => {
    var regex = /(?<=\[).+?(?=\])/g;
    return regex.exec(errorMessage);
}

