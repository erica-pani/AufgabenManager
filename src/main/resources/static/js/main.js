
const createBoardButton = document.querySelector('#create-board-button');
const modalOverlay = document.querySelector('#modal-overlay');
const boardCreationButton = document.querySelector('#board-creation-button');
const boardList = document.querySelector('#board-list');
const newTeamButton = document.querySelector('#new-team-button');
const teamList = document.querySelector('.team-list');

let user_id;
let username;
let currentTeam = null; // id vom aktuellen Team

const privateCache = []; // cache für private borads
let publicCache = {}; //cache für public boards

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

    return await res.json();
}

async function me() {
    const url = '/user/me';

    let map = await fecthData(url, 'GET', null);

    user_id = map.user_id;
    username = map.username;
}

async function createBoard(name, ownerIsUser, ownerId) {
    const url = '/board/new';

    const board = {
        name: name,
        ownerisUser: ownerIsUser,
        ownerId: ownerId,
    };

    if (ownerIsUser) {
        currentTeam = null;
    }

    const res = await fecthData(url, 'POST', board);

    if (!res) {
        console.log("Probleme beim erstellen eines Boards");
        return
    }

    console.log(res);

    cacheBoard(res);
    renderBoard(res);

    console.log("erfolgreich", res);
}

function cacheBoard(board) {
    /*
    if (board.ownerId === user_id) {
        if(!privateCache.includes(board)) {
        privateCache.push(board);
        return;
    }

    const ownerId = board.ownerId;

    publicCache[ownerId] ||= [];

    if(!publicCache[ownerId].includes(board)) {
        publicCache[ownerId].push(board);
    }

    */
}

async function fetchTeams() {
    const url = `/team/teams?userId=${user_id}`;

    const teams = await fecthData(url, 'GET', null);

    console.log(teams);

    teams.forEach(team => {
        renderTeam(team);
    });
}

async function createTeam(name) {
    
    const url = '/team/create'

    const teamBody = {
        name: name,
        creatorId: user_id,
    }

    const team = await fecthData(url, 'POST', teamBody);

    renderTeam(team);
}

function clickedIsModal(event) {
    const clicked = event.srcElement.id;

    if (clicked === "modal-overlay") {
        return true;
    }

    return false;
}

//true wenn User false wenn Team
async function fetchBoards(userOrTeams) {

    let url = `/board/teams/${currentTeam}`;

    if (userOrTeams) {
        url = `/board/private/${user_id}`;
    }
    
    let boards = await fecthData(url, 'GET', null);

    console.log(boards);

    boards.forEach(element => {
        cacheBoard(element);
        renderBoard(element);
    });
}

async function changeTeam(clicked) {
    document.querySelector('.active').classList.remove('active');
    clicked.classList.add('active');
    currentTeam = clicked['teamId'];

    boardList.innerHTML = '';

    boards = await fetchBoards(false);
}

async function onAppStart() {
    await me();
    await fetchTeams();
    await fetchBoards(true);
}

function renderTeam(team) {
    const newTeam  = document.createElement('button');

    newTeam.textContent = `${team.name}`;
    newTeam.classList.add('team');

    Object.defineProperty(newTeam, 'teamId', {
        value: team.teamId,
        writable: false,
        configurable: false,
        enumerable: true
    });

    console.log(newTeam['teamId']);

    teamList.appendChild(newTeam);
}

function renderBoard(board) {
    const newBoard = document.createElement('div');

    newBoard.innerHTML = `
        <h2>${board.name}</h2>
        <p>${board.exerciseNumber} Aufgaben</p>
    `;

    newBoard.classList.add('board');

    boardList.insertBefore(newBoard, boardList.lastElementChild);
}

document.addEventListener('DOMContentLoaded', () => {
    onAppStart();
}); 

createBoardButton.addEventListener('click', () => {
    document.querySelector('#card-header').textContent = "Neues Board";
    
    modalOverlay.classList.remove('hidden');
});

modalOverlay.addEventListener('click', (event) => {
    
    if (clickedIsModal(event)) {
        modalOverlay.classList.add('hidden');
    }

});

boardCreationButton.addEventListener('click', () => {
    const name = document.querySelector('input[name="board-name-input"]').value.trim();

    if (document.querySelector('#card-header').textContent === "Neues Team") {
        createTeam(name);
        modalOverlay.classList.add('hidden');
        document.querySelector('input[name="board-name-input"]').value = '';
        return;
    }

    let ownerIsUser = false;

    if (!currentTeam) {
        ownerIsUser = true;
        currentTeam = user_id;
    }

    createBoard(name, ownerIsUser, currentTeam);

    modalOverlay.classList.add('hidden');
    document.querySelector('input[name="board-name-input"]').value = '';
});

newTeamButton.addEventListener('click', () => {
    document.querySelector('#card-header').textContent = "Neues Team";

    modalOverlay.classList.remove('hidden');
});

teamList.addEventListener('click', (event) => {
    const clicked = event.target.closest('.team');
    if (!clicked) return;

    changeTeam(clicked);
});