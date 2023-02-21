//----------------------DECLARACIONES-----------------------
const quiz_box = document.querySelector('.quiz_box');
//const result_box = document.querySelector('.result_box');
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
//----------------------DECLARACIONES-----------------------

//----------------------AL INICIAR-----------------------
(async () => {
    const playersRaw = await fetch('play?action=sendPlayers');//se piden los jugadores al back
    players = await playersRaw.json();

    const questionsRaw = await fetch('play?action=sendQuestions');//se piden las preguntas
    questions = await questionsRaw.json();

    const gameRaw = await fetch('play?action=sendGame');//se piden los datos de la partida
    game = await gameRaw.json();

    quiz_box.classList.remove('d-none');//se muestra el contenedor de las preguntas
    console.log(questions)
    showQuestions(0);
    //queCounter(1);
    //startTimer(15);
    //startTimerLine(0);
})();

const showQuestions = index => {
    let option_tag = '<div class="btn btn-custom col-xl-10 col-lg-10" ' +
        'value="'+questions[index]['respuestas'][0]['id']
        +'">'+questions[index]['respuestas'][0]['contenido']+'</div>'
    + '<div class="btn btn-custom col-xl-10 col-lg-10" ' +
        'value="'+questions[index]['respuestas'][1]['id']
        +'">'+questions[index]['respuestas'][1]['contenido']+'</div>'
    +'<div class="btn btn-custom col-xl-10 col-lg-10" ' +
        'value="'+questions[index]['respuestas'][2]['id']
        +'">'+questions[index]['respuestas'][2]['contenido']+'</div>'
    +'<div class="btn btn-custom col-xl-10 col-lg-10" ' +
        'value="'+questions[index]['respuestas'][3]['id']
        +'">'+questions[index]['respuestas'][3]['contenido']+'</div>';

    que_text.innerHTML = questions[index]['contenido'];
    option_list.innerHTML = option_tag;

    const option = option_list.querySelectorAll('.option');

}
//----------------------AL INICIAR-----------------------

//----------------------DURANTE LA PARTIDA-----------------------
//al clicker boton de siguiente
next_btn.onclick = () => {
    if (que_count < questions.length - 1) { //if question count is less than total question length
        que_count++; //increment the que_count value
        que_numb++; //increment the que_numb value
        showQuetions(que_count); //calling showQuestions function
        queCounter(que_numb); //passing que_numb value to queCounter
        clearInterval(counter); //clear counter
        clearInterval(counterLine); //clear counterLine
        startTimer(timeValue); //calling startTimer function
        startTimerLine(widthValue); //calling startTimerLine function
        timeText.textContent = "Tiempo Restante"; //change the timeText to Time Left
        next_btn.classList.remove("show"); //hide the next button
    } else {
        clearInterval(counter); //clear counter
        clearInterval(counterLine); //clear counterLine
        showResult(); //calling showResult function
    }
}


//----------------------CRONOMETRO-----------------------
let timeValue = 15;
let que_count = 0;
let que_numb = 1;
let userScore = 0;
let counter;
let counterLine;
let widthValue = 0;

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
            timeText.textContent = "Time Off"; //change the time text to time off
            const allOptions = option_list.children.length; //getting all option items
            let correcAns = questions[que_count].answer; //getting correct answer from array
            for (let i = 0; i < allOptions; i++) {
                if (option_list.children[i].textContent == correcAns) { //if there is an option which is matched to an array answer
                    option_list.children[i].setAttribute("class", "option correct"); //adding green color to matched option
                    option_list.children[i].insertAdjacentHTML("beforeend", tickIconTag); //adding tick icon to matched option
                    console.log("Time Off: Auto selected correct answer.");
                }
            }
            for (let i = 0; i < allOptions; i++) {
                option_list.children[i].classList.add("disabled"); //once user select an option then disabled all options
            }
            next_btn.classList.add("show"); //show the next button if user selected any option
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
