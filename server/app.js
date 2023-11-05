const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const app = express();

app.use(cors());
app.use(bodyParser.json());

// Przykładowa baza daanych (tymczasowa)
let registeredUsers = [];
let votes = [];
const candidates = [];
candidates.push({candidateId: '1', name: 'Tom',})
candidates.push({candidateId: '2', name: 'Sam',})
candidates.push({candidateId: '3', name: 'Anna',})
candidates.push({candidateId: '4', name: 'Kathy',})

// Endpoint rejestracji
// banalny zapis bez zachowania bezpieczenstwa na potrzeby poczatkowej implementacji
app.post('/register', (req, res) => {
    const { email, password } = req.body;

    // Sprawdź, czy użytkownik już istnieje
    const existingUser = registeredUsers.find(user => user.email === email);
    if (existingUser) {
        return res.status(400).send('Użytkownik już istnieje');
    }

    registeredUsers.push({ email, password });
    res.status(200).send('Zarejestrowano pomyślnie');
});

// Endpoint logowania
app.post('/login', (req, res) => {
    const { email, password } = req.body;

    // Znajdź użytkownika w rejestrze
    const user = registeredUsers.find(user => user.email === email && user.password === password);

    if (user) {
        res.status(200).send('Zalogowano pomyślnie');
    } else {
        res.status(401).send('Błąd logowania');
    }
});

// Endpoint głosowania
app.post('/vote', (req, res) => {
    const { candidateId, publicKey, privateKeySignature } = req.body;
    // Tutaj powinna być logika zapisywania głosu do block chaina 
    // tak samo jak rejestracja i logowanie na potrzeby demonstracji zrobione w banalny sposob
    votes.push({ candidateId, publicKey, privateKeySignature });
    res.status(200).send('Głos zapisany pomyślnie');
});

// Endpoint do pobrania wyników
app.get('/results', (req, res) => {
    // Tutaj powinno byc polaczenie z blockachainem i odpieranie wynikow
    // W tym przykładzie zwracamy tylko zawartość tablicy głosów
    res.status(200).json(votes);
});

const port = 3001;
app.listen(port, () => {
    console.log(`Serwer nasłuchuje na porcie ${port}`);
});
