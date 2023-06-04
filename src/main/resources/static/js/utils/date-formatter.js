const formatDate = (date) => {
    const newDate = new Date(date);
    const yyyy = newDate.getFullYear();
    let mm = newDate.getMonth() + 1;
    let dd = newDate.getDate();

    if (dd < 10) dd = '0' + dd;
    if (mm < 10) mm = '0' + mm;

    return dd + '.' + mm + '.' + yyyy;
}
const formatMonth = (date) =>{
    const monthString = ["Січень",
        "Лютий",
        "Березень",
        "Квітень",
        "Травень",
        "Червень",
        "Липень",
        "Серпень",
        "Вересень",
        "Жовтень",
        "Листопад",
        "Грудень"];

    const dateObject = new Date(date);
    return monthString[dateObject.getMonth()] + " " + dateObject.getFullYear() ;
}

export {formatDate,formatMonth}