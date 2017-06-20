function setCookie(value) {
    document.cookie = "cookie-msg-test=" + value + "; path=/";
    return true;
}

function updateMessage() {
    var t = document.forms['sender'].elements['username'];
    setCookie(t.value);
    setTimeout(updateMessage, 100);
}