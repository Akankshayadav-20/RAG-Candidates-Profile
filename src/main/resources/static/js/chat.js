async function askQuestion() {

    let input = document.getElementById("question");
    let question = input.value.trim();

    if (question === "") {
        return;
    }

    let chatBox = document.getElementById("chat-box");

    let button = document.querySelector(".input-area button");

    button.disabled = true;

    // Show user message

    chatBox.innerHTML += `
        <div class="user-message">
            <strong>You</strong><br>
            ${question}
        </div>
    `;

    chatBox.scrollTop = chatBox.scrollHeight;

    input.value = "";

    // Typing Indicator

    chatBox.innerHTML += `
        <div id="typing" class="ai-message">
            Candidate AI is typing...
        </div>
    `;

    chatBox.scrollTop = chatBox.scrollHeight;

    try {

        let response = await fetch("/ask", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                question: question
            })

        });

        if (!response.ok) {
			const error = await response.text();
            throw new Error(erro);
        }

        let data = await response.json();

        // Remove typing message

        document.getElementById("typing").remove();

        // Show AI response

        chatBox.innerHTML += `
            <div class="ai-message">
                <strong>Candidate AI</strong><br>
                ${data.answer}
            </div>
        `;

    } catch (error) {

        if (document.getElementById("typing")) {
            document.getElementById("typing").remove();
        }

        chatBox.innerHTML += `
            <div class="ai-message">
                ❌ Unable to connect to the server.
            </div>
        `;

    } finally {

        button.disabled = false;

        chatBox.scrollTop = chatBox.scrollHeight;

    }

}

// Press Enter to Send

document.getElementById("question")
.addEventListener("keypress", function(event) {

    if (event.key === "Enter") {

        event.preventDefault();

        askQuestion();

    }

});