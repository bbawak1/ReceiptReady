//setup express to handle routing and rendering
var express = require('express');
var app = express();
app.set('view engine', 'ejs');

//setup bodyparser to extra data from form posts
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));
//setup static public directory (to link images from view pages)
app.use(express.static('public'))

// route to index page
app.get('/', function(req, res) {
    res.render('page/index');
});
// route to documentation page
app.get('/docs', function(req, res) {
    res.render('page/docs');
});
//start the web app
app.listen(8080, function () {
	console.log('App listening on port 8080!');
});

// route to quickstart page
app.get('/quickstart', function(req, res) {
    res.render('page/quickstart');
});
// route to add page
app.get('/add', function(req, res) {
    res.render('page/add');
});

// route to edit page
app.get('/edit', function(req, res) {
    res.render('page/edit');
});

// route to delete page
app.get('/delete', function(req, res) {
    res.render('page/delete');
});

// route to summary page
app.get('/summary', function(req, res) {
    res.render('page/summary');
});