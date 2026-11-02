const sqlite3 = require('sqlite3');
const helper = require('./account_helper');

let db;

// Before each test, start with a fresh, empty in-memory database
beforeEach((done) => {
  db = new sqlite3.Database(':memory:');
  helper.setupDatabase(db, done);
});

// After each test, close that database
afterEach((done) => {
  db.close(done);
});

// validateAccount

test('blank name is not valid', () => {
  expect(helper.validateAccount('   ', 'pass123')).toBe('Name and password are required.');
});

test('blank password is not valid', () => {
  expect(helper.validateAccount('bernard', '')).toBe('Name and password are required.');
});

test('name and password filled in is valid', () => {
  expect(helper.validateAccount('bernard', 'pass123')).toBe(null);
});

// createAccount

test('create account with valid name and password succeeds', (done) => {
  helper.createAccount(db, 'bernard', 'pass123', (error) => {
    expect(error).toBe(null);
    done();
  });
});

test('create account with a name that already exists fails', (done) => {
  helper.createAccount(db, 'bernard', 'pass123', () => {
    helper.createAccount(db, 'bernard', 'other', (error) => {
      expect(error).toBe('An account with that name already exists.');
      done();
    });
  });
});

test('create account with blank name does not add to the database', (done) => {
  helper.createAccount(db, '', 'pass123', (error) => {
    expect(error).toBe('Name and password are required.');
    db.get('SELECT COUNT(*) AS count FROM users', (err, row) => {
      expect(row.count).toBe(0);
      done();
    });
  });
});

// checkLogin

test('login with correct name and password succeeds', (done) => {
  helper.createAccount(db, 'bernard', 'pass123', () => {
    helper.checkLogin(db, 'bernard', 'pass123', (error) => {
      expect(error).toBe(null);
      done();
    });
  });
});

test('login with wrong password fails', (done) => {
  helper.createAccount(db, 'bernard', 'pass123', () => {
    helper.checkLogin(db, 'bernard', 'wrong', (error) => {
      expect(error).toBe('Invalid username or password.');
      done();
    });
  });
});

test('login with a name that does not exist fails', (done) => {
  helper.checkLogin(db, 'nobody', 'pass123', (error) => {
    expect(error).toBe('Invalid username or password.');
    done();
  });
});