
```markdown
# ShowcaseAI 🚀

ShowcaseAI is an AI-powered portfolio platform where developers can create profiles, post achievements, and let Meta Llama 4 auto-generate polished bios, smart tags, and recommendations. Built with Spring Boot, Cerebras Cloud, and Docker.

## Features

- Create and manage user profiles  
- Add achievements with title, description, links, and media  
- AI-powered bio and tag generation (Meta Llama 4)  
- Search and filter profiles by name, skills, or location  
- Dockerized for easy deployment  

Made with ❤️ by ShivanshBagga at the FutureStack GenAI Hackathon

## Getting Started

### Prerequisites

- Java 17 or above  
- Maven  
- Docker (optional, for containerized deployment)  
- Internet connection (required for AI API calls)  

### Installation & Running

1. **Clone the repository**

   ```
   git clone https://github.com/baggashivansh/ShowcaseAIx.git
   cd ShowcaseAIx
   ```

2. **Build the project**

   ```
   mvn clean package
   ```

3. **Run the application**

   ```
   mvn spring-boot:run
   ```

4. **Access the app**

   Open your browser and visit:

   ```
   http://localhost:8080
   ```

5. **Using Docker (optional)**

   To run using Docker, build and start the container:

   ```
   docker build -t showcaseai .
   docker run -p 8080:8080 showcaseai
   ```

### Notes

- Add your Cerebras AI API key in `src/main/resources/application.properties` before running.
- `.gitignore` excludes sensitive files like configuration and build outputs.

---

Feel free to contribute or file issues for improvements!

```
