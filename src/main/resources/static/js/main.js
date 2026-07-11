
const createBoardButton = document.querySelector('#create-board-button');

let user_id;
let username;

async function fecthData(url, method, body) {
    
    const fetchInfo = {
        method: method,
        headers: {'Content-Type': "application/json"},
    }

    if (body) {
        fetchInfo.body = JSON.stringify(body);
    }

    const res = await fetch(url, fetchInfo);

    if (!res.ok) {
        const errorText = await res.text(); 
        console.error("Fetch error:", res.status, errorText);
        return null;
    }

    let data = await res.json();

    return data;
}

async function createBoard(name, ownerIsUser, ownerId) {
    const url = '/board/new';

    const board = {
        name: name,
        ownerisUser: ownerIsUser,
        ownerId: ownerId,
    };

    const res = await fecthData(url, 'POST', board);

    if (!res) {
        console.log("Probleme beim erstellen eines Boards");
        return
    }

    console.log("erfolgreich", res);
}

async function me() {
    const url = '/user/me';

    let map = await fecthData(url, 'GET', null);

    user_id = map.user_id;
    username = map.username;
}

document.addEventListener('DOMContentLoaded', async function() {
    
    await me();
    
    const url = `/board/private/${user_id}`;

    let boards = await fecthData(url, 'GET', null);

    console.log(boards);

}); 

createBoardButton.addEventListener('click', () => {

    createBoard("BoardJS", true, user_id);
});