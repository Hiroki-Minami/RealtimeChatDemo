# RealtimeChatDemo

This project demonstrates the integration of Apache Kafka within a Spring Framework environment. The system comprises a RESTful API server developed using Spring Boot, which also has Websocket connection and enable Realtime chat with a Angular frontend. Data management is handled through a Mongo database, focusing on user account information.

## Tech Stack

[![Tech Stack](https://skillicons.dev/icons?i=spring,angular,tailwind,docker,materialui,kafka,mongodb,&perline=7)](https://skills.thijs.gg)

- **Spring Boot**: For building the RESTful API server.
- **Spring Security**: Ensures secure communication and authentication.
- **Angular.js**: Powers the frontend interface.
- **Tailwind CSS**: Facilitates rapid UI development and styling.
- **Apache Kafka**: Enables efficient, high-performance communication.
- **MongoDB**: Provides the database backend for managing user account data.

## Usage

### Prerequisites
- Java Development Kit (JDK)
- Node.js
- Docker

### Installation
1. Clone this repository to your local machine.
   ```bash
   git clone https://github.com/your-username/your-repository.git
   ```
2. Run backend server

    Run the server with your IDE or something like that.
3. Run frontend Next.js server
    ```bash
    cd front
    npm install
    ng serve
    ```