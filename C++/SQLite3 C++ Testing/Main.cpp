/*
 * Main.cpp
 *
 *  Created on: Feb 7, 2013
 *      Author: andreas
 */

#include <iostream>
#include <sqlite3.h>

#define PREPARED_STMT 1

using namespace std;

// This is the callback function to display the select data in the table
static int callback(void *NotUsed, int argc, char **argv, char **szColName) {
	for (int i = 0; i < argc; i++) {
		std::cout << szColName[i] << " = " << argv[i] << std::endl;
	}

	std::cout << "\n";

	return 0;
}

int runQuery(sqlite3 *db, const char* query) {
	char *szErrMsg = 0;
	int rc = sqlite3_exec(db, query, callback, 0, &szErrMsg);
	if (rc != SQLITE_OK) {
		std::cout << "SQL Error: " << szErrMsg << std::endl;
		sqlite3_free(szErrMsg);
		//break;
	}
	return rc;
}

int main(int args, char** argv) {
	sqlite3 *db;
#if PREPARED_STMT
	sqlite3_stmt *stmt;
#endif

	// open database
	int rc = sqlite3_open("sqllite_test.db", &db);

	if (rc) {
		std::cout << "Can't open database\n";
	} else {
		std::cout << "Open database successfully\n";
	}

	// prepare our sql statements
	const char *pSQL[6];
	pSQL[0] = "CREATE TABLE delta(time TIMESTAMP, value INT)";
	pSQL[1] = "INSERT INTO delta VALUES (strftime('%s','now'), 1234)";
	pSQL[3] = "SELECT * FROM delta";

	runQuery(db, pSQL[0]);
	std::cout << "Database created.\n";

#if PREPARED_STMT
	if(sqlite3_prepare(
					db,
					"INSERT INTO delta VALUES (strftime('%s','now'),?)", // stmt
					-1,// If than zero, then stmt is read up to the first nul terminator
					&stmt,
					0// Pointer to unused portion of stmt
			) != SQLITE_OK) {
		std::cout << "SQL Error preparing statement.\n";
	}
	std::cout << "Statement prepared.\n";
#endif
	// execute sql
	for (int i = 0; i < 1000; i++) {
#if PREPARED_STMT
		sqlite3_reset(stmt);

		if (sqlite3_bind_int (
						stmt,
						1,  // Index of wildcard
						1234
				)
				!= SQLITE_OK) {
			std::cout << "Could not bind int.\n";
			return 1;
		}

		if (sqlite3_step(stmt) != SQLITE_DONE) {
			std::cout << "Could not step (execute) stmt.\n";
			return 1;
		}
#else
		runQuery(db, pSQL[1]);
#endif
		if ((i % 100) == 0) {
			std::cout << "Inserted " << i << " elements.\n";
		}
	}
	std::cout << "Done inserting.\n";
	runQuery(db, pSQL[3]);

	// close database
	if (db) {
		sqlite3_close(db);
	}
	return 0;
}

