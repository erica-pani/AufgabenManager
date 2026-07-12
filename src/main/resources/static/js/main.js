
const createBoardButton = document.querySelector('#create-board-button');
const modalOverlay = document.querySelector('#modal-overlay');
const boardCreationButton = document.querySelector('#board-creation-button');

let user_id;
let username;
let currentTeam = user_id; // id vom aktuellen Team

let privateCache = []; // cache für private borads
let publicCache = []; //cache für public boards

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

function clickedIsModal(event) {
    const clicked = event.srcElement.id;

    if (clicked === "modal-overlay") {
        return true;
    }

    return false;
}

document.addEventListener('DOMContentLoaded', async function() {
    
    await me();
    
    const url = `/board/private/${user_id}`;

    let boards = await fecthData(url, 'GET', null);

    console.log(boards);

}); 

createBoardButton.addEventListener('click', () => {
    modalOverlay.classList.remove('hidden');
});

modalOverlay.addEventListener('click', (event) => {
    
    if (clickedIsModal(event)) {
        modalOverlay.classList.add('hidden');
    }

});

boardCreationButton.addEventListener('click', () => {
    const name = document.querySelector('input[name="board-name-input"]').value.trim();
    let ownerIsUser = false;

    if (currentTeam == user_id) {
        ownerIsUser = true;
    }

    createBoard(name, ownerIsUser, currentTeam);
    
});