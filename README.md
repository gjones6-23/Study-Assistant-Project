# AI Study Assistant

A Java-based command-line study assistant that uses the OpenAI API to turn lecture notes into useful study materials.

## Features

The Study Assistant allows the user to:

* Create a **Study Guide** from lecture notes
* Create **Flashcards** from lecture notes
* Create **both** a study guide and flashcards
* Enter lecture notes directly through the command line
* Generate summaries, key concepts, practice questions, and question-and-answer flashcards

The user selects what they want to create, pastes their lecture notes, and types `END` on a new line when finished. The program then sends the notes to the OpenAI API and displays the generated study materials.

## How It Works

1. The program checks for an `OPENAI_API_KEY` environment variable.
2. The user chooses between a study guide, flashcards, or both.
3. The user enters their lecture notes.
4. The program creates an API request containing the selected instructions and lecture notes.
5. The OpenAI API generates the requested study materials.
6. The program parses the API response and displays the results in the terminal.

## Technologies Used

* **Java**
* **OpenAI Responses API**
* **Gson** — used to parse the JSON response from the API
* **Java HttpClient** — used to send HTTP requests
* **Java Scanner** — used for command line user input
* **Git & GitHub** — used for version control and project hosting

## Setup

### 1. Java

Make sure Java is installed on your computer.

### 2. OpenAI API Key

The program expects an API key to be stored in an environment variable named:

OPENAI_API_KEY

The API key is not stored directly in the source code.

### 3. Gson

The project uses the Gson library to parse JSON returned by the API.

## Running the Program

Run `StudyAssistant.java` from your Java development environment or compile and run it from the command line with the required Gson dependency available.

Once started, follow the prompts in the terminal to choose the type of study material you want to generate and enter your lecture notes.

## Example Options

What would you like to create?
1. Study Guide
2. Flashcards
3. Both

The generated study materials are then displayed in the terminal.

## Author

Created as a Java programming project.
