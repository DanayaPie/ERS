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
            window.location.href = '../HTML/employeeHomepage.html';
        } else if (userObj.userRole === 'finance manager') {
            window.location.href = '../HTML/financeManagerHomepage.html';
        }
    }
});

/*
    sign up info
*/
async function signup() {

    let firstNameInput = document.querySelector('#firstName-input');
    let lastNameInput = document.querySelector('#lastName-input');
    let usernameInput = document.querySelector('#username-input');
    let passwordInput = document.querySelector('#password-input');
    let emailInput = document.querySelector('#email-input');
    let userRoleInput = document.querySelector('#userRole-input');

    try {
        let res = await fetch('http://127.0.0.1:8080/signup', {
            method: 'POST',
            credentials: 'include',

            body: JSON.stringify({
                firstName: firstNameInput.value,
                lastName: lastNameInput.value,
                username: usernameInput.value,
                password: passwordInput.value,
                email: emailInput.value,
                userRole: userRoleInput.value
            })
        });

        let data = await res.json();

        if (res.status === 400) {
            let signUpErrorMessage = document.querySelector('#errorMessage')
            
            signUpErrorMessage.textContent = data.message;
            signUpErrorMessage.style.color = 'red';
        }

        if (res.status === 200) {
            window.location.href = '../index.html';
        }
    
    } catch(err) {
        console.log(err);
    }
}

/*
    sign up functionality
*/
let signUpBtn = document.querySelector('#signUp-btn');

signUpBtn.addEventListener('click', signupBtnClicked);

function signupBtnClicked() {
    signup();

}

// 'Enter' key set up
var input = document.getElementById("userRole-input");

// Execute a function when the user releases a key on the keyboard
input.addEventListener("keyup", function(event) {
  // Number 13 is the "Enter" key on the keyboard
  if (event.keyCode === 13) {
    // Cancel the default action, if needed
    event.preventDefault();
    // Trigger the button element with a click
    document.getElementById("signUp-btn").click();
  }
});