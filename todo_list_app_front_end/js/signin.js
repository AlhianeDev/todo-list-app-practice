
import { postData } from "./utils.js";

const SIGNIN_URL = "http://localhost:8080/api/v1/auth/login";

const signinFormEl = document.getElementById("sign-in-form");

const neededEls = document.querySelectorAll(".needed");

const invalidMsgEls = document.querySelectorAll(".invalid-msg");

const emailEl = document.getElementsByName("email")[0];

const passwordEl = document.getElementsByName("password")[0];

signinFormEl.addEventListener("submit", async (event) => {

    event.preventDefault();

    const user = {

        email: emailEl.value,

        password: passwordEl.value

    }

    postData(SIGNIN_URL, user).then(response => {

        console.log(response);

        if (response.token == undefined) {

            passwordEl.value = "";

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

            sessionStorage.setItem("token", response.token);

            sessionStorage.setItem("userId", response.userId);

            window.location.href = "/just-do-it.html";

        }

    }).catch(error => {

        console.log(error);

    })

});
