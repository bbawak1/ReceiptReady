// Returns an error message when the name or password is blank, or null when both are filled in
var validateAccount = function (username, password) {
	if (!username || username.trim() === '' || !password) {
		return 'Name and password are required.';
	}
	return null;
}

// Creates the users table with username and password columns if it does not exist yet
var setupDatabase = function (db, callback) {
	db.run('CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT NOT NULL)', callback);
}

// Adds a new account to the users table, then calls back with an error message or null
var createAccount = function (db, username, password, callback) {
	var error = validateAccount(username, password);
	if (error) {
		callback(error);
		return;
	}
	var sql = 'INSERT INTO users (username, password) VALUES (?, ?)';
	db.run(sql, [username.trim(), password], function (err) {
		if (err) {
			callback('An account with that name already exists.');
			return;
		}
		callback(null);
	});
}

// Looks for an account with the given name and password, then calls back with an error message or null
var checkLogin = function (db, username, password, callback) {
	var sql = 'SELECT username FROM users WHERE username = ? AND password = ?';
	db.get(sql, [username, password], function (err, row) {
		if (err || !row) {
			callback('Invalid username or password.');
			return;
		}
		callback(null);
	});
}

module.exports = { validateAccount, setupDatabase, createAccount, checkLogin }