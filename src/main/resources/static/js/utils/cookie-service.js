function setCookie(name, value, expiresInDays) {
    const expires = new Date();
    expires.setDate(expires.getDate() + expiresInDays);
    document.cookie = `${name}=${encodeURIComponent(value)}; 
    expires=${expires.toUTCString()}; path=/; secure; HttpOnly; domain=localhost; SameSite=Lax`;
}


function getCookie(name) {
    const cookieName = `${name}=`;
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim();
        if (cookie.indexOf(cookieName) === 0) {
            return decodeURIComponent(cookie.substring(cookieName.length));
        }
    }
    return null;
}

function deleteCookie(name) {
    document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/; secure; SameSite=Lax`;
}

function saveTokensToCookies(accessToken, refreshToken) {

    const tokenPayloadBase64 = accessToken.split('.')[1];
    const tokenPayload = JSON.parse(atob(tokenPayloadBase64));
    const username = tokenPayload.sub;

    setCookie('access_token', accessToken, 1);

    setCookie('refresh_token', refreshToken, 30);

    document.cookie = `username=${encodeURIComponent(username)}; path=/; SameSite=Lax; secure`;
}

function cookiesLogout() {
    deleteCookie('is_user_authorized');
    deleteCookie('access_token');
    deleteCookie('refresh_token');
    deleteCookie('username');
}

export {saveTokensToCookies,cookiesLogout, getCookie,setCookie}