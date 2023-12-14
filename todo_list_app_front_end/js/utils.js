
export async function postData(url, object = {}, token = "") {

    const response = await fetch(url, {

        method: "POST",
        
        mode: "cors", // no-cors, *cors, same-origin

        headers: {

            'Accept': 'application/json',

            'Content-Type': 'application/json',

            'Authorization': `Bearer ${token}`
            
        },
    
        body: JSON.stringify(object),
    
    });

    return response.json();

}

export async function getData(url = "", token = "") {

    const response = await fetch(url, {

        method: "GET",
        
        mode: "cors", // no-cors, *cors, same-origin

        headers: {

            'Accept': 'application/json',

            'Content-Type': 'application/json',

            'Authorization': `Bearer ${token}`
            
        }
    
    });

    return response.json();

}

export async function putData(url = "", object = {}, token = "") {

    const response = await fetch(url, {

        method: "PUT",
        
        mode: "cors", // no-cors, *cors, same-origin

        headers: {

            'Accept': 'application/json',

            'Content-Type': 'application/json',

            'Authorization': `Bearer ${token}`
            
        },
    
        body: JSON.stringify(object),
    
    });

    return response.json();

}

export async function voidPutData(url = "", token = "") {

    await fetch(url, {

        method: "PUT",
        
        mode: "cors", // no-cors, *cors, same-origin

        headers: {

            'Accept': 'application/json',

            'Content-Type': 'application/json',

            'Authorization': `Bearer ${token}`
            
        }
    
    });

} 

export async function deleteData(url = "", token = "") {

    await fetch(url, {

        method: "DELETE",
        
        mode: "cors", // no-cors, *cors, same-origin

        headers: {

            'Accept': 'application/json',

            'Content-Type': 'application/json',

            'Authorization': `Bearer ${token}`
            
        }
    
    });

} 

export async function patchData(url = "", token = "", requestHeader = {}) {

    const response = await fetch(url, {

        method: "PATCH",
        
        mode: "cors", // no-cors, *cors, same-origin

        headers: {

            'Accept': 'application/json',

            'Content-Type': 'application/json',

            'Authorization': `Bearer ${token}`,
            
            'firstName': requestHeader.firstName,

            'lastName': requestHeader.lastName,

            'password': requestHeader.password,

            'newEmail': requestHeader.newEmail,

            'oldPassword': requestHeader.oldPassword,

            'newPassword': requestHeader.newPassword
            
        }
    
    });

    return response.json();

}
