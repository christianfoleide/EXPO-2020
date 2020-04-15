let radioInputs = document.querySelectorAll('.rate-area input')
let submitButton = document.getElementById('submit-button')
let errorMessage = document.getElementById('error-message')

const hideErrorMessage = () => {
    errorMessage.style.visibility = 'hidden'
}

submitButton.addEventListener('click', (e) => {

    for (let i = 0; i < radioInputs.length; i++) {
        if (radioInputs[i].checked && radioInputs[i].type === 'radio') {
            return
        }
    }
    e.preventDefault();
    errorMessage.style.visibility = 'visible'

})

hideErrorMessage()
