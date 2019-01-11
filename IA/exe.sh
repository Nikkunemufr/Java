#!/bin/bash
javac src/*/*.java -d out
cd out
echo les classes ont bien été compilé

echo Choisissez la classe que vous souhaitez executer
echo -1 pour le generate and test
echo -2 pour le test du A*
echo -3 pour le test Backtracking
echo -4 pour le test Datamining
echo -5 pour le test Diagnosis
echo -6 pour le test Planification
echo -7 pour le test Planning

read choix

case $choix in
	1) java examples/Test;;
	2) java examples/TestAstar;;
	3) java examples/TestBacktracking;;
	4) 	echo 1 pour bdd par default 2 pour votre bdd;
		read needBdd;
		case $needBdd in
			1)java examples/TestDatamining;;
			2)echo chemin de votre bdd \:;
				read bdd;
				java examples/TestDatamining $bdd;;
			*) mauvaise sasie;;
		esac;;
	5) java examples/TestDiagnosis;;
	6) java examples/TestPlanif;;
	7) java examples/TestPlanning;;
	*) echo mauvaise saisie ;;
esac
