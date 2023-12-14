
const barEl = document.getElementById("bar");

const linksEl = document.getElementById("links");

const passwordCheckboxEl = document.getElementById("password-checkbox");

const passwordLabelEl = document.getElementById("password-label");

const PasswordInputEl = document.querySelector(".password-input");

const plusTaskEl = document.getElementById("plus-task");

const addTaskFormEl = document.getElementById("add-task-form");

const closePopupEl = document.getElementById("close-popup");

const profileBtnEl = document.getElementById("profile-btn");

const dropDownMenuEl = document.getElementById("drop-down-menu");

// Responsive menu bar:

barEl.addEventListener("click", _ => {

    linksEl.classList.toggle("show");

});

// show - hide password:

if (passwordCheckboxEl != null) {

    passwordCheckboxEl.addEventListener("change", (event) => {

        if (!event.currentTarget.checked) {

            PasswordInputEl.setAttribute("type", "password")

            passwordLabelEl.textContent = "Show Password";

        } else {

            PasswordInputEl.setAttribute("type", "text");

            passwordLabelEl.textContent = "Hide Password";

        }

    });

}

// show - hide add task form:

if (plusTaskEl != null && addTaskFormEl != null && closePopupEl != null) {

    plusTaskEl.addEventListener("click", event => {

        addTaskFormEl.classList.add("show");

        event.currentTarget.style.display = "none";

    });

    closePopupEl.addEventListener("click", _ => {

        addTaskFormEl.classList.remove("show");

        plusTaskEl.style.display = "block";

    });
    
}

// show - hide drop down menu:

if (profileBtnEl != null && dropDownMenuEl != null) {

    document.addEventListener("click", event => {

        if (
            
            !event.target.classList.contains("drop-down-menu") &&

            !event.target.parentElement.classList.contains("drop-down-menu")
        
        ) {

            if (dropDownMenuEl.classList.contains("show")) {

                dropDownMenuEl.classList.remove("show");

            } else {

                if (
                    
                    event.target.classList.contains("profile-btn") ||

                    event.target.parentElement.classList.contains("profile-btn")
                
                ) {

                    dropDownMenuEl.classList.add("show");
                    
                }

            }

        }

    });

}
