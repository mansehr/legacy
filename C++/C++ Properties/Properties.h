/*
 * Properties.h
 *
 *  Created on: Feb 9, 2013
 *      Author: andreas
 */

#ifndef PROPERTIES_H_
#define PROPERTIES_H_

#include <map>
#include <fstream>
#include <string>
#include <iostream>

namespace mansehr {

using namespace std;

typedef map<const string,string> PropMap;

class Properties {
public:
	Properties(const char* propFileName);
	virtual ~Properties();

	string get(string propertyName);
private:
	PropMap propMap;
};

} /* namespace mansehr */
#endif /* PROPERTIES_H_ */
