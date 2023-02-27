//----------------------DECLARACIONES-----------------------
const quiz_box = document.querySelector('.quiz_box');
const result_box = document.querySelector('.result_box');
const que_text = document.querySelector('.pregunta');
const option_list = document.querySelector('.opciones');
const title = document.querySelector('.titulo');
const time_line = document.querySelector('.card-header .time_line');
const timeText = document.querySelector('.timer .time_left_txt');
const timeCount = document.querySelector('.timer .timer_sec');

const next_btn = document.querySelector(".btn_siguiente");
const bottom_ques_counter = document.querySelector(".total_preg");

let players;
let questions;
let game;
let usersScore;

let timeValue = 15;
let que_count = 0;
let que_numb = 1;
let counter;
let counterLine;
let widthValue = 0;
//----------------------DECLARACIONES-----------------------

//----------------------AL INICIAR-----------------------
(async () => {
    const playersRaw = await fetch('play?action=sendPlayers');//se piden los jugadores al back
    players = await playersRaw.json();
    console.log(players)

    const questionsRaw = await fetch('play?action=sendQuestions');//se piden las preguntas
    questions = await questionsRaw.json();
    console.log(questions)

    const gameRaw = await fetch('play?action=sendGame');//se piden los datos de la partida
    game = await gameRaw.json();
    console.log(game)

    quiz_box.classList.remove('d-none');//se muestra el contenedor de las preguntas
    showQuestions(que_count);
    queCounter(1);
    startTimer(15);
    startTimerLine(0);
    setTitle();
})();
//----------------------AL INICIAR-----------------------

//----------------------DURANTE LA PARTIDA-----------------------
//mostrar las preguntas y opciones
const showQuestions = index => {
    let option_tag = '<div class="btn btn-custom col-xl-10 col-lg-10 option d-flex align-items-center justify-content-center gap-2" ' +
        'data-id="' + questions[index]['respuestas'][0]['id']
        + '">' + questions[index]['respuestas'][0]['contenido'] + '</div>'
        + '<div class="btn btn-custom col-xl-10 col-lg-10 option d-flex align-items-center justify-content-center gap-2" ' +
        'data-id="' + questions[index]['respuestas'][1]['id']
        + '">' + questions[index]['respuestas'][1]['contenido'] + '</div>'
        + '<div class="btn btn-custom col-xl-10 col-lg-10 option d-flex align-items-center justify-content-center gap-2" ' +
        'data-id="' + questions[index]['respuestas'][2]['id']
        + '">' + questions[index]['respuestas'][2]['contenido'] + '</div>'
        + '<div class="btn btn-custom col-xl-10 col-lg-10 option d-flex align-items-center justify-content-center gap-2" ' +
        'data-id="' + questions[index]['respuestas'][3]['id']
        + '">' + questions[index]['respuestas'][3]['contenido'] + '</div>';

    que_text.innerHTML = questions[index]['contenido'];
    option_list.innerHTML = option_tag;

    const option = option_list.querySelectorAll('.option');

    for (let i = 0; i < option.length; i++) {
        option[i].setAttribute('onclick', 'optionSelected(this)');
    }
}

//al clicker boton de siguiente
next_btn.onclick = async () => {
    if (que_count < questions.length - 1) { //if question count is less than total question length
        que_count++; //increment the que_count value
        que_numb++; //increment the que_numb value
        setTitle();
        showQuestions(que_count); //calling showQuestions function
        queCounter(que_numb); //passing que_numb value to queCounter
        clearInterval(counter); //clear counter
        clearInterval(counterLine); //clear counterLine
        startTimer(timeValue); //calling startTimer function
        startTimerLine(widthValue); //calling startTimerLine function
        timeText.textContent = "Tiempo Restante"; //change the timeText to Time Left
        next_btn.classList.add("d-none"); //hide the next button
    } else {
        clearInterval(counter); //clear counter
        clearInterval(counterLine); //clear counterLine
        setUsersScore(); //se guarda el puntaje de los usuarios en la partida
        await showResult(); //calling showResult function
    }
}

//se crean los iconos de pregunta correcta o incorrecta
let tickIconTag = '<div class="icon tick"><i class="fas fa-check"></i></div>';
let crossIconTag = '<div class="icon cross"><i class="fas fa-times"></i></div>';

//si el jugador selecciona una respuesta
function optionSelected(answer) {
    clearInterval(counter); //clear counter
    clearInterval(counterLine); //clear counterLine
    let userAns = answer.dataset.id; //getting user selected option
    let correctAns = foundCorrectAns(); //getting correct answer from array
    const allOptions = option_list.children.length; //getting all option items

    sendUserAns(userAns, userAns == correctAns ? 1 : 0);

    if (userAns == correctAns) { //if user selected option is equal to array's correct answer
        answer.classList.add('bg-success') //adding green color to correct selected option
        answer.insertAdjacentHTML("beforeend", tickIconTag); //adding tick icon to correct selected option
    } else {
        answer.classList.add("bg-danger"); //adding red color to correct selected option

        answer.insertAdjacentHTML("beforeend", crossIconTag); //adding cross icon to correct selected option

        for (let i = 0; i < allOptions; i++) {
            if (option_list.children[i].dataset.id == correctAns) { //if there is an option which is matched to an array answer
                option_list.children[i].classList.add('bg-success') //adding green color to matched option
                option_list.children[i].insertAdjacentHTML("beforeend", tickIconTag); //adding tick icon to matched option
            }
        }
    }
    for (let i = 0; i < allOptions; i++) {
        option_list.children[i].classList.add("disabled"); //once user select an option then disabled all options
    }
    next_btn.classList.remove('d-none'); //show the next button if user selected any option
}

//buscar respuesta correcta
const foundCorrectAns = () => {
    let que_options = questions[que_count]['respuestas'];
    let correctAnsId;

    que_options.map((question) => {
        if (question['esCorrecta'] === 1) {
            correctAnsId = question['id'];
        }
    })

    return correctAnsId;
}

function queCounter(index) {
    //creating a new span tag and passing the question number and total question
    bottom_ques_counter.innerHTML = '<span class="d-flex gap-1"><p>' + index + '</p> de <p>' + questions.length + '</p> Preguntas</span>';  //adding new span tag inside bottom_ques_counter
}

const setTitle = () => {
    if (que_count % 2 === 0 || que_count === 0) {
        title.innerHTML = 'Turno de ' + players[0]['nombre'];
    } else {
        title.innerHTML = 'Turno de ' + players[1]['nombre'];
    }
}

//enviar respuesta del usuario al back
const sendUserAns = (userAns, isTrue) => {
    const queSelect = questions[que_count]['id'];
    const playerNum = que_count % 2 !== 0 ? 2 : 1;
    fetch('play?action=getUserAns', {
        method: 'POST',
        body: JSON.stringify({
            player: playerNum,
            question: queSelect,
            answer: userAns,
            game: game['id'],
            isTrue: isTrue,
        }),
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    })
}

//guardar puntajes en la base de datos
const setUsersScore = () => {
    fetch('play?action=setUsersScore',{
        method: 'POST',
    })
}
//----------------------DURANTE LA PARTIDA-----------------------

//----------------------AL FINALIZAR LA PARTIDA-----------------------
async function showResult() {
    await getUsersScore(game['id']);

    quiz_box.classList.add('d-none'); //hide quiz box
    result_box.classList.remove('d-none'); //show result box

    const winner = result_box.querySelector(".winner");
    const dataTable = result_box.querySelector(".data");

    if(usersScore[0]['puntaje'] > usersScore[1]['puntaje']){
        winner.innerHTML = '¡El ganador es ' + players[0]['nombre']+'!';
    } else {
        if (usersScore[0]['puntaje'] < usersScore[1]['puntaje']){
            winner.innerHTML = '¡El ganador es ' + players[0]['nombre']+'!';
        } else {
            winner.innerHTML = '¡Es un empate!';
        }
    }

    dataTable.innerHTML = '<tr><th scope="row">'+players[0]['nombre']+'</th>'+'<td>'+usersScore[0]['puntaje']+'</td></tr>'+
        '<tr><th scope="row">'+players[1]['nombre']+'</th>'+'<td>'+usersScore[1]['puntaje']+'</td></tr>'
}
const getUsersScore = async (partidaId) => {
    const userScoreRaw = await fetch('play?action=sendUsersScore&partidaId='+partidaId);//se piden los jugadores al back
    usersScore = await userScoreRaw.json();
    console.log(usersScore)
}
//----------------------AL FINALIZAR LA PARTIDA-----------------------

//----------------------CRONOMETRO-----------------------
function startTimer(time) {
    counter = setInterval(timer, 1000);

    function timer() {
        timeCount.textContent = time; //changing the value of timeCount with time value
        time--; //decrement the time value
        if (time < 9) { //if timer is less than 9
            let addZero = timeCount.textContent;
            timeCount.textContent = "0" + addZero; //add a 0 before time value
        }
        if (time < 0) { //if timer is less than 0
            clearInterval(counter); //clear counter
            timeText.textContent = "Se acabó"; //change the time text to time off
            const allOptions = option_list.children.length; //getting all option items
            let correctAns = foundCorrectAns(); //getting correct answer from array
            for (let i = 0; i < allOptions; i++) {
                if (option_list.children[i].dataset.id == correctAns) { //if there is an option which is matched to an array answer
                    option_list.children[i].classList.add('bg-success') //adding green color to matched option
                    option_list.children[i].insertAdjacentHTML("beforeend", tickIconTag); //adding tick icon to matched option
                }
            }
            for (let i = 0; i < allOptions; i++) {
                option_list.children[i].classList.add("disabled"); //once user select an option then disabled all options
            }
            next_btn.classList.remove('d-none'); //show the next button if user selected any option
        }
    }
}

function startTimerLine(time) {
    counterLine = setInterval(timer, 29);

    function timer() {
        time += 1; //upgrading time value with 1
        time_line.style.width = time + "px"; //increasing width of time_line with px by time value
        if (time > 549) { //if time value is greater than 549
            clearInterval(counterLine); //clear counterLine
        }
    }
}
//----------------------CRONOMETRO-----------------------