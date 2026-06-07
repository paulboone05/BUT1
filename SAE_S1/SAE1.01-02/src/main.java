import extensions.File;
import extensions.CSVFile;

class main extends Program {

    final String QUESTION_FACILES_CHEMIN = "../ressources/csv/questions_facile.csv";
    final Question[] QUESTIONS_FACILES = new Question [100];
    int indice_question_faciles = 0;

    final String QUESTION_INTERMEDIAIRES_CHEMIN = "../ressources/csv/questions_intermediaire.csv";
    final Question[] QUESTIONS_INTERMEDIAIRES = new Question [100];
    int indice_question_intermediaires = 0;

    final String QUESTION_DIFFICILES_CHEMIN = "../ressources/csv/questions_difficile.csv";
    final Question[] QUESTIONS_DIFFICILES = new Question [100];
    int indice_question_difficles = 0;

    Joueur joueur1 = newJoueur("");
    Joueur joueur2 = newJoueur("");

    //algorithme principal
    void algorithm () {    

        clearScreen(); //clear le terminal
        initialiser_questions_CSV(); //initialise les questions 
        intro(); //règle du jeu
        
        //début du jeu
        //tant qu'un joueur n'a pas atteint 10 points
        while (joueur1.score < 10 && joueur2.score < 10) {
            affiche_par_caractere("King James : Le score est [" + joueur1.nom + " " + joueur1.score + " - " + joueur2.score + " " + joueur2.nom + "]"); //affichage du score
            println();
            tour(joueur1); //tour du joueur 1
            affiche_par_caractere("King James : Le score est [" + joueur1.nom + " " + joueur1.score + " - " + joueur2.score + " " + joueur2.nom + "]"); //affichage du score
            println();
            tour(joueur2); //tour du joueur 2
        }

        //fin du jeu
        //si joueur 1 a gagné
        if (joueur1.score > joueur2.score) {
            affiche_par_caractere("King James : Félicitations, " + joueur1.nom + " est le nouveau King of the Quizz !" + "\n");
            affiche_dessin("../ressources/txt/tatum.txt");
        }

        //si joueur 2 a gagné
        if (joueur2.score > joueur1.score) {
            affiche_par_caractere("King James : Félicitations, " + joueur2.nom + " est le nouveau King of the Quizz !" + "\n");
            affiche_dessin("../ressources/txt/curry.txt");
        }

        //s'il y a égalité
        if (joueur1.score == joueur2.score) {
            affiche_par_caractere("King james : " + joueur1.nom + " et " + joueur2.nom + ", vous êtes à égalité pour cette partie, je reste le King of the Quizz !" + "\n");
            affiche_dessin("../ressources/txt/james.txt");            
        }
    }

    //crée le terrain en lisant le fichier terrain.txt
    char [][] initialiser (){

        File file = newFile("../ressources/txt/terrain.txt");
        char [][] tab = new char[15][40];
        int lineNo = 0;

        while (ready(file)) {
            String line = readLine(file);
            for (int id = 0; id < 40; id++) {
                tab[lineNo][id] = charAt(line, id);
            }
            lineNo++;
        }
        return tab;
    }

    //affiche le terrain
    void afficher_Tab(char[][] tab){

        for(int idl = 0; idl < length(tab,1); idl++){
            for(int idc = 0; idc < length(tab,2); idc++){
                print(tab[idl][idc]);
            }
            println();
        }
    }

    //donne un nombre aléatoire de déplacement entre 6 et 17
    int accorder_deplacement() {

        double random = random();
        random = random * 18;

        if (random<6) {
            random = random + 6;
        }
        return (int)random;
    }

    //donne un nombre aléatoire entre deux entiers
    int nombre_aleatoire(int min, int max) {

        return (int) (random()*(max-min)+min);
    }

    //teste la fonction accorder_deplacement
    void test_accorder_deplacement () {

        for (int i=0; i<50; i++) {
            assertTrue((accorder_deplacement()>=6 && accorder_deplacement()<=20));
        }
    }

    //crée un joueur
    Joueur newJoueur(String nom) {

        Joueur j = new Joueur();
        j.nom = nom;
        j.score = 0;
        return j;
    }

    //teste la fonction newJoueur
    void test_newJoueur() {

        Joueur joueur_test = newJoueur("Paul");
        Joueur joueur_test_2 = new Joueur();
        joueur_test_2.nom = "Paul";
        joueur_test_2.score = 0;
        assertEquals(joueur_test.nom, joueur_test_2.nom);
        assertEquals(joueur_test.score, joueur_test_2.score);
    }

    //affiche les chaines avec des pauses de 15ms entre chaque caractère
    void affiche_par_caractere(String chaine) {

        for (int i=0; i<length(chaine); i++) {
            print(charAt(chaine,i));
            sleep(15);
        }
        println();
    }

    //affiche "l'introduction" du jeu et demande les noms des joueurs
    void intro() {

        println();
        affiche_par_caractere("==================");
        affiche_par_caractere("King of the Quizz");
        affiche_par_caractere("==================" + "\n");
        affiche_dessin("../ressources/txt/trophee.txt");
        println();
        affiche_par_caractere("Entrez prénom joueur 1 : ");
        joueur1.nom = readString();
        println();
        affiche_par_caractere("Entrez prénom joueur 2 : ");
        joueur2.nom = readString();
        println();
        affiche_par_caractere("King James : Voilà donc nos deux basketteurs " + joueur1.nom + " et " + joueur2.nom + " !");
        affiche_par_caractere("King James : Chacun votre tour, sur un demi-terrain,");
        affiche_par_caractere("King James : Vous allez recevoir un nombre de déplacements aléatoire entre 6 et 17.");
        affiche_par_caractere("King James : Ces déplacements vous permettront de vous positionner sur des zones de shoot.");
        affiche_par_caractere("King James : Une position à 3pts sera une question difficile à répondre.");
        affiche_par_caractere("King James : Une position à 2pts sera une question intermédiaire à répondre.");
        affiche_par_caractere("King James : Une position à 1pt sera une question facile à répondre.");
        affiche_par_caractere("King James : Attention ! Vous ne pouvez pas shooter depuis une ligne blanche, un trait de déplacement ou le panier,");
        affiche_par_caractere("King James : Sinon le tour passe à l'adversaire.");
        affiche_par_caractere("King James : Que le premier à 10pts ou plus l'emporte !");
        affiche_par_caractere("King James : Le joueur 2 bénificie du droit de réponse :");
        affiche_par_caractere("King James : Si le joueur 1 atteint 10pts, le joueur 2 joue tout de même une dernière fois.");
        println();
        affiche_dessin("../ressources/txt/ballon.txt");
        println();
    }

    //éxecute un tour
    void tour(Joueur joueur) {

        int deplacement = accorder_deplacement();
        char[][] terrain = initialiser();
        affiche_par_caractere("King James : Au tour de " + joueur.nom + " !");
        affiche_par_caractere("King James : Tu as obtenu " + deplacement + " déplacements." + "\n");
        println();
        int shoot = 2;

        //tant qu'il reste des déplacements ou que le joueur ne shoot pas
        while (deplacement > 0 && shoot == 2) {

            afficher_Tab(terrain);
            println();
            affiche_par_caractere("Vos déplacement(s) restant(s) : " + deplacement);
            println();
            int direction;
            boolean condition_direction = false;

            //demande au joueur la direction du déplacement
            do {
                affiche_par_caractere("Où souhaitez-vous vous déplacer (gauche[1], droite[2], haut[3], bas[4]) : ");
                direction = readInt();
                println();
                if (direction != 1 && direction != 2 && direction != 3 && direction != 4) {
                    affiche_par_caractere("King James : Attention à bien répondre une valeur entre 1 et 4.");
                } else {
                    condition_direction = true;
                }
            } while (!condition_direction);

            //récupère la position actuelle du joueur
            int[] position = position_actuelle(terrain);
            int idxL = position[0];
            int idxC = position[1];

            //calcule le nombre de déplacements possibles dans chaque direction
            int maxDeplacements = 0;
            boolean directionPossible = false;
            int casesDeplacement = 0; 

            //vérification des déplacements possibles en fonction de la direction
            do {
                //demande combien de cases il veut déplacer
                affiche_par_caractere("De combien de cases souhaitez-vous vous déplacer ? :");
                casesDeplacement = readInt();
                println();

                //vérifie le nombre de déplacements possibles selon la direction
                if (direction == 1) {  
                    maxDeplacements = idxC - 2;  
                } else if (direction == 2) {  
                    maxDeplacements = length(terrain[0]) - idxC - 3;  
                } else if (direction == 3) {  
                    maxDeplacements = idxL - 1;  
                } else if (direction == 4) {  
                    maxDeplacements = length(terrain) - idxL - 2; 
                }

                //vérifie si le nombre de cases ne dépasse pas les limites
                if (casesDeplacement <= deplacement && casesDeplacement <= maxDeplacements && casesDeplacement > 0) {
                    directionPossible = true;
                    deplacement -= casesDeplacement;  
                } else {
                    affiche_par_caractere("King James : Attention ! Vous ne pouvez pas dépasser les limites du terrain ou le nombre de déplacements restants.");
                }

            } while (!directionPossible);

            //déplace le joueur du nombre de cases choisi
            for (int i = 0; i < casesDeplacement; i++) {
                terrain = maj_terrain(terrain, direction, shoot);
                afficher_Tab(terrain);
                println();
                sleep(100);
            }

            //demande si le joueur veut shooter
            boolean condition_shoot = false;
            
            do {
                affiche_par_caractere("Souhaitez-vous shooter ? (oui[1], non[2]) : ");
                shoot = readInt();
                if (shoot != 1 && shoot != 2) {
                    affiche_par_caractere("King James : Attention à bien répondre une valeur entre 1 et 2.");
                } else {
                    condition_shoot = true;
                }
            } while (!condition_shoot);
            println();

        }

        //si le joueur shoot
        if (shoot == 1) {

            int[] position = position_actuelle(terrain);
            int idxL = position[0];
            int idxC = position[1];

            //si le joueur shoot dans une zone où on ne peut pas shooter
            if (terrain[idxL][idxC-1] == '█' || terrain[idxL][idxC-1] == 'O' || terrain[idxL][idxC-1] == '-') {
                affiche_par_caractere("King James : Attention à bien lire les règles du jeu, on ne peut pas tirer depuis le panier, une ligne du terrain ou une trace de déplacement !");
                affiche_par_caractere("King James : Vous ne marquez pas de point pour ce tour.");
            }
            
            //si le joueur shoot dans la zone à 1pt
            if (terrain[idxL][idxC-1] == ' ') {

                int i = nombre_aleatoire(0, rowCount(loadCSV(QUESTION_FACILES_CHEMIN)));
                String question = QUESTIONS_FACILES[i].question;
                String reponse1 = QUESTIONS_FACILES[i].reponses[0];
                String reponse2 = QUESTIONS_FACILES[i].reponses[1];
                String reponse3 = QUESTIONS_FACILES[i].reponses[2];
                String reponse4 = QUESTIONS_FACILES[i].reponses[3];

                String[] reponses = new String[]{reponse1, reponse2, reponse3, reponse4};
        
                for (int j = 0; j < 4; j++) {
                    int k = nombre_aleatoire(0, 4);
                    String temp = reponses[j];
                    reponses[j] = reponses[k];
                    reponses[k] = temp;
                }

                //affichage de la question et des réponses mélangées
                affiche_par_caractere(question);
                println(reponses[0] + "[1]");
                println(reponses[1] + "[2]");
                println(reponses[2] + "[3]");
                println(reponses[3] + "[4]");
                println();

                int reponse;
                boolean condition_reponse = false;

                do {
                    affiche_par_caractere("Votre réponse : ");
                    reponse = readInt();
                    println();
                    if (reponse >= 1 && reponse <= 4) {
                        condition_reponse = true;
                    } else {
                        affiche_par_caractere("King James : Attention à bien répondre une valeur entre 1 et 4.");
                    }
                } while (!condition_reponse);

                //trouver l'indice de la bonne réponse
                int bonneReponse = -1;

                for (int j = 0; j < 4; j++) {
                    if (reponses[j] == reponse1) {
                        bonneReponse = j + 1; 
                    }
                }

                if (reponse == bonneReponse) {
                    affiche_par_caractere("King James : Bien joué(e) ! Vous marquez 1pt.");
                    println();
                    joueur.score += 1;
                    affiche_dessin("../ressources/txt/facile.txt");
                    println();
                } else {
                    affiche_par_caractere("King James : Mauvaise réponse ! La bonne réponse était : " + reponse1 + ". Vous ne marquez pas de points.");
                    println();
                }
            }

            //si le joueur shoot dans la zone à 2pts
            if (terrain[idxL][idxC-1] == '░') {

                int i = nombre_aleatoire(0, rowCount(loadCSV(QUESTION_INTERMEDIAIRES_CHEMIN)));
                String question = QUESTIONS_INTERMEDIAIRES[i].question;
                String reponse1 = QUESTIONS_INTERMEDIAIRES[i].reponses[0];
                String reponse2 = QUESTIONS_INTERMEDIAIRES[i].reponses[1];
                String reponse3 = QUESTIONS_INTERMEDIAIRES[i].reponses[2];
                String reponse4 = QUESTIONS_INTERMEDIAIRES[i].reponses[3];

                String[] reponses = new String[]{reponse1, reponse2, reponse3, reponse4};
            
                for (int j = 0; j < 4; j++) {
                    int k = nombre_aleatoire(0, 4);
                    String temp = reponses[j];
                    reponses[j] = reponses[k];
                    reponses[k] = temp;
                }

                //affichage de la question et des réponses mélangées
                affiche_par_caractere(question);
                println(reponses[0] + "[1]");
                println(reponses[1] + "[2]");
                println(reponses[2] + "[3]");
                println(reponses[3] + "[4]");
                println();

                int reponse;
                boolean condition_reponse = false;

                do {
                    affiche_par_caractere("Votre réponse : ");
                    reponse = readInt();
                    println();
                    if (reponse >= 1 && reponse <= 4) {
                        condition_reponse = true;
                    } else {
                        affiche_par_caractere("King James : Attention à bien répondre une valeur entre 1 et 4.");
                    }
                } while (!condition_reponse);

                //trouver l'indice de la bonne réponse
                int bonneReponse = -1;

                for (int j = 0; j < 4; j++) {
                    if (reponses[j] == reponse1) {
                        bonneReponse = j + 1; 
                    }
                }

                if (reponse == bonneReponse) {
                    affiche_par_caractere("King James : Bien joué(e) ! Vous marquez 2pts.");
                    println();
                    joueur.score += 2;
                    affiche_dessin("../ressources/txt/intermediaire.txt");
                    println();
                } else {
                    affiche_par_caractere("King James : Mauvaise réponse ! La bonne réponse était : " + reponse1 + ". Vous ne marquez pas de points.");
                    println();
                }
            }

            //si le joueur shoot dans la zone à 3pts
            if (terrain[idxL][idxC-1] == '▒') {

                int i = nombre_aleatoire(0, rowCount(loadCSV(QUESTION_DIFFICILES_CHEMIN)));
                String question = QUESTIONS_DIFFICILES[i].question;
                String reponse1 = QUESTIONS_DIFFICILES[i].reponses[0];
                String reponse2 = QUESTIONS_DIFFICILES[i].reponses[1];
                String reponse3 = QUESTIONS_DIFFICILES[i].reponses[2];
                String reponse4 = QUESTIONS_DIFFICILES[i].reponses[3];

                String[] reponses = new String[]{reponse1, reponse2, reponse3, reponse4};
            
                for (int j = 0; j < 4; j++) {
                    int k = nombre_aleatoire(0, 4);
                    String temp = reponses[j];
                    reponses[j] = reponses[k];
                    reponses[k] = temp;
                }

                //affichage de la question et des réponses mélangées
                affiche_par_caractere(question);
                println(reponses[0] + "[1]");
                println(reponses[1] + "[2]");
                println(reponses[2] + "[3]");
                println(reponses[3] + "[4]");
                println();

                int reponse;
                boolean condition_reponse = false;

                do {
                    affiche_par_caractere("Votre réponse : ");
                    reponse = readInt();
                    println();
                    if (reponse >= 1 && reponse <= 4) {
                        condition_reponse = true;
                    } else {
                        affiche_par_caractere("King James : Attention à bien répondre une valeur entre 1 et 4.");
                    }
                } while (!condition_reponse);

                //trouver l'indice de la bonne réponse
                int bonneReponse = -1;

                for (int j = 0; j < 4; j++) {
                    if (reponses[j] == reponse1) {
                        bonneReponse = j + 1; 
                    }
                }

                if (reponse == bonneReponse) {
                    affiche_par_caractere("King James : Bien joué(e) ! Vous marquez 3pts.");
                    println();
                    joueur.score += 3;
                    affiche_dessin("../ressources/txt/difficile.txt");
                    println();
                } else {
                    affiche_par_caractere("King James : Mauvaise réponse ! La bonne réponse était : " + reponse1 + ". Vous ne marquez pas de points.");
                    println();
                }
            }
        }
    }

    //renvoie la position actuelle du joueur
    int[] position_actuelle(char[][] terrain) {

        for (int idxL = 0; idxL < length(terrain, 1); idxL++) {
            for (int idxC = 0; idxC < length(terrain, 2); idxC++) {
                if (terrain[idxL][idxC] == '■') {
                    return new int[]{idxL, idxC};
                }
            }
        }
        return new int[]{0,0};
    }

    //teste la fonction position_actuelle
    void test_position_actuelle() {

        char[][] terrain = new char[][]{
                                        {'a','a','a'},
                                        {'a','■','a'},
                                        {'a','a','a'}
                                       };
        assertTrue((position_actuelle(terrain) == new int [] {1,1}));
    }

    //affiche le terrain avec le déplacement du joueur
    char[][] maj_terrain (char[][] terrain, int direction, int shoot) {

        int[] position = position_actuelle(terrain);
        int idxL = position[0]; 
        int idxC = position[1]; 

        //si on va à gauche 
        if (direction == 1) {
            terrain[idxL][idxC-2] = terrain[idxL][idxC];
            terrain[idxL][idxC-1] = '-';
            terrain[idxL][idxC] = '-';
        }

        //si on va à droite
        if (direction == 2) {
            terrain[idxL][idxC+2] = terrain[idxL][idxC];
            terrain[idxL][idxC+1] = '-';
            terrain[idxL][idxC] = '-';
        }

        //si on va en haut
        if (direction == 3) {
            terrain[idxL-1][idxC] = terrain[idxL][idxC];
            terrain[idxL][idxC] = '|';
        }

        //si on va en bas
        if (direction == 4) {
            terrain[idxL+1][idxC] = terrain[idxL][idxC];
            terrain[idxL][idxC] = '|';
        }
        return terrain;
    }   

    //crée un terrain 5x5 avec un joueur au milieu pour le test
    char[][] terrain_5_5(){

        char[][] terrain = new char[5][5];
        for (int idxL = 0; idxL < length(terrain, 1); idxL++) {
            for (int idxC = 0; idxC < length(terrain, 2); idxC++) {
                terrain[idxL][idxC] = ' '; 
            }
        }
        terrain[2][2] = '■';
        return terrain;
    }

    //teste la fonction maj_terrain
    void test_maj_terrain () {

        //si on va à gauche 
        char[][] terrain = terrain_5_5();
        char[][] t1 = maj_terrain(terrain, 1, 0);
        assertTrue(t1[2][0] == '■');  
        assertTrue(t1[2][1] == '-');  
        assertTrue(t1[2][2] == '-'); 

        //si on va à droite
        char[][] terrain2 = terrain_5_5();
        char[][] t2 = maj_terrain(terrain2, 2, 0);
        assertTrue(t2[2][4] == '■');  
        assertTrue(t2[2][3] == '-');  
        assertTrue(t2[2][2] == '-');

        //si on va en haut
        char[][] terrain3 = terrain_5_5();
        char[][] t3 = maj_terrain(terrain3, 3, 0);
        assertTrue(t3[1][2] == '■');  
        assertTrue(t3[2][2] == '|');

        //si on va en bas
        char[][] terrain4 = terrain_5_5();
        char[][] t4 = maj_terrain(terrain4, 4, 0);
        assertTrue(t4[3][2] == '■');  
        assertTrue(t4[2][2] == '|');
    }

    //crée une question
    Question newQuestion(String question, String reponse1, String reponse2, String reponse3, String reponse4){

        Question q = new Question();
        q.question = question;
        q.reponses = new String[] {reponse1, reponse2, reponse3, reponse4};
        return q;
    }

    //crée les tableaux de questions
    void initialiser_questions_CSV(){

        //tableau questions faciles
        CSVFile table_facile = loadCSV(QUESTION_FACILES_CHEMIN, ',');

        for(int i = 0; i < rowCount(table_facile); i++){
            QUESTIONS_FACILES[indice_question_faciles] = newQuestion(getCell(table_facile, i, 0),getCell(table_facile, i, 1),getCell(table_facile, i, 2),getCell(table_facile, i, 3), getCell(table_facile, i, 4));
            indice_question_faciles++;
        }

        //tableau questions intermédiaires
        CSVFile table_intermediaire = loadCSV(QUESTION_INTERMEDIAIRES_CHEMIN, ',');

        for(int i = 0; i < rowCount(table_intermediaire); i++){
            QUESTIONS_INTERMEDIAIRES[indice_question_intermediaires] = newQuestion(getCell(table_intermediaire, i, 0),getCell(table_intermediaire, i, 1),getCell(table_intermediaire, i, 2),getCell(table_intermediaire, i, 3), getCell(table_intermediaire, i, 4));
            indice_question_intermediaires++;
        }

        //tableau questions difficiles
        CSVFile table_difficile = loadCSV(QUESTION_DIFFICILES_CHEMIN, ',');

        for(int i = 0; i < rowCount(table_difficile); i++){
            QUESTIONS_DIFFICILES[indice_question_difficles] = newQuestion(getCell(table_difficile, i, 0),getCell(table_difficile, i, 1),getCell(table_difficile, i, 2),getCell(table_difficile, i, 3), getCell(table_difficile, i, 4));
            indice_question_difficles++;
        }

    }

    //faire un clear automatique
    void clearScreen() {
        print("\033c");
    }

    //affiche les dessisns 
    void affiche_dessin(String dessin) {

        File fichier = newFile(dessin);

        while(ready(fichier)) {
            println(readLine(fichier));
        }
    }
}  