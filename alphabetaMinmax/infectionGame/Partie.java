package Infect;
import java.util.Random;
import java.util.Scanner;
import java.util.*;


/*

La classe Partie est une classe qui est utilisé pour créer un réseau à l'aide de la classe Graphe.

*/

public class Partie{
	
	private int taille;			//la variable "taille" permet de déclarer le nombre de machine pour déterminer la taille du tableau
	public Graphe[][] reseau;		//le tableau "reseau" est un tableau d'objet Graphe qui permet de simuler notre réseau
	private boolean tour = false;		//la variable "tour" permet de fixer le tour de l'attaquant ou defenseur durant la partie (utilisé pour l'algorithme) false = defenseur true = attaquant

	public Partie(int taille){
		this.taille = taille;
		this.reseau = new Graphe[taille][taille];
	}

	/* Permet de créer un réseau */
	public void init(Graphe[][] reseau){
		Random a = new Random();
		int MachineInfect = a.nextInt(taille);
		
		/* Boucle qui permet l'infection d'une machine de façon aléatoire  */
		for (int i = 0; i < taille; i++){
			for (int j = 0; j < taille; j++){
				reseau[i][j] = new Graphe(false);
				
				if (i == MachineInfect && j == MachineInfect){
							reseau[MachineInfect][MachineInfect].destroyMachine();
				}
			}		
		}
	
		/* Boucle qui permet d'initialiser les liens entre machine */
		for (int machine = 0; machine < taille; machine++){
			
			/* Pour chaque machine on choisit aléatoirement un nombre de lien */
			int nbr = a.nextInt(taille) + 1;
			nbr = (nbr == taille)? 1 : nbr;		//évite que le nombre soit supérieur à la limite de "taille"
			
			/* Pour chaque lien on choisit une machine aléatoirement */
			for (int i = 0; i < nbr; i++){
			
				int lienMachine = a.nextInt(taille);
				lienMachine = (lienMachine == machine)? lienMachine + 1: lienMachine;	//évite qu'une machine soit lie à elle même
				lienMachine = (lienMachine == taille)? 0 : lienMachine;		//évite que le nombre soit supérieur à la limite de "taille"
				
				/* On change la machine choisit si elle est déjà lié avec la machine en cours de configuration */
				while (reseau[machine][lienMachine].getLien() == true && i < nbr){
				
					lienMachine++;
					lienMachine = (lienMachine == taille)? 0 : lienMachine;  
					
					if (lienMachine == machine){
						lienMachine++; 
						lienMachine = (lienMachine == taille)? 0 : lienMachine;  
					}
					else{
						i++;
					}
				}
				
				/* On utilise la méthode de la classe Graphe pour mettre les liens entre les 2 machines */
				reseau[machine][lienMachine].changeEtat(true);
				reseau[lienMachine][machine].changeEtat(true);
			}
		}
	}

	/* Permet d'appeler la méthode "copy" de la classe Graphe afin de copier tout les objets du tableau mis en argument */
	public Graphe[][] copy(Graphe[][] reseau){
		Graphe[][] newreseau = new Graphe[taille][taille];
		
		for (int i = 0; i < taille; i++){ 
			
			for (int j = 0; j < taille; j++){
				
				newreseau[i][j] = reseau[i][j].clone();
			}
		}
		
		return newreseau;
	}
	
	/* Permet de lancer une partie (l'ia est par défaut dans le rôle de l'attaquant)*/
	public void jeu(Graphe[][] reseau){
		ArrayList<ArrayList<Integer>> listeCoups = coupsReseau(reseau, false);		//Permet d'obtenir les coups possibles pour le défenseur (false)
		
		/*
		 Amélioration future implémentation de la fonction "powerset"
			System.out.println(powerset(listeCoups));
		*/

		/* Puisque le reseau est créer de façon totalement aléatoire, cette boucle permet d'obtenir une version plus jouable afin de tester les capacités de l'IA */
		while (listeCoups.size() > 13){
			reseau = new Graphe[taille][taille];
			init(reseau);
			listeCoups = coupsReseau(reseau, false);
		}	//outil de test
		

		Graphe[][] newreseau = new Graphe[taille][taille];
		ArrayList<Integer> coupIA = new ArrayList<Integer>();
		Scanner scan = new Scanner(System.in);		//Permet de récupérer les coups jouer par le joueur
		int coup;
		
		while (isEnd(reseau) == false){
			
			ArrayList<ArrayList<Integer>> coupDefence = coupsReseau(reseau, false);
			afficheinfection(reseau);
			affichereseau(coupDefence);
			
			/* ligne pour IA défence
				newreseau = copy(reseau);
				coupIA = minmax(newreseau, 10);
				coup = coupIA.get(1);
				System.out.println("L'IA a joué le coup : " + coupIA.get(1));
			*/

			coup = scan.nextInt();	//coup d'un joueur defence
			defence(coup, reseau);

			if (isEnd(reseau) == false){
	
				ArrayList<ArrayList<Integer>> coupAttaque = coupsReseau(reseau, true);
				affichereseau(coupAttaque);

				newreseau = copy(reseau);
				coupIA = minmax(newreseau, 10);
				coup = coupIA.get(1);
				System.out.println("L'IA a joué le coup : " + coupIA.get(1));
				/* ligne pour joueur attaque
					coup = scan.nextInt();
				*/
				attack(coup, reseau);
			}
		}

		System.out.println("Le joueur " + victoire(reseau) + " a gagné avec un reseau possedant : " + getValue(reseau)/2 + " liens entre machine.");
	}

	/* Permet de retirer le lien entre 2 machines */
	public void defence(int nbr, Graphe[][] reseau){
		ArrayList<ArrayList<Integer>> listeCoups = coupsReseau(reseau, false);
		int machine = nbr;
		reseau[listeCoups.get(machine).get(0)][listeCoups.get(machine).get(1)].changeEtat(false);
		reseau[listeCoups.get(machine).get(1)][listeCoups.get(machine).get(0)].changeEtat(false);
		tour = true;
		
	}

	/* Permet d'infecter une machine */
	public void attack(int nbr, Graphe[][] reseau){
		ArrayList<ArrayList<Integer>> listeCoups = coupsReseau(reseau, true);
		int machine = nbr;
		reseau[listeCoups.get(machine).get(1)][listeCoups.get(machine).get(1)].destroyMachine();
		tour = false;
	}
	
	/* Permet de lister les coups d'attaque ou de défence  */
	public ArrayList<ArrayList<Integer>> coupsReseau(Graphe[][] reseau, boolean phase){
		ArrayList<ArrayList<Integer>> mescoups = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < taille; i++){
			for (int j = 0; j < taille; j++){
				ArrayList<Integer> moncoup = new ArrayList<Integer>();
				if (reseau[i][j].getLien()){
					if (reseau[i][i].getInfection() == phase){
						if (phase == false || reseau[j][j].getInfection() != phase){
							moncoup.add(i);
							moncoup.add(j);
							mescoups.add(moncoup);
						}
					}
				}
			}
		}
		return mescoups;
	}

	/* Méthode powerset à améliorer
	public static <T> List<List<T>> powerset(Collection<T> list){
		List<List<T>> ps = new ArrayList<List<T>>();
		ps.add(new ArrayList<T>());

		for (T item : list){
			List<List<T>> newPs = new ArrayList<List<T>>();

			for (List<T> subset : ps){
				newPs.add(subset);
				
				List<T> newSubset = new ArrayList<T>(subset);
				newSubset.add(item);
				newPs.add(newSubset);

			}
			ps = newPs;
		}
		return ps;
	}
	*/

	/* Permet d'afficher les liens entre machine */
	public void affichereseau(ArrayList<ArrayList<Integer>> CoupsPossible){
		System.out.println("Machine liée :");
		
		for (int i = 0; i < CoupsPossible.size(); i++){
			System.out.println(i + ". " + CoupsPossible.get(i).get(0) + " -> " + CoupsPossible.get(i).get(1));
		}
	}

	/* Permet d'afficher les machines infecté */
	public void afficheinfection(Graphe[][] reseau){
		System.out.println("Machine infectée :");
		
		for (int i = 0; i < taille; i++){
			if (reseau[i][i].getInfection()){
				System.out.println(i + ";");
			}
		}
	}

	/* Permet de détecter la fin de partie */
	public boolean isEnd(Graphe[][] reseau){
		ArrayList<ArrayList<Integer>> CoupsDefence = coupsReseau(reseau, false);
		ArrayList<ArrayList<Integer>> CoupsAttack = coupsReseau(reseau, true);
		if (CoupsAttack.size() == 0 || CoupsDefence.size() == 0){
			return true;
		}
		
		for (int i = 0; i < taille; i++){
			if (!reseau[i][i].getInfection()){
				return false;
			}
		}
		return true;
	}

	/* Permet de désigne un vainceur */
	public String victoire(Graphe[][] reseau){
		
		for (int i = 0; i < taille; i++){
			if (!reseau[i][i].getInfection()){
				return "Defence";	
			}
		}
		return "Attaque";	
	}

	/* Permet d'évaluer le reseau pour que l'IA decide quelle coup jouer */
	public Integer getValue(Graphe[][] reseau){
		Integer compteur = 0;
		for (int i = 0; i < taille; i++){
			for (int j = 0; j < taille; j++){
				if (reseau[i][j].getLien() == true){
					if (!reseau[i][i].getInfection() && !reseau[j][j].getInfection()){
						compteur++;
					}
				}
			}
		}
		return compteur;
	}

	/* IA  donné en cours 
	 	On utilise une liste pour retourner 2 valeurs	
	 */
	public ArrayList<Integer> minmax(Graphe[][] myreseau , int d){
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		if (d == 0 || isEnd(myreseau)){
			list1.add(getValue(myreseau));
			list1.add(null);
			return list1;
		}
		if (tour == false){
			list1.add(-100000);
			list1.add(null);
			ArrayList<ArrayList<Integer>> listeCoups = coupsReseau(myreseau, false);
			for (int i = 0; i < listeCoups.size(); i++){
				Graphe[][] s = copy(myreseau);
				defence(i, s);
				ArrayList<Integer> list2 = new ArrayList<Integer>(); 
				list2 = minmax(s, d-1);
				if (list1.get(0) < list2.get(0)){
					list1.set(0, list2.get(0));
					list1.set(1, i);
				}
			}
			return list1;
		}else{
			list1.add(100000);
			list1.add(null);
			ArrayList<ArrayList<Integer>> listeCoups = coupsReseau(myreseau, true);
			for (int i = 0; i < listeCoups.size(); i++){
				Graphe[][] s = copy(myreseau);
				attack(i, s);
				ArrayList<Integer> list2 = minmax(s, d-1);
				if (list1.get(0) > list2.get(0)){
					list1.set(0, list2.get(0));
					list1.set(1, i);
				}
			}
			return list1;
		}
	}

	public static void main(String[] args)throws Exception{
		int taille = 6;
		Partie match = new Partie(taille);
		match.init(match.reseau);
		match.jeu(match.reseau);
	}
}
