
COMPILATION
===========

	make (tout simplement)

Il ne s'agit pas à proprement parler d'une compilation, mais de la fabrication
de fichiers .pro autonomes dans un répertoire "exe" à partir des sources du
répertoire "src". Cela évite de devoir modifier les répertoires de recherche
d'IDL.

2 procédures IDL sont proposées:

- hdfdump : procédure qui permet d'obtenir des informations sur un fichier HDF
            et ses SDS (Scientific DataSets). Fonctionnement assez similaire à
            la commande hdp, l'interactivité en plus.

- gethdf  : fonction permettant d'extraire un SDS d'un fichier HDF

