
const BASE_URL = ''; // Falls Backend auf gleichem Server läuft. Sonst z.B. 'http://localhost:8080'

const ENDPOINTS = {
    USER_ME: `${BASE_URL}/user/me`,
    BOARD_NEW: `${BASE_URL}/board/new`,
    BOARD_PRIVATE: (userId) => `${BASE_URL}/board/private/${userId}`,
    BOARD_TEAM: (teamId) => `${BASE_URL}/board/teams/${teamId}`,
    TEAM_TEAMS: (userId) => `${BASE_URL}/team/teams?userId=${userId}`,
    TEAM_CREATE: `${BASE_URL}/team/create`,
};

const createBoardButton = document.querySelector('#create-board-button');
const modalOverlay = document.querySelector('#modal-overlay');
const boardCreationButton = document.querySelector('#board-creation-button');
const boardList = document.querySelector('#board-list');
const newTeamButton = document.querySelector('#new-team-button');
const teamList = document.querySelector('.team-list');

let user_id;
let username;
let currentTeam = null; // id vom aktuellen Team

let privateCache = []; // cache für private boards
let publicCache = {};  // cache für public boards


async function fetchData(url, method, body) {
    const fetchInfo = {
        method: method,
        headers: { 'Content-Type': "application/json" },
    };

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
    let map = await fetchData(ENDPOINTS.USER_ME, 'GET', null);

    if (map) {
        user_id = map.user_id;
        username = map.username;
    }
}

async function createBoard(name, ownerIsUser, ownerId) {
    const board = {
        name: name,
        ownerisUser: ownerIsUser,
        ownerId: ownerId,
    };

    if (ownerIsUser) {
        currentTeam = null;
    }

    const res = await fetchData(ENDPOINTS.BOARD_NEW, 'POST', board);

    if (!res) {
        console.log("Probleme beim Erstellen eines Boards");
        return;
    }

    cacheBoard(res);
    renderBoard(res);
    console.log("Erfolgreich erstellt:", res);
}

async function fetchTeams() {
    const teams = await fetchData(ENDPOINTS.TEAM_TEAMS(user_id), 'GET', null);

    if (teams && Array.isArray(teams)) {
        teams.forEach(team => {
            renderTeam(team);
        });
    }
}

async function createTeam(name) {
    const teamBody = {
        name: name,
        creatorId: user_id,
    };

    const team = await fetchData(ENDPOINTS.TEAM_CREATE, 'POST', teamBody);

    if (team) {
        renderTeam(team);
    }
}

// true wenn User, false wenn Team
async function fetchBoards(userOrTeams) {
    const url = userOrTeams 
        ? ENDPOINTS.BOARD_PRIVATE(user_id) 
        : ENDPOINTS.BOARD_TEAM(currentTeam);

    let boards = await fetchData(url, 'GET', null);

    boardList.querySelectorAll(':scope > *:not(:last-child)')
        .forEach(el => el.remove());

    if (boards && Array.isArray(boards)) {
        boards.forEach(element => {
            renderBoard(element);
        });
    }

    if(userOrTeams) {
        privateCache = boards;
        return;
    }

    cacheBoards(boards);
}


function cacheBoards(boards) {

    if (!publicCache[currentTeam]) {
        publicCache[currentTeam] = [];
    }

    publicCache[currentTeam] = boards;
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

function clickedIsModal(event) {
    return event.srcElement.id === "modal-overlay";
}

async function changeToPrivate() {
    currentTeam = null;

    privateCache.forEach(board => {
        renderBoard(board);
    });

    await fetchBoards(true);
}

async function changeTeam(clicked) {
    const activeItem = document.querySelector('.active');
    if (activeItem) {
        activeItem.classList.remove('active');
    }
    clicked.classList.add('active');

    boardList.querySelectorAll(':scope > *:not(:last-child)')
        .forEach(el => el.remove());

    if (clicked.id === "private") {
        await changeToPrivate();
        return;
    }

    currentTeam = clicked['teamId'];

    (publicCache[currentTeam] || []).forEach(board => {
        renderBoard(board);
    });

    await fetchBoards(false);
}

function renderTeam(team) {
    const newTeam = document.createElement('button');

    newTeam.textContent = `${team.name}`;
    newTeam.classList.add('team');

    Object.defineProperty(newTeam, 'teamId', {
        value: team.teamId,
        writable: false,
        configurable: false,
        enumerable: true
    });

    teamList.appendChild(newTeam);
}

function renderBoard(board) {
    const newBoard = document.createElement('div');

    newBoard.innerHTML = `
        <h2>${board.name}</h2>
        <p>${board.exerciseNumber || 0} Aufgaben</p>
    `;

    newBoard.classList.add('board');
    boardList.insertBefore(newBoard, boardList.lastElementChild);
}

async function onAppStart() {
    await me();
    await fetchTeams();
    await fetchBoards(true);
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
    const nameInput = document.querySelector('input[name="board-name-input"]');
    const name = nameInput.value.trim();

    if (document.querySelector('#card-header').textContent === "Neues Team") {
        createTeam(name);
        modalOverlay.classList.add('hidden');
        nameInput.value = '';
        return;
    }

    let ownerIsUser = false;
    let ownerId = currentTeam;

    if (!currentTeam) {
        ownerIsUser = true;
        ownerId = user_id;
    }

    createBoard(name, ownerIsUser, ownerId);

    modalOverlay.classList.add('hidden');
    nameInput.value = '';
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

document.querySelector('#private').addEventListener('click', (event) =>{
    const clicked = event.target.closest('.team');
    changeTeam(clicked);

    console.log(privateCache);
    console.log(publicCache);
});