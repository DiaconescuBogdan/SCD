#define TIMES 10
byte n = 0;
byte finished = 0; //variabila folosita pentru a marca terminarea unui proces

active [2] proctype P(){
	byte i = 1; //variabila contor
	byte temp; //variabila auxiliara
	do :: ( i > TIMES ) -> break //verifica daca i este mai mare decat TIMES
		:: else -> 
		temp = n;
		n = temp + 1;
		i++;
	od;
	finished++; //proces terminat
}

active proctype main(){
finished == 2; //asteapta terminarea proceselor
printf("n = %d", n);
assert ( n > 2); 
}