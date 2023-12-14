
import { deleteData, getData, patchData } from "./utils.js";

const BASE_URL = "http://localhost:8080/api/v1/users";

const USER_ID = sessionStorage.getItem("userId");

const TOKEN = sessionStorage.getItem("token");

const usernameEl = document.getElementById("username");

const accountBtnEl = document.getElementById("account-btn");

const fullContainerEl = document.querySelector(".full-container");

const closeAccountPopup = document.getElementById("close-account-popup");

const changeBtnEl = document.querySelectorAll(".change-btn");

const formsEl = fullContainerEl.querySelectorAll("form");

const deleteAccountBtnEl = document.getElementById("delete-account-btn");

const logoutBtnEl = document.getElementById("logout-btn");

if (TOKEN == null) window.location.href = "/signin.html";

function updateData() {

    getData(`${BASE_URL}/${USER_ID}`, TOKEN).then(response => {

        usernameEl.textContent = response.firstName;

    });

}

updateData();

accountBtnEl.addEventListener("click", event => {

    event.preventDefault();

    fullContainerEl.classList.add("show");

});

closeAccountPopup.addEventListener("click", _ => {

    fullContainerEl.classList.remove("show");

});

changeBtnEl.forEach(btn => {

    btn.addEventListener("click", event => {

        const changeBtn = event.target;

        const theClassName = changeBtn.getAttribute("data-access");

        const formEl = document.querySelector(`.${theClassName}`);

        const targetData = theClassName.split("-")[0];

        if (formEl.classList.contains("show")) {

            formEl.classList.remove("show");

            changeBtn.textContent = `Change ${targetData}`;

        } else {

            formEl.classList.add("show");

            changeBtn.textContent = `I won't change ${targetData}`;

        }

    });

});

const requestHeader = {

    'firstName': "",

    'lastName': "",

    'password': "",

    'newEmail': "",

    'oldPassword': "",

    'newPassword': "",

}

function changeFirstAndLastName(form, target) {

    const inputEl = document.getElementsByName(target)[0];

    getData(`${BASE_URL}/${USER_ID}`, TOKEN)
        
        .then(response => { inputEl.value = response[target]; });

    form.addEventListener("submit", event => {

        event.preventDefault();

        Swal.fire({

            title: `Do you want to change your ${target}?`,

            showDenyButton: true,

            confirmButtonText: "Change",

            denyButtonText: `Don't Change`
            
        }).then((result) => {
                
            if (result.isConfirmed) {

                requestHeader[target] = inputEl.value;

                patchData(`${BASE_URL}/update-${target}`, TOKEN, requestHeader)
                    
                    .then(response => {

                        console.log(response);

                        getData(`${BASE_URL}/${USER_ID}`, TOKEN).then(response => {
                            
                            inputEl.value = response[target];

                        });

                    });

                event.target.classList.remove("show");

                Swal.fire("Changed!", "", "success");

                updateData();

                const changeBtn = document.querySelector(`[data-access='${target}-form']`);

                changeBtn.textContent = `Change ${target}`;
                
            } else if (result.isDenied) {

                Swal.fire("Changes are not saved", "", "info");

            }

        });

    });

}

function changeEmailAndPassword(form, target, thOld, thNew, password) {

    const oldInputEl = document.getElementsByName(thOld)[0];

    const newInputEl = document.getElementsByName(thNew)[0];

    const passwordInputEl = document.getElementsByName(password)[0];

    if (form.classList.contains("email-form")) {

        getData(`${BASE_URL}/${USER_ID}`, TOKEN)
            
            .then(response => { oldInputEl.value = response.email });

    }

    form.addEventListener("submit", event => {

        event.preventDefault();

        Swal.fire({

            title: `Do you want to change your ${target}?`,

            showDenyButton: true,

            confirmButtonText: "Change",

            denyButtonText: `Don't Change`
            
        }).then((result) => {
                
            if (result.isConfirmed) {

                if (target == "email") requestHeader.password = passwordInputEl.value;

                if (target == "password") requestHeader[thOld] = oldInputEl.value;

                requestHeader[thNew] = newInputEl.value;

                patchData(`${BASE_URL}/update-${target}`, TOKEN, requestHeader)

                    .then(response => { console.log(response) });

                event.target.classList.remove("show");

                Swal.fire("Changed!", "", "success");

                sessionStorage.clear();

                window.location.href = "/signin.html";
                
            } else if (result.isDenied) {

                Swal.fire("Changes are not saved", "", "info");

            }

        });

    });

}

formsEl.forEach(form => {

    if (form.classList.contains("firstName-form")) {

        changeFirstAndLastName(form, "firstName");

    } else if (form.classList.contains("lastName-form")) {

        changeFirstAndLastName(form, "lastName");

    } else if (form.classList.contains("email-form")) {

        changeEmailAndPassword(
            
            form,
            
            "email", "oldEmail", "newEmail", "password"
        
        );

    } else {

        changeEmailAndPassword(
            
            form,
            
            "password", "oldPassword", "newPassword", "passwordConfirm"
        
        );

    }

});

deleteAccountBtnEl.addEventListener("click", (event) => {

    event.preventDefault();

    Swal.fire({

        title: `Do you want to delete your account?`,

        showDenyButton: true,

        confirmButtonText: "Delete",

        denyButtonText: `Don't Delete`
        
    }).then(result => {

        if (result.isConfirmed) {

            deleteData(`${BASE_URL}/${USER_ID}`, TOKEN);

            Swal.fire("Deleted!", "", "success");

            window.location.href = "/signup.html";

        } else {

            Swal.fire("Account is not deleted", "", "info");

        }

    });

});

logoutBtnEl.addEventListener("click", event => {

    event.preventDefault();

    sessionStorage.clear();

    window.location.href = "/signin.html";

});
