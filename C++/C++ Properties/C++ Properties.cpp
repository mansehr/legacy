//============================================================================
// Name        : C++.cpp
// Author      : Andreas Sehr
// Version     :
// Copyright   : GPL2
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <map>
#include <string>
#include "Properties.h"
using namespace std;

int main() {

	mansehr::Properties p("test.props");
	cout << "this: "<<  p.get("this") << endl;
	cout << "test: " << p.get("test") << endl;
	cout << "scentens" << p.get("scentens") << endl;

	return 0;
}
