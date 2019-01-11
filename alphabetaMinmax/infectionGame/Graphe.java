package Infect;

/*

La classe Graphe est une classe qui est utilisé pour créer un tableau d'objet.
ex: 
	graphe[machine actuel][machine visé]
	
	graphe[0][0] = machine 0 vers machine 0 
	Nous avons un cas d'une machine qui se cible soit même. Ce qui 
	va permettre d'obtenir des informations sur sont état
	(infecté ou non).
	
	graphe[0][1] = machine 0 vers machine 1
	Nous avons un cas d'une machine qui cible une autre
	machine. Ce qui va permettre d'obtenir des informations sur
	la connection entre les deux machines.

*/

public class Graphe implements Cloneable {
	
	private boolean lien;				//la vairiable "lien" permet d'établir un lien avec 2 machines
	private boolean infection = false;	//la variable "infection" permet de déterminer si une machine est infectée

	public Graphe(boolean etat){
		this.lien = etat;
	}

	/* Permet d'obtenir l'état de la variable "infection". */
	public boolean getInfection(){
		return infection;
	}

	/* Permet d'obtenir l'état de la variable "lien". */
	public boolean getLien(){
		return lien;
	}

	/* Permet d'infecter une machine en passant la variable "infection" en true. */
	public void destroyMachine(){
		infection = true;
	}

	/* Permet de désactiver ou d'activer le lien entre 2 machines */
	public void changeEtat(boolean b){
		lien = b;
	}

	/* Permet de dupliquer un objet*/
	public Graphe clone() {
		Graphe copie = null;

		try {
			copie = (Graphe) super.clone();
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		return copie;
	}
}
