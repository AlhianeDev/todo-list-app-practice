
import { postData, deleteData, getData, voidPutData, putData } from "./utils.js";

const TOKEN = sessionStorage.getItem("token");

const BASE_URL = "http://localhost:8080/api/v1/todos";

let pageNumber = 1;

const todosContainerEl = document.getElementById("todos-container");

const addTaskFormEl = document.getElementById("add-task-form");

const submitBtn = addTaskFormEl.querySelector("input[type=\"submit\"]");

const taskIdEl = document.getElementsByName("taskId")[0];

const taskTitleEl = document.getElementsByName("taskTitle")[0];

const taskDescEl = document.getElementsByName("taskDesc")[0];

const plusTaskEl = document.getElementById("plus-task");

const paginationEl = document.getElementById("pagination");

if (TOKEN == null) window.location.href = "/signin.html";

function generatePagination() {

    paginationEl.innerHTML = "";

    let counter = 1;

    getData(BASE_URL, TOKEN).then(response => {

        for (let i = 0; i < response.length; i += 6) {

            const aEl = document.createElement("a");

            aEl.href = "#";

            aEl.textContent = counter;

            if (counter == pageNumber) aEl.className = "active";

            aEl.addEventListener("click", event => {

                event.preventDefault();

                const aEls = paginationEl.querySelectorAll("a");

                aEls.forEach(a => { a.classList.remove("active"); });

                event.target.classList.add("active");

                pageNumber = +aEl.textContent;

                readTodos(pageNumber);

            });

            paginationEl.append(aEl);

            counter++;

        }

    });

}

generatePagination();

function readTodos(pageNumber) {

    todosContainerEl.innerHTML = "";
    
    getData(`${BASE_URL}/${pageNumber - 1}/6`, TOKEN).then(response => {

        for (let index in response) createTodo(response[index], response[index].id);

    });

}

readTodos(pageNumber);

function readTodoById(id) {

    return getData(`${BASE_URL}/${id}`, TOKEN);

}

function confirmTodo(id) {

    voidPutData(`${BASE_URL}/confirmation/${id}`, TOKEN).then(_ => { 
        
        generatePagination();

        readTodos(pageNumber);
    
    });

}

function updateTodo(id) {

    addTaskFormEl.classList.add("show");

    submitBtn.setAttribute("value", "Update Task");

    readTodoById(id).then(response => {

        taskIdEl.value = response.id;

        taskTitleEl.value = response.todoTitle;

        taskDescEl.value = response.todoDesc;

    });

}

function deleteTodo(id) {

    Swal.fire({

        title: `Do you want to delete todo?`,

        icon: "warning",

        showDenyButton: true,

        confirmButtonText: "Delete",

        denyButtonText: `Don't Delete`
        
    }).then(result => {

        if (result.isConfirmed) {

            deleteData(`${BASE_URL}/${id}`, TOKEN).then(_ => { 

                getData(`${BASE_URL}/${pageNumber - 1}/6`, TOKEN).then(response => {

                    if (response.length == 0) pageNumber--;

                    console.log(pageNumber);

                    generatePagination();

                    readTodos(pageNumber);
            
                });
            
            });

            Swal.fire("Deleted!", "", "success");

        } else {

            Swal.fire("Todo is not deleted", "", "info");

        }

    });

}

function createTodo(todo, id) {

    const todoEl = document.createElement("div");

    todoEl.classList.add("todo");

    if (todo.isConfirmed == true) todoEl.classList.add("confirm");

    const actionsEl = document.createElement("div");

    actionsEl.className = "actions";

    const confirmBtn = document.createElement("a");

    confirmBtn.setAttribute("href", "#");

    confirmBtn.textContent = todo.isConfirmed == true ? "Unconfirm" : "Confirm";

    confirmBtn.addEventListener("click", _ => { confirmTodo(id) });

    actionsEl.appendChild(confirmBtn);

    const updateBtn = document.createElement("a");

    updateBtn.setAttribute("href", "#");

    updateBtn.textContent = "Update";

    updateBtn.addEventListener("click", _ => { updateTodo(id) });

    actionsEl.appendChild(updateBtn);

    const deleteBtn = document.createElement("a");

    deleteBtn.setAttribute("href", "#");

    deleteBtn.textContent = "Delete";

    deleteBtn.addEventListener("click", _ => { deleteTodo(id) });

    actionsEl.appendChild(deleteBtn);

    todoEl.appendChild(actionsEl);

    const todoHeadEl = document.createElement("div");

    todoHeadEl.className = "todo-head";

    const todoTitleEl = document.createElement("h3");

    todoTitleEl.className = "todo-title";

    todoTitleEl.textContent = todo.todoTitle;

    todoHeadEl.appendChild(todoTitleEl);

    const dotsEl = document.createElement("div");

    dotsEl.className = "dots";

    dotsEl.addEventListener("click", _ => { actionsEl.classList.toggle("show") });

    const spanEl1 = document.createElement("span");

    const spanEl2 = document.createElement("span");

    const spanEl3 = document.createElement("span");

    dotsEl.appendChild(spanEl1);

    dotsEl.appendChild(spanEl2);

    dotsEl.appendChild(spanEl3);

    todoHeadEl.appendChild(dotsEl);

    todoEl.appendChild(todoHeadEl);

    const todoDescEl = document.createElement("p");

    todoDescEl.className = "todo-desc";

    todoDescEl.textContent = todo.todoDesc;

    todoEl.appendChild(todoDescEl);

    const createdDateEl = document.createElement("h4");

    createdDateEl.className = "created-date";

    let text = new Date(todo.createdDate).toString();

    let index = text.indexOf('(');

    let date = text.substring(0, index - 1);

    createdDateEl.textContent = date;

    todoEl.appendChild(createdDateEl);

    todosContainerEl.appendChild(todoEl);

}

addTaskFormEl.addEventListener("submit", (event) => {

    event.preventDefault();

    if (submitBtn.getAttribute("value") === "Add Task") {

        const todo = {

            "todoTitle": taskTitleEl.value,

            "todoDesc": taskDescEl.value

        }

        postData(BASE_URL, todo, TOKEN).then(response => {

            if (response.error_message != undefined) {

                Swal.fire({

                    title: "Warning!",

                    text: `${response.error_message}`,

                    icon: "warning",

                    confirmButtonColor: "#3085d6"

                });

            } else {

                taskTitleEl.value = "";

                taskDescEl.value = "";

                generatePagination();

                readTodos(pageNumber);

            }

            taskTitleEl.value = "";

            taskDescEl.value = "";

        });
        
    } else {

        const todo = {

            "id": +(taskIdEl.value),

            "todoTitle": taskTitleEl.value,

            "todoDesc": taskDescEl.value

        }

        putData(BASE_URL, todo, TOKEN).then(response => {

            if (response.error_message != undefined) {

                Swal.fire({

                    title: "Warning!",

                    text: `${response.error_message}`,

                    icon: "warning",

                    confirmButtonColor: "#3085d6"

                });

            } else {
                
                submitBtn.setAttribute("value", "Add Task");

                addTaskFormEl.classList.remove("show");

                plusTaskEl.style.display = "block";

                generatePagination();

                readTodos(pageNumber);

            }

            taskTitleEl.value = "";

            taskDescEl.value = "";

        });

    }

});
