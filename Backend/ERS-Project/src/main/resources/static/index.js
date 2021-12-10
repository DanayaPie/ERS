/*
    check login status
*/
window.addEventListener('load', async () => {

    console.log('index.checkLoginStatus executed');

    let res = await fetch('http://127.0.0.1:8080/checkloginstatus', {
        method: 'GET',
        // ensure that the browser retains cookie and includes it in the future
        credentials: 'include' 
    });

    // 200 code = already logged in
    if (res.status === 200) {
        let userObj = await res.json(); // 1st fetch promise as JSON

        if (userObj.userRole === 'employee') {
            window.location.href = './HTML/employeeHomepage.html';
        } else if (userObj.userRole === 'finance manager') {
            window.location.href = './HTML/financeManagerHomepage.html';
        }
    }
});


/*
    login info
*/
async function login() {
    let usernameInput = document.querySelector('#username-input');
    let passwordInput = document.querySelector('#password-input');

    try {
        let res = await fetch('http://127.0.0.1:8080/login', {
            method: 'POST',
            credentials: 'include',
            // JSON.stringify will take a JavaScript object and turn it into JSON
            body: JSON.stringify({
                username: usernameInput.value,
                password: passwordInput.value
            })
        });

        let data = await res.json(); // 1st fetch promise as JSON

        /* 
            check if login is incorrect or not 
        */
        // 400 status code = represent an object with 'message' property 
        if (res.status === 400) {
            // let loginErrorMessage = document.createElement('p'); // create <p>
            let loginErrorMessage = document.querySelector('#errorMessage');

            loginErrorMessage.textContent = data.message; //
            loginErrorMessage.style.color = 'red';
            // adding the loginErrorMessage to the bottom of #login-info
            // loginDiv.appendChild(loginErrorMessage);
        }

        // 200 status code = data variable represent an object corresponds w user
        if (res.status === 200) {
            if (data.userRole === 'employee') {
                window.location.href = './HTML/employeeHomepage.html';
            } else if (data.userRole === 'finance manager') {
                window.location.href = './HTML/financeManagerHomepage.html';
            }
        }
    } catch(err) {
        console.log(err);
    }
}

/*
    Login functionality
*/
let loginBtn = document.querySelector('#login-btn');

loginBtn.addEventListener('click', loginBtnClicked); // 'click'

function loginBtnClicked() {
    login();
}

// 'Enter' key set up
var input = document.getElementById("password-input");

// Execute a function when the user releases a key on the keyboard
input.addEventListener("keyup", function(event) {
  // Number 13 is the "Enter" key on the keyboard
  if (event.keyCode === 13) {
    // Cancel the default action, if needed
    event.preventDefault();
    // Trigger the button element with a click
    document.getElementById("login-btn").click();
  }
});