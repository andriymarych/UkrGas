
const createFeedback = () => {
    const label = document.getElementById('feedback-label');

    const fullName = document.getElementById('full-name-input').value;
    const email = document.getElementById('email-input').value;
    const feedbackType = document.getElementById('feedback-type').value;
    const feedbackCategory = document.getElementById('feedback-category').value;
    const content = document.getElementById("feedback-content").value;

    if (fullName === '' || email === '' || feedbackType === ''
        || feedbackCategory === '' || content ==='') {
        label.innerHTML = '* Заповніть усі  поля';
        return;
    }if(content.length < 10){
        label.innerHTML = '* Текстове поле звернення повинне містити більше 30 символів';
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
    fetch('api/v1/feedback/create', {
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
            displayCreatedFeedback(data)
            console.log(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

const displayCreatedFeedback = (feedbackResponseObject) => {
    let feedbackResponseDiv  = document.createElement("div");
    feedbackResponseDiv.setAttribute("id","feedback-response");

    let firstLineResponse = document.createElement("p");
    firstLineResponse.setAttribute("class","first-line-response");
    firstLineResponse.innerHTML = "Дякуємо за відгук !";

    let secondLineResponse = document.createElement("p");
    secondLineResponse.setAttribute("class","second-line-response");
    secondLineResponse.innerHTML = "Номер зареєстрованого звернення  - " + feedbackResponseObject.data.id;

    document.getElementById("form-container").style.display = "none";

    feedbackResponseDiv.appendChild(firstLineResponse);
    feedbackResponseDiv.appendChild(secondLineResponse);

    document.getElementById("main-section").appendChild(feedbackResponseDiv);
}