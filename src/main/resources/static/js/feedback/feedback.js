
const registrationButton = document.getElementById('create-account-button');




const createFeedback = () => {
    const label = document.getElementById('feedback-label');

    const fullName = document.getElementById('full-name-input').value;
    const email = document.getElementById('email-input').value;
    const feedbackType = document.getElementById('feedback-type').value;
    const feedbackCategory = document.getElementById('feedback-category').value;
    const content = document.getElementById("feedback-content").value;

    if (fullName === '' || email === '' || feedbackType === '' || feedbackCategory === '' ) {
        label.innerHTML = '*Заповніть всі поля!';
        return;
    }
    else {
        label.innerHTML = '';
    }

    const data = {
        fullName: fullName,
        email: email,
        feedbackType: feedbackType,
        feedbackCategory :feedbackCategory,
        content: content
    }
    sendFeedbackData(data);
}

const sendFeedbackData = (data) => {
    console.log(data);
    console.log(data.password);
    fetch('/auth/registration', {
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
            console.error('Error:', error);
        });
}

