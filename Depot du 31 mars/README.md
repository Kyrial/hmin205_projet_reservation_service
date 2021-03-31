Avancement du projet à la date du 31 mars 2021

Groupe:
- Malika LIN-WEE-KUAN
- Melvin BARDIN

===========

**Activités implémenté:**

-> page de connexion/inscription

-> page de recherche de services

-> page de profil (activité est vide car viens d'etre crée)


===========

**page de connexion/inscription**
	
	Fonctionnalités :
	
	-> s'inscrire
		- si l'identifiant est déjà existant dans la base, l'inscription est refusée (signalé par un toast "pseudo déjà utilisé")
		- sinon, l'inscription est réussie
			- si client, l'activité "page de recherche de services" est lancée.
			- si fournisseur (non implémenté) la page d'acceuil pour les fournisseurs devrait etre lancé.
	
	-> se connecter
		- si mot de passe ou identifiant erroné, connexion refusée (signalé par un toast "pseudo ou mot de passe erroné")
		- si la database contient une row avec l'identifiant et le mot de passe, la connexion est réussie
			- si client, l'activité "page de recherche de services" est lancée.
			- si fournisseur (non implémenté) la page d'acceuil pour les fournisseurs devrait etre lancée.
	
	-> acceder à la recherche de services en "mode invité"
			
**page de recherche de services**

	Fonctionnalités :
	
	-> faire défiler les services affichés (vu que la partie fournisseur n'a pas encore été implémenté, les services affiché sont des lignes de test inséré dans la base préalablement)
	
	-> faire une recherche de service (Attention, la recherche est pour le moment sensible à la casse)
	
	-> bouton en haut à droite
		- si utilisateur non connecté (utilisateur invité),  bouton "se connecter" ramène à l'activité connexion
		- si utilisateur connecté, bouton "profil", amène à l'activité profil
		
**page Profil**

	-> page vide,
	-> bouton retour opérationnel
	
	
	
	