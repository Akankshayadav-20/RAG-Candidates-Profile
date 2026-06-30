let currentSessionId = document.getElementById("sessionId")?.value || null;
// ---------------- ASK QUESTION ----------------
async function askQuestion() {

    let input = document.getElementById("question");
    let question = input.value.trim();

    if (question === "") return;

    let chatBox = document.getElementById("chat-box");
    let button = document.querySelector(".input-area button");

    button.disabled = true;

    const time = new Date().toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit"
    });

    chatBox.insertAdjacentHTML("beforeend", `
        <div class="user-message">
            <strong>You</strong><br>
            ${question}
        </div>
    `);

    input.value = "";
    chatBox.scrollTop = chatBox.scrollHeight;

    chatBox.insertAdjacentHTML("beforeend", `
        <div id="typing" class="ai-message">
            <strong>AI</strong><br>
            typing...
        </div>
    `);

    try {

        let response = await fetch("/ask", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
			body: JSON.stringify({
			    sessionId: currentSessionId ? Number(currentSessionId) : null,
			    question: question
			})
        });

        let data = await response.json();

        document.getElementById("typing")?.remove();

        chatBox.insertAdjacentHTML("beforeend", `
            <div class="ai-message">
                <strong>AI</strong><br>
                ${data.answer}
            </div>
        `);

        chatBox.scrollTop = chatBox.scrollHeight;

    } catch (error) {

        document.getElementById("typing")?.remove();

        chatBox.insertAdjacentHTML("beforeend", `
            <div class="ai-message">
                ❌ Error occurred
            </div>
        `);
    }

    button.disabled = false;
}


// ---------------- NEW CHAT ----------------
async function newChat() {

    let response = await fetch("/chat/new", {
        method: "POST"
    });

    let sessionId = await response.text();

    window.location.href = "/chat/" + sessionId;
}


// ---------------- ENTER KEY ----------------
document.getElementById("question")
.addEventListener("keypress", function(event) {

    if (event.key === "Enter") {
        event.preventDefault();
        askQuestion();
    }
});