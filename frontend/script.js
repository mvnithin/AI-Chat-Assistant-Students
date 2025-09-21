const chatDiv = document.getElementById('chat');
const promptInput = document.getElementById('prompt');
const sendBtn = document.getElementById('sendBtn');

function appendMessage(text, cls) {
  const d = document.createElement('div');
  d.className = 'msg ' + cls;
  d.innerText = text;
  chatDiv.appendChild(d);
  chatDiv.scrollTop = chatDiv.scrollHeight;
}

sendBtn.addEventListener('click', async () => {
  const prompt = promptInput.value.trim();
  if (!prompt) return;
  appendMessage('You: ' + prompt, 'user');
  promptInput.value = '';

  try {
    const res = await fetch('http://localhost:8080/api/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ prompt })
    });
    const data = await res.json();
    if (data.reply) {
      appendMessage('AI: ' + data.reply, 'ai');
    } else if (data.error) {
      appendMessage('Error: ' + data.error, 'ai');
    } else {
      appendMessage('Unexpected response', 'ai');
    }
  } catch (err) {
    appendMessage('Network error: ' + err.message, 'ai');
  }
});

// allow Enter key
promptInput.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') sendBtn.click();
});
