Avancement du projet à la date du 30 avril 2021

Groupe:
- Malika LIN-WEE-KUAN
- Melvin BARDIN

===========

**tache implémenté:**

-> Acceuil pour les fournisseur

-> page d'ajout / edition / suppression de service

-> tiroir de navigation (navigation drawer) pour le menu

==============

**Tache réalisé:**

-> Restructuration du code 
	
	-> Des activités:
		- création d'une classe abstraite dont toutes les activités héritent, 
		contiens les fonctions utilisées par chaque activité (notamment pour le tiroir de navigation).
		afin d'évité la recopie de code.
	
	-> Des ViewModel:
		-> création d'une classe Absraite dont toutes les viewModels héritent
			- Init avec la BBD.
			- Init des liveData présent dans chaque ViewModel.
			cela, pour éviter la recopie de code;
		
	-> DU XML:
		- Tout bloc présent dans plusieurs XML a été mis dans un fichier à part 
		pour eviter la recopie de code et alléger les XML des activités.
		- Réalisé notamment pour la Top_bar, navigation Drawer etc..
		- Les gros avantage a cela:
			Prenons exemple sur le bouton pour afficher la navigation Drawer:
				- Si nous avions dupliquer ce code dans chaque activité, il aurait fallu :
					- Avoir un ID différent dans chaque activité du bouton,
					- Adapter a chaque fois le code dans l'activity lors d'un findViewById
				- Isoler ce code dans un fichier à part, cela permet:
					- chaque XML inclu le fichier, donc pour chaque XML notre bouton à la meme ID.
					- le code dans le code n'est pas à adapté (findViewById toujours avec le meme ID)
						- ainsi, cela permet de facilement extraire le code de l'activity dans une classe abstraite,
						évitant le plus possible du code inutile.
						
-> Les Services

	-> Possibilité de créer, modifié, supprimer un service.
	
	-> les services créés apparaisse dans la zone de recherche

-> Le menu (tiroir de navigation)
	-> Le code est entièrement dans l'activity abstraite MainLayoutMenu.
	-> Les activités avec un tiroir de navigation héritent de MainLayoutMenu
	



