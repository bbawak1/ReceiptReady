
 // Set up Express and use EJS to display our pages
var express = require('express');
var app = express();
app.set('view engine', 'ejs');

// Get the information users enter into forms
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

// Connect to our SQLite database
// If the database doesn't exist, SQLite will create it
var sqlite3 = require('sqlite3');
var db = new sqlite3.Database(__dirname + '/users.db');

// Create a table to store usernames and passwords
// if we don't already have one
db.run('CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT NOT NULL)');

// When someone visits the website, send them to the login page
app.get('/', function(req, res) {
    res.redirect('/login');
});

// Display the login page
app.get('/login', function(req, res) {
    res.render('page/login', { error: null });
});

// Check if the username and password match an existing account
app.post('/login', function(req, res) {
    var username = req.body.username;
    var password = req.body.password;

    var sql = 'SELECT username FROM users WHERE username = ? AND password = ?';

    db.get(sql, [username, password], function(err, row) {
        // If the login information is wrong, show an error
        if (err || !row) {
            res.render('page/login', {
                error: 'Invalid username or password.'
            });
            return;
        }

        // If the login works, send the user to the landing page
        res.redirect('/landing?username=' + encodeURIComponent(row.username));
    });
});

// Display the page where users can create an account
app.get('/create', function(req, res) {
    res.render('page/create', { error: null });
});

// Save a new user's information in the database
app.post('/create', function(req, res) {
    var username = (req.body.username || '').trim();
    var password = req.body.password || '';

    // Make sure both fields have been filled out
    if (username === '' || password === '') {
        res.render('page/create', {
            error: 'Name and password are required.'
        });
        return;
    }

    var sql = 'INSERT INTO users (username, password) VALUES (?, ?)';

    db.run(sql, [username, password], function(err) {
        // Show an error if the account can't be created
        if (err) {
            res.render('page/create', {
                error: 'An account with that name already exists.'
            });
            return;
        }

        // Take the new user to the landing page
        res.redirect('/landing?username=' + encodeURIComponent(username));
    });
});

// Display the landing page with the user's name
app.get('/landing', function(req, res) {
    // Send the user back to login if no username is provided
    if (!req.query.username) {
        res.redirect('/login');
        return;
    }

    res.render('page/landing', {
        username: req.query.username
    });
});

// Start the application on port 8080
app.listen(8080, function() {
    console.log('App listening on port 8080!');
});
