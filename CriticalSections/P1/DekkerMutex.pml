bool flag[2] = false; //vector de tip bool
bool turn = 0; //variabila de tip bool 
byte count = 0; //variabila utilizata pentru a identifica procesul ce se aflat in sectiunea critica

active [2] proctype mutex() 	//creaza 2 procese 
{
 
 pid i = _pid; pid j = 1 - _pid; //stocheaza numarul de identificare al procesului
 // bucla infinita
 again:
 //sectiune non-critica
 flag[i] = true;
 
 do
 :: flag[j] ->
 if
 :: turn == j ->
 flag[i] = false;
 (turn != j); //procesul nu continua pana nu este indeplinita conditia
 flag[i] = true;
 :: else -> skip //se trece mai departe
 fi;
 :: else -> break // se iese din bucla do
 od;

 count++; //se incrementeaza cand un proces ajunge in sectiunea critica
 assert(count == 1);
 //sectiune critica
 count--; // se decrementeaza cand un proces iese din sectiunea critica

 turn = j;
 flag[i] = false;
 goto again;
 }
