const anchors = document.getElementsByClassName('pageAnchor');

for (let i = 0; i < anchors.length ; i++) {
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
    controlDisapearingBG(1);
}
