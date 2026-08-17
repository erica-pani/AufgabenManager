import { fetchData, ENDPOINTS, state} from './mainRefac.js';

const columns = document.querySelectorAll('.column');

async function createNewExercise(title, description, boardId) {
    const url = ENDPOINTS.ADD_EXERCISE;
    const body = {
        title: title,
        description: description,
        boardId: boardId
    };

    const exercise = await fetchData(url, 'POST', body);

    renderExercise(exercise);
}

function renderExercise(exercise) {

}

function initColumnToggle() {
    const columns = document.querySelectorAll('.column');

    columns.forEach(column => {
        const header = column.querySelector('.column-header');
        
        if (!header) return;

        header.addEventListener('click', () => {
            column.classList.toggle('expanded');
        });
    });
}

document.addEventListener('DOMContentLoaded', () => {
    initColumnToggle();
});

columns.forEach(column => {
    let button = column.querySelector('button');

    button.addEventListener('click', () => {
        
        let title = column.querySelector('input').value;
        createNewExercise(title, null, state.currentBoard);
    });
});