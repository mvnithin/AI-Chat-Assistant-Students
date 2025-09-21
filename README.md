# 🤖 Vibe Coding - AI Chat Assistant for Students (Spring Boot)

A ready-to-run **Spring Boot** backend + simple frontend (HTML/CSS/JS) that connects to the OpenAI Chat Completions API.
This repository is prepared so you can download, set your API key, and run locally.

## Quick start (Windows / Linux / Mac)
1. Unzip the project and open a terminal in the `backend/` folder.
2. Add your OpenAI API key in `backend/src/main/resources/application.properties`:

   ```
   openai.api.key=YOUR_OPENAI_API_KEY_HERE
   ```

3. Run the backend:
   - If you have Maven wrapper included: `./mvnw spring-boot:run`
   - Or with installed Maven: `mvn spring-boot:run`

4. Open `frontend/index.html` in your browser (or serve it with a static server). The frontend sends requests to `http://localhost:8080/api/chat`.

## What is included
- `backend/` : Spring Boot app (Java) with controller & service that calls OpenAI Chat Completion endpoint.
- `frontend/` : Simple chat UI (index.html, style.css, script.js).

## Notes
- Replace the API key placeholder in application.properties before running.
- The project uses `gpt-3.5-turbo` by default; you can change the model in `ChatService.java`.
- This project does not include advanced auth or rate limiting — do not expose your API key publicly.

## Author
Nithin M V
