package net.ubung.tictactoe_alpha_betapruning;

public class MiniMax {

    private  Spieler player = new Spieler(1, "X");
    private  final String COMP = "O";
    public static int counter = 0;


    // überprüft ob noch freie Felder vorhanden sind -- noch weitere steps notwendig sind
   private  boolean isFieldLeft(String[][] field) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (field[i][j].equals(""))
                    return true;
        return false;
    }

    //berechnet ob minimax oder gegner schon gewonnen hat
    private int checkWinner(String[][] field) {

        //checking row
        for (int row = 0; row < 3; row++) {
            if (field[row][0].equals(field[row][1]) && field[row][1].equals(field[row][2])) {
                if (field[row][0].equals(player.getPref())) {
                    return -1;
                } else if (field[row][0].equals(COMP))
                    return 1;
            }
        }

        //checking column
        for (int col = 0; col < 3; col++) {
            if (field[0][col].equals(field[1][col]) && field[1][col].equals(field[2][col])) {
                if (field[0][col].equals(player.getPref())) {
                    return -1;
                } else if (field[0][col].equals(COMP)) {
                    return 1;
                }
            }
        }

        //checking for Diagonals
        if (field[0][0].equals(field[1][1]) && field[1][1].equals(field[2][2])) {
            if (field[0][0].equals(player.getPref())){
                return -1;}
            else if (field[0][0].equals(COMP)){
                return 1;}
        }

        if (field[0][2].equals(field[1][1]) && field[1][1].equals(field[2][0])) {
            if (field[0][2].equals(player.getPref())){
                return -1;}
            else if (field[0][2].equals(COMP)){
                return 1;}
        }


        return 0;

    }
    
    private int alphaBeta(String[][] field,  boolean isMax, int alpha, int beta){

        int score = checkWinner(field);//wie bei minimax
        if (score == 1){
            return score;}
        else if (score == -1){
            return score;}
        else if (!isFieldLeft(field)){
            return 0;}

        if (isMax) {
            int best =alpha;
            aussen:
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (field[i][j].equals("")) {
                        field[i][j] = COMP;//eigenes feld setzen  und best mögliches ergebnis speichern
                        best = Math.max(best, alphaBeta(field,  false, best, beta));
                        MiniMax.counter++;
                        field[i][j] = "";// Feld zurücksetzen
                        if(best >= beta){
                            break aussen;
                        }
                    }
                }
            }
            return best;
        } else {//wen min dran ist
            int best = beta;
            aussen:
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (field[i][j].equals("")) {
                        field[i][j] = player.getPref(); //feld des gegners setzen und schlecht mögliches ergebnis speichern
                        best = Math.min(best, alphaBeta(field,  true, alpha, best));
                        MiniMax.counter++;
                        field[i][j] = "";//Feld wieder zurücksetzen
                        if (best <= alpha){
                            break aussen;
                    }
                    }
                }
            }
            return best;
        }
    }


    //überprüft alle möglichen Wege, wie das Spiel enden kann
    private int minimax(String[][] field, boolean isMax) {

        int score = checkWinner(field);
        if (score == 1){
            return score;}
        else if (score == -1){
            return score;}
        else if (!isFieldLeft(field)){
            return 0;}


        //wenn max dran ist
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (field[i][j].equals("")) {
                        field[i][j] = COMP;//eigenes feld setzen  und best mögliches ergebnis speichern
                        best = Math.max(best, minimax(field,  !isMax));
                        MiniMax.counter++;
                        field[i][j] = "";// Feld zurücksetzen
                    }
                }
            }
            return best;
        } else {//wen min dran ist
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (field[i][j].equals("")) {
                        field[i][j] = player.getPref(); //feld des gegners setzen und schlecht mögliches ergebnis speichern
                        best = Math.min(best, minimax(field,  !isMax));
                        MiniMax.counter++;
                        field[i][j] = "";//Feld wieder zurücksetzen
                    }
                }
            }
            return best;
        }
    }


    public  Move findBestMove(String[][] field){
       MiniMax.counter = 0;
        int bestVal = Integer.MIN_VALUE;
        Move bestMove = new Move();
        bestMove.setCol(-1);
        bestMove.setRow(-1);


        for (int i = 0; i < 3; i++) {//sucht mit der minimax-Methode den besten Zug aus und gibt ihn aus
            for (int j = 0; j < 3; j++) {
                if (field[i][j].equals("")){
                    field[i][j]=COMP;
                   // int moveVal = minimax(field, false);
                    int moveVal = alphaBeta(field,false,Integer.MIN_VALUE,Integer.MAX_VALUE);


                    field[i][j] = "";
                    if(moveVal > bestVal){
                        bestMove.setRow(i);
                        bestMove.setCol(j);
                        bestVal = moveVal;
                    }
                }

            }

        }
        System.out.println(MiniMax.counter);
        return bestMove;

    }

}
