
import { postData } from "./utils.js";

const SIGNUP_URL = "http://localhost:8080/api/v1/auth/register";

const signupFormEl = document.getElementById("sign-up-form");

const neededEls = document.querySelectorAll(".needed");

const invalidMsgEls = document.querySelectorAll(".invalid-msg");

const firstNameEl = document.getElementsByName("firstName")[0];

const lastNameEl = document.getElementsByName("lastName")[0];

const emailEl = document.getElementsByName("email")[0];

const passwordEl = document.getElementsByName("password")[0];

signupFormEl.addEventListener("submit", async (event) => {

    event.preventDefault();

    const user = {

        firstName: firstNameEl.value,

        lastName: lastNameEl.value,

        email: emailEl.value,

        password: passwordEl.value

    }

    postData(SIGNUP_URL, user).then(response => {

        if (response.token == undefined) {

            neededEls.forEach(neededEl => {
                
                neededEl.classList.remove("invalid");

            });

            invalidMsgEls.forEach(invalidMsgEl => {

                invalidMsgEl.textContent = "";

            });

            for (const prop in response) {


                document.getElementsByName(prop)[0].classList.add("invalid");

                document.querySelector(`.invalid-${prop}`).textContent = response[prop];

            }

        } else {

            window.location.href = "/signin.html";

        }

    }).catch(error => {

        console.log(error);

    })

});
