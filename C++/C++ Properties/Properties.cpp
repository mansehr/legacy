/*
 * Properties.cpp
 *
 *  Created on: Feb 9, 2013
 *      Author: andreas
 */

#include "Properties.h"
#include <exception>
#include <cstring>
#include <string>
#include <stdexcept>

#define BUFFER_SIZE 256

namespace mansehr {

Properties::Properties(const char* propFileName) {
	// Load file
	string row;
	fstream filestream(propFileName, fstream::in);
	if (!filestream.is_open()) {
		string errorString("File not found, ");
		throw runtime_error(errorString+propFileName);
	}
	while (getline(filestream, row)) {
		int i = row.find('=');
		if(i > 0) {
			propMap[row.substr(0,i)]=row.substr(i+1);
		}
		//cout << a++ << ":" << row.substr(0, i) << " = " << row.substr(i + 1) << endl;
	}
	filestream.close();
}

Properties::~Properties() {
	// TODO Auto-generated destructor stub
}

string Properties::get(string propertyName) {
	return propMap[propertyName];
}

} /* namespace mansehr */
