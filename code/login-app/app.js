// Importing the helper that holds the account and login logic
var helper = require('./account_helper');

// Setting up Express and also using EJS to display our pages
var express = require('express');
var app = express();
app.set('view engine', 'ejs');

// Get the information users enter into forms
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

// Connecting to the SQLite database
// If the database doesn't exist, SQLite will create it
var sqlite3 = require('sqlite3');
var db = new sqlite3.Database(__dirname + '/users.db');


helper.setupDatabase(db);

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
    helper.checkLogin(db, username, req.body.password, function(error) {
        // If the login information is wrong, show an error
        if (error) {
            res.render('page/login', { error: error });
            return;
        }

        // If the login works, send the user to the landing page
        res.redirect('/landing?username=' + encodeURIComponent(username));
    });
});

// Display the page where users can create an account
app.get('/create', function(req, res) {
    res.render('page/create', { error: null });
});

// Save a new user's information in the database
app.post('/create', function(req, res) {
    var username = (req.body.username || '').trim();
    helper.createAccount(db, username, req.body.password, function(error) {
        // Show an error if the account can't be created
        if (error) {
            res.render('page/create', { error: error });
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

// Starting the application
app.listen(8080, function() {
    console.log('App listening on port 8080!');
});